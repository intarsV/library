package com.accenture.library.customEnumValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( {ElementType.FIELD, ElementType.METHOD})
@Retention( RetentionPolicy.RUNTIME)
@Constraint( validatedBy = MyEnumValidationImpl.class)
public @interface MyEnumValidation {

    Class<? extends Enum<?>> enumClass();

    String message() default "Not supported type value!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
