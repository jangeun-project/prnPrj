package kr.co.prnserver.member.controller;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.prnserver.member.dao.MemberDao;
import kr.co.prnserver.util.CookieUtil;
import kr.co.prnserver.util.MapUtil;
import kr.co.prnserver.util.SessionUtil;
import kr.co.prnserver.util.StringUtil;
import kr.co.prnserver.prnlist.dao.PrnMstDao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.co.prnserver.util.HashHelper;

@Controller
public class LoginController {

	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private PrnMstDao prnDao;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@RequestMapping(value="/login.do")
	public ModelAndView prnServerLogin(HttpServletRequest request, HttpServletResponse response)
	{
		Cookie[] cookies = request.getCookies();
		   
		ModelAndView mav = new ModelAndView("login");
		   
		
		//String savedId = CookieUtil.getCookie(request, response, CookieUtil.COOKIE_ID);
		//mav.addObject("savedId", "savedId");
		
		return mav;
	}
	
    @RequestMapping(value="/logout.do", method=RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response)
    {
        ModelAndView mav = new ModelAndView("redirect:/login.do");

        HttpSession session = request.getSession();
        session.removeAttribute("session_id");
        session.removeAttribute("session_nm");
        session.removeAttribute("session_userid");
        
        return mav;
    }
	
	@RequestMapping(value="/enterlogin.do")
	public ModelAndView enterlogin(HttpServletRequest request, HttpServletResponse response)
	{
        ModelAndView mav = new ModelAndView();
        HashMap<String, String> loginInfo = new HashMap<String, String>();
        String userid = request.getParameter("userid");
        String userpw = request.getParameter("userpw");
        loginInfo.put("userid", userid);
        loginInfo.put("userpw", userpw);
        
        HashMap<String, String> memberInfo = null;
       
        boolean rtval = false;
        try
        {
        	
        	//슈퍼관리자 grage= 9: 슈퍼관리자, 1:일반사용자, 2:승인권자, 3:승인이관자 
        	if ("boanmaster".equals(userid) && "dyfhd480!@".equals(userpw) )  {
    			
    			HttpSession session = request.getSession();
      	        session.setAttribute(SessionUtil.SESSION_ID, "admin");
      	        session.setAttribute(SessionUtil.SESSION_NM, "관리자");
      	        session.setAttribute(SessionUtil.SESSION_GD, "9");
      	        session.setAttribute(SessionUtil.SESSION_DP, "0");
      	        session.setAttribute(SessionUtil.SESSION_USERID, "admin");
      	        
      	        mav.setViewName("redirect:/MainList.do");
        	
        	/* 관리자 DB화 처리
        	if ("boanmaster".equals(useribd))  {	
        		
        		memberInfo = memberDao.selectAdminMember(loginInfo);
        		
        		//* 최초 비밀번호 :123qwe 으로 등록
        		if (memberInfo == null)
	            {
        			HashMap<String, String> insertMap = new HashMap<String, String>();
        			insertMap.put("adminpw", HashHelper.getMD5("123qwe"));
        			
        			memberDao.insertAdminMember(insertMap);
        			
        			HttpSession session = request.getSession();
          	        session.setAttribute(SessionUtil.SESSION_ID, "admin");
          	        session.setAttribute(SessionUtil.SESSION_NM, "관리자");
          	        session.setAttribute(SessionUtil.SESSION_GD, "9");
          	        session.setAttribute(SessionUtil.SESSION_DP, "0");
          	        session.setAttribute(SessionUtil.SESSION_USERID, "admin");
          	        
          	        mav.setViewName("redirect:/MainList.do");  
	                
	            }else{
	            	
	            	String password = HashHelper.getMD5(loginInfo.get("userpw")); 
	            	
	            	if (!password.equals(memberInfo.get("password"))) {
	                    mav.setViewName("common/result");
	                    mav.addObject("message_funcion", "Y");
	                    mav.addObject("message", "비밀 번호가 맞지 않습니다. 다시 확인하세요.");
	                }else{
	            		
	            		HttpSession session = request.getSession();
	          	        session.setAttribute(SessionUtil.SESSION_ID, "admin");
	          	        session.setAttribute(SessionUtil.SESSION_NM, "관리자");
	          	        session.setAttribute(SessionUtil.SESSION_GD, "9");
	          	        session.setAttribute(SessionUtil.SESSION_DP, "0");
	          	        session.setAttribute(SessionUtil.SESSION_USERID, "admin");
	          	        
	          	        mav.setViewName("redirect:/MainList.do");  
	                }

	            }

        		return mav;
        	*/	
        	}else{
        	
	            memberInfo = memberDao.selectMember(loginInfo);
	            
	            if (memberInfo == null)
	            {
	                mav.setViewName("common/result");
	                mav.addObject("message_funcion", "Y");
	                mav.addObject("message", "ID 및 비밀 번호를 다시 확인하세요.");
	                
	            }else{
	            	String firtPassWord = memberInfo.get("password");
	            	
	            	// 최초 로그인 초기 비번 1234
	            	if (firtPassWord == null) {
	            		
	            		if ("1234".equals(loginInfo.get("userpw"))) {
	            			rtval = true;
	            		}else{
	            			mav.setViewName("common/result");
	 	                    mav.addObject("message_funcion", "Y");
	 	                    mav.addObject("message", "비밀 번호가 맞지 않습니다. 다시 확인하세요.");
	            		}
	            		
	            	}else{

		                String password = HashHelper.getMD5(loginInfo.get("userpw"));
		               	if (!password.equals(memberInfo.get("password"))) {
		                    mav.setViewName("common/result");
		                    mav.addObject("message_funcion", "Y");
		                    mav.addObject("message", "비밀 번호가 맞지 않습니다. 다시 확인하세요.");
		                }else{
		               	
		                	rtval = true;
		                }

	            	}
	            }
        	}
        }
        catch (Exception e)
        {
            log.error(e.toString());
        }
        
      	HashMap<String, String> param = MapUtil.getRequestMap(request);
      	
      	if (memberInfo != null && rtval)
        {
      		log.info("===>정상로그인 : " + loginInfo.get("userid"));
      		
	        HttpSession session = request.getSession();
	        session.setAttribute(SessionUtil.SESSION_ID, memberInfo.get("loginId"));
	        session.setAttribute(SessionUtil.SESSION_NM, memberInfo.get("userName"));
	        session.setAttribute(SessionUtil.SESSION_GD, memberInfo.get("grade"));
	        session.setAttribute(SessionUtil.SESSION_DP, String.valueOf(memberInfo.get("deptCode")));
	        session.setAttribute(SessionUtil.SESSION_USERID, String.valueOf(memberInfo.get("userIdx")));
	        
	       // CookieUtil.setCookie(response, CookieUtil.COOKIE_ID,  memberInfo.get("USER_ID"), CookieUtil.COOKIE_DOMAIN);

		    
	        mav.setViewName("redirect:/MainList.do");  
	    }
 
        return mav;
		
	}
	
    @RequestMapping(value="/pwchangepop.do")
    public ModelAndView pwchangepop(HttpServletRequest request, HttpServletResponse response)
    {
    	String logId = request.getParameter("userId");
    	
        ModelAndView mav = new ModelAndView("ChangePwpop");
        
		mav.addObject("userid", logId);
		
        return mav;
    }
	
    @RequestMapping(value="/doChangePw.do", method=RequestMethod.POST)
    public ModelAndView doChangePw(HttpServletRequest request, HttpServletResponse response)
    {
    	ModelAndView mav = new ModelAndView("common/result");
    	 
    	String currentPassword = request.getParameter("currentPassword");
    	String password = request.getParameter("password");
    	
    	HashMap<String, String> loginInfo = new HashMap<String, String>();
    	loginInfo.put("userid", request.getParameter("userid"));
    	loginInfo.put("userpw", HashHelper.getMD5(request.getParameter("currentPassword")));
    	loginInfo.put("newuserpw", HashHelper.getMD5(request.getParameter("password")));
        
        
    	HashMap<String, String> memberInfo = null;
		try
        {
			if ("admin".equals( request.getParameter("userid")))  {
				memberInfo = memberDao.selectAdminMember(loginInfo);
			}else{
				memberInfo = memberDao.selectMember(loginInfo);
			}
	            
            if (memberInfo == null)
            {
            	mav.addObject("message_funcion", "Y");
                mav.addObject("message", "사용자를 찾을수 없습니다.");
                
                return mav;
            }else{
            	String userIdx = String.valueOf((memberInfo.get("userIdx")));
            	loginInfo.put("userIdx", userIdx);
            	
            	 int cnt = prnDao.selectMemberDetail(loginInfo);
            	 if (cnt > 0) {
            		 String getPassword = memberInfo.get("password");
            		 if (getPassword == null) {
            			 
            			 prnDao.setUpdatePw(loginInfo);
            		 }else{
	                	 if (!getPassword.equals(loginInfo.get("userpw"))) {
	                    	mav.addObject("message_funcion", "Y");
	                        mav.addObject("message", "현재 비밀번호를 다시 입력해 주세요.");
	                        
	                        return mav;
	                	 }else{   
	                		 prnDao.setUpdatePw(loginInfo);
	                	 }
            		 }
          			
          		}else{
            			prnDao.setInsertPw(loginInfo);

                }
           	 	
            }

		 
        }catch (Exception e)
        {
            log.error(e.toString());
        }

	 	mav.addObject("self_close", "Y");
	    mav.addObject("reload_function", "Y");
        mav.addObject("message", "변경 처리하였습니다.");
		
        return mav;
    }

}


