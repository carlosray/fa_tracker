package space.ipvz.fa.authservice.base.permission.aspect;

import java.lang.reflect.Method;
import java.util.Optional;

import space.ipvz.fa.authservice.base.exception.AccessForbiddenException;
import space.ipvz.fa.authservice.base.permission.Permission;
import space.ipvz.fa.authservice.base.permission.annotation.CheckPermission;
import space.ipvz.fa.authservice.base.service.PermissionChecker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Aspect
public class PermissionAspect {
    private static final Logger log = LoggerFactory.getLogger(PermissionAspect.class);

    private final PermissionChecker checker;
    private final ExpressionParser parser = new SpelExpressionParser();

    public PermissionAspect(PermissionChecker checker) {
        this.checker = checker;
    }

    @Pointcut("@annotation(checkPermission)")
    public void callAt(CheckPermission checkPermission) {
    }

    @Around(value = "callAt(annotation)", argNames = "pjp,annotation")
    public Object around(ProceedingJoinPoint pjp, CheckPermission annotation) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Class<?> returnType = method.getReturnType();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = pjp.getArgs();

        Assert.state(
                Publisher.class.isAssignableFrom(returnType),
                () -> "The returnType " + returnType + " on " + method + " must return an instance of " +
                        "org.reactivestreams.Publisher (i.e. Mono / Flux)"
        );

        Long groupId = getGroupId(parameterNames, args, annotation);

        Permission permission = Permission.builder(groupId)
                .resource(annotation.resource())
                .role(annotation.role());

        Mono<Boolean> afterCheck = checker.check(permission).filter(b -> b)
                .switchIfEmpty(Mono.error(() -> new AccessForbiddenException(permission)));

        if (Mono.class.isAssignableFrom(returnType)) {
            return afterCheck.flatMap(__ -> this.proceed(pjp));
        } else if (Flux.class.isAssignableFrom(returnType)) {
            return afterCheck.flatMapMany(__ -> this.proceed(pjp));
        } else {
            return afterCheck.flatMapMany(__ -> Flux.from(this.proceed(pjp)));
        }
    }

    private <T extends Publisher<?>> T proceed(final ProceedingJoinPoint pjp) {
        try {
            return (T) pjp.proceed();
        } catch (Throwable e) {
            throw Exceptions.propagate(e);
        }
    }

    private Long getGroupId(String[] parameterNames, Object[] args, CheckPermission annotation) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        Object groupId = parser.parseExpression(annotation.groupId()).getValue(context);

        return parseGroupId(groupId).orElse(-1L);
    }

    private Optional<Long> parseGroupId(@Nullable Object arg) {
        String id = arg == null || arg instanceof String ? (String) arg : arg.toString();
        try {
            return Optional.ofNullable(id).map(Long::parseLong);
        } catch (NumberFormatException e) {
            log.warn("Provided id is not parsable: {}", id);
            return Optional.empty();
        }
    }

}
