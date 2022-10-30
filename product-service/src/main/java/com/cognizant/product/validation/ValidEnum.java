package com.cognizant.product.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.TYPE_USE;

@Target({METHOD, FIELD, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EnumValidator.class)
public @interface ValidEnum {
	
	Class<? extends Enum<?>> enumClass();
	
    String message() default "Invalid enum value";
    
    boolean isRequired() default true;
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};

}
