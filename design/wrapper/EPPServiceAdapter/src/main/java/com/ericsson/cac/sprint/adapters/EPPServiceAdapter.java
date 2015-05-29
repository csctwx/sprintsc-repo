package com.ericsson.cac.sprint.adapters;



import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Holder;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amdocs.dc.api.sensitivedetails.CardSensitiveDetails;
import com.amdocs.mfs.api.epp.v1.sprint.encryption.OperationResponse;
import com.amdocs.mfs.api.epp.v1.sprint.paymentcommontypes.BanInfoAC;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.AuthorizePaymentRequest;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.AuthorizePaymentResponse;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.CancelAuthorizePaymentRequest;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.CancelAuthorizePaymentResponse;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.CapturePaymentRequest;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.CapturePaymentResponse;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.GeneratePreOrderTokenRequest;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.GeneratePreOrderTokenResponse;
import com.amdocs.mfs.api.epp.v1.sprint.walletoperationinnerrequestresponsetypes.ValidatePaymentOptionRequest;
import com.amdocs.mfs.api.epp.v1.sprint.walletoperationinnerrequestresponsetypes.ValidatePaymentOptionResponse;
import com.amdocs.mfs.transit.AuthenticatedPlaintextMessage;
import com.amdocs.mfs.transit.AuthenticatedPlaintextPart;
import com.amdocs.mfs.transit.EncryptedMessage;
import com.amdocs.mfs.transit.TransitHelper;
import com.amdocs.mfs.transit.TransitProtocol;
import com.ericsson.cac.sprint.adapters.common.EPPUtility;
import com.ericsson.cac.sprint.adapters.common.OperationResponseType;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.enterprisewalletservice.v1.enterprisewalletservice.Faultmessage;

@Component
public class EPPServiceAdapter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EPPServiceAdapter.class);
	
	@Autowired
	private EnterpriseWalletProxyService enterpriseWalletProxyService;

	@Autowired
	private EnterprisePaymentProxyService enterprisePaymentProxyService;
	
	@Autowired
	private EPPUtility eppUtility;
	
	@Autowired
	private KeyExchangeManager keyExchangeManager;
	
	Holder<WsMessageHeaderType> head = null;

	private static final String PEER_EPP_NPCI = "Uat1EppNpci";
	
	public ValidatePaymentOptionResponse validatePayment(BanInfoAC ban, ValidatePaymentOptionRequest request, Object details)
			throws DatatypeConfigurationException,
			JAXBException,
			TransformerFactoryConfigurationError,
			TransformerException,
			com.sprint.integration.eai.services.enterprisewalletservice.v1.enterprisewalletservice.Faultmessage, IOException {

		TransitProtocol transitProtocol = keyExchangeManager.getTransitProtocol();
		head = eppUtility.populateHeader();

		TransitHelper helper = transitProtocol.getHelper();
		
		EncryptedMessage req = eppUtility.generateEncryptedMessage(ban, request, details, helper, transitProtocol);
		com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage encryptedMessage = new com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage();
		EPPUtility.copyMessge(req, encryptedMessage);
		if (logger.isInfoEnabled()) {
			logger.info("validatePayment(BanInfoAC, ValidatePaymentOptionRequest, Object) - com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage encryptedMessage=" + EPPUtility.marshallObject(encryptedMessage));
		}
		String plaintextFromPeerA = generateWalletResponse(helper, req,
				encryptedMessage);

		ValidatePaymentOptionResponse validateResponse = (ValidatePaymentOptionResponse) EPPUtility.unmarshallObject(plaintextFromPeerA, OperationResponseType.WALLET);
		logger.debug("code:"
				+ validateResponse.getResponses().getResponse()
						.getResponseCode());
		logger.debug("message:"
				+ validateResponse.getResponses().getResponse()
						.getResponseMessage());
		logger.debug("details:"
				+ validateResponse.getResponses().getResponse()
						.getResponseDescription());
		return validateResponse;
	}

	
	
	public CancelAuthorizePaymentResponse cancelAuthorizePayment(BanInfoAC ban, CancelAuthorizePaymentRequest request)
			throws DatatypeConfigurationException, JAXBException, TransformerFactoryConfigurationError, TransformerException, com.sprint.integration.eai.services.enterprisepaymentservice.v1.enterprisepaymentservice.Faultmessage, IOException {
		TransitProtocol transitProtocol = keyExchangeManager.getTransitProtocol();
		head = eppUtility.populateHeader();

		TransitHelper helper = transitProtocol.getHelper();

		EncryptedMessage req = eppUtility.generateEncryptedMessage(ban, request, null, helper, transitProtocol);
		com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage encryptedMessage = new com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage();
		EPPUtility.copyMessge(req, encryptedMessage);

		String plaintextFromPeerA = generatePaymentResponse(helper, req,
				encryptedMessage);
		
		CancelAuthorizePaymentResponse validateResponse = (CancelAuthorizePaymentResponse) EPPUtility.unmarshallObject(plaintextFromPeerA, OperationResponseType.PAYMENT);
		logger.debug("code:"
				+ validateResponse.getResponses().getResponse()
						.getResponseCode());
		logger.debug("message:"
				+ validateResponse.getResponses().getResponse()
						.getResponseMessage());
		logger.debug("details:"
				+ validateResponse.getResponses().getResponse()
						.getResponseDescription());
		return validateResponse;
	}	
	
	public AuthorizePaymentResponse authorizePayment(BanInfoAC ban, AuthorizePaymentRequest request, CardSensitiveDetails details)
			throws JAXBException, TransformerFactoryConfigurationError,
			TransformerException, Faultmessage, DatatypeConfigurationException, com.sprint.integration.eai.services.enterprisepaymentservice.v1.enterprisepaymentservice.Faultmessage, IOException {
		TransitProtocol transitProtocol = keyExchangeManager.getTransitProtocol();
		head = eppUtility.populateHeader();
		TransitHelper helper = transitProtocol.getHelper();
		
		EncryptedMessage req = eppUtility.generateEncryptedMessage(ban, request, details, helper, transitProtocol);
		com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage encryptedMessage = new com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage();
		EPPUtility.copyMessge(req, encryptedMessage);

		String plaintextFromPeerA = generatePaymentResponse(helper, req,
				encryptedMessage);

			
		AuthorizePaymentResponse authorizePaymentResponse = (AuthorizePaymentResponse) EPPUtility.unmarshallObject(plaintextFromPeerA, OperationResponseType.PAYMENT);
		logger.debug("code:"
				+ authorizePaymentResponse.getResponses().getResponse()
						.getResponseCode());
		logger.debug("message:"
				+ authorizePaymentResponse.getResponses().getResponse()
						.getResponseMessage());
		logger.debug("details:"
				+ authorizePaymentResponse.getResponses().getResponse()
						.getResponseDescription());

		return authorizePaymentResponse;
	}
	
	public GeneratePreOrderTokenResponse generatePreOrderToken(BanInfoAC ban, GeneratePreOrderTokenRequest request, CardSensitiveDetails details)
			throws DatatypeConfigurationException, JAXBException, Faultmessage,
			TransformerFactoryConfigurationError, TransformerException, com.sprint.integration.eai.services.enterprisepaymentservice.v1.enterprisepaymentservice.Faultmessage, IOException {
		TransitProtocol transitProtocol = keyExchangeManager.getTransitProtocol();
		head = eppUtility.populateHeader();
		TransitHelper helper = transitProtocol.getHelper();

		EncryptedMessage req = eppUtility.generateEncryptedMessage(ban, request, details, helper, transitProtocol);
		com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage encryptedMessage = new com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage();
		EPPUtility.copyMessge(req, encryptedMessage);

		String plaintextFromPeerA = generatePaymentResponse(helper, req,
				encryptedMessage);

		GeneratePreOrderTokenResponse preOderResponse = (GeneratePreOrderTokenResponse) EPPUtility.unmarshallObject(plaintextFromPeerA, OperationResponseType.PAYMENT);
		logger.debug("code:"
				+ preOderResponse.getResponses().getResponse()
						.getResponseCode());
		logger.debug("message:"
				+ preOderResponse.getResponses().getResponse()
						.getResponseMessage());
		logger.debug("details:"
				+ preOderResponse.getResponses().getResponse()
						.getResponseDescription());
		return preOderResponse;
	}
	public CapturePaymentResponse capturePayment(BanInfoAC ban, CapturePaymentRequest request, CardSensitiveDetails details) throws JAXBException, com.sprint.integration.eai.services.enterprisepaymentservice.v1.enterprisepaymentservice.Faultmessage, TransformerFactoryConfigurationError, TransformerException, DatatypeConfigurationException { 
		TransitProtocol transitProtocol = keyExchangeManager.getTransitProtocol();
		head = eppUtility.populateHeader();
		TransitHelper helper = transitProtocol.getHelper();

		EncryptedMessage req = eppUtility.generateEncryptedMessage(ban, request, details, helper, transitProtocol);
		com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage encryptedMessage = new com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage();
		EPPUtility.copyMessge(req, encryptedMessage);
		
		String plaintextFromPeerA = generatePaymentResponse(helper, req,
				encryptedMessage);

		CapturePaymentResponse capturePaymentResponse = (CapturePaymentResponse) EPPUtility.unmarshallObject(plaintextFromPeerA, OperationResponseType.PAYMENT);
		logger.debug("code:"+ capturePaymentResponse.getResponses().getResponse().getResponseCode());
		logger.debug("message:"	+ capturePaymentResponse.getResponses().getResponse().getResponseMessage());
		logger.debug("details:"	+ capturePaymentResponse.getResponses().getResponse().getResponseDescription());
		return capturePaymentResponse;

    }
	
	private String generatePaymentResponse(
			TransitHelper helper,
			EncryptedMessage req,
			com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage encryptedMessage)
			throws com.sprint.integration.eai.services.enterprisepaymentservice.v1.enterprisepaymentservice.Faultmessage, TransformerFactoryConfigurationError, TransformerException {
		TransitProtocol transitProtocol = keyExchangeManager.getTransitProtocol();
		OperationResponse response = enterprisePaymentProxyService.processPayment(encryptedMessage, head);

		EncryptedMessage encryptedResponse = new EncryptedMessage();
		EPPUtility.copyResMessgeToSecurityMsg(encryptedResponse, response.getEncryptedMessage());
		
		AuthenticatedPlaintextMessage apMsg = transitProtocol.decryptResponse(
				encryptedResponse, req.metadata(),
				helper.peerNames(PEER_EPP_NPCI));

		AuthenticatedPlaintextPart apPartFromPeerA = apMsg
				.partFromPeer(PEER_EPP_NPCI);

		String resType = apPartFromPeerA.getType();
		if (resType != null) {
			logger.debug("resType Peer A:" + resType);
		}

		String plaintextFromPeerA = helper
				.getPlaintextAsString(apPartFromPeerA);

		logger.debug("plain text peer A" + apPartFromPeerA);

		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		StreamResult result = new StreamResult(new StringWriter());
		Source source = new StreamSource(new StringReader(plaintextFromPeerA));
		transformer.transform(source, result);
		String xmlString = result.getWriter().toString();
		logger.debug("decrypted:" + xmlString);
		return plaintextFromPeerA;
	}
	
	private String generateWalletResponse(
			TransitHelper helper,
			EncryptedMessage req,
			com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage encryptedMessage)
			throws Faultmessage, TransformerConfigurationException,
			TransformerFactoryConfigurationError, TransformerException {
		com.amdocs.mfs.api.epp.v1.sprint.encryption.OperationResponse response = enterpriseWalletProxyService
				.processWallet(encryptedMessage, head);
		TransitProtocol transitProtocol = keyExchangeManager.getTransitProtocol();
		EncryptedMessage encryptedResponse = new EncryptedMessage();
		EPPUtility.copyResMessgeToSecurityMsg(encryptedResponse, response.getEncryptedMessage());
		
		AuthenticatedPlaintextMessage apMsg = transitProtocol.decryptResponse(
				encryptedResponse, req.metadata(),
				helper.peerNames(PEER_EPP_NPCI));

		AuthenticatedPlaintextPart apPartFromPeerA = apMsg
				.partFromPeer(PEER_EPP_NPCI);

		String resType = apPartFromPeerA.getType();
		if (resType != null) {
			logger.debug("resType Peer A:" + resType);
		}

		String plaintextFromPeerA = helper
				.getPlaintextAsString(apPartFromPeerA);

		logger.debug("plain text peer A" + apPartFromPeerA);

		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		// initialize StreamResult with File object to save to file
		StreamResult result = new StreamResult(new StringWriter());
		Source source = new StreamSource(new StringReader(plaintextFromPeerA));
		transformer.transform(source, result);
		String xmlString = result.getWriter().toString();
		logger.debug("decrypted:" + xmlString);
		return plaintextFromPeerA;
	}
	
}
