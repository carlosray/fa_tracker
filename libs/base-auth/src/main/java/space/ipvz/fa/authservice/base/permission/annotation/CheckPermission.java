package space.ipvz.fa.authservice.base.permission.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import space.ipvz.fa.authservice.base.permission.model.Resource;
import space.ipvz.fa.authservice.base.permission.model.Role;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CheckPermission {
    Resource resource();

    Role role();

    /**
     * expression to obtain groupId
     */
    String groupId() default "";
}
