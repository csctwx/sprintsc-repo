/**
 * Sprint Template
 */
package com.ericsson.cac.sprint.test.selfcare.workflow.getSecretQuestions;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.selfcare.workflow.AccountSettingsWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SecretQuestions;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SecretQuestionsResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContext;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.selfcare.workflow.lostpin.model.QuestionInfo;
import com.ericsson.cac.sprint.test.selfcare.workflow.TestConfig;

/**
 * FoundPhoneTest is used to test all the scenarios for the service layer class
 * 
 * @author Ericsson
 * @version $Revision: 1.0 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class GetSecretQuestionsTest {

	// @Autowired
	// private LostPinService service;

	@Autowired
	private AccountSettingsWorkflow service;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GetSecretQuestionsTest.class);

	@Test
	public void testGetSecretQuestions() {
		try {

			// HeaderData headerData = new HeaderData();

			// Not Required for adapter call in this use case;

			// headerData.setDevice("iPhone");
			// headerData.setIpAddress("1.2.3.4");
			// headerData.setSessionInfo("session");
			// headerData.setUrl("");

			// SecurityQuestionList securityQuestionList = new
			// SecurityQuestionList();
			QuestionInfo expectedInfo = new QuestionInfo();
			expectedInfo.setQuestionCode("b01");
			expectedInfo.setQuestion("Who was your favorite teacher?");
			expectedInfo.setBrandCode("SPP");
			// securityQuestionList.getSecurityQuestionInfo().add(securityQuestionInfo);

			// SecurityRequest securityRequest = new SecurityRequest(headerData,
			// "3179566118");

			UserContextRequest userContextRequest = new UserContextRequest();

			UserContext userContext = new UserContext();
			userContext.setMsisdn("3179566118");

			userContextRequest.setUserContext(userContext);

			SecretQuestionsResponse response = service
					.getSecretQuestions(userContextRequest);

			// SecurityResponse response = service
			// .getSecretQuestions(userContextRequest);

			SecretQuestions secretQuestions = response.getSecretQuestions();

			String[] questionCodes = secretQuestions.getQuestionCodes();

			String[] questions = secretQuestions.getQuestions();

			// List<QuestionInfo> questionList = response
			// .getSecurityQuestionList();

			// QuestionInfo actualInfo = questionList.get(0);

			System.out.println("Expected Question= "
					+ expectedInfo.getQuestion() + ":: Actual Question="
					+ questions[0]);
			System.out.println("Expected QuestionCode= "
					+ expectedInfo.getQuestionCode()
					+ ":: Actual QuestionCode=" + questionCodes[0]);

			// System.out.println("Expected BrandCode= "
			// + expectedInfo.getBrandCode() + ":: Actual Question="
			// + actualInfo.getBrandCode());

			Assert.assertEquals(expectedInfo.getQuestion(), questions[0]);
			// Assert.assertEquals(expectedInfo.getBrandCode(),
			// actualInfo.getBrandCode());
			Assert.assertEquals(expectedInfo.getQuestionCode(),
					questionCodes[0]);
			
			// success handling assert
			Assert.assertEquals(Integer.valueOf(0), response.getStatusMessageResponse().getStatusMessage().getStatus());

			/*
			 * SecurityResponse actualResponse = new
			 * SecurityResponse(securityQuestionList);
			 * 
			 * Assert.assertEquals(response.getSecurityQuestionList()
			 * .getSecurityQuestionInfo().get(0).getQuestionCode(),
			 * actualResponse.getSecurityQuestionList()
			 * .getSecurityQuestionInfo().get(0).getQuestionCode());
			 */

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
}
