package com.ericsson.cac.sprint.adapters;

/**
 * 
 */

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amdocs.dc.api.sensitivedetails.BillingAddressType;
import com.amdocs.dc.api.sensitivedetails.CardSensitiveDetails;
import com.amdocs.mfs.api.epp.v1.sprint.encryption.OperationResponse;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.AuthorizePaymentRequest;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.AuthorizePaymentResponse;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.CancelAuthorizePaymentRequest;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.CancelAuthorizePaymentResponse;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.GeneratePreOrderTokenRequest;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.GeneratePreOrderTokenResponse;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationrequestresponsetypes.PaymentOperationType;
import com.amdocs.mfs.api.epp.v1.sprint.walletoperationinnerrequestresponsetypes.BanInfo;
import com.amdocs.mfs.api.epp.v1.sprint.walletoperationinnerrequestresponsetypes.ValidatePaymentOptionRequest;
import com.amdocs.mfs.api.epp.v1.sprint.walletoperationinnerrequestresponsetypes.ValidatePaymentOptionResponse;
import com.amdocs.mfs.transit.AuthenticatedPlaintextMessage;
import com.amdocs.mfs.transit.AuthenticatedPlaintextPart;
import com.amdocs.mfs.transit.EncryptedMessage;
import com.amdocs.mfs.transit.LocalTransitKeyManager;
import com.amdocs.mfs.transit.PlaintextPart;
import com.amdocs.mfs.transit.TransitCryptoKeyPairing;
import com.amdocs.mfs.transit.TransitHelper;
import com.amdocs.mfs.transit.TransitProtocol;
import com.ericsson.cac.sprint.adapters.common.EPPUtility;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.enterprisepaymentservice.v1.enterprisepaymentservice.Faultmessage;

/**
 * @author ebabadd
 * 
 */
@Component
public class EPPService {
	
	private Logger logger = LoggerFactory.getLogger(EPPService.class);

	@Autowired
	EnterpriseWalletProxyService enterpriseWalletProxyService;

	@Autowired
	EnterprisePaymentProxyService enterprisePaymentProxyService;

	Holder<WsMessageHeaderType> head = null;

	TransitProtocol transitProtocol = null; // as of now null

	public static HashMap<String, String> key_namehash = new HashMap<String, String>();

	public CancelAuthorizePaymentResponse cancelAuthorizePayment(BanInfo ban, CancelAuthorizePaymentRequest request)
			throws DatatypeConfigurationException, JAXBException, Faultmessage,
			TransformerFactoryConfigurationError, TransformerException {

		head = EPPUtility.populateHeader();

		TransitHelper helper = transitProtocol.getHelper();

		String peerAName = "Uat1EppNpci";

		// request.setReferenceId("KHK2495977501425924217551");
		request.setPaymentId("v7LLMZ3q0wVanShU74rnGpG1D7FVlU6hk83Z");

		JAXBContext jaxbContext = JAXBContext
				.newInstance(CancelAuthorizePaymentRequest.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(request, sw);

		String plainTextPeerA = sw.toString();

		PlaintextPart plaintextParta = helper
				.createPlaintextPart(
						peerAName,
						"http://api.mfs.amdocs.com/epp/v1/sprint/paymentOperationInnerRequestResponseTypes.xsd#cancelAuthorizePaymentRequest",
						plainTextPeerA);

		EncryptedMessage req = transitProtocol.encryptRequest(
				PaymentOperationType.CANCEL_AUTHORIZE_PAYMENT.value(),
				plaintextParta);

		if (req != null) {

			logger.debug("created encryted msg:" + req.getId() + " type:"
					+ req.getType() + " part:" + req.getParts());
		}

		String endpoint = "http://CA00061770:7088/mockEnterprisePaymentBinding";
		endpoint = System.getProperty("endpoint");

		// EnterprisePayAdatpor proxy =
		// EnterprisePayAdatpor.getInstance(endpoint, "", "");

		com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage encryptedMessage = new com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage();
		EPPUtility.copyMessge(req, encryptedMessage);
		EPPUtility.convertObjToxml(encryptedMessage);

		OperationResponse response = enterprisePaymentProxyService
				.processPayment(encryptedMessage, head);

		EncryptedMessage encryptedResponse = new EncryptedMessage();
		EPPUtility.copyResMessgeToSecurityMsg(encryptedResponse,
				response.getEncryptedMessage());
		AuthenticatedPlaintextMessage apMsg = transitProtocol.decryptResponse(
				encryptedResponse, req.metadata(), helper.peerNames(peerAName));

		String resType = apMsg.getType();
		if (resType != null) {
			logger.debug("resType:" + resType);
		}

		AuthenticatedPlaintextPart apPartFromPeerA = apMsg
				.partFromPeer(peerAName);

		resType = apPartFromPeerA.getType();
		if (resType != null) {
			logger.debug("resType Peer A:" + resType);
		}
		// resType = apPartFromPeerB.getType();
		if (resType != null) {
			logger.debug("resType Peer B:" + resType);
		}
		String plaintextFromPeerA = helper
				.getPlaintextAsString(apPartFromPeerA);

		logger.debug("plain text peer A" + apPartFromPeerA);
		// logger.debug("plain text peer B" + apPartFromPeerB);

		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		// initialize StreamResult with File object to save to file
		StreamResult result = new StreamResult(new StringWriter());
		Source source = new StreamSource(new StringReader(plaintextFromPeerA));
		transformer.transform(source, result);
		String xmlString = result.getWriter().toString();
		logger.debug("decrypted:" + xmlString);

		jaxbContext = JAXBContext
				.newInstance(CancelAuthorizePaymentResponse.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		StringReader reader = new StringReader(plaintextFromPeerA);
		CancelAuthorizePaymentResponse validateResponse = (CancelAuthorizePaymentResponse) unmarshaller
				.unmarshal(reader);
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

	public AuthorizePaymentResponse authorizePayment(BanInfo ban, AuthorizePaymentRequest request, CardSensitiveDetails details)
			throws JAXBException, TransformerFactoryConfigurationError,
			TransformerException, Faultmessage, DatatypeConfigurationException {

		head = EPPUtility.populateHeader();
		TransitHelper helper = transitProtocol.getHelper();
		String peerAName = "Uat1EppNpci";
		String peerBName = "Uat1EppPci";
		request.setExternalOrderId("KHK_TEST_ORDER_001");
		String requestId = UUID.randomUUID().toString();

		JAXBContext jaxbContext = JAXBContext
				.newInstance(AuthorizePaymentRequest.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(request, sw);

		String plainTextPeerA = sw.toString();

		BillingAddressType type = new BillingAddressType();
		type.setAddressLine1("DOUG");
		type.setAddressLine2("CHASE");
		type.setCity("3 Main St");
		type.setState("NY");
		type.setCountry("us");
		type.setZipCode("10453");

		details.setBillingAddress(type);

		jaxbContext = JAXBContext.newInstance(CardSensitiveDetails.class);
		jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		sw = new StringWriter();
		jaxbMarshaller.marshal(details, sw);

		String plainTextPeerB = sw.toString();

		PlaintextPart plaintextParta = helper
				.createPlaintextPart(
						peerAName,
						"http://api.mfs.amdocs.com/epp/v1/sprint/paymentOperationInnerRequestResponseTypes.xsd#authorizePaymentRequest",
						plainTextPeerA);
		PlaintextPart plaintextPartb = helper.createPlaintextPart(peerBName,
				"http://api.dc.amdocs.com/SensitiveDetails/", plainTextPeerB);

		EncryptedMessage req = transitProtocol.encryptRequest(
				PaymentOperationType.AUTHORIZE_PAYMENT.value(), plaintextParta,
				plaintextPartb);

		if (req != null) {

			logger.debug("created encryted msg:" + req.getId() + " type:"
					+ req.getType() + " part:" + req.getParts());
		}

		String endpoint = "http://CA00061770:7088/mockEnterprisePaymentBinding";
		endpoint = System.getProperty("endpoint");

		com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage encryptedMessage = new com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage();
		EPPUtility.copyMessge(req, encryptedMessage);
		EPPUtility.convertObjToxml(encryptedMessage);

		OperationResponse response = enterprisePaymentProxyService
				.processPayment(encryptedMessage, head);

		EncryptedMessage encryptedResponse = new EncryptedMessage();
		EPPUtility.copyResMessgeToSecurityMsg(encryptedResponse,
				response.getEncryptedMessage());
		AuthenticatedPlaintextMessage apMsg = transitProtocol.decryptResponse(
				encryptedResponse, req.metadata(),
				helper.peerNames(peerAName, peerBName));

		String resType = apMsg.getType();
		if (resType != null) {
			logger.debug("resType:" + resType);
		}

		AuthenticatedPlaintextPart apPartFromPeerA = apMsg
				.partFromPeer(peerAName);
		resType = apPartFromPeerA.getType();
		if (resType != null) {
			logger.debug("resType Peer A:" + resType);
		}

		if (resType != null) {
			logger.debug("resType Peer B:" + resType);
		}
		String plaintextFromPeerA = helper
				.getPlaintextAsString(apPartFromPeerA);
		// String plaintextFromPeerB =
		// helper.getPlaintextAsString(apPartFromPeerB);

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

		jaxbContext = JAXBContext.newInstance(AuthorizePaymentResponse.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		StringReader reader = new StringReader(plaintextFromPeerA);
		AuthorizePaymentResponse validateResponse = (AuthorizePaymentResponse) unmarshaller
				.unmarshal(reader);
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

	public ValidatePaymentOptionResponse validatePayment(BanInfo ban, ValidatePaymentOptionRequest request, CardSensitiveDetails details)
			throws DatatypeConfigurationException,
			JAXBException,
			TransformerFactoryConfigurationError,
			TransformerException,
			com.sprint.integration.eai.services.enterprisewalletservice.v1.enterprisewalletservice.Faultmessage {

		head = EPPUtility.populateHeader();

		TransitHelper helper = transitProtocol.getHelper();

		String peerAName = "Uat1EppNpci";
		String peerBName = "Uat1EppPci";

		com.amdocs.mfs.api.epp.v1.sprint.walletcommontypes.ActorApp info = new com.amdocs.mfs.api.epp.v1.sprint.walletcommontypes.ActorApp();
		info.setActorChannel("WEB_VMU");
		info.setActorId("249597750");
		info.setSourceTransactionId("TOP_249597750_1425924217551");

		JAXBContext jaxbContext = JAXBContext
				.newInstance(ValidatePaymentOptionRequest.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(request, sw);

		String plainTextPeerA = sw.toString();

		BillingAddressType type = new BillingAddressType();
		type.setAddressLine1("DOUG");
		type.setAddressLine2("CHASE");
		type.setCity("3 Main St");
		type.setState("NY");
		type.setCountry("US");
		type.setZipCode("10453");

		details.setBillingAddress(type);

		jaxbContext = JAXBContext.newInstance(CardSensitiveDetails.class);
		jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		sw = new StringWriter();
		jaxbMarshaller.marshal(details, sw);

		String plainTextPeerB = sw.toString();

		PlaintextPart plaintextParta = helper
				.createPlaintextPart(
						peerAName,
						"http://api.mfs.amdocs.com/epp/v1/sprint/walletOperationInnerRequestResponseTypes.xsd#validatePaymentOptionRequest",
						plainTextPeerA);
		PlaintextPart plaintextPartb = helper.createPlaintextPart(peerBName,
				"http://api.dc.amdocs.com/SensitiveDetails/", plainTextPeerB);

		EncryptedMessage req = transitProtocol
				.encryptRequest(
						com.amdocs.mfs.api.epp.v1.sprint.walletoperationrequestresponsetypes.WalletOperationType.VALIDATE_PAYMENT_OPTION
								.value(), plaintextParta, plaintextPartb);

		if (req != null) {

			logger.debug("created encryted msg:" + req.getId() + " type:"
					+ req.getType() + " part:" + req.getParts());
		}

		String endpoint = "http://CA00061770:7088/mockEnterprisePaymentBinding";
		endpoint = System.getProperty("walletEndpoint");

		com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage encryptedMessage = new com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage();
		EPPUtility.copyMessge(req, encryptedMessage);
		EPPUtility.convertObjToxml(encryptedMessage);

		// OperationResponse response =
		// enterpriseWalletProxyService.processWallet(encryptedMessage, head);
		com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage response = enterpriseWalletProxyService
				.processWallet(encryptedMessage, head);

		// convertObjToxml(response.getEncryptedMessage());

		EncryptedMessage encryptedResponse = new EncryptedMessage();
		EPPUtility.copyResMessgeToSecurityMsg(encryptedResponse, response);
		AuthenticatedPlaintextMessage apMsg = transitProtocol.decryptResponse(
				encryptedResponse, req.metadata(),
				helper.peerNames(peerAName, peerBName));

		String resType = apMsg.getType();
		if (resType != null) {
			logger.debug("resType:" + resType);
		}

		AuthenticatedPlaintextPart apPartFromPeerA = apMsg
				.partFromPeer(peerAName);

		resType = apPartFromPeerA.getType();
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

		jaxbContext = JAXBContext
				.newInstance(ValidatePaymentOptionResponse.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		StringReader reader = new StringReader(plaintextFromPeerA);
		ValidatePaymentOptionResponse validateResponse = (ValidatePaymentOptionResponse) unmarshaller
				.unmarshal(reader);
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

	public GeneratePreOrderTokenResponse generatePreOrderToken(BanInfo ban, GeneratePreOrderTokenRequest request, CardSensitiveDetails details)
			throws DatatypeConfigurationException, JAXBException, Faultmessage,
			TransformerFactoryConfigurationError, TransformerException {

		head = EPPUtility.populateHeader();
		TransitHelper helper = transitProtocol.getHelper();

		String peerAName = "Uat1EppNpci";
		String peerBName = "Uat1EppPci";
		JAXBContext jaxbContext = JAXBContext
				.newInstance(GeneratePreOrderTokenRequest.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(request, sw);

		String plainTextPeerA = sw.toString();

		BillingAddressType type = new BillingAddressType();
		type.setAddressLine1("DOUG");
		type.setAddressLine2("CHASE");
		type.setCity("3 Main St");
		type.setState("NY");
		type.setCountry("US");
		type.setZipCode("10453");

		details.setBillingAddress(type);

		jaxbContext = JAXBContext.newInstance(CardSensitiveDetails.class);
		jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		sw = new StringWriter();
		jaxbMarshaller.marshal(details, sw);

		String plainTextPeerB = sw.toString();

		PlaintextPart plaintextParta = helper
				.createPlaintextPart(
						peerAName,
						"http://api.mfs.amdocs.com/epp/v1/sprint/paymentOperationInnerRequestResponseTypes.xsd#generatePreOrderTokenRequest",
						plainTextPeerA);
		PlaintextPart plaintextPartb = helper.createPlaintextPart(peerBName,
				"http://api.dc.amdocs.com/SensitiveDetails/", plainTextPeerB);

		EncryptedMessage req = transitProtocol.encryptRequest(
				PaymentOperationType.GENERATE_PRE_ORDER_TOKEN.value(),
				plaintextParta, plaintextPartb);

		if (req != null) {

			logger.debug("created encryted msg:" + req.getId() + " type:"
					+ req.getType() + " part:" + req.getParts());
		}

		String endpoint = "http://CA00061770:7088/mockEnterprisePaymentBinding";
		endpoint = System.getProperty("endpoint");

		com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage encryptedMessage = new com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage();
		EPPUtility.copyMessge(req, encryptedMessage);
		EPPUtility.convertObjToxml(encryptedMessage);

		OperationResponse response = enterprisePaymentProxyService
				.processPayment(encryptedMessage, head);

		EncryptedMessage encryptedResponse = new EncryptedMessage();
		EPPUtility.copyResMessgeToSecurityMsg(encryptedResponse,
				response.getEncryptedMessage());
		AuthenticatedPlaintextMessage apMsg = transitProtocol.decryptResponse(
				encryptedResponse, req.metadata(),
				helper.peerNames(peerAName, peerBName));

		String resType = apMsg.getType();
		if (resType != null) {
			logger.debug("resType:" + resType);
		}

		AuthenticatedPlaintextPart apPartFromPeerA = apMsg
				.partFromPeer(peerAName);

		resType = apPartFromPeerA.getType();
		if (resType != null) {
			logger.debug("resType Peer A:" + resType);
		}

		if (resType != null) {
			logger.debug("resType Peer B:" + resType);
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

		jaxbContext = JAXBContext
				.newInstance(GeneratePreOrderTokenResponse.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		StringReader reader = new StringReader(plaintextFromPeerA);
		GeneratePreOrderTokenResponse preOderResponse = (GeneratePreOrderTokenResponse) unmarshaller
				.unmarshal(reader);
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

	public void authAndCapturePayment() throws DatatypeConfigurationException,
			JAXBException, Faultmessage, TransformerFactoryConfigurationError,
			TransformerException {
		head = EPPUtility.populateHeader();
		// defaut key
		LocalTransitKeyManager keyManager = (LocalTransitKeyManager) transitProtocol
				.getKeyManager();
		String peerBName = "Uat1EppNpci";

		String myDefKeyId = keyManager.getMyDefaultKey()
				.getEphemeralPublicKey().getId();
		String peerBName_key_mem = keyManager.getLatestPeerKey(peerBName)
				.getId();
		logger.debug("peer b key from latest peer in key Manager is:"
				+ peerBName_key_mem);
		TransitHelper helper = transitProtocol.getHelper();

		String messageType = "authAndCapturePayment";
		String plaintextTypeForPeerB = "http://api.mfs.amdocs.com/epp/v1/sprint/paymentOperationInnerRequestResponseTypes.xsd#authAndCapturePaymentRequest";
		String plaintextForPeerB = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
				+ "<ns3:authAndCapturePaymentRequest\n"
				+ "    xmlns:ns2=\"http://api.mfs.amdocs.com/epp/v1/sprint/baseOperationRequest.xsd\"\n"
				+ "    xmlns=\"http://api.mfs.amdocs.com/epp/v1/sprint/paymentCommonTypes.xsd\"\n"
				+ "    xmlns:ns3=\"http://api.mfs.amdocs.com/epp/v1/sprint/paymentOperationInnerRequestResponseTypes.xsd\">\n"
				+ "<ns3:authAndCaptureInfo>\n"
				+ "        <ns2:requestId>2495977501425924217551</ns2:requestId>\n"
				+ "        <ns2:referenceId>KHK2495977501425924217551</ns2:referenceId>\n"
				+ "        <ns2:transactionFor>BILL_PAY</ns2:transactionFor>\n"
				+ "        <ns2:additionalInformation>\n"
				+ "            <ns2:PricePlanSocCode>VMU321D4G</ns2:PricePlanSocCode>\n"
				+ "        </ns2:additionalInformation>\n"
				+ "        <ns2:PaymentRequestDetail>\n"
				+ "            <ns2:walletInfo>\n"
				+ "                <ns2:PaymentOptionId>zsAFXlL8SUZYs7Ou8gSTKk2ay7DDwG0ot7zp</ns2:PaymentOptionId>\n"
				+ "            </ns2:walletInfo>\n"
				+ "            <ns2:amounts>\n"
				+ "                <totalAmount>21.58</totalAmount>\n"
				+ "                <taxAmount>1.58</taxAmount>\n"
				+ "                <taxInfo>\n"
				+ "                    <taxTransactionIdList>\n"
				+ "                        <taxTransactionId>1000094330</taxTransactionId>\n"
				+ "                    </taxTransactionIdList>\n"
				+ "                    <taxInvoiceDate>2015-03-09-05:00</taxInvoiceDate>\n"
				+ "                    <zipCode>49001</zipCode>\n"
				+ "                </taxInfo>\n"
				+ "                <postableAmount>20.00</postableAmount>\n"
				+ "            </ns2:amounts>\n"
				+ "        </ns2:PaymentRequestDetail>\n"
				+ "    </ns3:authAndCaptureInfo>";

		PlaintextPart plaintextPartb = helper.createPlaintextPart(myDefKeyId,
				peerBName_key_mem, plaintextTypeForPeerB, plaintextForPeerB);

		TransitCryptoKeyPairing j = keyManager.getKeyPairing(myDefKeyId,
				peerBName_key_mem, false);
		if (j != null) {
			logger.debug("TransitCryptoKeyPairing:"
					+ j.getMyNames().size());
		}

		EncryptedMessage req = transitProtocol.encryptRequest(messageType,
				plaintextPartb);
		if (req != null) {
			logger.debug("created encryted msg:" + req.getId() + " type:"
					+ req.getType() + " part:" + req.getParts());
		}
		String endpoint = "http://CA00061770:7088/mockEnterprisePaymentBinding";

		com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage encryptedMessage = new com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage();
		EPPUtility.copyMessge(req, encryptedMessage);
		EPPUtility.convertObjToxml(encryptedMessage);

		OperationResponse response = enterprisePaymentProxyService
				.processPayment(encryptedMessage, head);
	}
}
