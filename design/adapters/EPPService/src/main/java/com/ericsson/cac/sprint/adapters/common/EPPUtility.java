package com.ericsson.cac.sprint.adapters.common;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.Holder;

import org.apache.axis.encoding.Base64;
import org.springframework.beans.factory.annotation.Value;

import com.amdocs.mfs.transit.EncryptedMessage;
import com.amdocs.mfs.transit.EncryptedPart;
import com.amdocs.mfs.transit.EphemeralKeyPair;
import com.sprint.integration.common.header.wsmessageheader.v2.TrackingMessageHeaderType;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;

public class EPPUtility {
	
	@Value("${EnterprisePaymentService.trackingHeadAppId}")
	private static String trackingHeadAppId;
	@Value("${EnterprisePaymentService.trackingHeadAppUsrId}")
	private static String trackingHeadAppUsrId;
	@Value("${EnterprisePaymentService.trackingHeadConsumerId}")
	private static String trackingHeadConsumerId;
	@Value("${EnterprisePaymentService.trackingHeadTimeToLive}")
	private static String trackingHeadTimeToLive;
	@Value("${EnterprisePaymentService.trackingHeadMessageId}")
	private static String trackingHeadMessageId;
	@Value("${EnterprisePaymentService.trackingHeadConversationId}")
	private static String trackingHeadConversationId;
	@Value("${EnterprisePaymentService.trackingHeadFailAppId}")
	private String trackingHeadFailAppId;
	@Value("${EnterprisePaymentService.trackingHeadFailAppUsrId}")
	private String trackingHeadFailAppUsrId;
	@Value("${EnterprisePaymentService.trackingHeadFailConsumerId}")
	private String trackingHeadFailConsumerId;
	@Value("${EnterprisePaymentService.trackingHeadFailTimeToLive}")
	private String trackingHeadFailTimeToLive;
	
	public static Holder<WsMessageHeaderType> populateHeader() throws DatatypeConfigurationException{
		WsMessageHeaderType successHead = new WsMessageHeaderType();
		TrackingMessageHeaderType trackingHead = new TrackingMessageHeaderType();
		trackingHead.setApplicationId(trackingHeadAppId);
		trackingHead.setApplicationUserId(trackingHeadAppUsrId);
		trackingHead.setConsumerId(trackingHeadConsumerId);
		trackingHead.setTimeToLive(new BigInteger(trackingHeadTimeToLive));
		trackingHead.setMessageId(trackingHeadMessageId);
		trackingHead.setConversationId(trackingHeadConversationId);
		GregorianCalendar c = new GregorianCalendar();
		trackingHead.setMessageDateTimeStamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
		successHead = new WsMessageHeaderType();
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

	public static void getObjectFromListByRefection(Object passObj,
			String fieldName) throws Exception {

		Field field = passObj.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		Map<String, Object> list = (Map<String, Object>) field.get(passObj);
		if (list != null) {
			System.out.println("map size:" + list.size());
			Set<Map.Entry<String, Object>> entrySet = list.entrySet();
			for (Map.Entry<String, Object> entry : entrySet) {
				System.out.println("myKeys key:" + entry.getKey() + " value:"
						+ entry.getValue().toString());
			}

		} else {
			System.out.println("reflection null");

		}
	}

	public static byte[] getObjectFromKeyManager(Object passObj,
			String fieldName) throws Exception {
		byte[] privateKey = null;
		Field field = passObj.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		Map<String, Object> list = (Map<String, Object>) field.get(passObj);
		if (list != null) {
			System.out.println("map size:" + list.size());
			Set<Map.Entry<String, Object>> entrySet = list.entrySet();
			for (Map.Entry<String, Object> entry : entrySet) {
				System.out.println("myKeys key:" + entry.getKey() + " value:"
						+ entry.getValue().toString());
				privateKey = getMyPrivate(entry.getValue(),
						"myEphemeralKeyPair");
				break;
			}

		} else {
			System.out.println("reflection null");

		}
		if (privateKey != null)
			System.out.println("private key from reflection:"
					+ convertByteToString(privateKey));
		return privateKey;
	}

	public static byte[] getMyPrivate(Object passObj, String fieldName)
			throws Exception {
		byte[] privateKey = null;
		Field field = passObj.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		Object emp = field.get(passObj);
		if (emp instanceof EphemeralKeyPair) {
			System.out.println("enter get private key in EphemeralKeyPair"
					+ emp.toString());
			privateKey = getMyPrivate(emp, "privateKey");
		}

		return privateKey;
	}

/*	public static void main(String[] arg) {

		com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage encryptedMessage = new com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage();
		encryptedMessage.setId("1");
		encryptedMessage.setCreated(2);
		encryptedMessage.setType("type");

		List<com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedPart> encryptedPart = new ArrayList<com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedPart>();
		com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedPart encryptedPart1 = new com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedPart();
		encryptedPart1.setCreated(2);
		encryptedPart1.setType("teyp");

		encryptedPart1.setId("d");
		encryptedPart1.setValue("tet");
		encryptedPart1.setSenderKeyId("ke");
		encryptedPart1.setRecipientKeyId("fdf");
		encryptedPart1.setIv("fdf");
		encryptedPart1.setMac("fdf");
		encryptedPart.add(encryptedPart1);
		encryptedMessage.getEncryptedPart().addAll(encryptedPart);

		convertObjToxml(encryptedMessage);
	}*/

	public static String convertObjToxml(
			com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage eppKeyExchangeRequestType) {
		String s = "";
		try {

			File file = new File("C:\\users\\emittya\\Desktop\\file.xml");
			JAXBContext jaxbContext = JAXBContext
					.newInstance(com.amdocs.mfs.api.epp.v1.sprint.encryption.EncryptedMessage.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(eppKeyExchangeRequestType, file);
			jaxbMarshaller.marshal(eppKeyExchangeRequestType, System.out);
			// s = FileUtils.readFileToString(file);
			// System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	
	/*	public static EppKeyExchangeRequestType generate(String keyId,
	String recipient, String signature, String ephemeralPublicKey,
	String certification) {
EppKeyExchangeRequestType eppKeyExchangeRequestType = new EppKeyExchangeRequestType();

// certification
byte[] cert = certification.getBytes();
SignatureCertificateInfoType signatureCertificateInfoType = new SignatureCertificateInfoType();
signatureCertificateInfoType.setCertificate(cert);

// list
List<SignatureCertificateInfoType> signatureCertificateInfo = new ArrayList<SignatureCertificateInfoType>();
signatureCertificateInfo.add(signatureCertificateInfoType);

SignatureCertificateListType signatureCertificateListType = new SignatureCertificateListType();
signatureCertificateListType.getSignatureCertificateInfo().addAll(
		signatureCertificateInfo);
eppKeyExchangeRequestType
		.setSignatureCertificateList(signatureCertificateListType);
// done set cert list

//
byte[] publicKey = ephemeralPublicKey.getBytes();
eppKeyExchangeRequestType.setEphemeralPublicKey(publicKey);

byte[] sig = signature.getBytes();
eppKeyExchangeRequestType.setSignature(sig);

eppKeyExchangeRequestType.setRecipient(recipient);
eppKeyExchangeRequestType.setKeyId(keyId);
return eppKeyExchangeRequestType;
}

public static EppKeyExchangeRequestType generate(String keyId,
	String recipient, byte[] signature, byte[] ephemeralPublicKey,
	byte[] certification) {
EppKeyExchangeRequestType eppKeyExchangeRequestType = new EppKeyExchangeRequestType();

// certification
byte[] cert = certification;
SignatureCertificateInfoType signatureCertificateInfoType = new SignatureCertificateInfoType();
signatureCertificateInfoType.setCertificate(cert);

// list
List<SignatureCertificateInfoType> signatureCertificateInfo = new ArrayList<SignatureCertificateInfoType>();
signatureCertificateInfo.add(signatureCertificateInfoType);

SignatureCertificateListType signatureCertificateListType = new SignatureCertificateListType();
signatureCertificateListType.getSignatureCertificateInfo().addAll(
		signatureCertificateInfo);
eppKeyExchangeRequestType
		.setSignatureCertificateList(signatureCertificateListType);
// done set cert list

//
byte[] publicKey = ephemeralPublicKey;
eppKeyExchangeRequestType.setEphemeralPublicKey(publicKey);

byte[] sig = signature;
eppKeyExchangeRequestType.setSignature(sig);

eppKeyExchangeRequestType.setRecipient(recipient);
eppKeyExchangeRequestType.setKeyId(keyId);
return eppKeyExchangeRequestType;
}*/

}
