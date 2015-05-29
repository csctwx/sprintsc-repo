package com.ericsson.cac.sprint.selfcare.workflow;

import com.ericsson.cac.sprint.selfcare.workflow.datamodel.Address;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.ChangeNumberResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.DeviceInsuranceResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.DeviceResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.ESNSwapEligibleRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.ESNSwapEligibleResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.ESNSwapPrepareRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.ESNSwapPrepareResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.SwapEsnResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;

public interface AccountDeviceWorkflow {

	public DeviceResponse getDevice(UserContextRequest request);

	public DeviceInsuranceResponse getDeviceInsurance(UserContextRequest request);

	public StatusMessageResponse suspendAccount(UserContextRequest request);

	public ESNSwapEligibleResponse checkEsnSwap(ESNSwapEligibleRequest esnSwaprequest, UserContextRequest request);

	public ESNSwapPrepareResponse prepareEsnSwap(ESNSwapPrepareRequest request);

	public SwapEsnResponse swapESN(ESNSwapPrepareRequest request);

	public ChangeNumberResponse changeNumber(Address request1,	UserContextRequest request);
	
}
