package com.ericsson.sprint.ca;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Required;

import com.drutt.pmm.runtime.ee.portal.PortalCommand;
import com.drutt.pmm.runtime.portal.PortalExecutionContext;
import com.ericsson.sprint.ca.log.PmmAuthLogger;

public class IdentifyBrand extends PortalCommand {

	public static final String BRAND_ID = "BrandId";
	public static final String ORIGIN_URL = "OriginUrl";
	private static final String CLASSNAME = "IdentifyBrand";
	private Set<Pattern> urlBrandPattern = new HashSet<Pattern>();
	
	private PmmAuthLogger logger = PmmAuthLogger.getLogger();

	@Required
	public void setUrlBrandPattern(Set<String> urlBrandPattern) {
		for (String regex : urlBrandPattern) {
			this.urlBrandPattern.add(Pattern.compile(regex));
		}
	}

	@Override
	public boolean execute(PortalExecutionContext ctx) {
		String sessionId = ctx.getRequest().getServletRequest().getSession().getId();
		
		//removing the extra / character  2783887
		String originUrl = ctx.getRequestData().getPathExtra() != null ? 
				ctx.getRequestData().getPathExtra().substring(1) :"";
		logger.debug(sessionId, CLASSNAME, "OriginUrl=" + originUrl);
		String domainName = ctx.getRequest().getServletRequest().getServerName();
		logger.debug(sessionId, CLASSNAME, "BaseUrl=" + domainName);
		
		String brandId = getBrandId(domainName,sessionId);
		logger.debug(sessionId, CLASSNAME, "BrandId=" + brandId);
		//Setting the information to be used in the next Command of GuestSetup
		ctx.getRequest().getServletRequest().setAttribute(BRAND_ID, brandId);
		ctx.getRequest().getServletRequest().getSession().setAttribute(ORIGIN_URL, originUrl);
		
//		UserProfile up = ctx.getUserContext().getUserProfile();
//		if (up != null) {
//			up.getMeta().put(BRAND_ID, brandId);
//			up.getMeta().put(ORIGIN_URL, originUrl);
//		} else {
//			logger.debug(sessionId, CLASSNAME,
//					"UserProfile could not be found.Returning false");
//			return false;
//		}
		return true;
	}

	private String getBrandId(String pathPrefix, String sid) {
		String brandId = "";
		for (Pattern urlRegex : urlBrandPattern) {
			logger.debug(sid, CLASSNAME, "**Looking for Pattern:"+urlRegex.pattern());
			Matcher urlMatcher = urlRegex.matcher(pathPrefix);
			if(urlMatcher.find()){
				logger.debug(sid, CLASSNAME, "**Found the Pattern:"+urlRegex.pattern());
				brandId = pathPrefix.substring(urlMatcher.start(),urlMatcher.end());
				break; //found the pattern break out
			}
		}
		return brandId;
	}

	public static void main(String[] args) {
		String testUrl = "sprint/myaccount:login";
		String brandId = testUrl.split("/")[0];
		System.out.println(brandId);

		String path = "/sprint/myaccount:login";
		String pathExtra = "";
		String pathLast = "";
		if (path != null) {
			int index = path.indexOf('/');
			if (index == 0) {
				path = path.substring(1);
				index = path.indexOf('/');
			}
			if (index != -1) {
				pathLast = path.substring(0, index);
				pathExtra = (new StringBuilder()).append("/")
						.append(path.substring(index + 1)).toString();
			} else {
				pathLast = path;
				pathExtra = null;
			}
		} else {
			pathLast = null;
			pathExtra = null;
		}
		
		System.out.println(pathLast);
		System.out.println(pathExtra.substring(1));
		
		//PathPrefix test
		String pathPrefix = "prepaid.boostmobile.com";
		Pattern pattern = Pattern.compile("boost[^\\.]+");
		Matcher matcher = pattern.matcher(pathPrefix);
		
		String foundValue = null;
		if(matcher.find()){
			System.out.println("FOUND");
			foundValue = pathPrefix.substring(matcher.start(), matcher.end());
		}
		System.out.println("Found:"+foundValue);
	}

}
