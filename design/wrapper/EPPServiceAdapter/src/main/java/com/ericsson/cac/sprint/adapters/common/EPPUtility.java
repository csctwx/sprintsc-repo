package com.ericsson.cac.sprint.adapters.common;

import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.Holder;

import org.apache.axis.encoding.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amdocs.dc.api.sensitivedetails.ACHSensitiveDetails;
import com.amdocs.dc.api.sensitivedetails.CardSensitiveDetails;
import com.amdocs.dc.api.sensitivedetails.PayPalSensitiveDetails;
import com.amdocs.mfs.api.epp.v1.sprint.paymentcommontypes.BanInfoAC;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.AuthorizePaymentRequest;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.CancelAuthorizePaymentRequest;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.CapturePaymentRequest;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationinnerrequestresponsetypes.GeneratePreOrderTokenRequest;
import com.amdocs.mfs.api.epp.v1.sprint.paymentoperationrequestresponsetypes.PaymentOperationType;
import com.amdocs.mfs.api.epp.v1.sprint.walletoperationinnerrequestresponsetypes.ValidatePaymentOptionRequest;
import com.amdocs.mfs.transit.EncryptedMessage;
import com.amdocs.mfs.transit.EncryptedPart;
import com.amdocs.mfs.transit.PlaintextPart;
import com.amdocs.mfs.transit.TransitHelper;
import com.amdocs.mfs.transit.TransitProtocol;
import com.sprint.integration.common.header.wsmessageheader.v2.TrackingMessageHeaderType;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;

@Component
public class EPPUtility {
	
	private static final Logger logger = Logger.getLogger(EPPUtility.class);
	
	@Value("${EppService.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${EppService.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${EppService.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${EppService.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${EppService.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${EppService.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${EppService.PEER_EAI}")
	private String peer_eai;
	@Value("${EppService.PEER_EPP_NPCI}")
	private String peer_epp_npci;
	@Value("${EppService.PEER_EPP_PCI}")
	private String peer_epp_pci;
	@Value("${EppService.paymentUrl}")
	private String paymentUrlText;
	@Value("${EppService.walletUrl}")
	private String walletUrlText;
	@Value("${EppService.sensativedetailsurl}")
	private String sensativedetailsurl;
	
	public EncryptedMessage generateEncryptedMessage(BanInfoAC ban, Object request, Object details, TransitHelper helper, TransitProtocol transitProtocol)
	throws JAXBException{
		String plainTextPeerEai = null;
		String plainTextPeerNPci = null;
		String plainTextPeerPci = null;
		PlaintextPart plainTextPartPeerEai = null;
		PlaintextPart plainTextPartPeerNPci = null;
		PlaintextPart plainTextPartPeerPci = null;
		String PEER_EAI = peer_eai;
		String PEER_EPP_NPCI = peer_epp_npci;
		String PEER_EPP_PCI = peer_epp_pci;
		String payment_option = null;
		String url = null;
		
		if(request!=null){
			if(request instanceof ValidatePaymentOptionRequest){
				request = (ValidatePaymentOptionRequest)request;
				url = walletUrlText;
				url+="validatePaymentOptionRequest";
				payment_option = com.amdocs.mfs.api.epp.v1.sprint.walletoperationrequestresponsetypes.WalletOperationType.VALIDATE_PAYMENT_OPTION.value();
			}else if(request instanceof GeneratePreOrderTokenRequest){
				request = (GeneratePreOrderTokenRequest)request;
				url = paymentUrlText;
				url+="generatePreOrderTokenRequest";
				payment_option = PaymentOperationType.GENERATE_PRE_ORDER_TOKEN.value();
			}else if(request instanceof AuthorizePaymentRequest){
				request = (AuthorizePaymentRequest)request;
				url = paymentUrlText;
				url+="authorizePaymentRequest";
				payment_option = PaymentOperationType.AUTHORIZE_PAYMENT.value();
			}else if(request instanceof CapturePaymentRequest){
				request = (CapturePaymentRequest)request;
				url = paymentUrlText;
				url+="capturePaymentRequest";
				payment_option = PaymentOperationType.CAPTURE_PAYMENT.value();
			}else if(request instanceof CancelAuthorizePaymentRequest){
				request = (CancelAuthorizePaymentRequest)request;
				url = paymentUrlText;
				url+="cancelAuthorizePaymentRequest";
				payment_option = PaymentOperationType.CANCEL_AUTHORIZE_PAYMENT.value();
			}
		}
		
		
		if(ban!=null){
			plainTextPeerEai = EPPUtility.marshallObject(ban);
			plainTextPartPeerEai = helper
					.createPlaintextPart(
							PEER_EAI,
							"EAIBanInfo",
							plainTextPeerEai);
		}

		
		if(request != null){
			plainTextPeerNPci = EPPUtility.marshallObject(request);
			plainTextPartPeerNPci = helper
					.createPlaintextPart(
							PEER_EPP_NPCI,
							url,
							plainTextPeerNPci);
		}
		
		if(details != null){
			plainTextPeerPci = EPPUtility.marshallObject(details);
			plainTextPartPeerPci = helper.createPlaintextPart(PEER_EPP_PCI,
					sensativedetailsurl, plainTextPeerPci);
		}
		EncryptedMessage req = null;

		if(plainTextPartPeerEai != null && plainTextPartPeerPci !=null){
			req = transitProtocol
					.encryptRequest(
							payment_option, plainTextPartPeerEai, plainTextPartPeerNPci,plainTextPartPeerPci);
		} else if(plainTextPartPeerEai != null){
			req = transitProtocol
					.encryptRequest(
							payment_option, plainTextPartPeerEai, plainTextPartPeerNPci);
		} else if(plainTextPartPeerPci!=null){
			req = transitProtocol
					.encryptRequest(
							payment_option, plainTextPartPeerNPci,plainTextPartPeerPci);
		} else {
			req = transitProtocol
					.encryptRequest(
							payment_option, plainTextPartPeerNPci);
		}
		
		if (req != null) {

			logger.debug("created encryted msg:" + req.getId() + " type:"
					+ req.getType() + " part:" + req.getParts());
			
		}

		
		return req;
		
	}
	
	public Holder<WsMessageHeaderType> populateHeader() throws DatatypeConfigurationException{
		String s = UUID.randomUUID().toString();
		s = s.substring(0, s.indexOf("-"));
		TrackingMessageHeaderType trackingHead = new TrackingMessageHeaderType();
		
		trackingHead.setApplicationId(trackingHeadAppId);
		trackingHead.setApplicationUserId(trackingHeadAppUsrId);
		trackingHead.setConsumerId(trackingHeadConsumerId);
		trackingHead.setMessageId(trackingHeadMessageId+s);
		trackingHead.setConversationId(trackingHeadConversationId+s);
		trackingHead.setTimeToLive(new BigInteger(trackingHeadTimeToLive));
		GregorianCalendar c = new GregorianCalendar();
		trackingHead.setMessageDateTimeStamp(DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(c));
		WsMessageHeaderType successHead = new WsMessageHeaderType();
		successHead.setTrackingMessageHeader(trackingHead);
		Holder<WsMessageHeaderType> successHeader = new Holder<WsMessageHeaderType>(successHead);
		return successHeader;
	}
	
	public static void copyMessge(
			EncryptedMessage req,
			com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage encryptedMessage) {
		encryptedMessage.setCreated(req.getCreated());
		encryptedMessage.setId(req.getId());
		encryptedMessage.setType(req.getType());
		List<com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedPart> list = copyEncrypt(req
				.getParts());
		encryptedMessage.getEncryptedPart().addAll(list);
	}

	private static List<com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedPart> copyEncrypt(
			EncryptedPart[] parts) {
		List<com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedPart> list = new ArrayList<com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedPart>();

		if (parts != null) {
			for (int i = 0; i < parts.length; i++) {
				com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedPart part = new com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedPart();
				part.setId(parts[i].getId());
				part.setCreated(parts[i].getCreated());
				part.setType(parts[i].getType());
				part.setIv(convertByteToString(parts[i].getIv()));
				part.setValue(convertByteToString(parts[i].getCiphertext()));
				part.setMac(convertByteToString(parts[i].getMac()));
				part.setRecipientKeyId(parts[i].getRecipientKeyId());
				part.setSenderKeyId(parts[i].getSenderKeyId());
				// part.setValue(parts[i].getCiphertext());
				list.add(part);
			}
		}
		return list;
	}

	public static void copyResMessgeToSecurityMsg(
			EncryptedMessage encryptedMessage,
			com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage responseMsg) {
		encryptedMessage.setCreated(responseMsg.getCreated());
		encryptedMessage.setId(responseMsg.getId());
		encryptedMessage.setType(responseMsg.getType());
		EncryptedPart[] array = copyResonseMessageToArray(responseMsg
				.getEncryptedPart());
		encryptedMessage.setParts(array);
	}

	private static EncryptedPart[] copyResonseMessageToArray(
			List<com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedPart> list) {
		EncryptedPart[] parts = null;

		if (list != null && list.size() > 0) {
			parts = new EncryptedPart[list.size()];
			int i = 0;
			if (parts != null) {
				for (com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedPart encryptedPart : list) {
					EncryptedPart part = new EncryptedPart();
					part.setId(encryptedPart.getId());
					part.setCreated(encryptedPart.getCreated());
					part.setType(encryptedPart.getType());
					part.setIv(Base64.decode(encryptedPart.getIv()));
					part.setCiphertext(Base64.decode(encryptedPart.getValue()));
					// part.setMac(Base64.encode(encryptedPart.getMac().getBytes()).getBytes());
					part.setMac(Base64.decode(encryptedPart.getMac()));

					part.setRecipientKeyId(encryptedPart.getRecipientKeyId());
					part.setSenderKeyId(encryptedPart.getSenderKeyId());

					parts[i] = part;
					i++;
				}

			}
		}

		return parts;
	}

	public static String convertByteToString(byte[] bytes) {

		return Base64.encode(bytes);
	}

	public static String convertByteToStringBase64(byte[] bytes) {

		return new String(bytes);
	}

	public static String marshallObject(Object o) throws JAXBException{
		JAXBContext jaxbContext = JAXBContext
				.newInstance(o.getClass());
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(o, sw);
		System.out.println("=============================================================="+sw.toString());
		return sw.toString();
	}
	
	public static Object unmarshallObject(String s, OperationResponseType type) throws JAXBException{
		JAXBContext jaxbContext = JAXBContext
				.newInstance(type.getRelatedClass());
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		StringReader reader = new StringReader(s);
		return unmarshaller.unmarshal(reader);
	}


}
