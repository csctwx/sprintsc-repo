package com.ericsson.cac.sprint.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ericsson.cac.sprint.shop.constants.ShopWorkFlowConstants;
import com.ericsson.cac.sprint.shop.workflow.util.CommonUtil;

public class AddHeaderFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
       
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        String path="";
        HttpServletResponse httpServletResponse=(HttpServletResponse) response;
        if(request instanceof HttpServletRequest){
            path=((HttpServletRequest) request).getRequestURI();
            String cacheConfig=applyHeaderConfig(path);
            if(!CommonUtil.isStringNullOfEmpty(cacheConfig)){               
                httpServletResponse.addHeader("Cache-Control", cacheConfig);
               
            }
        }
        chain.doFilter(request, httpServletResponse);
    }
   
    private String applyHeaderConfig(String path){
    	
        String cacheConfig="";
        if(ShopWorkFlowConstants.cacheHeaderMap!=null && !CommonUtil.isStringNullOfEmpty(path)){
        	for(String key: ShopWorkFlowConstants.cacheHeaderMap.keySet()){
        		if(path.matches(key)){
        			cacheConfig=ShopWorkFlowConstants.cacheHeaderMap.get(key);
        			break;
        		}
        	}
        }
        return cacheConfig;
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
       
    }

}
