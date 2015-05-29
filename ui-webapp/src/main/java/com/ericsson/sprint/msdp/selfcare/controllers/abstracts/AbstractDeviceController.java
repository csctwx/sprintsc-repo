package com.ericsson.sprint.msdp.selfcare.controllers.abstracts;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.DeviceResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;

public abstract class AbstractDeviceController extends AbstractBaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AbstractDeviceController.class);

	@RequestMapping(value = "/my-account-device", method = RequestMethod.GET)
	public String getDevice(Model model, @RequestHeader(value = SUBSCRIBER_ID_KEY) String subscriberId, HttpSession session) {
		if (logger.isDebugEnabled()) {
			logger.debug("getDevice(Model, String, HttpSession) - start"); //$NON-NLS-1$
		}

		UserContextRequest req = createUserContextRequest(subscriberId);
		DeviceResponse res = accountDevice.getDevice(req);

		model.addAttribute("mdn", res.getDevice().getMsisdn());
		model.addAttribute("model", res.getDevice().getModel());
		model.addAttribute("insured", res.getDevice().getInsurance().getInsured());
		

		String returnString = resolvePath("myaccount/device/device");
		if (logger.isDebugEnabled()) {
			logger.debug("getDevice(Model, String, HttpSession) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	@RequestMapping(value = "/my-account-device-purchase-history", method = RequestMethod.GET)
	public String getDevicePurchaseHistory() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDevicePurchaseHistory() - start"); //$NON-NLS-1$
		}

		String returnString = resolvePath("myaccount/device/device-purchase-history");
		if (logger.isDebugEnabled()) {
			logger.debug("getDevicePurchaseHistory() - end"); //$NON-NLS-1$
		}
		return returnString;
	}
}
