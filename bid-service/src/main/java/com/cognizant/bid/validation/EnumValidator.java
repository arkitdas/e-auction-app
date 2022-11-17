package com.cognizant.bid.validation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnumValidator  implements ConstraintValidator<ValidEnum, String> {
    private List<String> acceptedValues;
    boolean isRequired;
    @Override
    public void initialize(ValidEnum values) {
		acceptedValues = Arrays.stream(values.enumClass().getEnumConstants()).map(Enum::name).collect(Collectors.toList());
		isRequired = values.isRequired();
    }
 
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
    	if(!isRequired && StringUtils.isBlank(value)) {
    		return true;
    	}
    	if(StringUtils.isBlank(value)) {
    		return false;
    	}
        //validate against all enum values 
        boolean flag = acceptedValues.stream().anyMatch(value::equalsIgnoreCase);
        log.info("Enum validation result '{}' for value '{}'", flag, value);
        return flag;
    }
}