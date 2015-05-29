package com.ericsson.cac.sprint.selfcare.workflow;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.CartRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.CartResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.CoverageAreaRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.CoverageAreaResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;

public interface AccountWorkflow {

	public CoverageAreaResponse checkCoverageArea(CoverageAreaRequest request);

	public StatusMessageResponse clearCart(UserContextRequest request);

	public CartResponse addToCart(CartRequest request);

	public CartResponse getCart(UserContextRequest request);
}
