package ml.ipvz.fa.authservice.base.permission.aspect;

import java.lang.reflect.Method;

import ml.ipvz.fa.authservice.base.exception.AccessForbiddenException;
import ml.ipvz.fa.authservice.base.permission.Permission;
import ml.ipvz.fa.authservice.base.permission.annotation.CheckPermission;
import ml.ipvz.fa.authservice.base.service.PermissionChecker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.reactivestreams.Publisher;
import org.springframework.util.Assert;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

        String groupId = getGroupId(parameterNames, args, annotation);

        Permission permission = Permission.builder(groupId)
                .resource(annotation.resource())
                .role(annotation.role());

        Mono<Boolean> afterCheck = checker.check(permission).filter(b -> b)
                .switchIfEmpty(Mono.error(() -> accessForbiddenException(permission)));

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

    private String getGroupId(String[] parameterNames, Object[] args, CheckPermission annotation) {
        if (annotation.groupId().isBlank() && annotation.groupIdFieldName().isBlank()) {
            throw new IllegalArgumentException("One of the #groupId or #groupIdFieldName must be specified");
        }

        return annotation.groupId().isBlank() ?
                findGroupIdFromField(parameterNames, args, annotation.groupIdFieldName()) :
                annotation.groupId();
    }

    private String findGroupIdFromField(String[] parameterNames, Object[] args, String fieldName) {
        String groupId = null;
        for (int i = 0; i < parameterNames.length; i++) {
            if (fieldName.equalsIgnoreCase(parameterNames[i])) {
                Object arg = args[i];
                groupId = arg == null || arg instanceof String ? (String) arg : arg.toString();
                break;
            }
        }
        return groupId;
    }

    private Exception accessForbiddenException(Permission permission) {
        return new AccessForbiddenException("Access denied. Required role %s for resource %s in group %s"
                .formatted(permission.role, permission.resource, permission.groupId));
    }

}
