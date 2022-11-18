package com.cognizant.bid.validation;


import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Common Validator for email
 * @author Arkit Das
 *
 */
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
	
	String EMAIL_REGEX = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@"
			+ "(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
	
	boolean isRequired;
	
    @Override
    public void initialize(ValidEmail email) {
    	isRequired = email.isRequired();
    }
 
    @Override
    public boolean isValid(String emailAddress,
      ConstraintValidatorContext cxt) {
    	if(!isRequired && StringUtils.isBlank(emailAddress)) {
    		return true;
    	}
    	
    	Pattern emailAddressPattern = Pattern
    			.compile(EMAIL_REGEX);
    	Matcher matcher = emailAddressPattern.matcher(emailAddress);
        return matcher.matches();
    }
 
}
