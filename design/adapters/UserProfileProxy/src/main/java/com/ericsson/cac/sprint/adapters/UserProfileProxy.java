package com.ericsson.cac.sprint.adapters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.drutt.ws.msdp.userprofile_v2.MetaWs;
import com.drutt.ws.msdp.userprofile_v2.UserProfileService;
import com.drutt.ws.msdp.userprofile_v2.UserProfileServiceImplService;
import com.drutt.ws.msdp.userprofile_v2.WSException_Exception;

public class UserProfileProxy extends UserProfileServiceImplService {

    @Autowired
    private UserProfileService stub;

    public List<MetaWs> getMeta(String userURI, List<String> keys)
	    throws WSException_Exception {
	return stub.getMeta(userURI, keys);
    }

}

/*
 * public void setMeta_String(String userURI, String key, String value) throws
 * UserProfileException, WSException, RemoteException { if (userProfileService
 * == null) { initUserProfileServiceProxy(); }
 * userProfileService.setMeta_String(userURI, key, value);
 * 
 * }
 * 
 * 
 * public void setMeta_Strings(String userURI, String key, String[] value)
 * throws RemoteException, WSException, UserProfileException { if
 * (userProfileService == null) { initUserProfileServiceProxy(); }
 * userProfileService.setMeta_Strings(userURI, key, value); }
 * 
 * public void setMetas(String userURI, MetaWs[] meta) throws RemoteException,
 * WSException, UserProfileException { if (userProfileService == null) {
 * initUserProfileServiceProxy(); } userProfileService.setMetas(userURI, meta);
 * 
 * }
 * 
 * 
 * public String getMsisdn(String userId) throws RemoteException, WSException,
 * UserProfileException { if (userProfileService == null) {
 * initUserProfileServiceProxy(); } return userProfileService.getMsisdn(userId);
 * }
 * 
 * public String getUserId(String msisdn) throws RemoteException, WSException,
 * UserProfileException { if (userProfileService == null) {
 * initUserProfileServiceProxy(); } return userProfileService.getUserId(msisdn);
 * }
 * 
 * 
 * public void setPincode(String userURI, String pincode) throws
 * RemoteException, WSException, UserProfileException { if (userProfileService
 * == null) { initUserProfileServiceProxy(); }
 * userProfileService.setPincode(userURI, pincode); }
 * 
 * public void setMsisdn(String userURI, String msisdn) throws RemoteException,
 * WSException, UserProfileException { if (userProfileService == null) {
 * initUserProfileServiceProxy(); } userProfileService.setMsisdn(userURI,
 * msisdn); }
 * 
 * public String createUserProfile( UserProfileInformation
 * userProfileInformation) throws RemoteException, WSException,
 * UserProfileException { if (userProfileService == null) {
 * initUserProfileServiceProxy(); } return
 * userProfileService.createUserProfile(userProfileInformation); }
 * 
 * public void deleteUserProfile(String userURI) throws RemoteException,
 * WSException, UserProfileException { if (userProfileService == null) {
 * initUserProfileServiceProxy(); }
 * userProfileService.deleteUserProfile(userURI); }
 * 
 * 
 * public boolean userProfileExist(String userURI) throws RemoteException,
 * WSException, UserProfileException { if (userProfileService == null) {
 * initUserProfileServiceProxy(); } return
 * userProfileService.userProfileExist(userURI); }
 * 
 * 
 * public String getMetaAsString(String userURI, String key) throws
 * RemoteException, WSException, UserProfileException { if (userProfileService
 * == null) { initUserProfileServiceProxy(); } return
 * userProfileService.getMetaAsString(userURI, key); }
 * 
 * public String[] getMetaAsStrings(String userURI, String key) throws
 * RemoteException, WSException, UserProfileException { if (userProfileService
 * == null) { logger.debug("userProfileService is null...");
 * initUserProfileServiceProxy(); }
 * logger.debug("before call to getMetaAsStrings insidegetMetaAsStrings ...");
 * return userProfileService.getMetaAsStrings(userURI, key); }
 * 
 * 
 * public UserProfileInformation getUserProfileInformation(String userURI,
 * String[] key) throws RemoteException, WSException, UserProfileException { if
 * (userProfileService == null) { initUserProfileServiceProxy(); } return
 * userProfileService.getUserProfileInformation(userURI, key); }
 * 
 * 
 * public void deleteUserProfileById(String uid) throws RemoteException,
 * WSException, UserProfileException { if (userProfileService == null) {
 * initUserProfileServiceProxy(); }
 * userProfileService.deleteUserProfileById(uid); }
 * 
 * public UserProfileInformation getUserProfileInformationById(String uid,
 * String[] key) throws RemoteException, WSException, UserProfileException { if
 * (userProfileService == null) { initUserProfileServiceProxy(); } return
 * userProfileService.getUserProfileInformationById(uid, key); }
 * 
 * 
 * public void updateUserProfile(UserProfileInformation userProfileInformation)
 * throws RemoteException, WSException, UserProfileException { if
 * (userProfileService == null) { initUserProfileServiceProxy(); }
 * userProfileService.updateUserProfile(userProfileInformation); }
 * 
 * }
 */