package com.ericsson.sprint.msdp.selfcare.controllers.forms.validators;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ericsson.sprint.msdp.selfcare.controllers.forms.EsnSwapForm;

public class EsnSwapFormValidator implements Validator {
	private Pattern identical = Pattern.compile("111|222|333|444|555|666|777|888|999|000");
	private Pattern sequential = Pattern.compile("123|234|345|456|567|678|789");

	@Override
	public boolean supports(Class<?> arg0) {
		return EsnSwapForm.class.equals(arg0);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		EsnSwapForm form = (EsnSwapForm) arg0;
		if (StringUtils.isEmpty(form.getSerial())) {
			arg1.reject("error.esn.swap.serial.is.empty");
		} else if (StringUtils.isEmpty(form.getSerialConfirm())) {
			arg1.reject("error.esn.swap.serial.confirm.is.empty");
		} 
	/*
		else if (form.getPin().length() != 6) {
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
	*/
	}

}
