package kr.co.prnserver.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	 /**
     * NULL 또는 공백문자를 대체문자로 변환한다.
     * 
     * @param source
     * @param dest
     * @return
     */
    public static String nvl(String source, String dest)
    {
        if (source == null || "".equals(source))
        {
            return dest;
        }
        else
        {
            return source;
        }
    }
    
    public static String nvl(Object source, String dest)
    {
        if (source == null || "".equals(source))
        {
            return dest;
        }
        else
        {
            return (String)source;
        }
    }
    
    
    /**
     * 문자를 숫자로 변환한다.
     * 
     * @param source 숫자로 변환할 변수
     * @param dest   변수가 숫자가 아닌경우 대체할 값
     * @return
     */
    public static int toNumber(String source, int dest)
    {
        try
        {
            return Integer.parseInt(source);
        } 
        catch (NumberFormatException ex) 
        {
            return dest;
        }
        
    }
    
    public static String DecimalToString(Object obj)
    {
        if (obj != null)
        {
            BigDecimal i = (BigDecimal)obj;
            return i.toString();
        }
        else
        {
            return null;
        }
    }
    
    public static int DecimalToNumber(Object obj)
    {
        if (obj != null)
        {
            BigDecimal i = (BigDecimal)obj;
           
            return Integer.parseInt(i.toString());
        }
        else
        {
            return 0;
        }
    }
    
    /**
     * 
     * @param body
     * @return
     */
    public static String deleteJavaScript(String body)
    {
        try
        {
            int pos_start = 0;		// 테그시작위치 저장
            int pos_end;		// 테그종료위치 저장
            int pos_spacebar;	// 테그공백위치 저장

            if(body == null) return "";

            while ((pos_start = body.indexOf("<", pos_start)) >= 0){ 		// 테그 시작위치 저장
                pos_end = body.indexOf(">", pos_start+1); 		// 테그 종료위치 저장
                pos_spacebar = body.indexOf(" ", pos_start+1); 	// 테그 공백위치 저장

                if(pos_spacebar < 0 || pos_spacebar > pos_end) pos_spacebar = pos_end;
                String tag = body.substring(pos_start+1, pos_spacebar);

                if(tag.trim().toUpperCase().equals("SCRIPT")){
                    int imp_pos_start = body.indexOf("</", pos_start+1);
                    pos_end = body.indexOf(">", imp_pos_start+2);

                    body = body.substring(0, pos_start) + body.substring(pos_end+1);
                }else{
                    pos_start++;
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            body = "[FAIL TO HTML TAG ENCODE]";
        }
        return body;
    }
    
    /**
     * \와 같은 특수문자를 대체한다. String.replaceAll에서 오류뜸
     * @param s
     * @param from
     * @param to
     * @return
     */
    public static String replaceSpecialChar(String s, String from, String to)
    {
        StringBuffer buffer = new StringBuffer();
        
        int i = s.indexOf(from);
        do
        {
            if (i >= 0)
            {
                if (i == 0)
                {
                    buffer.append(to);
                }
                else
                {
                    buffer.append(s.substring(0, i)).append(to);
                }
                s = s.substring(i + 1);
            }
        }
        while ((i = s.indexOf(from)) != -1);
        
        buffer.append(s);
        
        return buffer.toString();
    }
}
