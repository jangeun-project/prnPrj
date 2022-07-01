package kr.co.prnserver.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {
	    public static final String SESSION_ID = "session_id";
	    public static final String SESSION_NM = "session_nm";
	    public static final String SESSION_DP = "session_dp";
	    public static final String SESSION_GD = "session_gd";
	    public static final String SESSION_USERID = "session_userid";
	    
	    public static String getSessionId(HttpServletRequest request)
	    {
	        HttpSession session = request.getSession(false);
	        
	        if (session == null)
	        {
	            return null;
	        }
	        else
	        {
	            return (String)session.getAttribute(SESSION_ID);
	        }
	    }
	    
	    public static String getSessionUserId(HttpServletRequest request)
	    {
	        HttpSession session = request.getSession(false);
	        
	        if (session == null)
	        {
	            return null;
	        }
	        else
	        {
	            return (String)session.getAttribute(SESSION_USERID);
	        }
	    }
}
