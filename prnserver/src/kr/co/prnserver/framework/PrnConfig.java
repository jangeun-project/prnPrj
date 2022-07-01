package kr.co.prnserver.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.Properties;

import kr.co.prnserver.util.StringUtil;

public class PrnConfig extends Properties implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -160647179898954304L;
    private final String configFileName = "config.properties";
    
    private static PrnConfig instance = null;
    
    
    public static PrnConfig getInstance()
    {
        if  (instance == null)
        {
            instance = new PrnConfig();
        }
        return instance;
    }
    
    private PrnConfig()
    {
        Properties jvmEnv = System.getProperties();
        
        String seperator = jvmEnv.getProperty("file.separator");
        String webroot   = jvmEnv.getProperty("webapp.root");
        
        String configFile = webroot + seperator + "WEB-INF" + seperator + "conf" + seperator + configFileName;

        File file = new File(configFile);
        if (file.exists())
        {
            FileInputStream fis = null;
            try
            {
                fis = new FileInputStream(file);
                super.load(fis);
                fis.close();
                fis = null;
            }
            catch (Exception ex)
            {
                if (fis != null)
                {
                    fis = null;
                }
            }
            
        }
    }
    
    public String getProperty(String key)
    {
        return super.getProperty(key);
    }
    
    public String getPropertyPath(String key)
    {
        String seperator = System.getProperty("file.separator");
        String path = super.getProperty(key);
        if (path != null)
        {
            path = StringUtil.replaceSpecialChar(path, "/", seperator);
        }
        path += seperator;

        return System.getProperty("webapp.root") + path;
    }
    
}
