package com.ericsson.sprint.msdp.selfcare.controllers.forms.validators;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ericsson.sprint.msdp.selfcare.controllers.forms.ContactInfoForm;

public class ContactInfoFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return ContactInfoForm.class.equals(arg0);
	}

	@Override
	public void validate(Object o, Errors arg1) {
		if (o instanceof ContactInfoForm) {
			ContactInfoForm form = (ContactInfoForm) o;
			if (StringUtils.isEmpty(form.getFirstName())) {
				arg1.reject("error.settings.profile.update.first.name.empty");
			} else if (StringUtils.isEmpty(form.getLastName())) {
				arg1.reject("error.settings.profile.update.last.name.empty");
			} else if (StringUtils.isEmpty(form.getMi())) {
				arg1.reject("error.settings.profile.update.middle.name.empty");
			} else if (StringUtils.isEmpty(form.getAltNumber())) {
				arg1.reject("error.settings.profile.update.alt.phonenumber.empty");
			} else if (StringUtils.isEmpty(form.getEmail())) {
				arg1.reject("error.settings.profile.update.email.empty");
			}
		}
	}

}
