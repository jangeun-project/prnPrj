package kr.co.prnserver.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	 /**
     * NULL �Ǵ� ���鹮�ڸ� ��ü���ڷ� ��ȯ�Ѵ�.
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
     * ���ڸ� ���ڷ� ��ȯ�Ѵ�.
     * 
     * @param source ���ڷ� ��ȯ�� ����
     * @param dest   ������ ���ڰ� �ƴѰ�� ��ü�� ��
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
            int pos_start = 0;		// �ױ׽�����ġ ����
            int pos_end;		// �ױ�������ġ ����
            int pos_spacebar;	// �ױװ�����ġ ����

            if(body == null) return "";

            while ((pos_start = body.indexOf("<", pos_start)) >= 0){ 		// �ױ� ������ġ ����
                pos_end = body.indexOf(">", pos_start+1); 		// �ױ� ������ġ ����
                pos_spacebar = body.indexOf(" ", pos_start+1); 	// �ױ� ������ġ ����

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
     * \�� ���� Ư�����ڸ� ��ü�Ѵ�. String.replaceAll���� ������
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
