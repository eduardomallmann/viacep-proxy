package com.eduardomallmann.studies.example.locations;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/**
 * Custom validation for the zip code passed in the path variable request.
 *
 * @author 013087631
 */
@Target({PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {ValidCEP.CepValidator.class})
@Documented
public @interface ValidCEP {

    String message() default "{error.address.cep.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Zip code Validator, responsible for the validation logic.
     */
    class CepValidator implements ConstraintValidator<ValidCEP, String> {

        private static final String CEP_REGEX = "[0-9]{8}";

        @Override
        public void initialize(final ValidCEP constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }

        @Override
        public boolean isValid(final String cep, final ConstraintValidatorContext context) {
            return cep.matches(CEP_REGEX);
        }
    }
}
