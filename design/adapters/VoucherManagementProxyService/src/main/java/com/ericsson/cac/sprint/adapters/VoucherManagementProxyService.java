package com.ericsson.cac.sprint.adapters;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.vouchermanagementservice.v1.vouchermanagementservice.SoapFault;
import com.sprint.integration.eai.services.vouchermanagementservice.v1.vouchermanagementservice.VoucherManagementService;
import com.sprint.integration.eai.services.vouchermanagementservice.v1.vouchermanagementservice.VoucherManagementServicePortType;
import com.sprint.integration.interfaces.managevoucher.v1.managevoucher.ManageVoucherRequestType;
import com.sprint.integration.interfaces.managevoucher.v1.managevoucher.ManageVoucherResponseType;


@Component
public class VoucherManagementProxyService extends VoucherManagementService {
    @Autowired
    private VoucherManagementServicePortType stub;
    
	public ManageVoucherResponseType manageVoucherResp(ManageVoucherRequestType  manageVoucherReq, Holder<WsMessageHeaderType> head) throws SoapFault{
		return stub.manageVoucher(manageVoucherReq, head);
	}
		
}
