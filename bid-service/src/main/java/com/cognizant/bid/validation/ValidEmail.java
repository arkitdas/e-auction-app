package com.cognizant.bid.validation;



import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 
 * @author Arkit Das
 *
 */
@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmail {
	
    String message() default "Invalid email address";
    
    boolean isRequired() default true;
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
