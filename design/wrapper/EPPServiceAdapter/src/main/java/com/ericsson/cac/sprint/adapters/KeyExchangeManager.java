package com.ericsson.cac.sprint.adapters;

import java.io.IOException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amdocs.mfs.transit.TransitProtocol;
import com.amdocs.mfs.transit.TransitProtocolBuilder;
import com.amdocs.mfs.transit.TransitProtocolHolder;
import com.ericsson.cac.sprint.adapters.common.EPPUtility;
import com.ericsson.cac.sprint.adapters.common.InitializerUtil;

@Component
public class KeyExchangeManager {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(KeyExchangeManager.class);
	
	
	/**
	 * EnterprisePaymentProxyService
	 */
	@Autowired
	private EnterprisePaymentProxyService proxy;
	
	/**
	 * EPPUtility
	 */
	@Autowired
	private EPPUtility eppUtility;
	
	/**
	 * to check init
	 */
	private boolean initCompleted = false;
	
	/**
	 * Key Store File Path
	 */
	private String keyStorePath="aidkeystore.jks";
	
	
	/**
	 * Key Store Pasword
	 */
	private String keyStorePassword="ExsiNqNKZpF1rUw6H8J5ny";
	
	/**
	 * @return the transitProtocol
	 */
	public TransitProtocol getTransitProtocol() {
		if(!initCompleted){
			try {
				initTransitProtocol();
			} catch (IOException e) {
				logger.error("getTransitProtocol()", e);
			}
		}
		return TransitProtocolHolder.getInstance();
	}
	
	
	/**
	 * Initialize protocol
	 * @throws IOException 
	 */
	private void initTransitProtocol() throws IOException{
		KeyStore keyStore = InitializerUtil.loadKeyStore(keyStorePath, keyStorePassword.toCharArray());
		PrivateKey privateKey = InitializerUtil.loadPrivateKey(keyStore, keyStorePassword.toCharArray());
		X509Certificate[] myIdentityCert = InitializerUtil.loadPortalIdentifyCertChain(keyStore);
		Map<String, X509Certificate> caCertChain = InitializerUtil.loadCaCertChain(keyStore);
		TransitProtocolBuilder transitProtocolBuilder = InitializerUtil.createTransitProtocolBuilder(
				myIdentityCert, privateKey, caCertChain);
		KeyExchanger keyExchanger = new KeyExchanger();
		keyExchanger.setEppUtility(eppUtility);
		keyExchanger.setProxy(proxy);
		transitProtocolBuilder.addPeriodicKeyRotation(keyExchanger);
		TransitProtocolHolder.setInstance(transitProtocolBuilder.build());
		initCompleted = true;
	}
	
}
