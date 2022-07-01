package kr.co.prnserver.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class MapUtil {
	public static HashMap<String, Object> getRequestObjectMap(HttpServletRequest request) 
    {
        String param="";
        HashMap<String, Object> map = new HashMap<String, Object>();
        try 
        {
            Map parameter = request.getParameterMap();
            if (parameter == null)
            {
                return null;
            }
            Object paramKey = null;
            String[] paramValue = null;

            Iterator it = parameter.keySet().iterator();
            while (it.hasNext()) 
            {
                paramKey = it.next();
                paramValue = (String[]) parameter.get(paramKey);
                String strKey = paramKey.toString();
                if (paramValue.length > 1) 
                {
                    param = request.getParameterValues(paramKey.toString()).toString();
                    map.put(strKey, StringUtil.deleteJavaScript((param )));
                }
                else 
                {
                    map.put(strKey, (paramValue[0] == null) ? "" : StringUtil.deleteJavaScript(paramValue[0].trim().toString()));
                }
            }
            return map;
        } 
        catch (Exception e) 
        {
            System.out.println("CommonUtil getRequestObjectMap()" + e.toString());
            return null;
        }
    }

	public static HashMap<String, String> getRequestMap(HttpServletRequest request) 
	{
        String param="";
        HashMap<String, String> map = new HashMap<String, String>();
        try 
        {
        	Map parameter = request.getParameterMap();
            if (parameter == null)
            {
                return null;
            }
            Object paramKey = null;
            String[] paramValue = null;

            Iterator it = parameter.keySet().iterator();
            while (it.hasNext()) 
            {
                paramKey = it.next();
                paramValue = (String[]) parameter.get(paramKey);
                String strKey = paramKey.toString();
                if (paramValue.length > 1) 
                {
                    param = request.getParameterValues(paramKey.toString()).toString();
                    map.put(strKey, StringUtil.deleteJavaScript((param )));
                }
                else 
                {
                    map.put(strKey, (paramValue[0] == null) ? "" : StringUtil.deleteJavaScript(paramValue[0].trim().toString()));
                }
            }
            return map;
        } 
        catch (Exception e) 
        {
            System.out.println("CommonUtil getRequestMap()" + e.toString());
            return null;
        }
    }
}
