package ml.ipvz.fa.authservice.base.permission.aspect;

import java.lang.reflect.Method;

import ml.ipvz.fa.authservice.base.permission.Permission;
import ml.ipvz.fa.authservice.base.permission.annotation.CheckPermission;
import ml.ipvz.fa.authservice.base.permission.model.Target;
import ml.ipvz.fa.authservice.base.service.PermissionChecker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static ml.ipvz.fa.authservice.base.permission.model.Target.ALL;
import static ml.ipvz.fa.authservice.base.permission.model.Target.ID;

@Aspect
public class PermissionAspect {
    private final PermissionChecker checker;

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

        String targetId = getTargetId(parameterNames, args, annotation);

        Permission permission = Permission.builder()
                .resource(annotation.resource(), targetId)
                .role(annotation.role());

        Mono<Boolean> afterCheck = checker.check(permission).filter(b -> b)
                .switchIfEmpty(Mono.error(() -> accessDeniedException(permission)));

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

    private String getTargetId(String[] parameterNames, Object[] args, CheckPermission annotation) {
        Target target = annotation.targetId().isBlank() && annotation.targetIdFieldName().isBlank() ? ALL : ID;
        return switch (target) {
            case ALL -> null;
            case ID -> annotation.targetId().isBlank() ?
                    findTargetIdFromField(parameterNames, args, annotation.targetIdFieldName()) :
                    annotation.targetId();
        };
    }

    private String findTargetIdFromField(String[] parameterNames, Object[] args, String fieldName) {
        String targetId = null;
        for (int i = 0; i < parameterNames.length; i++) {
            if (fieldName.equalsIgnoreCase(parameterNames[i])) {
                Object arg = args[i];
                targetId = arg == null || arg instanceof String ? (String) arg : arg.toString();
                break;
            }
        }
        return targetId;
    }

    private Exception accessDeniedException(Permission permission) {
        String msg = "Access denied. Required role %s for resource %s and %s"
                .formatted(
                        permission.role,
                        permission.resource,
                        permission.target == ALL ? "all ids" : "id " + permission.targetId
                );
        return new ResponseStatusException(HttpStatus.FORBIDDEN, msg);
    }

}
