package com.ericsson.cac.sprint.test.shop.epp.workflow;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.amdocs.mfs.api.epp.v1.sprint.walletcommontypes.ActorApp;
import com.ericsson.cac.sprint.shop.workflow.datamodel.QueryChannelPolicyRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.QueryChannelPolicyResponse;
import com.ericsson.cac.sprint.shop.workflow.impl.ShopEppWorkFlowImpl;
import com.ericsson.cac.sprint.test.shop.workflow.TestConfig;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class GetQueryChannelPolicyTest {
	
	@Autowired
	private ShopEppWorkFlowImpl service;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GetQueryChannelPolicyTest.class);
	
	@Test
	 public void testgetQueryChannelPolicy() {

	  try {
	  // CommonObjectCreator objectCreator=new CommonObjectCreator();
	   QueryChannelPolicyRequest request= new QueryChannelPolicyRequest();
	   ActorApp actorApp= new ActorApp();
	   actorApp.setSourceTransactionId("1000000000");
	   request.setApplicationInfo(actorApp);
	   QueryChannelPolicyResponse response=service.getQueryChannelPolicy("bst");
	   System.out.println(new Gson().toJson(response));
	  // Assert.assertEquals(Integer.valueOf(0), response.getStatus());

	  } catch (Exception e) {
	   Assert.fail(e.getMessage());
	  }
	 }

}
