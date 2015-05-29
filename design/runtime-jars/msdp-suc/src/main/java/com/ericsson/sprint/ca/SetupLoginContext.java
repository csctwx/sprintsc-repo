package com.ericsson.sprint.ca;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;
import com.drutt.pmm.runtime.context.UserExecutionContext;
import com.drutt.pmm.runtime.ee.integration.GuestUserPolicy;
import com.drutt.pmm.runtime.ee.integration.UserSecureTokenPolicy;
import com.drutt.pmm.runtime.ee.portal.PortalCommand;
import com.drutt.pmm.runtime.portal.PortalExecutionContext;
import com.drutt.pmm.userprofile.core.UserContext;
import com.drutt.pmm.userprofile.core.UserPolicy;
import com.drutt.pmm.userprofile.core.UserPolicyFactory;
import com.drutt.pmm.userprofile.model.UserProfile;
import com.ericsson.drutt.ws.client.ServiceLocator;
import com.ericsson.sprint.ca.log.PmmAuthLogger;
import com.ericsson.sprint.cac.CommandMetaWs;
import com.ericsson.sprint.cac.CommandResult;
import com.ericsson.sprint.cac.EsbCommandService;
import com.ericsson.sprint.cac.WSExceptionException;
import com.ericsson.sprint.cac.common.CommandServiceUtil;

public class SetupLoginContext extends PortalCommand {

	private static final String MSISDN_PARAM = "msisdn";
	private static final String PIN_PARAM = "pin";
	private PmmAuthLogger logger = PmmAuthLogger.getLogger();
	private ServiceLocator serviceLocator;
	private GuestUserPolicy guestUserPolicy;
	private UserSecureTokenPolicy userSecureTokenPolicy;
	private String cookieName;
    private String headerName;

	  
	public ServiceLocator getServiceLocator() {
		return serviceLocator;
	}

	

	public void setServiceLocator(ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}

	public void setGuestUserPolicy(GuestUserPolicy guestUserPolicy)
    {
        this.guestUserPolicy = guestUserPolicy;
    }
	
	public void setUserSecureTokenPolicy(UserSecureTokenPolicy userSecureTokenPolicy)
    {
        this.userSecureTokenPolicy = userSecureTokenPolicy;
    }
	
	public void setCookieName(String cookieName)
    {
        this.cookieName = cookieName;
    }

    public void setHeaderName(String headerName)
    {
        this.headerName = headerName;
    }

	@Override
	public boolean execute(PortalExecutionContext ctx) {
		String sid = ctx.getRequest().getServletRequest().getSession().getId();
	    String classname = this.getClass().getSimpleName();
		logger.debug(sid, classname, "Starting the SetupLoginContext command");
		
		/**
		 * Steps To Apply here:
		 * 1- Userlookup call and verify user over Sprint (Meta parameters will be fetched from UP)
		 *    The list of meta expected to be filled in during this routine:
				category=empty string (it was guest before authentication)
				accountStatus
				lastAccessTime
				creationTime - only if there was no UP with corresponding BAN found in MSDP
				device
				isSoftLock
				softLockTimestamp
				isHardLocked
		 * 2- Create Userprofile as UP[subId,subId]
		 * 3- Move metas from GuestUserProfile to newly created UP
		 * 4- Remove the cookie for the Guest User ??
		 * 5- Remove the GuestUserPRofile ??
		 */
		//test
		EsbCommandService commandService =serviceLocator.getCommandService();
		CommandResult lookupResult = null;
		
		//TODO: add the logic in case it comes from the Zone App with EQD header
		String msisdn = ctx.getRequest().getServletRequest().getParameter(MSISDN_PARAM);
		String pin = ctx.getRequest().getServletRequest().getParameter(PIN_PARAM);
		
		logger.info(sid, classname, "Sending parameters to Userlookup- msisdn:"+msisdn+"-pin:"+pin);
		try {
			lookupResult = commandService.commandRequest(CommandServiceUtil.GENERAL_LOOKUP_COMMAND, getLookupCommandParameters(msisdn,pin));
		} catch (WSExceptionException e) {
			//TODO: handle the Error Message for this one
			logger.error(sid, classname, "Error while calling the Lookup-Message:",e);
			lookupResult = null;
		}
		if(lookupResult == null || !lookupResult.isResult()){
			logger.info(sid, classname, "**Lookup is not SUCCESSFULL**");
			setTheErrorResponse(lookupResult,ctx);
			return false;
		}
		String subscriberId = lookupResult.getUri();
		logger.info(sid, classname, "SetupUserContext for SubscriberId:"+subscriberId);
		if (setupUserContext(subscriberId, ctx)){
			//Continue with Metas
			setMetasFromLookup(ctx,lookupResult);
			//Copy Guest Metas
			UserProfile guestUP = getGuestUserProfile(ctx);
			ctx.getUserContext().getUserMeta().put(IdentifyBrand.BRAND_ID, guestUP.getMeta().getString(IdentifyBrand.BRAND_ID));
			ctx.getUserContext().getUserMeta().put(IdentifyBrand.ORIGIN_URL, guestUP.getMeta().getString(IdentifyBrand.ORIGIN_URL));
			ctx.getUserContext().getUserMeta().put("error", "");
			ctx.getUserContext().getUserMeta().put("Error", "");
		}else{
			logger.warn(sid, classname, "**SetupUserContext FAILED: Returning false");
			return false;
		}
		return true;
	}
	
	private void setTheErrorResponse(CommandResult lookupResult,
			PortalExecutionContext ctx) {
		if(lookupResult == null){
			return;
		}
		String sid = ctx.getRequest().getServletRequest().getSession().getId();
	    String classname = this.getClass().getSimpleName();
		String errorText = "";
		for (CommandMetaWs meta : lookupResult.getExtraAttributes()) {
			if(meta.getKey().equalsIgnoreCase("Error")){
				errorText = meta.getValue();
			}
		}

		
		logger.debug(sid, classname, "Setting Error Message To Response-ErrorMessage="+errorText);
		UserProfile guestUP = getGuestUserProfile(ctx);
		guestUP.getMeta().put("error", errorText);
		
		logger.debug(sid, classname, "GUEST UserProfile Id="+guestUP.getId().toString());
		logger.debug(sid, classname, "GUEST UserProfile Name="+guestUP.getName());
		logger.debug(sid, classname, "GUEST UserProfile Uid="+guestUP.getUid());
		logger.debug(sid, classname, "GUEST UserProfile Msisdn="+guestUP.getMsisdn());
		
		//ctx.getResponse().getHeaders().setHeader("LOOKUP_ERROR", errorText);

	}


	private List<CommandMetaWs> getLookupCommandParameters(String msisdn,
			String pin) {
		List<CommandMetaWs> parameters = new ArrayList<CommandMetaWs>();
		CommandMetaWs msisdnMeta = new CommandMetaWs();
		CommandMetaWs pinMeta = new CommandMetaWs();

		msisdnMeta.setKey(CommandServiceUtil.MSISDN_META_KEY);
		msisdnMeta.setValue(msisdn);
		pinMeta.setKey(CommandServiceUtil.PIN_META_KEY);
		pinMeta.setValue(pin);
		parameters.add(msisdnMeta);
		parameters.add(pinMeta);
		return parameters;
	}


	private void setMetasFromLookup(UserExecutionContext userContext,
			CommandResult lookupResult) {
		for (CommandMetaWs commandMeta : lookupResult.getExtraAttributes()) {
			
			if(commandMeta.getKey().equalsIgnoreCase("Error")){
				userContext.getUserContext().getUserMeta().put(commandMeta.getKey(), "");
			} else {
				userContext.getUserContext().getUserMeta().put(commandMeta.getKey(), commandMeta.getValue());
			}
			
			
		}

	}

	private UserProfile getGuestUserProfile(PortalExecutionContext ctx) {
		String name = null;
        Cookie cookies[] = ctx.getRequest().getServletRequest().getCookies();
        
        if(cookies != null && userSecureTokenPolicy != null && cookieName != null)
        {
            Cookie arr[] = cookies;
            int len = arr.length;
            for(int i = 0; i < len; i++)
            {
                Cookie c = arr[i];
                if(!c.getName().equals(cookieName))
                    continue;
                String id = headerName == null ? "" : ctx.getRequest().getHeaders().getHeaderValue(headerName);
                if(null == id)
                    id = "";
                String token = extractToken(c.getValue());
                name = userSecureTokenPolicy.getUsernameFromToken(ctx, token, id);
            }

        }
        
        UserPolicy userPolicy = UserPolicyFactory.getInstance().getUserPolicy();
        UserProfile guestUser = userPolicy.findNormalUserProfileByName(name);
		return guestUser;
	}

	private boolean setupUserContext(String subscriberId, UserExecutionContext ctx) {

		String sid = ctx.getRequestId();
		String classname = this.getClass().getSimpleName();
		
		if(subscriberId == null || subscriberId.isEmpty()){
			logger.info(sid, classname, "**SuscriberId is empty or null..Returning false from SetupContext**");
			return false;
		}

		try {
			UserPolicy userPolicy = UserPolicyFactory.getInstance()
					.getUserPolicy();
			UserContext uc = null;

			UserProfile up = userPolicy.findNormalUserProfileByName(subscriberId);

			if (up != null) {
				uc = userPolicy.findUserContext(up.getName());
			} else {
				logger.debug(sid, classname,
						"userPolicy.findUserProfileByName("+subscriberId+") returned null value");
			}

			if (uc == null) {
				logger.debug(sid, classname, "User not found: " + subscriberId
						+ ". Creating new user profile.");
				up = userPolicy.createUserProfile(subscriberId, subscriberId);
				userPolicy.getModelManager().getUserTransaction().commit();
				uc = userPolicy.findUserContext(up.getName());
			}

			if (uc == null) {
				logger.info(sid, classname, "User not found: " + subscriberId
						+ ". Failed to fetch info for the new user profile.");
				logger.debug(sid, classname, "Setup ctx. FAIL");
				return false;
			}

			logger.info(sid, classname, "Setting the UserContext.."+uc.getMsisdn());
			ctx.setUserContext(uc);
			logger.debug(sid, classname, "Setup ctx. SUCCESS");

			return true;
		} catch (Exception e) {
			logger.warn(sid, classname, "Error during Setup Ctx", e);
		}
		return false;
	}
	
	
	protected static String extractToken(String value) {
		int tokenStart = value.indexOf(':');
		return tokenStart <= -1 || tokenStart >= value.length() ? value : value
				.substring(tokenStart + 1);
	}
	

}
