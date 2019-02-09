package com.wei.validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 为为
 * @create 1/26
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {

        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (required) {
            return ValidateUtils.isMobile(value);
        } else {
            if (StringUtils.isEmpty(value)) {
                return true;
            } else {
                return ValidateUtils.isMobile(value);
            }
        }
    }
}
