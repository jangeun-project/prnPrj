/*
 * Created on 2011. 3. 22.
 *
 * 
 * 
 */
package kr.co.prnserver.template.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.prnserver.common.service.MailService;
import kr.co.prnserver.framework.ValidateException;
import kr.co.prnserver.member.dao.MemberDao;
import kr.co.prnserver.template.TemplateService;
import kr.co.prnserver.util.MapUtil;
import kr.co.prnserver.util.SessionUtil;
import kr.co.prnserver.util.StringUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TemplateController
{

    @Autowired
    private TemplateService service;
    @Autowired
    private MailService mailService;
	@Autowired
	private MemberDao memberDao;
    
    private Logger log = Logger.getLogger(this.getClass());

    public void setTemplateService(TemplateService templateService)
    {
        this.service = templateService;
    }
    
    
    @RequestMapping(value="/TemplateList.do")
    public ModelAndView list(HttpServletRequest request)
    {
        ModelAndView mav = new ModelAndView("prnserver/template/TemplateList");
        
        try
        {
            List<HashMap<String, String>> lists = service.getTemplateList(null);
            
            mav.addObject("count",    lists.size());
            mav.addObject("template", lists);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            log.error("TemplateController] " + e.toString());
        }
        
        return mav;
    }
    
    
    //발송
    @RequestMapping(value="/SendMail.do", method=RequestMethod.POST)
    public ModelAndView sendMail(HttpServletRequest request)
    {
        ModelAndView mav = new ModelAndView();
        
        String logId = "";
        
        HashMap<String, String> param = MapUtil.getRequestMap(request);
        
        param.put("userid", logId);
        
        HttpSession session = request.getSession(false);
        
        String email = (String)session.getAttribute(SessionUtil.SESSION_GD);
        param.put("email", email);
        
        try
        {
        	HashMap<String, String> itemInfom  = memberDao.selectMember(param);
            
            param.put("item", itemInfom.get("NAME"));
            param.put("url", itemInfom.get("URL"));
            param.put("hname", itemInfom.get("ENAME"));
            param.put("hphone", itemInfom.get("PHONE"));
            param.put("hemail", itemInfom.get("EMAIL"));
            
        	if (mailService.validate(param))
            {
        		
        		String getEmail = StringUtil.nvl(param.get("memId"), "");
	        	try
		        {
	        		String itemSeq = StringUtil.nvl(param.get("itemSeq"), "");
        			
        			HashMap<String, String> Param = new HashMap<String, String>();
        			param.put("id", getEmail);
	                HashMap<String, String> memberInfo = memberDao.selectMember(param);
	                
	                param.put("receiver", memberInfo.get("EMAIL"));
	                param.put("name", memberInfo.get("MEM_NAME"));
	                param.put("hosp", memberInfo.get("HOSPITAL"));
	                param.put("password", memberInfo.get("PW"));
	                
        			mailService.sendMail(param);
        			param.put("result", "succ");
	            	param.put("message", "");
		        	//memberDao.insertMailResult(param);
		        }
		        catch (Exception ex)
		        {
		            ex.printStackTrace();
		            log.error("TemplateController.send] " + ex.toString());
		            mav.addObject("message",  ex.getMessage());
		            
		        	param.put("result", "fail");
		        	param.put("message", ex.toString());
		        	//memberDao.insertMailResult(param);
		        }
		        		        
	            mav.addObject("message",  "메일 발송 하였습니다.");
	            //mav.addObject("goUrl",    "/sfc/MailMaster.do");
        	}
        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
            log.error("TemplateController.send] " + ex.toString());
            mav.addObject("message",  ex.getMessage());	
        }
        mav.setViewName("common/result");
        
        return mav;
    }
    
}
