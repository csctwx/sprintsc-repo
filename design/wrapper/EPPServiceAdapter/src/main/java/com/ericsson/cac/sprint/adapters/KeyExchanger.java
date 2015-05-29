package com.ericsson.cac.sprint.adapters;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.amdocs.mfs.transit.EphemeralPublicKey;
import com.amdocs.mfs.transit.TransitCryptoConfig;
import com.amdocs.mfs.transit.TransitCryptoHelper;
import com.amdocs.mfs.transit.TransitKeyExchanger;
import com.ericsson.cac.sprint.adapters.common.EPPUtility;
import com.sprint.integration.interfaces.eppkeyexchange.v1.eppkeyexchange.EppKeyExchangeRequestType;
import com.sprint.integration.interfaces.eppkeyexchange.v1.eppkeyexchange.EppKeyExchangeResponseType;
import com.sprint.integration.interfaces.eppkeyexchange.v1.eppkeyexchange.ResponseInfoType;
import com.sprint.integration.interfaces.eppkeyexchange.v1.eppkeyexchange.SignatureCertificateInfoType;
import com.sprint.integration.interfaces.eppkeyexchange.v1.eppkeyexchange.SignatureCertificateListType;

public class KeyExchanger implements TransitKeyExchanger {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(KeyExchanger.class);
	
	private EnterprisePaymentProxyService proxy;
	
	private EPPUtility eppUtility;
	
	

	/**
	 * @param proxy the proxy to set
	 */
	public void setProxy(EnterprisePaymentProxyService proxy) {
		this.proxy = proxy;
	}

	/**
	 * @param eppUtility the eppUtility to set
	 */
	public void setEppUtility(EPPUtility eppUtility) {
		this.eppUtility = eppUtility;
	}

	@Override
	public EphemeralPublicKey exchangeKeys(String recipientName, EphemeralPublicKey ephemeralPublicKey) {
		if (logger.isDebugEnabled()) {
			logger.debug("exchangeKeys(String, EphemeralPublicKey) - EPPKeyExchange:::exchangeKeys entered.");
		}
		boolean keyExchangeStatus = Boolean.FALSE;
		Date recipientExpiryDate = null;
		EphemeralPublicKey recipientEphemeralPublicKey = null;

		try {
			TransitCryptoHelper transitCryptoHelper = new TransitCryptoHelper(
					new TransitCryptoConfig());
			EppKeyExchangeRequestType keyExchangeRequest = new EppKeyExchangeRequestType();
			keyExchangeRequest = createEppKeyExchangeRequestType(ephemeralPublicKey, transitCryptoHelper, recipientName);
			
			if (logger.isInfoEnabled()) {
				logger.info("exchangeKeys(String, EphemeralPublicKey) - EnterprisePaymentProxyService proxy=" + proxy);
				logger.info("exchangeKeys(String, EphemeralPublicKey) - EPPUtility eppUtility=" + eppUtility);
			}
			EppKeyExchangeResponseType keyExchangeResponseType = proxy.eppKeyExchange(keyExchangeRequest, eppUtility.populateHeader());

			if ( (keyExchangeResponseType != null)) {
				//KeyExchangeResponse keyExchangeResponse = keyExchangeResponseType.getResponseInfo();
				ResponseInfoType keyExchangeResponseInfo = keyExchangeResponseType.getKeyExchangeResponse().getResponseInfo();
				if ((keyExchangeResponseInfo != null)
						&& (keyExchangeResponseType.getKeyExchangeResponse().getResponseInfo() != null)){
					if (logger.isDebugEnabled()) {
						logger.debug("exchangeKeys(String, EphemeralPublicKey) - Got successful response for eppKeyExchange service call. recipient" + recipientName);
					}
					recipientEphemeralPublicKey = new EphemeralPublicKey();
					recipientEphemeralPublicKey.setId(keyExchangeResponseType.getKeyExchangeResponse().getKeyId());
							
					recipientEphemeralPublicKey.setSig(keyExchangeResponseType.getKeyExchangeResponse().getSignature());
					recipientEphemeralPublicKey.setCreated(keyExchangeResponseType.getKeyExchangeResponse().getCreated());
					Long expires = keyExchangeResponseType.getKeyExchangeResponse().getExpires();
					if ((expires != null )) {
						recipientExpiryDate = new Date(expires);
					}
					recipientEphemeralPublicKey.setExpires(expires);
					recipientEphemeralPublicKey
							.setPublicKey(keyExchangeResponseType.getKeyExchangeResponse().getEphemeralPublicKey());
					
					SignatureCertificateListType recipientSigCertList = keyExchangeResponseType.getKeyExchangeResponse().getSignatureCertificateList();
					if ((recipientSigCertList != null)
							&& ((recipientSigCertList.getSignatureCertificateInfo() !=null))
							&& recipientSigCertList.getSignatureCertificateInfo().size()> 0) {
						int i = 0;
						byte[][] sigCertChain = new byte[recipientSigCertList.getSignatureCertificateInfo().size()][];
						for (SignatureCertificateInfoType signatureCertificateInfoType : recipientSigCertList
								.getSignatureCertificateInfo()) {
							sigCertChain[i] = new byte[signatureCertificateInfoType
									.getCertificate().length];
							if ((signatureCertificateInfoType != null)
									&& ((signatureCertificateInfoType.getCertificate()) != null)
									&& signatureCertificateInfoType
											.getCertificate().length > 0) {
								for (int j = 0; j < signatureCertificateInfoType
										.getCertificate().length; j++) {
									sigCertChain[i][j] = signatureCertificateInfoType
											.getCertificate()[j];
								}
							}
							i++;
						}
						recipientEphemeralPublicKey
								.setSigCertChain(sigCertChain);
					}
					keyExchangeStatus = Boolean.TRUE;
				}
			}
		} catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				logger.debug("exchangeKeys(String, EphemeralPublicKey) - Exception caught while exchanging Keys "+ex);
			}
			ex.printStackTrace();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("exchangeKeys(String, EphemeralPublicKey) - recipientName=" + recipientName + ", Status=" + keyExchangeStatus + ", Key Expiry=" + recipientExpiryDate);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("exchangeKeys(String, EphemeralPublicKey) - EPPKeyExchange:::exchangeKeys ends.");
		}
		return recipientEphemeralPublicKey;
	}

	@Override
	public Set<String> getExchangeNames() {
		if (logger.isDebugEnabled()) {
			logger.debug("getExchangeNames() - EPPKeyExchange:::getExchangeNames entered.");
		}
		Set<String> exchangeNameSet = null;
	
		//TODO: take from properties
		String exchangeNames="EAITest|Uat1EppPci|Uat1EppNPci";

		if ( (exchangeNames != null)) {
			StringTokenizer exchangeNameToken = new StringTokenizer(
					exchangeNames, "|");
			if ( (exchangeNameToken != null) ) {
				while (exchangeNameToken.hasMoreTokens()) {
					if ( (exchangeNameSet == null)) {
						exchangeNameSet = new LinkedHashSet<String>();
					}
					exchangeNameSet.add(exchangeNameToken.nextToken());
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getExchangeNames() - EPPKeyExchange:::getExchangeNames ends." + exchangeNameSet != null ? exchangeNameSet.toString(): "");
		}
		return exchangeNameSet;
	}
	
	/**
	 * Method to frame {@link com.sprint.integration.interfaces.eppkeyexchange.v1.eppkeyexchange.EppKeyExchangeRequestType} with channel's
	 * Ephemeral key details.
	 * 
	 * @param myDefPub
	 *            {@link com.amdocs.mfs.transit.EphemeralPublicKey}
	 * @param transitCryptohelper
	 *            {@link com.amdocs.mfs.transit.TransitCryptoHelper}
	 * @param recipient
	 *            {@linkplain String} peer name with whom key exchange will be
	 *            performed.
	 * @return EppKeyExchangeRequestType {@link com.sprint.integration.interfaces.eppkeyexchange.v1.eppkeyexchange.EppKeyExchangeRequestType}
	 */
	private EppKeyExchangeRequestType createEppKeyExchangeRequestType(
			EphemeralPublicKey myDefPub,
			TransitCryptoHelper transitCryptohelper, String recipient) {
		if (logger.isDebugEnabled()) {
			logger.debug("createEppKeyExchangeRequestType(EphemeralPublicKey, TransitCryptoHelper, String) - EPPKeyExchange:::createEppKeyExchangeRequestType starts.");
		}
		
		EppKeyExchangeRequestType request = new EppKeyExchangeRequestType();
		
		request.setCreated(myDefPub.getCreated());
		request.setKeyId(myDefPub.getId());
		request.setExpires(myDefPub.getExpires());
		request.setEphemeralPublicKey(myDefPub.getPublicKey());
		request.setRecipient(recipient);
		int length = myDefPub.getSigCertChain().length;
		SignatureCertificateInfoType[] signatureCertificateInfo = new SignatureCertificateInfoType[length];
		
		SignatureCertificateListType signatureCertificateChain = new SignatureCertificateListType(
				);
		
		int j = 0;
		for (byte[] x509Certificate : myDefPub.getSigCertChain()) {
			signatureCertificateInfo[j] = new SignatureCertificateInfoType();
			signatureCertificateInfo[j].setCertificate(x509Certificate);
			j++;
		}
		signatureCertificateChain.getSignatureCertificateInfo().addAll(Arrays.asList(signatureCertificateInfo)) ;
		request.setSignatureCertificateList(signatureCertificateChain);
		request.setSignature(myDefPub.getSig());
		if (logger.isDebugEnabled()) {
			logger.debug("createEppKeyExchangeRequestType(EphemeralPublicKey, TransitCryptoHelper, String) - EPPKeyExchange:::createEppKeyExchangeRequestType Exits.");
		}
		return request;
	}
}
