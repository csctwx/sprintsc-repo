package com.ericsson.sprint.ca;
import java.util.Iterator;

import javax.servlet.http.Cookie;

import com.drutt.pmm.runtime.context.UserExecutionContext;
import com.drutt.pmm.runtime.ee.integration.GuestUserPolicy;
import com.drutt.pmm.runtime.ee.integration.SetupUserContext;
import com.drutt.pmm.runtime.ee.integration.UserSecureTokenPolicy;
import com.drutt.pmm.userprofile.core.UserPolicy;
import com.drutt.pmm.userprofile.core.UserPolicyFactory;
import com.drutt.pmm.userprofile.model.UserProfile;
import com.drutt.pmm.util.Meta;
import com.ericsson.sprint.ca.log.PmmAuthLogger;


/**
 * Custom class in order to have only limited numbers of Guest user profile
 * instead of creating one for the each time.
 * @author eiltpeh
 *
 */
public class SetupGuestUser extends SetupUserContext
{

	
	public static final String _REV_ID_ = "$Revision: $";
	private GuestUserPolicy guestUserPolicy;
	private UserSecureTokenPolicy userSecureTokenPolicy;
	private String cookieName;
	private String headerName;
	
    public SetupGuestUser()
    {
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

    protected String lookupUsername(UserExecutionContext ctx)
    {
        return createGuestUser(ctx);
    }

    public String createGuestUser(UserExecutionContext ctx)
    {
    	PmmAuthLogger logger = PmmAuthLogger.getLogger();
    	String sid = ctx.getRequest().getServletRequest().getSession().getId();
    	
        String name = null;
        Cookie cookies[] = ctx.getRequest().getServletRequest().getCookies();
        if(cookies != null && userSecureTokenPolicy != null && cookieName != null)
        {
            Cookie cookieArray[] = cookies;
            int cookiLength = cookieArray.length;
            for(int i = 0; i < cookiLength; i++)
            {
                Cookie c = cookieArray[i];
                if(!c.getName().equals(cookieName))
                    continue;
                String id = headerName == null ? "" : ctx.getRequest().getHeaders().getHeaderValue(headerName);
                if(null == id)
                    id = "";
                String token = SetupLoginContext.extractToken(c.getValue());
                name = userSecureTokenPolicy.getUsernameFromToken(ctx, token, id);
            }

        }
        UserPolicy userPolicy = UserPolicyFactory.getInstance().getUserPolicy();
        if(name == null)
            name = guestUserPolicy.getName(ctx);
        String msisdn = name;
        
        //first look for the UserProfile in MSDP.If not then create
        UserProfile up = userPolicy.findNormalUserProfileByName(name);
        if(up == null){
        	logger.debug(sid, "SetupGuestUser", "Creating UserProfile in Msdp :"+name);
        	up = userPolicy.createUserProfile(name, msisdn);
        }
        logger.debug(sid, "SetupGuestUser", "Founded UserPRofile from MSDP :"+up.getName() + " - Msisdn:"+up.getMsisdn());
        
        if(up == null)
            return null;
        if(guestUserPolicy.getAdditionalUserMeta() != null)
        {
            Meta meta = up.getMeta();
            java.util.Map.Entry e;
            for(Iterator i = guestUserPolicy.getAdditionalUserMeta().entrySet().iterator(); i.hasNext(); meta.put((String)e.getKey(), (String)e.getValue()))
                e = (java.util.Map.Entry)i.next();

        }
        up.getModelManager().getUserTransaction().flush();
        return name;
    }

}
