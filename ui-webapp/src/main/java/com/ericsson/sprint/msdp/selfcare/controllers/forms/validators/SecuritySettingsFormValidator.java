package com.ericsson.sprint.msdp.selfcare.controllers.forms.validators;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ericsson.sprint.msdp.selfcare.controllers.forms.SecuritySettingsForm;

public class SecuritySettingsFormValidator implements Validator {
	private Pattern identical = Pattern.compile("111|222|333|444|555|666|777|888|999|000");
	private Pattern sequential = Pattern.compile("123|234|345|456|567|678|789");

	@Override
	public boolean supports(Class<?> arg0) {
		return SecuritySettingsForm.class.equals(arg0);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		SecuritySettingsForm form = (SecuritySettingsForm) arg0;
		if (StringUtils.isEmpty(form.getPin())) {
			arg1.reject("error.security.settings.pin.is.empty");
		} else if (StringUtils.isEmpty(form.getConfirmPin())) {
			arg1.reject("error.security.settings.confirm.pin.is.empty");
		} else if (form.getPin().length() != 6) {
			arg1.reject("error.security.settings.pin.length");
		} else if (StringUtils.isEmpty(form.getSecretAnswer())) {
			arg1.reject("error.security.settings.secret.answer.is.empty");
		} else if (StringUtils.isEmpty(form.getConfirmSecretAnswer())) {
			arg1.reject("error.security.settings.confirm.secret.answer.is.empty");
		} else if (!form.getConfirmPin().equals(form.getPin())) {
			arg1.reject("error.security.settings.pin.not.match");
		} else if (!form.getConfirmSecretAnswer().equals(form.getSecretAnswer())) {
			arg1.reject("error.security.settings.secret.answer.not.match");
		} else if (identical.matcher(form.getPin()).find()) {
			arg1.reject("error.security.settings.pin.three.identical.numbers");
		} else if (sequential.matcher(form.getConfirmPin()).find()) {
			arg1.reject("error.security.settings.pin.three.sequential.numbers");
		}
	}

}
