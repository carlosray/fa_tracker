package ml.ipvz.fa.authservice.base.permission.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ml.ipvz.fa.authservice.base.permission.model.Resource;
import ml.ipvz.fa.authservice.base.permission.model.Role;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CheckPermission {
    Resource resource();

    Role role();

    /**
     * must be specified if you use {@link Target == ID} and {@link #targetIdFieldName is not set}
     */
    String targetId() default "";

    /**
     * must be specified if you use {@link Target == ID} and {@link #targetId is not set}
     */
    String targetIdFieldName() default "";
}
