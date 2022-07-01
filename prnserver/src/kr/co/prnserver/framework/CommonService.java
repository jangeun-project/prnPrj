/*
 * Created on 2011. 3. 23.
 *
 * 
 * 
 */
package kr.co.prnserver.framework;

import java.util.HashMap;
import org.apache.log4j.Logger;

public abstract class CommonService
{
    private Logger log = Logger.getLogger(this.getClass());
    
    
    @SuppressWarnings("unchecked")
    public void error(Class clazz, String method, Exception ex)
    {
        ex.printStackTrace();
        System.err.println(clazz.toString() + "-" + method + ":" + ex.toString());
        log.error(clazz.toString() + "-" + method + ":" + ex.toString());
    }
    
    @SuppressWarnings("unchecked")
    public void debug(Class clazz, String method, String debugMessage)
    {
        System.out.println(clazz.toString() + "-" + method + ":" + debugMessage);
        log.debug(clazz.toString() + "-" + method + ":" + debugMessage);
    }
    
    public abstract boolean validate(HashMap<String, String> param) throws ValidateException;
}
