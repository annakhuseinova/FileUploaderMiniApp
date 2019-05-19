package com.annakhuseinova.FileUploaderApp.validation;

import com.annakhuseinova.FileUploaderApp.entities.SystemUser;
import com.annakhuseinova.FileUploaderApp.entities.User;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, SystemUser> {

    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(SystemUser user, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;

        final Object firstObject = new BeanWrapperImpl(user).getPropertyValue(firstFieldName);
        final Object secondObject = new BeanWrapperImpl(user).getPropertyValue(secondFieldName);
        valid = firstObject == null && secondObject == null || firstObject != null && firstObject.equals(secondObject);

        if (!valid){
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addPropertyNode(firstFieldName)
                    .addConstraintViolation().disableDefaultConstraintViolation();
        }
        return valid;
    }

}
