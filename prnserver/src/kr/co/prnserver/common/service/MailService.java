
package kr.co.prnserver.common.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import kr.co.prnserver.framework.CommonService;
import kr.co.prnserver.util.StringUtil;
import kr.co.prnserver.template.dao.TemplateDao;

import kr.co.prnserver.framework.ValidateException;
import kr.co.prnserver.member.dao.MemberDao;
import kr.co.prnserver.framework.PrnConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MailService extends CommonService
{
    @Autowired
    private TemplateDao templateDao;
	@Autowired
	private MemberDao memberDao;
    
    /**
     * 
     * @param param
     */
    public synchronized void sendMail(HashMap<String, String> param) throws Exception
    {
    	if (validate(param))
        {
            String tid = StringUtil.nvl(param.get("tid"), "");
            
            HashMap<String, String> template = new HashMap<String, String>();
            template.put("id", tid);
            template = templateDao.select(template);
            
            if (template == null)
            {
                throw new Exception("메일 템플릿이 존재하지 않습니다.");
            }
            
            
            PrnConfig config = PrnConfig.getInstance();
            
            String host = config.getProperty("mail.host");
            if (host == null)
            {
                host = "spam.tids.co.kr";
            }
            
            String contents = StringUtil.nvl(template.get("CONTENT"), "");
            String title = StringUtil.nvl(template.get("TITLE"), "");
            
            contents = contents.replaceAll("%number%",   StringUtil.nvl(param.get("number"), ""));
   
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            
            Session session = Session.getDefaultInstance(props, null);
            
            try
	        {
	            MimeMessage msg = new MimeMessage(session);                    
	            msg.setFrom(new InternetAddress(param.get("email")));                  //보내는 사람 설정
	            InternetAddress[] address = {new InternetAddress(param.get("receiver"))};                  
	            msg.setRecipients(Message.RecipientType.TO, address);                       //받는 사람설정                   
	            msg.setSubject(title);                                      //제목 설정
	            msg.setSentDate(new java.util.Date());                          //보내는 날짜 설정
	            msg.setContent(contents, "text/html;charset=euc-kr");             // 내용 설정 (HTML 형식)
	            msg.setHeader("Content-Transfer-Encoding","base64");
	            msg.setSentDate(new Date());
	            
	            Transport.send(msg);  
	            
	            param.put("result", "succ");
	            param.put("message", "");

	        	//memberDao.insertMailResult(param);
	        }catch (Exception ex)
	        {	     
	        	 ex.printStackTrace();
	        	 
	        	 param.put("result", "fail");
	        	 param.put("message", ex.toString());
	        	// memberDao.insertMailResult(param);
	        }
                 
        }
    }
    
    
    /**
     * 
     */
    @Override
    public boolean validate(HashMap<String, String> param) throws ValidateException
    {
        if ("".equals(StringUtil.nvl(param.get("tid"), "")))
        {
            throw new ValidateException("메일 템플릿 ID가 존재하지 않습니다.");
        }
        /*
        if ("".equals(StringUtil.nvl(param.get("receiver"), "")))
        {
            throw new ValidateException("받는분 메일주소가 존재하지 않습니다.");
        }
        */
        if ("".equals(StringUtil.nvl(param.get("email"), "")))
        {
            throw new ValidateException("보내시는분 메일주소가 존재하지 않습니다.");
        }
        
        return true;
    }
    
    /**
     * 
     * @param param
     */
    public void sendMailToMR(HashMap<String, String> param) throws Exception
    {
        if (validate(param))
        {
            String tid = StringUtil.nvl(param.get("tid"), "");
            
            HashMap<String, String> template = new HashMap<String, String>();
            template.put("id", tid);
            template = templateDao.select(template);
            
            if (template == null)
            {
                throw new Exception("메일 템플릿이 존재하지 않습니다.");
            }
            
            
            PrnConfig config = PrnConfig.getInstance();
            
            String host = config.getProperty("mail.host");
            if (host == null)
            {
                host = "spam.tids.co.kr";
            }
            
            String mailtitle = StringUtil.nvl(param.get("mr"), "") + StringUtil.nvl(param.get("title"), "");
            String contents = StringUtil.nvl(template.get("CONTENT"), "");
            
            contents = contents.replaceAll("%mr%",   StringUtil.nvl(param.get("mr"), ""));
            contents = contents.replaceAll("%cnt%",   StringUtil.nvl(param.get("cnt"), ""));
            contents = contents.replaceAll("%point%",   StringUtil.nvl(param.get("point"), ""));
            
        	for (int j = 1 ; j<=Integer.parseInt( String.valueOf(param.get("temCnt"))) ; j++) {
    			contents = contents.replaceAll("%pname"+j+"%",   StringUtil.nvl(param.get("pname"+j), ""));
    			contents = contents.replaceAll("%name"+j+"%",   StringUtil.nvl(param.get("name"+j), ""));
    			contents = contents.replaceAll("%id"+j+"%",   StringUtil.nvl(param.get("id"+j), ""));
    			contents = contents.replaceAll("%point"+j+"%",   StringUtil.nvl(param.get("point"+j), ""));
           	}
                
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            
            Session session = Session.getDefaultInstance(props, null);
            
            MimeMessage msg = new MimeMessage(session);                    
            msg.setFrom(new InternetAddress(param.get("email")));                  //보내는 사람 설정
            InternetAddress[] address = {new InternetAddress(param.get("receiver"))};                  
            msg.setRecipients(Message.RecipientType.TO, address);                       //받는 사람설정                   
            msg.setSubject(mailtitle);                                      //제목 설정
            msg.setSentDate(new java.util.Date());                          //보내는 날짜 설정
            msg.setContent(contents, "text/html;charset=euc-kr");             // 내용 설정 (HTML 형식)
            msg.setHeader("Content-Transfer-Encoding","base64");
            msg.setSentDate(new Date());
            
            Transport.send(msg);                        
            
        }
    }
}
