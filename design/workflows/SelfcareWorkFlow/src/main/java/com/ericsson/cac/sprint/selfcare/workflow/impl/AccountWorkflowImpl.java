package com.ericsson.cac.sprint.selfcare.workflow.impl;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ericsson.cac.sprint.adapters.QueryCsaProxyService;
import com.ericsson.cac.sprint.adapters.QueryResourceInfoProxyService;
import com.ericsson.cac.sprint.selfcare.workflow.AccountWorkflow;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.CartRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.CartResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.CoverageAreaRequest;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.CoverageAreaResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.StatusMessageResponse;
import com.ericsson.cac.sprint.selfcare.workflow.datamodel.UserContextRequest;
import com.ericsson.cac.sprint.selfcare.workflow.util.AlarmUtil;
import com.ericsson.cac.sprint.selfcare.workflow.util.HeaderHandler;
import com.ericsson.cac.sprint.selfcare.workflow.util.Service;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.querycsa.v1.querycsa.Faultmessage2;
import com.sprint.integration.eai.services.queryresourceinfoservice.v1.queryresourceinfoservice.SoapFaultv2;
import com.sprint.integration.interfaces.queryavailablenpacsa.v1.queryavailablenpacsa.NpaCsaQueryInfoType;
import com.sprint.integration.interfaces.queryavailablenpacsa.v1.queryavailablenpacsa.QueryAvailableNpaCsaResponseType;
import com.sprint.integration.interfaces.queryavailablenpacsa.v1.queryavailablenpacsa.QueryAvailableNpaCsaType;
import com.sprint.integration.interfaces.querycsa.v2.querycsav2.GeocodingTypeCodeType;
import com.sprint.integration.interfaces.querycsa.v2.querycsav2.PostalCodeType;
import com.sprint.integration.interfaces.querycsa.v2.querycsav2.QueryCsaV2ResponseType;
import com.sprint.integration.interfaces.querycsa.v2.querycsav2.QueryCsaV2Type;

@Component
public class AccountWorkflowImpl implements AccountWorkflow {

	/** Logger class info */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AccountWorkflowImpl.class);

	@Autowired
	private HeaderHandler headerHandler;
	
	@Autowired
	QueryCsaProxyService csaProxyService;
	
	@Autowired
	private QueryResourceInfoProxyService queryResourceInfoProxyService;
	

	@Override
	public CoverageAreaResponse checkCoverageArea(CoverageAreaRequest request) {LOGGER.info("checkCoverageArea");
    String requestCheckCoverageArea = request.getZipCode();
    CoverageAreaResponse coverageAreaResponse = new CoverageAreaResponse();
    Holder<WsMessageHeaderType> header;
    if (!StringUtils.isEmpty(requestCheckCoverageArea)) {
       
        try {                
            header = headerHandler.getHeader(Service.QUERY_CSA_PROXY_SERVICE);
            QueryCsaV2Type queryCsaV2Type = new QueryCsaV2Type();
            PostalCodeType postalCodeType = new PostalCodeType();
            queryCsaV2Type.setGeoCode(GeocodingTypeCodeType.ZIP);
            postalCodeType.setUspsPostalCd(request.getZipCode());               
            queryCsaV2Type.setZip(postalCodeType);
            QueryCsaV2ResponseType queryCsaV2ResponseType = csaProxyService.queryCsaV2(queryCsaV2Type, header);               
            coverageAreaResponse.setCsa(queryCsaV2ResponseType.getCsa());
            coverageAreaResponse.setNpa(queryCsaV2ResponseType.getNpa()); 
            coverageAreaResponse.setNxx(queryCsaV2ResponseType.getNxx());
            LOGGER.debug("CSA Value : "+queryCsaV2ResponseType.getCsa());
            LOGGER.debug("NPA Value : "+queryCsaV2ResponseType.getNpa());
            LOGGER.debug("NPA Value : "+queryCsaV2ResponseType.getNxx());
            coverageAreaResponse.setStatusmessage(0);               
        } catch (DatatypeConfigurationException datatypeConfigurationException) {
            coverageAreaResponse.setStatusmessage(704);
            LOGGER.error("DatatypeConfigurationException"+datatypeConfigurationException);
            AlarmUtil.raiseSnmpAlarm();
            coverageAreaResponse.setStatusmessage(704);               
        } catch (Faultmessage2 e) {
            LOGGER.debug("Calling queryAvailableNpaCsa method"+e);
            coverageAreaResponse = new CoverageAreaResponse();
            coverageAreaResponse = queryAvailableNpaCsa(request);
           
        }
    }else{
    	    coverageAreaResponse.setStatusmessage(704); 
          }
   
    return coverageAreaResponse;
    }

	private CoverageAreaResponse queryAvailableNpaCsa(CoverageAreaRequest request) {
		LOGGER.info("queryAvailableNpaCsa");
        String requestCheckCoverageArea = request.getZipCode();
        CoverageAreaResponse coverageAreaResponse = new CoverageAreaResponse();
        if (!StringUtils.isEmpty(requestCheckCoverageArea)) {
            Holder<WsMessageHeaderType> header;
            try {
                header = headerHandler.getHeader(Service.QUERY_CSA_PROXY_SERVICE);
                QueryAvailableNpaCsaType queryAvailableNpaCsaType = new QueryAvailableNpaCsaType();
                NpaCsaQueryInfoType npaCsaQueryInfoType = new NpaCsaQueryInfoType();
                npaCsaQueryInfoType.setZipCode(request.getZipCode());
                queryAvailableNpaCsaType.setNpaCsaQueryInfo(npaCsaQueryInfoType);
                QueryAvailableNpaCsaResponseType queryAvailableNpaCsaResponseType = queryResourceInfoProxyService.queryAvailableNpaCsa(queryAvailableNpaCsaType, header);
                coverageAreaResponse.setNpa(queryAvailableNpaCsaResponseType.getNpaCsaInfoList().getNpaCsaInfo().get(0).getNpa());
                LOGGER.debug("NPA Value : "+queryAvailableNpaCsaResponseType.getNpaCsaInfoList().getNpaCsaInfo().get(0).getNpa());
                coverageAreaResponse.setStatusmessage(0);
            } catch (DatatypeConfigurationException | SoapFaultv2 datatypeConfigurationException) {
                LOGGER.error("DatatypeConfigurationException queryAvailableNpaCsa method"+datatypeConfigurationException);
                AlarmUtil.raiseSnmpAlarm();
                coverageAreaResponse.setStatusmessage(704);
            }
           
        }   
        return coverageAreaResponse;
	}

	@Override
	public StatusMessageResponse clearCart(UserContextRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CartResponse addToCart(CartRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CartResponse getCart(UserContextRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
