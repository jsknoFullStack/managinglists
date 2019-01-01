package com.jskno.managinglistsbe.security.persistence.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UserValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserConstraint {

    String message() default "confirmPassword->Passwords must match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
