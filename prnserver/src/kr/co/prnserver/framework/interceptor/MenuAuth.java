package kr.co.prnserver.framework.interceptor;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.prnserver.util.StringUtil;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MenuAuth extends HandlerInterceptorAdapter{
	 @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	    {
	        HttpSession session = request.getSession(false);
	 
	        String uri = request.getRequestURI();
	                
	        HashMap<String, String> menu = null;
	        
	        try
	        {
	            if (uri != null && !"".equals(uri))
	            {
	                uri = uri.substring(uri.lastIndexOf("/"));
	            }
	            
	            menu = (HashMap<String, String>)session.getAttribute("menu");
	            
	            if (menu != null)
	            {
	                if ("".equals(StringUtil.nvl(menu.get(uri), "")))
	                {
	                    response.sendRedirect("/prnserver/html/bo/error.html");
	                    return false;
	                }
	                session.setAttribute("currMenu", menu.get(uri));
	            }
	        }
	        catch (Exception ex)
	        {
	            ex.printStackTrace(System.err);
	            response.sendRedirect("/prnserver/html/bo/error.html");
	            return false;
	        }

	        return super.preHandle(request, response, handler);
	    }
}
