package com.jskno.managinglistsbe.security.persistence;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserValidator implements ConstraintValidator<UserConstraint, User> {

    @Override
    public void initialize(UserConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
