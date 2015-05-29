package com.ericsson.sprint.msdp.selfcare.controllers.abstracts.settings;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.Address;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberAccount;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberAccountRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SubscriberAccountResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.AbstractBaseController;
import com.ericsson.sprint.msdp.selfcare.controllers.forms.ContactInfoForm;
import com.ericsson.sprint.msdp.selfcare.controllers.forms.validators.ContactInfoFormValidator;

public abstract class AbstractContactInfoController extends AbstractBaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AbstractContactInfoController.class);

	private static final String CONTACT_FORM = "contactInfoForm";
	
	private static final Map<String, String> months;
	private static final Map<String, String> days;
	private static final Map<String, String> years;
	static{
		 months = new LinkedHashMap<>();
		 months.put("1", "JAN");
		 months.put("2", "FEB");
		 months.put("3", "MAR");
		 months.put("4", "APR");
		 months.put("5", "MAY");
		 months.put("6", "JUN");
		 months.put("7", "JUL");
		 months.put("8", "AUG");
		 months.put("9", "SEP");
		 months.put("10", "OCT");
		 months.put("11", "NOV");
		 months.put("12", "DEC");
		 days = new LinkedHashMap<>();
		 for(int i = 1 ; i <= 31; i++){
			 days.put(Integer.toString(i), Integer.toString(i));
		 }
		 years = new LinkedHashMap<>();
		 for(int i =  Calendar.getInstance().get(Calendar.YEAR); i > Calendar.getInstance().get(Calendar.YEAR) - 90; i--){
			 years.put(Integer.toString(i), Integer.toString(i));
		 }
		 
	}

	/**
	 * @param model
	 * @param subscriberId
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/my-account-profile", method = RequestMethod.GET)
	public String profile(
			Model model,
			@RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId, 
			HttpSession session) {
		if (logger.isInfoEnabled()) {
			logger.info("profile(Model, String, HttpSession) - model=" + model); //$NON-NLS-1$
		}

		if (!model.containsAttribute(CONTACT_FORM)) {
			UserContextRequest request = createUserContextRequest(subscriberId);
			SubscriberAccountResponse response = accountHome.getAccount(request);			
			ContactInfoForm form = new ContactInfoForm();
			populateContactInfoForm(response.getSubscriberAccount(), form);
			model.addAttribute(CONTACT_FORM, form);
		}
		
		model.addAttribute("months", years);
		model.addAttribute("years", months);
		model.addAttribute("days", days);
		
		return resolvePath("myaccount/settings/profile/form");
	}

	/**
	 * @param model
	 * @param form
	 * @param results
	 * @param subscriberId
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/my-account-profile", method = RequestMethod.POST)
	public String updateProfile(
			Model model, 
			@ModelAttribute ContactInfoForm form, 
			BindingResult results,
			@RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId, 
			HttpSession session) {

		new ContactInfoFormValidator().validate(form, results);
		
		if (logger.isInfoEnabled()) {
			logger.info("updateProfile(Model, ContactInfoForm, BindingResult, String, HttpSession) - form=" + form); //$NON-NLS-1$
		}

		if (!results.hasErrors()) {
			SubscriberAccount account = accountHome.getAccount(createUserContextRequest(subscriberId)).getSubscriberAccount();
			
			Address address = account.getAddress();
			address.setCity(form.getAddr1City());
			address.setAddress1(form.getAddr1Line1());
			address.setAddress2(form.getAddr1Line2());
			address.setState(form.getAddr1State());
			address.setZipCode(form.getAddr1Zip());
			
			Calendar calendar = Calendar.getInstance();
			if (form.getBirthDayDate() != null && form.getBirthDayMonth() != null && form.getBirthDayYear() != null){
				calendar.set(Calendar.DATE, Integer.valueOf(form.getBirthDayDate()));
				calendar.set(Calendar.MONTH, Integer.valueOf(form.getBirthDayMonth()));
				calendar.set(Calendar.YEAR, Integer.valueOf(form.getBirthDayYear()));
			}
			account.setBirthdate(calendar.getTime());
			
			account.setFirstName(form.getFirstName());
			account.setLastName(form.getLastName());
			account.setMiddleName(form.getMi());
			account.setAlternatePhone(form.getAltNumber());
			account.setPreferredLanguage(form.getPreferredLanguage());
			// Making Request
			//
			SubscriberAccountRequest request = new SubscriberAccountRequest();
			request.setSubscriberAccount(account);

			UserContextRequest context = createUserContextRequestWithMdn(subscriberId, session);
			StatusMessageResponse response = accountSettings.updateAccount(request, context);
			if (response.getStatusMessage().getStatus() != 0){
				results.reject("error.settings.profile.update.response", new String[]{response.getStatusMessage().getReason()} ,null);
			}else{
				model.addAttribute("message", response.getStatusMessage().getReason());
			}
			if (logger.isInfoEnabled()) {
				logger.info("udpateProfile(Model, ContactInfoForm, BindingResult, String, HttpSession) - request=" + request + ", response=" + response); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}

		if (logger.isInfoEnabled()) {
			logger.info("udpateProfile(Model, ContactInfoForm, String, HttpSession)"); //$NON-NLS-1$
		}
		return profile(model, subscriberId, session);
	}

	/**
	 * @param response
	 * @param form
	 */
	private void populateContactInfoForm(SubscriberAccount account, ContactInfoForm form) {
		Address address = account.getAddress();
		String addr1City = address.getCity();
		String addr1Line1 = address.getAddress1();
		String addr1Line2= address.getAddress2();
		String addr1State= address.getState();
		String addr1Zip= address.getZipCode();
		
		// Prob
		String addr2City= null;
		String addr2Line1= null;
		String addr2Line2= null;
		String addr2State= null;
		String addr2Zip= null;
		
		String altNumber= account.getAlternatePhone();
		String birthDayDate = null, birthDayMonth = null, birthDayYear = null;
		if (account.getBirthdate() != null){
			Calendar instance = Calendar.getInstance();
			instance.setTime(account.getBirthdate());
			birthDayDate = Integer.toString(instance.get(Calendar.DATE));
			birthDayMonth = Integer.toString(instance.get(Calendar.MONTH));
			birthDayYear = Integer.toString(instance.get(Calendar.YEAR));
		}
		String email= account.getEmail();
		String firstName= account.getFirstName();
		String lastName= account.getLastName();
		String mi= account.getMiddleName();
		String preferredLanguage= account.getPreferredLanguage();
		String phoneNumber = account.getMsisdn();
		
		form.setAddr1City(addr1City);
		form.setAddr1Line1(addr1Line1);
		form.setAddr1Line2(addr1Line2);
		form.setAddr1State(addr1State);
		form.setAddr1Zip(addr1Zip);
		form.setAddr2City(addr2City);
		form.setAddr2Line1(addr2Line1);
		form.setAddr2Line2(addr2Line2);
		form.setAddr2State(addr2State);
		form.setAddr2Zip(addr2Zip);
		form.setAltNumber(altNumber);
		form.setBirthDayDate(birthDayDate);
		form.setBirthDayMonth(birthDayMonth);
		form.setBirthDayYear(birthDayYear);
		form.setEmail(email);
		form.setFirstName(firstName);
		form.setLastName(lastName);
		form.setMi(mi);
		form.setPhoneNumber(phoneNumber);
		form.setPreferredLanguage(preferredLanguage);
	}
}
