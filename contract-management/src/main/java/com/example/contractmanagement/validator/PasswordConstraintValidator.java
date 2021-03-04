package com.example.contractmanagement.validator;
/*
 * author : Madhurya
 * */
import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
	 @Override
	    public void initialize(ValidPassword arg0) {
	    }

	    @Override
	    public boolean isValid(String password, ConstraintValidatorContext context) {
	        PasswordValidator validator = new PasswordValidator(Arrays.asList( 
	           new CharacterRule(EnglishCharacterData.Special, 1),
	           new WhitespaceRule()));

	        RuleResult result = validator.validate(new PasswordData(password));
	        if (result.isValid()) {
	            return true;
	        }
	        
	        List<String> messages = validator.getMessages(result);
	        String messageTemplate = String.join(",", messages);
	        context.buildConstraintViolationWithTemplate(messageTemplate)
	        	.addConstraintViolation()
	        	.disableDefaultConstraintViolation();
	        return false;
	    }
}

