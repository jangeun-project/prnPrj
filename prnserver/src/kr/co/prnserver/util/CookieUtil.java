package kr.co.prnserver.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static final String COOKIE_ID         = "PRN_ID";
    public static final String COOKIE_DOMAIN     = "prnserver.co.kr" ; 
    public static final String COOKIE_PRINT       = "PRN_PRINT";
    public static final String COOKIE_DEV_DOMAIN = "dev.prnserver.co.kr";
    
    public static String getCookie(HttpServletRequest request, HttpServletResponse response, String cookieName)
    {
        Cookie[] cookies = request.getCookies();
        
        String cookieValue = null;
        
        //TEST
      long time = System.currentTimeMillis();
    	SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    	String str = dayTime.format(new Date(time));
    	
        
        try {
	        if(cookies != null && cookies.length > 0) {
	        	for (Cookie cookie : cookies) 
	            {
	            	//TEST
	                System.out.println(str + "================get : domain:"+cookie.getName()+"||"+ java.net.URLDecoder.decode(cookie.getValue(), "utf-8") );
	                
	                if (cookieName.equals(cookie.getName()))
	                {
	                    cookieValue = java.net.URLDecoder.decode(cookie.getValue(), "utf-8");
	                     break;
	                }
	            }
	        }	
        }catch (Exception ex)
        {
    		ex.printStackTrace(System.err);
    		return cookieValue;
        }
        return cookieValue;
    }
    
    
    public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue, String domain)
    {
 
 // TEST   	
    	long time = System.currentTimeMillis();
    	SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    	String str = dayTime.format(new Date(time));
    	System.out.println(str + "================Set : domain:"+domain+"||"+ cookieName );
    	
    	try {
	    	String enCookie = java.net.URLEncoder.encode(cookieValue, "utf-8");
	        Cookie cookie = new Cookie(cookieName, enCookie);
	        cookie.setMaxAge(60 * 60 * 8);
	        cookie.setPath("/");
	        cookie.setDomain(domain);
	        response.addCookie(cookie);
    	}catch (Exception ex)
        {
    		ex.printStackTrace(System.err);
        }
    }
    
    public static void removeCookie( HttpServletResponse response, String cookieName ) {
		Cookie cookie = new Cookie( cookieName, "" );
		cookie.setPath( "/" );
		// maxAge
		cookie.setMaxAge( 0 );
		
		response.addCookie( cookie );
	}
    
    /*
    public static HashMap<String, String> getSitCookieInfo(HttpServletRequest request)
    {
        HashMap<String, String> cookieInfo = null;
        
    	try {
	        String id       = getCookie(request, java.net.URLDecoder.decode(CookieUtil.COOKIE_ID, "utf-8"));
	        String print     = getCookie(request, CookieUtil.COOKIE_PRINT);
	        
	        if (id != null && !"".equals(id) && print != null && !"".equals(print))
	        {
	            cookieInfo = new HashMap<String, String>();
	            cookieInfo.put(COOKIE_ID,        id);
	            cookieInfo.put(COOKIE_PRINT,     print);
	        }
    	}
    	catch (Exception ex)
        {
    		ex.printStackTrace(System.err);
    		return cookieInfo;
        }
        return cookieInfo;
    }
    */
}
