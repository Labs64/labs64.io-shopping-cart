package io.labs64.ecommerce.v1.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = CurrencyValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCurrency {
    String message() default "{validation.currency.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
