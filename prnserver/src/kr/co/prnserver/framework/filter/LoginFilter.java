package kr.co.prnserver.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {
	private FilterConfig filterConfig;

    public void destroy()
    {
        filterConfig = null;
    }

    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException
    {
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        
        String uri = ((HttpServletRequest) request).getRequestURI().toLowerCase();

        if (!"/prnserver/login.do".equals(uri) &&  !"/prnserver/enterlogin.do".equals(uri))
        {
            if (session == null || session.getAttribute("session_id") == null)
            {
           		((HttpServletResponse) response).sendRedirect("/prnserver/html/information.html");
            }
            else
            {
                chain.doFilter(request, response);
            }
        }
        else
        {
            chain.doFilter(request, response);
        }
    }

    
    public void init(FilterConfig filterCfg) throws ServletException
    {
        this.filterConfig = filterCfg;
    }
}
