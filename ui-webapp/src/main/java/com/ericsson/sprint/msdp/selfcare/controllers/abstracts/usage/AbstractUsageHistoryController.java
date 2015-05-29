package com.ericsson.sprint.msdp.selfcare.controllers.abstracts.usage;

import org.apache.log4j.Logger;

import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SearchCriteriaRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.TalkHistory;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.TalkHistoryResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.TextHistory;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.TextHistoryResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.sprint.msdp.selfcare.controllers.abstracts.AbstractBaseController;

public abstract class AbstractUsageHistoryController extends AbstractBaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AbstractUsageHistoryController.class);

	private static final String PARAM_PAGE_NUMBER = "pageNumber";
	private static final String PARAM_DAYS_AGO = "daysAgo";

	/**
	 * @param model
	 * @param daysAgo
	 * @param pageNumber
	 * @param subscriberId
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/usage-talk-history", method = {RequestMethod.GET, RequestMethod.POST})
	public String talkHistory(
			Model model, 
			@RequestParam(value = PARAM_DAYS_AGO, defaultValue = "7") String daysAgo,
			@RequestParam(value = PARAM_PAGE_NUMBER, defaultValue = "1") String pageNumber,
			
			@RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId, 
			HttpSession session) {
		if (logger.isDebugEnabled()) {
			logger.debug("talkHistory(Model, String, String, String, HttpSession) - start"); //$NON-NLS-1$
		}
		
		UserContextRequest request = createUserContextRequest(subscriberId);
		SearchCriteriaRequest criteria = createSearchCriteria(daysAgo, pageNumber);

		TalkHistoryResponse response = accountService.getTalkHistory(request , criteria);
		TalkHistory[] talkHistory = response.getTalkHistory();
		if (talkHistory != null){
			for (TalkHistory t : talkHistory) {
				if (logger.isInfoEnabled()) {
					logger.info("talkHistory(Model, String, String, String, HttpSession) - TalkHistory - t=" + t); //$NON-NLS-1$
					logger.info("talkHistory(Model, String, String, String, HttpSession) - TalkHistory - t.from=" + t.getFrom()); //$NON-NLS-1$
					logger.info("talkHistory(Model, String, String, String, HttpSession) - TalkHistory - t.to=" + t.getTo()); //$NON-NLS-1$
					logger.info("talkHistory(Model, String, String, String, HttpSession) - TalkHistory - t.date=" + t.getDate()); //$NON-NLS-1$
				}
			}
		}
		model.addAttribute("talkHistory", talkHistory);

		if (logger.isInfoEnabled()) {
			logger.info("talkHistory(Model, String, String, String, HttpSession) - talkHistory=" + talkHistory); //$NON-NLS-1$
		}

		String returnString = resolvePath("myaccount/usage/talk-history");
		if (logger.isDebugEnabled()) {
			logger.debug("talkHistory(Model, String, String, String, HttpSession) - end - return value=" + returnString); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * @param model
	 * @param daysAgo
	 * @param pageNumber
	 * @param subscriberId
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/usage-text-history", method = RequestMethod.GET)
	public String textHistory(
			Model model, 
			@RequestParam(value = PARAM_DAYS_AGO, defaultValue = "7") String daysAgo,
			@RequestParam(value = PARAM_PAGE_NUMBER, defaultValue = "1") String pageNumber,
			@RequestHeader(SUBSCRIBER_ID_KEY) String subscriberId, 
			HttpSession session) {
		if (logger.isDebugEnabled()) {
			logger.debug("textHistory(Model, String, String, String, HttpSession) - start"); //$NON-NLS-1$
		}
		
		UserContextRequest request = createUserContextRequest(subscriberId);
		SearchCriteriaRequest criteria = createSearchCriteria(daysAgo, pageNumber);

		TextHistoryResponse response = accountService.getTextHistory(request, criteria);
		TextHistory[] textHistory = response.getTextHistory();
		if (textHistory != null){
			for (TextHistory t : textHistory) {
				if (logger.isInfoEnabled()) {
					logger.info("textHistory(Model, String, String, String, HttpSession) - TextHistory - t=" + t); //$NON-NLS-1$
					logger.info("textHistory(Model, String, String, String, HttpSession) - TextHistory - t.from=" + t.getFrom()); //$NON-NLS-1$
					logger.info("textHistory(Model, String, String, String, HttpSession) - TextHistory - t.to=" + t.getTo()); //$NON-NLS-1$
					logger.info("textHistory(Model, String, String, String, HttpSession) - TextHistory - t.date=" + t.getDate()); //$NON-NLS-1$
				}
			}
		}
		model.addAttribute("textHistory", textHistory);
		
		if (logger.isInfoEnabled()) {
			logger.info("textHistory(Model, String, String, String, HttpSession) - textHistory=" + textHistory); //$NON-NLS-1$
		}

		String returnString = resolvePath("myaccount/usage/text-history");
		if (logger.isDebugEnabled()) {
			logger.debug("textHistory(Model, String, String, String, HttpSession) - end - return value=" + returnString); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * @param daysAgo
	 * @param pageNumber
	 * @return
	 */
	private SearchCriteriaRequest createSearchCriteria(String daysAgo, String pageNumber) {
		if (logger.isDebugEnabled()) {
			logger.debug("createSearchCriteria(String, String) - start"); //$NON-NLS-1$
		}

		if (logger.isInfoEnabled()) {
			logger.info("createSearchCriteria(String, String) - daysAgo=" + daysAgo + ", pageNumber=" + pageNumber); //$NON-NLS-1$ //$NON-NLS-2$
		}

		SearchCriteriaRequest criteria = new SearchCriteriaRequest();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, Integer.valueOf(daysAgo) * -1);
		criteria.setFromDate(calendar.getTime());
		criteria.setPageNumber(Integer.valueOf(pageNumber));

		if (logger.isDebugEnabled()) {
			logger.debug("createSearchCriteria(String, String) - end - return value=" + criteria); //$NON-NLS-1$
		}
		return criteria;
	}

}
