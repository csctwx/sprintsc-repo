package com.ericsson.cac.sprint.adapters.common;

import org.apache.log4j.Logger;

import com.amdocs.mfs.transit.TransitConfig;
import com.amdocs.mfs.transit.TransitCryptoConfig;
import com.amdocs.mfs.transit.TransitCryptoHelper;
import com.amdocs.mfs.transit.TransitProtocolBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class InitializerUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(InitializerUtil.class);
	
	/**
	 * Alias for key store
	 */
	private static final String ALIAS = "aidTest";
	
	/**
	 * caCertStr
	 */
	private static final String CA_CERT_STR = "amdocs-cmi-nonprd-spr-ecc-class1|amdocs-cmi-nonprd-spr-ecc-class3|amdocs-cmi-nonprd-spr-ecc-root|amdocs-cmi-nonprd-spr-rsa-class1|amdocs-cmi-nonprd-spr-rsa-class3|amdocs-cmi-nonprd-spr-rsa-root";
	
	/**
	 * Loads the key store file from absolute path of keyStoreFile.
	 * 
	 * @param keyStoreFile
	 *            {@link String} Key Store system path.
	 * 
	 * @throws java.io.IOException
	 */
	public static KeyStore loadKeyStore(String keyStoreFile, char[] keyStorePwd)
			throws IOException {
        
		if (keyStoreFile == null || keyStorePwd == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("loadKeyStore(String, char[]) - Keystore file or password details are missing.");
			}
			return null;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("loadKeyStore(String, char[]) - InitializerUtil:::loadKeyStore entered");
		}
		InputStream inputStream = null;
		KeyStore keyStore = null;
		try {
			keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			if (logger.isDebugEnabled()) {
				logger.debug("loadKeyStore(String, char[]) - keyStoreFile String before -->>>>" + keyStoreFile.toString());
			}
			inputStream = InitializerUtil.class.getClassLoader().getResourceAsStream(keyStoreFile);
			keyStore.load(inputStream, keyStorePwd);
		} catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				logger.debug("loadKeyStore(String, char[]) - Exception caught while loadKeyStore file "+ex.fillInStackTrace());
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("loadKeyStore(String, char[]) - keyStore String -->>>>" + keyStore.toString());
		}
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("loadKeyStore(String, char[]) - size of entire keystore " + keyStore.size());
			}         
		} catch (KeyStoreException e) {
			logger.error("loadKeyStore(String, char[])", e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("loadKeyStore(String, char[]) - \n");
		}

		if (logger.isDebugEnabled()) {
			if (logger.isDebugEnabled()) {
				logger.debug("loadKeyStore(String, char[]) - InitializerUtil:::loadKeyStore ends.");
			}
		}
		return keyStore;
	}
	
	/**
	 * Loads portals private key from KeyStore.
	 * 
	 * @param keyStore
	 *            {@link java.security.KeyStore}
	 * @param alias
	 *            {@link String} Portal's cert CN Value.
	 * @param keyStorePwd
	 *            {@link char[]} the key store password.
	 * @return {@link java.security.PrivateKey} Portal's private key.
	 */
	public static PrivateKey loadPrivateKey(KeyStore keyStore, char[] keyStorePwd) {
		if (keyStore == null || keyStorePwd == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("loadPrivateKey(KeyStore, char[]) - Keystore or alias details are missing.");
			}
			return null;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("loadPrivateKey(KeyStore, char[]) - InitializerUtil:::loadPrivateKey entered");
		}
		PrivateKey myPrivateKey = null;
		if (logger.isDebugEnabled()) {
			logger.debug("loadPrivateKey(KeyStore, char[]) - input -->>  password " + keyStorePwd.toString() + " alias " + ALIAS.toString() + " keystore " + keyStore.toString());
		}

		try {
			KeyStore.ProtectionParameter protectionPass = new KeyStore.PasswordProtection(keyStorePwd);
			if (logger.isDebugEnabled()) {
				logger.debug("loadPrivateKey(KeyStore, char[]) - debug, password " + protectionPass.toString());
			}
			
			KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(ALIAS, protectionPass);

			myPrivateKey = privateKeyEntry.getPrivateKey();
		} catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				logger.debug("loadPrivateKey(KeyStore, char[]) - Exception caught while loadPrivateKey from KeyStor");
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("loadPrivateKey(KeyStore, char[]) - string " + myPrivateKey.toString());
		}
		if (logger.isDebugEnabled()) {
			logger.debug("loadPrivateKey(KeyStore, char[]) - format " + myPrivateKey.getFormat());
		}
		if (logger.isDebugEnabled()) {
			logger.debug("loadPrivateKey(KeyStore, char[]) - InitializerUtil:::loadPrivateKey ends.");
		}
		return myPrivateKey;
	}
	
	/**
	 * Loads Portal Identity Certificate chain from {@link java.security.KeyStore}.
	 * 
	 * @param keyStore
	 *            {@link java.security.KeyStore}
	 * @param alias
	 *            {@link String} Portal's cert CN Value.
	 * @return {@link java.security.cert.X509Certificate}[] Portal's identity cert chain.
	 */
	public static X509Certificate[] loadPortalIdentifyCertChain(KeyStore keyStore) {
		if (keyStore == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("loadPortalIdentifyCertChain(KeyStore) - Keystore or alias details are missing.");
			}
			return null;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("loadPortalIdentifyCertChain(KeyStore) - InitializerUtil:::loadPortalIdentifyCertChain entered");
		}
		X509Certificate[] potalIdentityCert = null;
		try {
			int certNum = 0;

			java.security.cert.Certificate[] portalCertChain = keyStore
					.getCertificateChain(ALIAS);

			for (java.security.cert.Certificate certificate : portalCertChain) {
				if (certificate instanceof X509Certificate) {
					if (potalIdentityCert == null) {
						potalIdentityCert = new X509Certificate[portalCertChain.length];
					}
					potalIdentityCert[certNum] = (X509Certificate) certificate;
					certNum++;
					}
			}
		} catch (KeyStoreException ex) {
			if (logger.isDebugEnabled()) {
				logger.debug("loadPortalIdentifyCertChain(KeyStore) - Exception caught while loadPortalIdentifyCertChain from KeyStore");
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("loadPortalIdentifyCertChain(KeyStore) - InitializerUtil:::loadPortalIdentifyCertChain ends.");
		}
		return potalIdentityCert;
	}
	
	public static Map<String, X509Certificate> loadCaCertChain(KeyStore keyStore) {
		if (keyStore == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("loadCaCertChain(KeyStore) - Keystore or caCert information is missing.");
			}
			return null;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("loadCaCertChain(KeyStore) - InitializerUtil:::loadCaCertChain entered");
		}
		Map<String, X509Certificate> caCertsMap = null;
		try {
			if (CA_CERT_STR != null) {
				StringTokenizer caCertAliasNames = new StringTokenizer(
						CA_CERT_STR, "|");
				while (caCertAliasNames.hasMoreElements()) {
					String caCertKey = caCertAliasNames.nextToken();
					java.security.cert.Certificate caCert;
					caCert = keyStore.getCertificate(caCertKey);
					if (caCert instanceof X509Certificate) {
						if ((caCertsMap==null)) {
							caCertsMap = new HashMap<String, X509Certificate>();
						}
						X509Certificate caCertValue = (X509Certificate) caCert;
						caCertsMap.put(caCertKey, caCertValue);
						if (logger.isDebugEnabled()) {
							logger.debug("loadCaCertChain(KeyStore) - key" + keyStore.getCertificateAlias(caCert));
						}
					}
				}
			}
		} catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				logger.debug("loadCaCertChain(KeyStore) - Exception caught while loadCaCertChain from KeyStore");
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("loadCaCertChain(KeyStore) - InitializerUtil:::loadCaCertChain ends");
		}
		return caCertsMap;
	}
	
	/**
	 * Method to create portal ephemeral and adds to transit key manager.
	 * 
	 * @param myIdentityCert
	 *            {@link java.security.cert.X509Certificate}[] Portal Identity certificate.
	 * @param privateKey
	 *            {@link java.security.PrivateKey} Portal's Private Key.
	 * @param caCertChain
	 *            {@link java.util.Map}<{@link String}, {@link java.security.cert.X509Certificate}> CA Cert
	 *            chain.
	 * @param alias
	 *            {@link String} Portal's CN value.
	 * @return {@link com.amdocs.mfs.transit.TransitProtocolBuilder} initialized transit protocol
	 *         instance with the initial key generated
	 */
	public static TransitProtocolBuilder createTransitProtocolBuilder(
			X509Certificate[] myIdentityCert, PrivateKey privateKey,
			Map<String, X509Certificate> caCertChain) {
		if (logger.isDebugEnabled()) {
			logger.debug("createTransitProtocolBuilder(X509Certificate[], PrivateKey, Map<String,X509Certificate>, String) - KeyFactory test:");
		}
		
		if (myIdentityCert == null || privateKey == null || caCertChain == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("createTransitProtocolBuilder(X509Certificate[], PrivateKey, Map<String,X509Certificate>, String) - Channel  or PrivateKey details are missing.");
			}
			return null;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("createTransitProtocolBuilder(X509Certificate[], PrivateKey, Map<String,X509Certificate>, String) - InitializeUtil:::createTransitProtocolHolder starts");
		}
		TransitProtocolBuilder transitProtocolBuilder = null;
		try {
			TransitCryptoHelper transitCryptoHelper = new TransitCryptoHelper(new TransitCryptoConfig());
			Set<TrustAnchor> trustAnchors = transitCryptoHelper.getTrustAnchors(caCertChain);
			TransitConfig tC = new TransitConfig();
			transitProtocolBuilder = new TransitProtocolBuilder(tC, trustAnchors);
			transitProtocolBuilder.addMyIdentity(myIdentityCert, privateKey, ALIAS);
			transitProtocolBuilder.addLocalKeyManager();
		} catch (Exception ex) {
			logger.error("createTransitProtocolBuilder(X509Certificate[], PrivateKey, Map<String,X509Certificate>, String)", ex);
			if (logger.isDebugEnabled()) {
				logger.debug("createTransitProtocolBuilder(X509Certificate[], PrivateKey, Map<String,X509Certificate>, String) - Error generating ephemeral key and constructing TransitProtocolHolder :: ");
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("createTransitProtocolBuilder(X509Certificate[], PrivateKey, Map<String,X509Certificate>, String) - InitializeUtil:::createTransitProtocolHolder ends");
		}
		return transitProtocolBuilder;
	}
}
