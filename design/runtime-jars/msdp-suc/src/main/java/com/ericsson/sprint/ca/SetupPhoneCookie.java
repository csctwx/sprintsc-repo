package com.ericsson.sprint.ca;

import javax.servlet.http.Cookie;

import com.drutt.pmm.runtime.context.ExecutionContext;
import com.drutt.pmm.runtime.ee.integration.GuestUserPolicy;
import com.drutt.pmm.runtime.ee.integration.UserSecureTokenPolicy;
import com.drutt.pmm.runtime.ee.portal.PortalCommand;
import com.drutt.pmm.runtime.portal.PortalExecutionContext;

// Referenced classes of package com.drutt.pmm.runtime.ee.integration:
// UserSecureTokenPolicy, GuestUserPolicy

public class SetupPhoneCookie extends PortalCommand {
	private String cookieName;
	private String parameterName;
	private UserSecureTokenPolicy userSecureTokenPolicy;
	private GuestUserPolicy guestUserPolicy;
	private int secureCookieMaxAgeInSecond;
	private static int secureCookieMaxAge;

	public SetupPhoneCookie() {
		secureCookieMaxAgeInSecond = 31536000;
	}

	public void setSecureCookieMaxAgeInSecond(int secureCookieMaxAgeInSecond) {
		this.secureCookieMaxAgeInSecond = secureCookieMaxAgeInSecond;
	}

	private static void setSecureCookieMaxAge(int secureCookieMaxAge) {
		secureCookieMaxAge = secureCookieMaxAge;
	}

	public void init() {
		setSecureCookieMaxAge(secureCookieMaxAgeInSecond);
	}

	public void setUserSecureTokenPolicy(UserSecureTokenPolicy secureTokenPolicy) {
		userSecureTokenPolicy = secureTokenPolicy;
	}

	public void setCookieName(String cookieName) {
		this.cookieName = cookieName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public void setGuestUserPolicy(GuestUserPolicy guestUserPolicy) {
		this.guestUserPolicy = guestUserPolicy;
	}

	public boolean execute(PortalExecutionContext ctx) {
		String username = getUserName(ctx);
		if (username == null) {
			return false;
		} else {
			String token = userSecureTokenPolicy.createToken(ctx, username,
					getId(ctx));
			setUserCookieOnResponse(cookieName, ctx, token);
			return true;
		}
	}

	protected static void setUserCookieOnResponse(String cookieName,
			ExecutionContext ctx, String token) {
		Cookie idCookie = new Cookie(cookieName, (new StringBuilder())
				.append(System.currentTimeMillis()).append(":").append(token)
				.toString());
		idCookie.setPath("/");
		idCookie.setMaxAge(secureCookieMaxAge);
		ctx.getRequest().getServletResponse().addCookie(idCookie);
	}

	protected static String extractToken(String value) {
		int tokenStart = value.indexOf(':');
		return tokenStart <= -1 || tokenStart >= value.length() ? value : value
				.substring(tokenStart + 1);
	}

	private String getUserName(PortalExecutionContext ctx) {
		String username = null;
		if (ctx.getUserContext() != null)
			username = ctx.getUserContext().getName();
		if (username == null && guestUserPolicy != null)
			username = guestUserPolicy.getName(ctx);
		return username;
	}

	private String getId(PortalExecutionContext ctx) {
		String id = parameterName == null ? "" : ctx.getRequest()
				.getServletRequest().getParameter(parameterName);
		if (null == id)
			id = "";
		return id;
	}

}
