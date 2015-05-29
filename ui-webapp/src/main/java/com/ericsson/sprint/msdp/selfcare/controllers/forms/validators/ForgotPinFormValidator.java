package com.ericsson.sprint.msdp.selfcare.controllers.forms.validators;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ericsson.sprint.msdp.selfcare.controllers.forms.ForgotPinForm;

public class ForgotPinFormValidator implements Validator {
	
	public static final String MOBILE_PATTERN = "\\d{10}";
	
	@Override
	public boolean supports(Class<?> arg0) {
		return ForgotPinForm.class.equals(arg0);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		ForgotPinForm form = (ForgotPinForm) arg0;
		if (StringUtils.isEmpty(form.getSecretAnswer())) {
			arg1.reject("error.forgot.pin.secret.answer.is.empty");
		} else if (StringUtils.isEmpty(form.getMdn())){
			arg1.reject("error.forgot.pin.mobile.number.is.empty");
		} else if (!form.getMdn().matches(MOBILE_PATTERN)){
			arg1.reject("error.forgot.pin.mobile.number.is.invalid");
		}
	}
}
