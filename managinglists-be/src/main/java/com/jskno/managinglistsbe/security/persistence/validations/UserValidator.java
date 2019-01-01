package com.jskno.managinglistsbe.security.persistence.validations;

import com.jskno.managinglistsbe.security.persistence.User;
import com.jskno.managinglistsbe.security.persistence.validations.UserConstraint;

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
