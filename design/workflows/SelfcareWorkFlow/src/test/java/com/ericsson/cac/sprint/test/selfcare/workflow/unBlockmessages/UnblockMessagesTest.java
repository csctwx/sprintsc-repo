package com.ericsson.cac.sprint.test.selfcare.workflow.unBlockmessages;

import javax.xml.datatype.DatatypeConfigurationException;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ericsson.cac.sprint.adapters.SmsPreferenceManagementProxyService;
import com.ericsson.cac.sprint.selfcare.workflow.AccountSettingsWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.BlockedMessagesRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.Recipient;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContext;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.selfcare.workflow.util.HeaderHandler;
import com.ericsson.cac.sprint.test.selfcare.workflow.TestConfig;
import com.sprint.integration.eai.services.smspreferencemanagementservice.v1.smspreferencemanagementservice.SoapFault;
import com.sprint.integration.interfaces.querysmspreferenceinfo.v1.querysmspreferenceinfo.QuerySmsPreferenceInfoResponseType;

/**
 * @author ebabadd
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UnblockMessagesTest {
 private static final Logger LOGGER = LoggerFactory.getLogger(UnblockMessagesTest.class);
 
 @Autowired
 private AccountSettingsWorkflow service;
 @Autowired
 private HeaderHandler headerHandler;
 private QuerySmsPreferenceInfoResponseType querySmsPreferenceInfoResponse;
 @Autowired
 private SmsPreferenceManagementProxyService smsPreferenceManagementProxyService;
 
 
 @Test
 public void testUnblockMessages() throws DatatypeConfigurationException, SoapFault{
  
  LOGGER.debug("Before testUnblockMessages.. ");
  UserContextRequest userContextRequest = new UserContextRequest();
  BlockedMessagesRequest blockedMessagesRequest = new BlockedMessagesRequest();
  
  UserContext userContext = new UserContext();
  userContext.setMsisdn("3179566118");
  userContextRequest.setUserContext(userContext);
  
  String sender="test@sprint.com";

  Recipient recipient = new Recipient();
  recipient.setSender(sender);
  Recipient[] recipients2 = { recipient };
  blockedMessagesRequest.setRecipients(recipients2);
  
  StatusMessageResponse response = service.unblockMessages(userContextRequest,blockedMessagesRequest);
  LOGGER.debug("After testUnbelockMessages.. "+response.toString()+"TESTTT"+response.getStatusMessage().getDescription());
  Assert.assertEquals("SUCCESS", response.getStatusMessage().getDescription());
  
 }

}
