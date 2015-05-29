/**
 * Sprint Template
 */
package com.ericsson.cac.sprint.test.selfcare.workflow.lostpin;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.selfcare.workflow.commons.HeaderData;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.LostPinService;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.AccountSecurityRequest;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.AccountSecurityResponse;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.BrandCodeRequest;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.BrandCodeResponse;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.LoginAttemptRequest;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.LoginAttemptResponse;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.LostPinResponse;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.QuestionInfo;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.ResendSecurityRequest;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.ResendSecurityResponse;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.SecurityRequest;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.SecurityResponse;
import com.ericsson.cac.sprint.test.selfcare.workflow.TestConfig;

/**
 * LostPhoneTest is used to test all the scenarios for the service layer class
 * 
 * @author Ericsson
 * @version $Revision: 1.0 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class LostPinServiceTest {

	@Autowired
	private LostPinService service;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(LostPinServiceTest.class);

	@Test
	public void testSecurityQuestion() {
		try {

			HeaderData headerData = new HeaderData();

			// Not Required for adapter call in this use case;

			// headerData.setDevice("iPhone");
			headerData.setIpAddress("1.2.3.4");
			headerData.setSessionInfo("session");
			headerData.setUrl("");

			//SecurityQuestionList securityQuestionList = new SecurityQuestionList();
			QuestionInfo expectedInfo = new QuestionInfo();
			expectedInfo.setQuestionCode("b01");
			expectedInfo.setQuestion("Who was your favorite teacher?");			
			expectedInfo.setBrandCode("SPP");
			//securityQuestionList.getSecurityQuestionInfo().add(securityQuestionInfo);

			SecurityRequest securityRequest = new SecurityRequest(headerData,
					"3179566118");

			SecurityResponse response = service
					.fetchSecurityQuestionsForLostPin(securityRequest);
			
			List<QuestionInfo> questionList = response.getSecurityQuestionList();
			QuestionInfo actualInfo = questionList.get(0);
			
			System.out.println("Expected Question= " +expectedInfo.getQuestion() + ":: Actual Question=" +actualInfo.getQuestion());
			System.out.println("Expected QuestionCode= " +expectedInfo.getQuestionCode() + ":: Actual QuestionCode=" +actualInfo.getQuestionCode());
			System.out.println("Expected BrandCode= " +expectedInfo.getBrandCode() + ":: Actual Question=" +actualInfo.getBrandCode());
			
			Assert.assertEquals(expectedInfo.getQuestion(), actualInfo.getQuestion());
			Assert.assertEquals(expectedInfo.getBrandCode(), actualInfo.getBrandCode());
			Assert.assertEquals(expectedInfo.getQuestionCode(), actualInfo.getQuestionCode());
			
			/*SecurityResponse actualResponse = new SecurityResponse(securityQuestionList);

			Assert.assertEquals(response.getSecurityQuestionList()
					.getSecurityQuestionInfo().get(0).getQuestionCode(),
					actualResponse.getSecurityQuestionList()
							.getSecurityQuestionInfo().get(0).getQuestionCode());*/

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void testValidateAnswer() {

		String mdn = "9133878656";
		HeaderData headerData = new HeaderData();

		// Not Required for adapter call in this use case;

		// headerData.setDevice("iPhone");
		headerData.setIpAddress("1.2.3.4");
		headerData.setSessionInfo("session");
		headerData.setUrl("");

		AccountSecurityRequest accountSecurityRequest = new AccountSecurityRequest(
				headerData, mdn);
		accountSecurityRequest.setQuestion("What is your pet's name?");
		accountSecurityRequest.setAnswer("Scrambled Answer");

		GregorianCalendar c = new GregorianCalendar();

		LoginAttemptResponse loginAttemptResponse = null;
		try {
			loginAttemptResponse = new LoginAttemptResponse(DatatypeFactory
					.newInstance().newXMLGregorianCalendar(c));
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		AccountSecurityResponse response = service.validateSecurityAnswers(
				accountSecurityRequest, loginAttemptResponse);

		System.out.println(response.getMessage());

		LostPinResponse actualResponse = new LostPinResponse("SUCCESS");

		Assert.assertEquals(response.getMessage(), actualResponse.getMessage());

	}

	@Test
	public void testBrandCode() {
		try {

			HeaderData headerData = new HeaderData();

			// Not Required for adapter call in this use case;

			// headerData.setDevice("iPhone");
			headerData.setIpAddress("1.2.3.4");
			headerData.setSessionInfo("session");
			headerData.setUrl("");

			com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.SearchCriteriaType searchCriteria = new com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.SearchCriteriaType();
			searchCriteria.setMdn("3179566118");
			
			BrandCodeRequest brandCodeRequest = new BrandCodeRequest(headerData,
					"3179566118");
			brandCodeRequest.setBrandCode("SPP");

			BrandCodeResponse response = service
					.validateBrandCode(brandCodeRequest);

			BrandCodeResponse actualResponse = new BrandCodeResponse("SUCCESS");
			
			System.out.println(response.getMessage());

			Assert.assertEquals(response.getMessage(),
					actualResponse.getMessage());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void testresendSecurityInfoToUser() {
		try {

			String mdn = "3179566118";
			HeaderData headerData = new HeaderData();

			// Not Required for adapter call in this use case;

			// headerData.setDevice("iPhone");
			headerData.setIpAddress("1.2.3.4");
			headerData.setSessionInfo("session");
			headerData.setUrl("");

			ResendSecurityRequest request = new ResendSecurityRequest(
					headerData, mdn);

			ResendSecurityResponse response = service
					.resendSecurityInfoToUser(request);

			System.out.println(response.getMessage());

			LostPinResponse actualResponse = new LostPinResponse("SUCCESS");

			Assert.assertEquals(response.getMessage(),
					actualResponse.getMessage());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void testValidateAnswerForUnlockedAccount__AnswerMatched() {

		String mdn = "9133878656";
		HeaderData headerData = new HeaderData();

		// Not Required for adapter call in this use case;

		// headerData.setDevice("iPhone");
		headerData.setIpAddress("1.2.3.4");
		headerData.setSessionInfo("session");
		headerData.setUrl("");

		AccountSecurityRequest accountSecurityRequest = new AccountSecurityRequest(
				headerData, mdn);
		accountSecurityRequest.setQuestion("What is your pet's name?");
		accountSecurityRequest.setAnswer("Scrambled Answer");

		XMLGregorianCalendar lockedUntilDate = null;
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		try {
			lockedUntilDate = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
		}

		LoginAttemptResponse loginAttemptResponse = new LoginAttemptResponse(
				lockedUntilDate);

		AccountSecurityResponse response = service.validateSecurityAnswers(
				accountSecurityRequest, loginAttemptResponse);

		System.out.println(response.getMessage());

		// If we recieve the mdn, then the loginstatus update is successfull
		LostPinResponse actualResponse = new LostPinResponse("SUCCESS");

		Assert.assertEquals(response.getMessage(), actualResponse.getMessage());

	}

	@Ignore
	public void testValidateAnswerForUnlockedAccount_AnswerNotMatched() {

		String mdn = "3179566118";
		HeaderData headerData = new HeaderData();

		// Not Required for adapter call in this use case;

		// headerData.setDevice("iPhone");
		headerData.setIpAddress("1.2.3.4");
		headerData.setSessionInfo("session");
		headerData.setUrl("");

		AccountSecurityRequest accountSecurityRequest = new AccountSecurityRequest(
				headerData, mdn);
		accountSecurityRequest.setAnswer("Scrambled Answer");

		XMLGregorianCalendar lockedUntilDate = null;
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		try {
			lockedUntilDate = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
		}

		LoginAttemptResponse loginAttemptResponse = new LoginAttemptResponse(
				lockedUntilDate);

		AccountSecurityResponse response = service.validateSecurityAnswers(
				accountSecurityRequest, loginAttemptResponse);

		System.out.println(response.getMessage());

		// If we recieve the mdn, then the loginstatus update is successfull
		LostPinResponse actualResponse = new LostPinResponse(mdn);

		Assert.assertEquals(response.getMessage(), actualResponse.getMessage());

	}

	@Ignore
	public void testValidateAnswerForLockedAccount__AnswerMatched() {

		String mdn = "3179566118";
		HeaderData headerData = new HeaderData();

		// Not Required for adapter call in this use case;

		// headerData.setDevice("iPhone");
		headerData.setIpAddress("1.2.3.4");
		headerData.setSessionInfo("session");
		headerData.setUrl("");

		AccountSecurityRequest accountSecurityRequest = new AccountSecurityRequest(
				headerData, mdn);
		accountSecurityRequest.setAnswer("Scrambled Answer");

		XMLGregorianCalendar lockedUntilDate = null;
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		try {
			lockedUntilDate = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
		}

		LoginAttemptResponse loginAttemptResponse = new LoginAttemptResponse(
				lockedUntilDate);

		AccountSecurityResponse response = service.validateSecurityAnswers(
				accountSecurityRequest, loginAttemptResponse);

		System.out.println(response.getMessage());

		// If we recieve the mdn, then the loginstatus update is successfull
		LostPinResponse actualResponse = new LostPinResponse("SUCCESS");

		Assert.assertEquals(response.getMessage(), actualResponse.getMessage());

	}

	@Ignore
	public void testValidateAnswerForLockedAccount_AnswerNotMatched() {

		String mdn = "3179566118";
		HeaderData headerData = new HeaderData();

		// Not Required for adapter call in this use case;

		// headerData.setDevice("iPhone");
		headerData.setIpAddress("1.2.3.4");
		headerData.setSessionInfo("session");
		headerData.setUrl("");

		AccountSecurityRequest accountSecurityRequest = new AccountSecurityRequest(
				headerData, mdn);
		accountSecurityRequest.setAnswer("Scrambled Answer");

		XMLGregorianCalendar lockedUntilDate = null;
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		try {
			lockedUntilDate = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
		}

		LoginAttemptResponse loginAttemptResponse = new LoginAttemptResponse(
				lockedUntilDate);

		AccountSecurityResponse response = service.validateSecurityAnswers(
				accountSecurityRequest, loginAttemptResponse);

		System.out.println(response.getMessage());

		// If we recieve the mdn, then the loginstatus update is successfull
		LostPinResponse actualResponse = new LostPinResponse("F");

		Assert.assertEquals(response.getMessage(), actualResponse.getMessage());

	}
	
	@Test
	public void testValidateAnswer_InvalidMDN() {

		String mdn = "";
		HeaderData headerData = new HeaderData();

		// Not Required for adapter call in this use case;

		// headerData.setDevice("iPhone");
		headerData.setIpAddress("1.2.3.4");
		headerData.setSessionInfo("session");
		headerData.setUrl("");

		AccountSecurityRequest accountSecurityRequest = new AccountSecurityRequest(
				headerData, mdn);
		accountSecurityRequest.setQuestion("What is your pet's name?");
		accountSecurityRequest.setAnswer("Scrambled Answer");

		GregorianCalendar c = new GregorianCalendar();

		LoginAttemptResponse loginAttemptResponse = null;
		try {
			loginAttemptResponse = new LoginAttemptResponse(DatatypeFactory
					.newInstance().newXMLGregorianCalendar(c));
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		AccountSecurityResponse response = service.validateSecurityAnswers(
				accountSecurityRequest, loginAttemptResponse);

		System.out.println(response.getMessage());

		//LostPinResponse actualResponse = new LostPinResponse("SUCCESS");

		Assert.assertEquals("Please enter a valid MDN", response.getMessage());

	}
	
	@Test
	public void testValidateAnswer_InvalidAnswer() {

		String mdn = "3179566118";
		HeaderData headerData = new HeaderData();

		// Not Required for adapter call in this use case;

		// headerData.setDevice("iPhone");
		headerData.setIpAddress("1.2.3.4");
		headerData.setSessionInfo("session");
		headerData.setUrl("");

		AccountSecurityRequest accountSecurityRequest = new AccountSecurityRequest(
				headerData, mdn);
		accountSecurityRequest.setQuestion("What is your pet's name?");
		accountSecurityRequest.setAnswer("");

		GregorianCalendar c = new GregorianCalendar();

		LoginAttemptResponse loginAttemptResponse = null;
		try {
			loginAttemptResponse = new LoginAttemptResponse(DatatypeFactory
					.newInstance().newXMLGregorianCalendar(c));
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		AccountSecurityResponse response = service.validateSecurityAnswers(
				accountSecurityRequest, loginAttemptResponse);

		System.out.println(response.getMessage());

		//LostPinResponse actualResponse = new LostPinResponse("SUCCESS");

		Assert.assertEquals("Please enter a Answer", response.getMessage());

	}
	
	@Test
	public void testValidateAnswer_InvalidQuestion() {

		String mdn = "3179566118";
		HeaderData headerData = new HeaderData();

		// Not Required for adapter call in this use case;

		// headerData.setDevice("iPhone");
		headerData.setIpAddress("1.2.3.4");
		headerData.setSessionInfo("session");
		headerData.setUrl("");

		AccountSecurityRequest accountSecurityRequest = new AccountSecurityRequest(
				headerData, mdn);
		accountSecurityRequest.setQuestion("");
		accountSecurityRequest.setAnswer("Scrambled Answer");

		GregorianCalendar c = new GregorianCalendar();

		LoginAttemptResponse loginAttemptResponse = null;
		try {
			loginAttemptResponse = new LoginAttemptResponse(DatatypeFactory
					.newInstance().newXMLGregorianCalendar(c));
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		AccountSecurityResponse response = service.validateSecurityAnswers(
				accountSecurityRequest, loginAttemptResponse);

		System.out.println(response.getMessage());

		//LostPinResponse actualResponse = new LostPinResponse("SUCCESS");

		Assert.assertEquals("Please select a Question", response.getMessage());

	}
	
	@Test
	public void testBrandCode_InvaliMDN() {
		try {

			String mdn = "3179566118";
			HeaderData headerData = new HeaderData();

			// Not Required for adapter call in this use case;

			// headerData.setDevice("iPhone");
			headerData.setIpAddress("1.2.3.4");
			headerData.setSessionInfo("session");
			headerData.setUrl("");

			com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.SearchCriteriaType searchCriteria = new com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.SearchCriteriaType();
			searchCriteria.setMdn(mdn);
			
			BrandCodeRequest brandCodeRequest = new BrandCodeRequest(headerData,
					mdn);
			brandCodeRequest.setBrandCode("");

			BrandCodeResponse response = service
					.validateBrandCode(brandCodeRequest);

			//BrandCodeResponse actualResponse = new BrandCodeResponse("SUCCESS");
			
			System.out.println(response.getMessage());

			Assert.assertEquals("Please enter a Brandcode",response.getMessage());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testBrandCode_InvaliBrandCode() {
		try {

			String mdn = "";
			HeaderData headerData = new HeaderData();

			// Not Required for adapter call in this use case;

			// headerData.setDevice("iPhone");
			headerData.setIpAddress("1.2.3.4");
			headerData.setSessionInfo("session");
			headerData.setUrl("");

			com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.SearchCriteriaType searchCriteria = new com.sprint.integration.interfaces.querysubscriberprepaidinfo.v1.querysubscriberprepaidinfo.SearchCriteriaType();
			searchCriteria.setMdn(mdn);
			
			BrandCodeRequest brandCodeRequest = new BrandCodeRequest(headerData,
					mdn);
			brandCodeRequest.setBrandCode("SPP");

			BrandCodeResponse response = service
					.validateBrandCode(brandCodeRequest);

			//BrandCodeResponse actualResponse = new BrandCodeResponse("SUCCESS");
			
			System.out.println(response.getMessage());

			Assert.assertEquals("Please enter a valid MDN",response.getMessage());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testresendSecurityInfoToUser_InvalidMDN() {
		try {

			String mdn = "";
			HeaderData headerData = new HeaderData();

			// Not Required for adapter call in this use case;

			// headerData.setDevice("iPhone");
			headerData.setIpAddress("1.2.3.4");
			headerData.setSessionInfo("session");
			headerData.setUrl("");

			ResendSecurityRequest request = new ResendSecurityRequest(
					headerData, mdn);

			ResendSecurityResponse response = service
					.resendSecurityInfoToUser(request);

			System.out.println(response.getMessage());

			//LostPinResponse actualResponse = new LostPinResponse("SUCCESS");

			Assert.assertEquals("Please enter a valid MDN",response.getMessage());
			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}
	
	@Test
	public void testLoginAttemptStatus() {

		String mdn = "9133878656";
		HeaderData headerData = new HeaderData();

		// headerData.setDevice("iPhone");
		headerData.setIpAddress("1.2.3.4");
		headerData.setSessionInfo("session");
		headerData.setUrl("");

		LoginAttemptRequest loginAttemptReq = new LoginAttemptRequest(
				headerData, mdn);


		LoginAttemptResponse response = service.loginAttemptStatus(loginAttemptReq);

		System.out.println(response.getMessage());

		LostPinResponse actualResponse = new LostPinResponse("SUCCESS");

		Assert.assertEquals(response.getMessage(), actualResponse.getMessage());

	}
	
	@Test
	public void testLoginAttemptStatus_InvalidMDN() {

		String mdn = "";
		HeaderData headerData = new HeaderData();

		// headerData.setDevice("iPhone");
		headerData.setIpAddress("1.2.3.4");
		headerData.setSessionInfo("session");
		headerData.setUrl("");

		LoginAttemptRequest loginAttemptReq = new LoginAttemptRequest(
				headerData, mdn);


		LoginAttemptResponse response = service.loginAttemptStatus(loginAttemptReq);

		System.out.println(response.getMessage());

		//LostPinResponse actualResponse = new LostPinResponse("SUCCESS");

		Assert.assertEquals("Please enter a valid MDN",response.getMessage());

	}

}
