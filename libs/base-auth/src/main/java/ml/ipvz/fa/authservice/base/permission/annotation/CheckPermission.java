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
     * must be specified if {@link #groupIdFieldName is not set}
     */
    long groupId() default -1;

    /**
     * must be specified if {@link #groupId is not set}
     */
    String groupIdFieldName() default "";
}
