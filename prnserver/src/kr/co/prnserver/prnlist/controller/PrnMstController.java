package kr.co.prnserver.prnlist.controller;

import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


import kr.co.prnserver.util.BinaryWriter;
import kr.co.prnserver.util.MapUtil;
import kr.co.prnserver.member.dao.MemberDao;
import kr.co.prnserver.prnlist.service.PrnMstService;
import kr.co.prnserver.util.PathUtil;
import kr.co.prnserver.util.StringUtil;
import kr.co.prnserver.common.service.MailService;

@Controller
public class PrnMstController {

	 private Logger log = Logger.getLogger(this.getClass());
	 
	@Autowired
	private PrnMstService prnService;
	
	@Autowired
	private MemberDao memberDao;
	
    @Autowired
    private MailService mailService;
	 
	 /**
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/viewImage.do")
	public void scanThumnail(@RequestParam(value="imagePath") String imagePath,
			                 @RequestParam(value="imageId") int imageId,
			                 HttpServletResponse response) throws IOException {
		
	
		new PathUtil(String.format("%s/%05d.jpg", imagePath, imageId )).write( response );	
	}
	
	 /**
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/downloadPdf.do")
	public void scanViewPdf(@RequestParam(value="imagePath") String imagePath,
			                 HttpServletResponse response) throws IOException {
		
		   new BinaryWriter(imagePath ).write( response );
	}
	
	
	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/MainList.do")
	 public ModelAndView GetMainList(HttpServletRequest request, HttpServletResponse response)
	 {
		HttpSession session = request.getSession();
	    String sessionId = (String)session.getAttribute("session_id");
	    
	    String seachPeriod = StringUtil.nvl(request.getParameter("seachPeriod"), "1D");
	    String pageType = StringUtil.nvl(request.getParameter("pageType"), "list");
	    
	    //9:관리자, 1:사용자, 2:승인자, 3:승인이관자
	    String sessionGd = (String)session.getAttribute("session_gd");
    
	    
	    HashMap<String, Object> prnInfo = new HashMap<String, Object>();

	    int totCount =  0;

		ModelAndView mav = new ModelAndView();
		
	    mav.addObject("sessionGd", sessionGd);
	    
        prnInfo.put("stRow", String.valueOf(1)); 
        prnInfo.put("edRow", String.valueOf(5));
		
	    prnInfo.put("userId", sessionId);
        prnInfo.put("authCheck", "user");
        
        String authCheck = "user";
        
	    if ("9".equals(sessionGd)) {
	    	prnInfo.put("authCheck", "admin");
	    }
        
		try
        {
			//조회가능 날짜-> 
			String setDay = "1";
		       
		    prnInfo.put("seq", "1");
			List<HashMap<String, String>> configList= prnService.getConfigList(prnInfo);
			mav.addObject("configList", configList);
			
			if(configList!=null && configList.size() > 0)
	       	{
				HashMap<String, String> hm = configList.get(0);
				setDay = String.valueOf(hm.get("setDay"));
				prnInfo.put("setDay", setDay);
	       	}
			//->
			
			if ("9".equals(sessionGd)) {
				prnInfo.put("setDay", "30");
			}
			
			String searchSt = request.getParameter("searchSt");
			String searchEt = request.getParameter("searchEt");
			
	        if (searchSt == null || searchSt == "") {
	        	searchSt = getCurrentSDt(Integer.parseInt(setDay));
	        }
	        
	        if (searchEt == null || searchEt == "") {
	        	searchEt = getCurrentEDt(0);
	        }
		
		    // 스켄	
			List<HashMap<String, String>> scanList= prnService.getScanList(prnInfo);
			mav.addObject("scanList", scanList);
			
			// 발신
			List<HashMap<String, String>> sendList= prnService.getSendFaxList(prnInfo);
			mav.addObject("sendList", sendList);
			
			// 수신
			List<HashMap<String, String>> receiveList= prnService.getReceiveFaxList(prnInfo);
			mav.addObject("receiveList", receiveList);
			 
			// 승인
			List<HashMap<String, String>> ConfirmList= prnService.getConfirmFaxUserList(prnInfo);
			mav.addObject("ConfirmList", ConfirmList);
			
        }catch (Exception e)
        {
            log.error(e.toString());
        }
      
		mav.addObject("menuCur", "menu00");
		mav.setViewName("MainList");
		
	    
        return mav;
		 
	 }
	
	
	 /**
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/ScanList.do")
	 public ModelAndView GetScanList(HttpServletRequest request, HttpServletResponse response)
	 {
		HttpSession session = request.getSession();
	    String sessionId = (String)session.getAttribute("session_id");
	    
	    //9:관리자, 1:사용자, 2:승인자, 3:승인이관자
	    String sessionGd = (String)session.getAttribute("session_gd");
	    String authCheck = "user";
	    
	    if ("9".equals(sessionGd)) {
	    	authCheck = "admin";
	    }
	    
	    String seachPeriod = StringUtil.nvl(request.getParameter("seachPeriod"), "1D");
	    String pageType = StringUtil.nvl(request.getParameter("pageType"), "list");
	    
	    HashMap<String, Object> prnInfo = new HashMap<String, Object>();

		int pageRow  = 10;
	    int totCount =  0;

        String page = StringUtil.nvl(request.getParameter("page"), "1");
        int iPage = StringUtil.toNumber(page, 10);
        
        prnInfo.put("stRow", String.valueOf((iPage - 1) * pageRow + 1)); 
        prnInfo.put("edRow", String.valueOf(iPage * pageRow));
        
		ModelAndView mav = new ModelAndView();
		
		String searchSt = request.getParameter("searchSt");
		String searchEt = request.getParameter("searchEt");
		
	    prnInfo.put("searchDocNm", request.getParameter("searchDocNm"));
	    prnInfo.put("searchDepart", request.getParameter("searchDepart"));
	    prnInfo.put("searchUserNm", request.getParameter("searchUserNm"));
	    prnInfo.put("userId", sessionId);
        prnInfo.put("authCheck", authCheck);
        
	
		try
        {
	        //조회가능 날짜-> 
			String setDay = "1";
		       
		    prnInfo.put("seq", "1");
			List<HashMap<String, String>> configList= prnService.getConfigList(prnInfo);
			mav.addObject("configList", configList);
			
			if(configList!=null && configList.size() > 0)
	       	{
				HashMap<String, String> hm = configList.get(0);
				setDay = String.valueOf(hm.get("setDay"));
				prnInfo.put("setDay", setDay);
	       	}
			//->
			
			if (searchSt == null || searchSt == "") {
	        	searchSt = getCurrentSDt(Integer.parseInt(setDay));
	        }
	        
	        if (searchEt == null || searchEt == "") {
	        	searchEt = getCurrentEDt(0);
	        }
			
		    prnInfo.put("searchSt", StringUtil.nvl(request.getParameter("searchSt"), searchSt));
		    prnInfo.put("searchEt", StringUtil.nvl(request.getParameter("searchEt"), searchEt));
			
			List<HashMap<String, String>> scanList= prnService.getScanList(prnInfo);
			mav.addObject("scanList", scanList);
			
			if(scanList!=null && scanList.size() > 0)
        	{
				HashMap<String, String> hm = scanList.get(0);
        		totCount = Integer.parseInt(String.valueOf(hm.get("totalcnt")));
        		mav.addObject("totalcnt", totCount);
        	}else{
        		totCount = 0;
        		mav.addObject("totalcnt", totCount);
        	}
			 
        }catch (Exception e)
        {
            log.error(e.toString());
        }
      
        /*******************************************************************
         ** 
         **
         **/
		 HashMap<String, Object> pagingInfo = new HashMap<String, Object>();
		 
        pagingInfo.put("page",  page);
        pagingInfo.put("count", totCount);
        pagingInfo.put("prows", pageRow);
        pagingInfo.put("psize", 10);
        mav.addObject("paging", pagingInfo);
        /******************************************************************/
        
	    mav.addObject("seachPeriod", seachPeriod);
	    mav.addObject("pageType", pageType);
	    
		mav.addObject("menuCur", "menu01");
		mav.setViewName("ScanList");
		
		mav.addObject("searchSt", searchSt);
		mav.addObject("searchEt", searchEt);
		mav.addObject("sessionGd", sessionGd);
		
        return mav;
		 
	 }
	 
	/** 팩스발송
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/SendFaxList.do")
	 public ModelAndView SendFaxList(HttpServletRequest request, HttpServletResponse response)
	 {
		HttpSession session = request.getSession();
	    String sessionUserId = (String)session.getAttribute("session_userid");
	    String sessionId = (String)session.getAttribute("session_id");
	    
	    //9:관리자, 1:사용자, 2:승인자, 3:승인이관자
	    String sessionGd = (String)session.getAttribute("session_gd");
	    String authCheck = "user";
	    
	    
	    String seachPeriod = StringUtil.nvl(request.getParameter("seachPeriod"), "1D");
	    String pageType = StringUtil.nvl(request.getParameter("pageType"), "list");
	    
	    HashMap<String, Object> prnInfo = new HashMap<String, Object>();

		int pageRow  = 10;
	    int totCount =  0;

        String page = StringUtil.nvl(request.getParameter("page"), "1");
        int iPage = StringUtil.toNumber(page, 10);
        
        prnInfo.put("stRow", String.valueOf((iPage - 1) * pageRow + 1)); 
        prnInfo.put("edRow", String.valueOf(iPage * pageRow));
        
        
		ModelAndView mav = new ModelAndView();
		
		String searchSt = request.getParameter("searchSt");
		String searchEt = request.getParameter("searchEt");

	    prnInfo.put("searchDocNm", request.getParameter("searchDocNm"));
	    prnInfo.put("searchDepart", request.getParameter("searchDepart"));
	    prnInfo.put("searchUserNm", request.getParameter("searchUserNm"));
	    prnInfo.put("userId", sessionId);
	    prnInfo.put("authCheck", authCheck);
	    
        
		try
        {
			//조회가능 날짜-> 
			String setDay = "1";
		       
		    prnInfo.put("seq", "1");
			List<HashMap<String, String>> configList= prnService.getConfigList(prnInfo);
			mav.addObject("configList", configList);
			
			if(configList!=null && configList.size() > 0)
	       	{
				HashMap<String, String> hm = configList.get(0);
				setDay = String.valueOf(hm.get("setDay"));
				prnInfo.put("setDay", setDay);
	       	}
			//->
			
		    if ("9".equals(sessionGd)) {
		    	prnInfo.put("setDay", "");
		    	prnInfo.put("authCheck", "admin");
		    }
		    
			if (searchSt == null || searchSt == "") {
	        	searchSt = getCurrentSDt(Integer.parseInt(setDay));
	        }
	        
	        if (searchEt == null || searchEt == "") {
	        	searchEt = getCurrentEDt(0);
	        }
			
		    prnInfo.put("searchSt", StringUtil.nvl(request.getParameter("searchSt"), searchSt));
		    prnInfo.put("searchEt", StringUtil.nvl(request.getParameter("searchEt"), searchEt));
		    
		    
			List<HashMap<String, String>> list= prnService.getSendFaxList(prnInfo);
			mav.addObject("list", list);
			
			if(list!=null && list.size() > 0)
        	{
				HashMap<String, String> hm = list.get(0);
        		totCount = Integer.parseInt(String.valueOf(hm.get("totalcnt")));
        		mav.addObject("totalcnt", totCount);
        	}else{
        		mav.addObject("totalcnt", 0);
        	}

			 
        }catch (Exception e)
        {
            log.error(e.toString());
        }
      
		
  	     /*******************************************************************
         ** 
         **
         **/
		 HashMap<String, Object> pagingInfo = new HashMap<String, Object>();
		 
        pagingInfo.put("page",  page);
        pagingInfo.put("count", totCount);
        pagingInfo.put("prows", pageRow);
        pagingInfo.put("psize", 10);
        mav.addObject("paging", pagingInfo);
        /******************************************************************/
        
	    mav.addObject("seachPeriod", seachPeriod);
	    mav.addObject("pageType", pageType);
	    
	    mav.addObject("menuCur", "menu02");
		mav.setViewName("SendFaxList");
		
		mav.addObject("searchSt", searchSt);
		mav.addObject("searchEt", searchEt);
		mav.addObject("sessionGd", sessionGd);
		 
        return mav;
		 
	 }
	

		/** 팩스승인
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping(value="/ConfirmFaxList.do")
		public ModelAndView ConfirmFaxList(HttpServletRequest request, HttpServletResponse response)
		{
			HttpSession session = request.getSession();
		    String sessionUserId = (String)session.getAttribute("session_userid");
		    String sessionId = (String)session.getAttribute("session_id");
		    String sessionDp = (String)session.getAttribute("session_dp");
		    
		    //9:관리자, 1:사용자, 2:승인자, 3:승인이관자
		    String sessionGd = (String)session.getAttribute("session_gd");
		    String authCheck = "user";
		    
		    String seachPeriod = StringUtil.nvl(request.getParameter("seachPeriod"), "1D");
		    String pageType = StringUtil.nvl(request.getParameter("pageType"), "list");
		    
		    HashMap<String, Object> prnInfo = new HashMap<String, Object>();

			int pageRow  = 10;
		    int totCount =  0;

	        String page = StringUtil.nvl(request.getParameter("page"), "1");
	        int iPage = StringUtil.toNumber(page, 10);
	        
	        prnInfo.put("stRow", String.valueOf((iPage - 1) * pageRow + 1)); 
	        prnInfo.put("edRow", String.valueOf(iPage * pageRow));
	        
			
			String searchSt = request.getParameter("searchSt");
			String searchEt = request.getParameter("searchEt");
			
			ModelAndView mav = new ModelAndView();
			
		    prnInfo.put("searchDocNm", request.getParameter("searchDocNm"));
		    prnInfo.put("searchDepart", request.getParameter("searchDepart"));
		    prnInfo.put("searchUserNm", request.getParameter("searchUserNm"));
		    prnInfo.put("userId", sessionId);
		    
		    //어드민
		    if ("9".equals(sessionGd)) {
		    	authCheck = "admin";
		    	prnInfo.put("authCheck", "admin");
		    	
		    //승인자	
		    }else if ("2".equals(sessionGd)) {
		    	authCheck = "confirm";
		    	prnInfo.put("authCheck", "confirm");
		    //승인 이관자
		    }else if ("3".equals(sessionGd)) {
		    	authCheck = "confirm";
		    	prnInfo.put("authCheck", "confirm");
		    }else{
		    	authCheck = "user";
		    	prnInfo.put("authCheck", "user");
		    }
		    
			try
	        {
				
				List<HashMap<String, String>> list = null;
				
				//관리자
				if ("9".equals(sessionGd)) {
					
					prnInfo.put("setDay", "");
					if (searchSt == null || searchSt == "") {
			        	searchSt = getCurrentSDt(0);
			        }
			        
			        if (searchEt == null || searchEt == "") {
			        	searchEt = getCurrentEDt(0);
			        }
			        
			        prnInfo.put("searchSt",  searchSt);
			        prnInfo.put("searchEt",  searchEt);
			        
					list= prnService.getConfirmFaxList(prnInfo);
	
				}else{
					
					String setDay = "1";
				       
				    prnInfo.put("seq", "1");
					List<HashMap<String, String>> configList= prnService.getConfigList(prnInfo);
					mav.addObject("configList", configList);
					
					if(configList!=null && configList.size() > 0)
			       	{
						HashMap<String, String> hm = configList.get(0);
						setDay = String.valueOf(hm.get("setDay"));
						prnInfo.put("setDay", setDay);
			       	}
					
					if (searchSt == null || searchSt == "") {
			        	searchSt = getCurrentSDt(Integer.parseInt(setDay));
			        }
			        
			        if (searchEt == null || searchEt == "") {
			        	searchEt = getCurrentEDt(0);
			        }
					
				    prnInfo.put("searchSt",  searchSt);
				    prnInfo.put("searchEt",  searchEt);
					
		        
					list= prnService.getConfirmFaxUserList(prnInfo);
					
					
					//승인이관자숫자 조회
					HashMap<String, String> tranInfo = new HashMap<String, String>();
					tranInfo.put("userid", sessionId);
					int cnt = prnService.getTranConfirmCnt(tranInfo);
					mav.addObject("tranCnt", cnt);
				}
				
				mav.addObject("list", list);
				
				if(list!=null && list.size() > 0)
	        	{
					HashMap<String, String> hm = list.get(0);
	        		totCount = Integer.parseInt(String.valueOf(hm.get("totalcnt")));
	        		mav.addObject("totalcnt", totCount);
	        	}else{
	        		mav.addObject("totalcnt", 0);
	        	}
				 
	        }catch (Exception e)
	        {
	            log.error(e.toString());
	        }
	      
			
	  	     /*******************************************************************
	         ** 
	         **
	         **/
			 HashMap<String, Object> pagingInfo = new HashMap<String, Object>();
			 
	        pagingInfo.put("page",  page);
	        pagingInfo.put("count", totCount);
	        pagingInfo.put("prows", pageRow);
	        pagingInfo.put("psize", 10);
	        mav.addObject("paging", pagingInfo);
	        /******************************************************************/
	        
		    mav.addObject("seachPeriod", seachPeriod);
		    mav.addObject("pageType", pageType);
		    
		    mav.addObject("menuCur", "menu04");
			mav.setViewName("ConfirmFaxList");
			
			mav.addObject("searchSt", searchSt);
			mav.addObject("searchEt", searchEt);
			
			mav.addObject("sessionId", sessionId);
			mav.addObject("sessionDp", sessionDp);
			mav.addObject("sessionGd", sessionGd);
			 
	        return mav;
			 
	 }
	
		/** 수신 팩스
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping(value="/ReceiveFaxList.do")
		 public ModelAndView ReceiveFaxList(HttpServletRequest request, HttpServletResponse response)
		 {
			HttpSession session = request.getSession();                                    
		    String sessionUserId = (String)session.getAttribute("session_userid");     
		    String session_id = (String)session.getAttribute("session_id");
		    
		    //9:관리자, 1:사용자, 2:승인자, 3:승인이관자
		    String sessionGd = (String)session.getAttribute("session_gd");
		    String authCheck = "user";
		    
		                                                                               
		    String seachPeriod = StringUtil.nvl(request.getParameter("seachPeriod"), "1D");               
		    String pageType = StringUtil.nvl(request.getParameter("pageType"), "list");
		    String seachNumber = request.getParameter("seachNumber");       
		    
		    HashMap<String, Object> prnInfo = new HashMap<String, Object>();           
	                                                                                 
			int pageRow  = 10;                                                           
		    int totCount =  0;                                                         
	                                                                                 
	        String page = StringUtil.nvl(request.getParameter("page"), "1");         
	        int iPage = StringUtil.toNumber(page, 10);                               
	                                                                                 
	        prnInfo.put("stRow", String.valueOf((iPage - 1) * pageRow + 1));         
	        prnInfo.put("edRow", String.valueOf(iPage * pageRow));                   
	                                                                                 
	                                                                                 
			ModelAndView mav = new ModelAndView();                                       
			                                                                             
			String searchSt = request.getParameter("searchSt");                          
			String searchEt = request.getParameter("searchEt");                          
			                                                                             
		    prnInfo.put("searchDocNm", request.getParameter("searchDocNm"));
		    prnInfo.put("searchDepart", request.getParameter("searchDepart"));
		    prnInfo.put("searchUserNm", request.getParameter("searchUserNm"));
		    prnInfo.put("userId", sessionUserId);
		    prnInfo.put("seachNumber", seachNumber);

	       
		   try
	       {
				
				String setDay = "1";
			       
			    prnInfo.put("seq", "1");
				List<HashMap<String, String>> configList= prnService.getConfigList(prnInfo);
				mav.addObject("configList", configList);
				
				if(configList!=null && configList.size() > 0)
		       	{
					HashMap<String, String> hm = configList.get(0);
					setDay = String.valueOf(hm.get("setDay"));
					prnInfo.put("setDay", setDay);
		       	}
				
				if ("9".equals(sessionGd)) {
			    	authCheck = "admin";
			    	prnInfo.put("setDay", "");
			    }
				
				if (searchSt == null || searchSt == "") {
		        	searchSt = getCurrentSDt(Integer.parseInt(setDay));
		        }
		        
		        if (searchEt == null || searchEt == "") {
		        	searchEt = getCurrentEDt(0);
		        }   
				
			    prnInfo.put("searchSt", StringUtil.nvl(request.getParameter("searchSt"), searchSt));
			    prnInfo.put("searchEt", StringUtil.nvl(request.getParameter("searchEt"), searchEt));
				
				List<HashMap<String, String>> list= prnService.getReceiveFaxList(prnInfo);
				mav.addObject("list", list);
				
				if(list!=null && list.size() > 0)
	        	{
					HashMap<String, String> hm = list.get(0);
	        		totCount = Integer.parseInt(String.valueOf(hm.get("totalcnt")));
	        		mav.addObject("totalcnt", totCount);
	        	}else{
	        		mav.addObject("totalcnt", 0);
	        	}
				 
	       }catch (Exception e)
	       {
	           log.error(e.toString());
	       }
	     
			 /*******************************************************************
	         ** 
	         **
	         **/
			HashMap<String, Object> pagingInfo = new HashMap<String, Object>();
			 
	        pagingInfo.put("page",  page);
	        pagingInfo.put("count", totCount);
	        pagingInfo.put("prows", pageRow);
	        pagingInfo.put("psize", 10);
	        mav.addObject("paging", pagingInfo);
	        /******************************************************************/
	        
		    mav.addObject("seachPeriod", seachPeriod);
		    mav.addObject("pageType", pageType);
		    
			mav.addObject("menuCur", "menu03");
			mav.setViewName("ReceiveFaxList");
			
			mav.addObject("searchSt", searchSt);
			mav.addObject("searchEt", searchEt);
			mav.addObject("seachNumber", seachNumber);
			mav.addObject("sessionGd", sessionGd);
			
	        return mav;
			 
		 }
		
	 /**
	  * 팩스 발송 승인
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doConfirm.do")
	 public ModelAndView DoConfirm(HttpServletRequest request, HttpServletResponse response)  throws Exception 
	 {
		 ModelAndView mav = new ModelAndView("common/result");
		 
		 HttpSession session = request.getSession();
		 String sessionUserId = (String)session.getAttribute("session_userid");
		 String sessionId = (String)session.getAttribute("session_id");
			
		 String logId  = request.getParameter("logId");
		 String seachPeriod = StringUtil.nvl(request.getParameter("seachPeriod"), "1D");
		 String searchSt = request.getParameter("searchSt");
		 String searchEt = request.getParameter("searchEt");
		 String page = request.getParameter("page");
			
	        if (searchSt == null || searchSt == "") {
	        	searchSt = getCurrentSDt(0);
	        }
	        
	        if (searchEt == null || searchEt == "") {
	        	searchEt = getCurrentEDt(0);
	        }

		 
		 HashMap<String, String> memInfo = new HashMap<String, String>();
		 memInfo.put("logId", logId);
		 memInfo.put("confirmId", sessionId);
		 memInfo.put("tid", "MAIL01");
        
		 try
	        {
			   // 메일보내기 위해 팩스번호 얻음 
			   // HashMap<String, Object> Info = new HashMap<String, Object>();
			   // Info.put("logId", logId);
			   // List<HashMap<String, String>> list= prnService.getSendFaxInform(Info);
			   // if (list.size() > 0) {
			    	
			   // 	HashMap<String, String> im = new HashMap<String, String>();
			   // 	im = (java.util.HashMap<String, String>)list.get(0);
			   // 	memInfo.put("number", String.valueOf(im.get("faxnumber")));
			    	
		        	if (prnService.setUpdateConfirm(memInfo)) {
		        		//sendMailToAsk(memInfo);
		        	}
			   // }
			 
	        }catch (Exception e)
	        {
	            log.error(e.toString());
	        }

	        mav.setViewName("redirect:/ConfirmFaxList.do?seachPeriod=" + seachPeriod + "&searchSt=" +  searchSt + "&searchEt=" + searchEt + "&page=" + page);
	        
	        mav.addObject("message", "승인 처리하였습니다.");
	        
         return mav;
	 }	
	
	 /**
	  * 팩스 발송 반려
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doReject.do")
	 public ModelAndView DoReject(HttpServletRequest request, HttpServletResponse response)  throws Exception 
	 {
		 ModelAndView mav = new ModelAndView("common/result");
		 
		 HttpSession session = request.getSession();
		 String sessionUserId = (String)session.getAttribute("session_userid");
		 String sessionId = (String)session.getAttribute("session_id");
		 			
		 String logId  = request.getParameter("logId");
		 String seachPeriod = StringUtil.nvl(request.getParameter("seachPeriod"), "1D");
		 String searchSt = request.getParameter("searchSt");
		 String searchEt = request.getParameter("searchEt");
		 String page = request.getParameter("page");
			
	        if (searchSt == null || searchSt == "") {
	        	searchSt = getCurrentSDt(0);
	        }
	        
	        if (searchEt == null || searchEt == "") {
	        	searchEt = getCurrentEDt(0);
	        }

		 
		 HashMap<String, String> memInfo = new HashMap<String, String>();
		 memInfo.put("logId", logId);
		 memInfo.put("confirmId", sessionId);
		 memInfo.put("status", "W");
       
		 try
	        {
		        prnService.setUpdateConfirmStatus(memInfo) ;
			 
	        }catch (Exception e)
	        {
	            log.error(e.toString());
	        }

	        mav.setViewName("redirect:/ConfirmFaxList.do?seachPeriod=" + seachPeriod + "&searchSt=" +  searchSt + "&searchEt=" + searchEt + "&page=" + page);
	        
	        mav.addObject("message", "반려 처리하였습니다.");
	        
        return mav;
	 }	
	
		
	 public void sendMailToAsk(HashMap<String, String> param)
	 {
	    	HashMap<String, String> MailParam = new HashMap<String, String>();
	    	MailParam.put("userid", param.get("userId"));
	    	
	    	try {
		    	//담당자
		    	HashMap<String, String> userInfom  = memberDao.selectMember(MailParam);
		    	 
		    	HashMap<String, String> ReParam = new HashMap<String, String>();
		    	ReParam.put("tid", param.get("tid"));
		    	
		    	//받는분 메일
		    	ReParam.put("receiver", userInfom.get("email"));
		    	ReParam.put("userName", userInfom.get("userName"));
		    	
		    	//보내는분 메일
		    	ReParam.put("email", userInfom.get("email"));
		    
		    	ReParam.put("number", param.get("number"));
		        
				mailService.sendMail(ReParam);
				
				ReParam.put("result", "succ");
				ReParam.put("message", "");

	    	}
	    	catch (Exception e) {
				e.printStackTrace();
	    		
	    	}
	 }
	 
	
	 /**
	 * 사용자 리스트
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/MemberList.do")
	 public ModelAndView GetMemberList(HttpServletRequest request, HttpServletResponse response)
	 {
		ModelAndView mav = new ModelAndView();
		
        String searchId = request.getParameter("searchId"); 
        String searchName = request.getParameter("searchName"); 
        String searchDept = request.getParameter("searchDept"); 
        String searchGrade = request.getParameter("searchGrade");
        
        HashMap<String, Object> memInfo = new HashMap<String, Object>();
        
        memInfo.put("searchId", searchId);
        memInfo.put("searchName", searchName);
        memInfo.put("searchDept", searchDept);
        memInfo.put("searchGrade", searchGrade);
	    
        int pageRow  = 10;                                                           
	    int totCount =  0;                                                         
                                                                                 
        String page = StringUtil.nvl(request.getParameter("page"), "1");         
        int iPage = StringUtil.toNumber(page, 10);                               
                                                                                 
        memInfo.put("stRow", String.valueOf((iPage - 1) * pageRow + 1));         
        memInfo.put("edRow", String.valueOf(iPage * pageRow));     
               
		try
        {
			List<HashMap<String, String>> memberList= prnService.getMemberList(memInfo);
			mav.addObject("memberList", memberList);
			
			if(memberList!=null && memberList.size() > 0)
        	{
				HashMap<String, String> hm = memberList.get(0);
        		totCount = Integer.parseInt(String.valueOf(hm.get("totCnt")));
        		mav.addObject("totalcnt", totCount);
        	}
		 
        }catch (Exception e)
        {
            log.error(e.toString());
        }
      
		mav.addObject("menuCur", "menu05");
		mav.setViewName("MemberList");
		
		 /*******************************************************************
         ** 
         **
         **/
		HashMap<String, Object> pagingInfo = new HashMap<String, Object>();
		 
        pagingInfo.put("page",  page);
        pagingInfo.put("count", totCount);
        pagingInfo.put("prows", pageRow);
        pagingInfo.put("psize", 10 );
        mav.addObject("paging", pagingInfo);
        /******************************************************************/
		 
        mav.addObject("searchId", searchId);
        mav.addObject("searchName", searchName);
        mav.addObject("searchDept", searchDept);
        mav.addObject("searchGrade", searchGrade);
        return mav;
		 
	 }
	 
	
	 /**
	 * 로그 리스트
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/LogList.do")
	 public ModelAndView GetLogList(HttpServletRequest request, HttpServletResponse response)
	 {
			ModelAndView mav = new ModelAndView();
			
	       String searchId = request.getParameter("searchId"); 
	       String searchName = request.getParameter("searchName"); 
	       String searchDept = request.getParameter("searchDept"); 
	       String searchGrade = request.getParameter("searchGrade");
	       String seachNumber = request.getParameter("seachNumber");    
	       
	       HashMap<String, Object> memInfo = new HashMap<String, Object>();
	       
	       memInfo.put("searchId", searchId);
	       memInfo.put("searchName", searchName);
	       memInfo.put("searchDept", searchDept);
	       memInfo.put("searchGrade", searchGrade);
		    
	       int pageRow  = 10;                                                           
		    int totCount =  0;                                                         
	                                                                                
	       String page = StringUtil.nvl(request.getParameter("page"), "1");         
	       int iPage = StringUtil.toNumber(page, 10);                               
	                                                                                
	       memInfo.put("stRow", String.valueOf((iPage - 1) * pageRow + 1));         
	       memInfo.put("edRow", String.valueOf(iPage * pageRow));     
	       
		   String searchSt = request.getParameter("searchSt");                          
		   String searchEt = request.getParameter("searchEt");                          
			                                                                             
		   if (searchSt == null || searchSt == "") {                                
		    	searchSt = getCurrentSDt(0);                                             
		   }                                                                        
		                                                                             
		   if (searchEt == null || searchEt == "") {                                
		    	searchEt = getCurrentEDt(0);                                             
		   }   
		    
		   memInfo.put("searchSt", searchSt);  
		   memInfo.put("searchEt", searchEt);
		   try
	       {
				List<HashMap<String, String>> logList= prnService.getLogList(memInfo);
				mav.addObject("logList", logList);
				
				if(logList!=null && logList.size() > 0)
		       	{
					HashMap<String, String> hm = logList.get(0);
		       		totCount = Integer.parseInt(String.valueOf(hm.get("totCnt")));
		       		mav.addObject("totalcnt", totCount);
		       	}
			 
	       }catch (Exception e)
	       {
	           log.error(e.toString());
	       }
	     
			mav.addObject("menuCur", "menu06");
			mav.setViewName("LogList");
			
			 /*******************************************************************
	        ** 
	        **
	        **/
			HashMap<String, Object> pagingInfo = new HashMap<String, Object>();
			 
	       pagingInfo.put("page",  page);
	       pagingInfo.put("count", totCount);
	       pagingInfo.put("prows", pageRow);
	       pagingInfo.put("psize", 10 );
	       mav.addObject("paging", pagingInfo);
	       /******************************************************************/
	
			mav.addObject("searchSt", searchSt);
			mav.addObject("searchEt", searchEt);
			mav.addObject("seachNumber", seachNumber);
			
	       return mav;
		 
	 }
	

	/**
	  * 발송 팩스 내용 수정
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ModifySendFaxPop.do")
	 public ModelAndView ModifySendFaxPop(HttpServletRequest request, HttpServletResponse response)  throws Exception 
	 {
			ModelAndView mav = new ModelAndView("ModifySendFaxPop");
	       
			String logId = request.getParameter("logId");

	        HashMap<String, Object> Info = new HashMap<String, Object>();
	        Info.put("logId", logId);
			
	        String faxTitle= "";
	        String receiveName= "";
	        String receiver= "";
		    List<HashMap<String, String>> list= prnService.getModifyInform(Info);
		    if (list.size() > 0) {
		    	
		    	HashMap<String, String> im = new HashMap<String, String>();
		    	im = (java.util.HashMap<String, String>)list.get(0);
		    	faxTitle =  im.get("faxTitle");
		    	receiveName =  im.get("receiveName");
		    	receiver =  im.get("receiver");
		    	
		    }
		    
		    mav.addObject("logId", logId);
		    mav.addObject("faxTitle", faxTitle);
		    mav.addObject("receiveName", receiveName);
		    mav.addObject("receiver", receiver);
	       return mav;
	 }

	 /**
	 * 발송 내용 수정
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value="/doModifySendFax.do")
	 public ModelAndView doModifySendFax(HttpServletRequest request, HttpServletResponse response)  throws Exception 
	 {
		   ModelAndView mav = new ModelAndView("common/result");
		   
		   String logId = request.getParameter("logId");
		   String title = request.getParameter("faxTitle");
		   String receiveName = request.getParameter("receiveName");
		   String receiver = request.getParameter("receiver");
	       
	       HashMap<String, String> memInfo = new HashMap<String, String>();
	       memInfo.put("logId", logId);
	       memInfo.put("title", title);
	       memInfo.put("receiveName", receiveName);
	       memInfo.put("receiver", receiver);
	       
			try
	       {
				prnService.updateModify(memInfo);
			 
	       }catch (Exception e)
	       {
	           log.error(e.toString());
	       }
			
		   mav.addObject("self_close", "Y");
		   mav.addObject("reload_function", "Y");
	       mav.addObject("message", "저장 하였습니다.");
	       
	       return mav;
	 }
	
		
	
	
	 /**
	 * 설정
	 * @param request
	 * @param response
	 * @return
	 */
	 @RequestMapping(value="/SetConfig.do")
	 public ModelAndView SetConfig(HttpServletRequest request, HttpServletResponse response)
	 {
		ModelAndView mav = new ModelAndView();
		
       String page = StringUtil.nvl(request.getParameter("page"), "1");
       int iPage = StringUtil.toNumber(page, 10);
       
       HashMap<String, Object> memInfo = new HashMap<String, Object>();
       memInfo.put("seq", "1");
	    
       String setDay = "1";
       
		try
       {
			List<HashMap<String, String>> configList= prnService.getConfigList(memInfo);
			mav.addObject("configList", configList);
			
			if(configList!=null && configList.size() > 0)
	       	{
				HashMap<String, String> hm = configList.get(0);
				setDay = String.valueOf(hm.get("setDay"));
	       		mav.addObject("setDay", setDay);
	       	}
		 
       }catch (Exception e)
       {
           log.error(e.toString());
       }
     
		mav.addObject("menuCur", "menu07");
		mav.setViewName("SetConfig");
		 
       return mav;
	 }
	
	 
	 /**
	  * 설정 저장
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value="/SaveSetConfig.do")
	 public ModelAndView SaveSetConfig(HttpServletRequest request, HttpServletResponse response)  throws Exception 
	 {
		 ModelAndView mav = new ModelAndView("common/result");
        
        HashMap<String, String> memInfo = new HashMap<String, String>();
	    
        String setday  = request.getParameter("setday");
        
        memInfo.put("setday", setday);
        
		try
        {
			HashMap<String, String> config = new HashMap<String, String>();
			int cnt = prnService.getConfigListCnt(config);
			
			if (cnt > 0) {
				prnService.setConfig(memInfo);
			}else{
				prnService.insertConfig(memInfo);
			}
		 
        }catch (Exception e)
        {
            log.error(e.toString());
        }
		
     
		mav.addObject("go_function", "Y");
        mav.addObject("message", "저장 하였습니다.");
        mav.addObject("goUrl", "/prnserver/SetConfig.do");
        
        return mav;
	 }
	 
		 
		 
	 /**
	  * 사용자 삭제
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/DeleteId.do")
	 public ModelAndView SetDeleteId(HttpServletRequest request, HttpServletResponse response)  throws Exception 
	 {
		 ModelAndView mav = new ModelAndView("common/result");
        
        HashMap<String, String> memInfo = new HashMap<String, String>();
	    
        String userId  = request.getParameter("selectedId");
        
        memInfo.put("userId", userId);
        
		try
        {
			prnService.setDeleteId(memInfo);
		 
        }catch (Exception e)
        {
            log.error(e.toString());
        }
		
     
		mav.addObject("go_function", "Y");
        mav.addObject("message", "삭제 처리하였습니다.");
        mav.addObject("goUrl", "/prnserver/MemberList.do");
        
        return mav;
	 }
	 
	 /**
	  * 사용자 수정 팝업
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ModifyIdPop.do")
	 public ModelAndView ModifyIdPop(HttpServletRequest request, HttpServletResponse response)  throws Exception 
	 {
		ModelAndView mav = new ModelAndView("NewIdpop");
        
        HashMap<String, String> memInfo = new HashMap<String, String>();
	    
        memInfo.put("userid", request.getParameter("userId"));
        
        HashMap<String, String> memberInfo = null;
        memberInfo = memberDao.selectMember(memInfo);
        
        if (memberInfo != null)
        {
        	mav.addObject("newUserYn", "N");	
        	mav.addObject("userId", memberInfo.get("loginId"));	
        	mav.addObject("userPw", memberInfo.get("password"));
        	mav.addObject("userNm", memberInfo.get("userName"));
        	mav.addObject("depart", memberInfo.get("deptName"));
        	mav.addObject("email", memberInfo.get("email"));
        	mav.addObject("grade", memberInfo.get("grade"));
        }
      
        return mav;
	 }
	 
	 /**
	  * 사용자 정보 수정
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/UpdateId.do")
	 public ModelAndView UpdateId(HttpServletRequest request, HttpServletResponse response)  throws Exception 
	 {
		 HttpSession session = request.getSession();                                    
	     String sessionUserId = (String)session.getAttribute("session_userid"); 
	    
		 ModelAndView mav = new ModelAndView("common/result");
		 
		 String userId  = request.getParameter("userId");
		 String userNm  = request.getParameter("userNm");
		 String depart  = request.getParameter("depart");
		 String email  = request.getParameter("email");
		 String grade  = request.getParameter("grade");
		 
		 HashMap<String, String> memInfo = new HashMap<String, String>();
		 memInfo.put("userid", userId);
		 memInfo.put("userNm", userNm);
		 memInfo.put("depart", depart);
		 memInfo.put("email", email);
		 memInfo.put("grade", grade);
        
		 try
	        {
	        	prnService.setUpdateId(memInfo);
			 
	        	//로그 저장
	        	memInfo.put("userName", sessionUserId);
	        	prnService.insertLog(memInfo);
	        }catch (Exception e)
	        {
	            log.error(e.toString());
	        }

		 	mav.addObject("self_close", "Y");
		    mav.addObject("reload_function", "Y");
	        mav.addObject("message", "수정 처리하였습니다.");
	        
         return mav;
	 }
	 
	 
	 /**
	  * 권한 이관 등록 팝업
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/trnsConfirmPop.do")
	 public ModelAndView trnsConfirmPop(HttpServletRequest request, HttpServletResponse response)  throws Exception 
	 {
		ModelAndView mav = new ModelAndView("TranConfirmPop");
		
		String userId = request.getParameter("userId"); 
        String userDp = request.getParameter("userDp"); 
        String searchName  = request.getParameter("searchName");
		
		HashMap<String, Object> memInfo = new HashMap<String, Object>();
		
		memInfo.put("stRow", 1);         
        memInfo.put("edRow", 50); 
		memInfo.put("searchDeptCd", userDp);
		memInfo.put("searchName", searchName);
		 
		List<HashMap<String, String>> memberList= prnService.getMemberList(memInfo);
		
		if(memberList!=null && memberList.size() > 0)
    	{
			mav.addObject("memberList", memberList);
    	}
		
		mav.addObject("userId", userId);
		mav.addObject("userDp", userDp);
		mav.addObject("searchName", searchName);
		
        return mav;
	 }
	
	 /**
	  * 승인 이관 업데이트
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doChangeConfirm.do")
	 public ModelAndView ChangeConfirm(HttpServletRequest request, HttpServletResponse response)  throws Exception 
	 {
		 HttpSession session = request.getSession();                                    
		 String sessionId = (String)session.getAttribute("session_id");
	     
		 ModelAndView mav = new ModelAndView("common/result");
		 
		 String logid  = request.getParameter("logId");
		 String status = request.getParameter("status");
		 
		 HashMap<String, String> memInfo = new HashMap<String, String>();
		 memInfo.put("userid", logid);
		 memInfo.put("status", status);
		 memInfo.put("setName", sessionId);
		 
		 try
	        {
	        	prnService.setChangeConfirm(memInfo);
			 
	        }catch (Exception e)
	        {
	            log.error(e.toString());
	        }

		 	mav.addObject("self_close", "Y");
		    mav.addObject("reload_function", "Y");
	        mav.addObject("message", "수정 처리하였습니다.");
	        
        return mav;
	 }
	
		
		
	 /**
	  * 신규사용자등록 팝업
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/NewIdpop.do")
	 public ModelAndView NewIdpop(HttpServletRequest request, HttpServletResponse response)  throws Exception 
	 {
		 ModelAndView mav = new ModelAndView("NewIdpop");
		 mav.addObject("newUserYn", "Y");
     	 mav.addObject("userId", "");	
     	 mav.addObject("userPw", "");
     	 mav.addObject("userNm", "");
     	 mav.addObject("depart", "");
     	 mav.addObject("email", "");
     	 mav.addObject("grade", "");
         return mav;
	 }
	 
	
	
	 /**
	  * 신규사용자 등록
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/InsertNewId.do")
	 public ModelAndView SetInsertId(HttpServletRequest request, HttpServletResponse response)  throws Exception 
	 {
		 ModelAndView mav = new ModelAndView("common/result");
		 
		 String userId  = request.getParameter("userId");
		 String userPw  = request.getParameter("userPw");
		 String userNm  = request.getParameter("userNm");
		 String depart  = request.getParameter("depart");
		 String email   = request.getParameter("email");
		 String grade   = request.getParameter("grade");
		 
		 HashMap<String, String> memInfo = new HashMap<String, String>();
		 memInfo.put("userId", userId);
		 memInfo.put("userPw", userPw);
		 memInfo.put("userNm", userNm);
		 memInfo.put("depart", depart);
		 memInfo.put("email", email);
		 memInfo.put("grade", grade);
        
		 try
	        {
				prnService.setInsertId(memInfo);
			 
	        }catch (Exception e)
	        {
	            log.error(e.toString());
	        }
	      
		 
		    mav.addObject("reload_function", "Y");
	        mav.addObject("message", "등록 처리하였습니다.");
	        mav.addObject("goUrl", "/prnserver/MemberList.do");
	        
         return mav;
	 }
	
	
	@RequestMapping(value="/ListExcelDown.do")
    public ModelAndView checkExcelDown(HttpServletRequest request, HttpServletResponse response)
    {
        
    	ModelAndView mav = new ModelAndView("ListExcelDown");
		String searchSt = request.getParameter("searchSt");
		String searchEt = request.getParameter("searchEt");
		
        HashMap<String, Object> searchInfo = new HashMap<String, Object>();
    
		try
        {
			if (searchSt == null || searchSt == "") {
	        	searchSt = getCurrentSDt(0);
	        }
	        
	        if (searchEt == null || searchEt == "") {
	        	searchEt = getCurrentEDt(0);
	        }
	        
	        searchInfo.put("searchSt",  searchSt);
	        searchInfo.put("searchEt",  searchEt);

			List<HashMap<String, String>> excelList= prnService.getExcelList(searchInfo);
			mav.addObject("excelList", excelList);


        }
        catch (Exception e)
        {
            log.error(e.toString());
        }
        
        return mav;
    }
	    
	@RequestMapping(value="/scanListDown.do")
    public ModelAndView scanListDown(HttpServletRequest request, HttpServletResponse response)
    {
		
		HttpSession session = request.getSession();
	    String sessionId = (String)session.getAttribute("session_id");
	    
	    //9:관리자, 1:사용자, 2:승인자, 3:승인이관자
	    String sessionGd = (String)session.getAttribute("session_gd");
	    String authCheck = "user";
	    
	    if ("9".equals(sessionGd)) {
	    	authCheck = "admin";
	    }
	    
	    String seachPeriod = StringUtil.nvl(request.getParameter("seachPeriod"), "1D");
	    String pageType = StringUtil.nvl(request.getParameter("pageType"), "list");
	    
	    HashMap<String, Object> prnInfo = new HashMap<String, Object>();

		ModelAndView mav = new ModelAndView();
		
		String searchSt = request.getParameter("searchSt");
		String searchEt = request.getParameter("searchEt");
		
	    prnInfo.put("searchDocNm", request.getParameter("searchDocNm"));
	    prnInfo.put("searchDepart", request.getParameter("searchDepart"));
	    prnInfo.put("searchUserNm", request.getParameter("searchUserNm"));
	    prnInfo.put("userId", sessionId);
        prnInfo.put("authCheck", authCheck);
        
        int totCount =  0;
        
        List<HashMap<String, String>> scanList = null;
        
		try
        {
			
	        //조회가능 날짜-> 
			String setDay = "1";
		       
		    prnInfo.put("seq", "1");
			List<HashMap<String, String>> configList= prnService.getConfigList(prnInfo);
			mav.addObject("configList", configList);
			
			if(configList!=null && configList.size() > 0)
	       	{
				HashMap<String, String> hm = configList.get(0);
				setDay = String.valueOf(hm.get("setDay"));
				prnInfo.put("setDay", setDay);
	       	}
			//->
			
			if (searchSt == null || searchSt == "") {
	        	searchSt = getCurrentSDt(Integer.parseInt(setDay));
	        }
	        
	        if (searchEt == null || searchEt == "") {
	        	searchEt = getCurrentEDt(0);
	        }
			
		    prnInfo.put("searchSt", StringUtil.nvl(request.getParameter("searchSt"), searchSt));
		    prnInfo.put("searchEt", StringUtil.nvl(request.getParameter("searchEt"), searchEt));
			
	        prnInfo.put("stRow", String.valueOf(0)); 
	        prnInfo.put("edRow", String.valueOf(50));
	        
	        
			scanList = prnService.getScanList(prnInfo);
			
			if(scanList!=null && scanList.size() > 0)
        	{
				HashMap<String, String> hm = scanList.get(0);
        		totCount = Integer.parseInt(String.valueOf(hm.get("totalcnt")));
        		mav.addObject("totalcnt", totCount);
        	}else{
        		totCount = 0;
        		mav.addObject("totalcnt", totCount);
        	}
			 
        }catch (Exception e)
        {
            log.error(e.toString());
        }
		
		
		
		
//		String root_path = request.getSession().getServletContext().getRealPath("/");  
    	
  //  	root_path = root_path.replace("sfc", "sit");
    	//String root_path = "http://sit.hmphanmi.co.kr:8080/sit/";
    	
  //  	String itemSeq = StringUtil.nvl(request.getParameter("itemSeq"), "");
 //   	String outputName = "OutFile";
    
//    	String fullPath = root_path + "upload/" + itemSeq + "/" ;
    	
    	int bufferSize = 1024 * 2;
    	
    	ZipArchiveOutputStream zosGcp = null;
    	HashMap<String, String> param = MapUtil.getRequestMap(request);
    
		try
        {
		     /*   	
		        	if (request.getHeader("User-Agent").indexOf("MSIE 5.5") > -1) {
		        		response.setHeader("Content-Disposition", "filename=" + outputName + "_gcp.zip" + ";");
		        	}else{
		        		response.setHeader("Content-Disposition", "attachment; filename=" + outputName + "_gcp.zip" + ";");
		        	}
		        	response.setHeader("Content-Transfer-Encoding", "binary");
		       */ 	
		        	
        	response.setHeader("Content-Disposition", "filename=ScanFile.zip" + ";");;
          	OutputStream osGcp = response.getOutputStream();
          	zosGcp = new ZipArchiveOutputStream(osGcp);
        	
        	zosGcp.setEncoding("EUC-KR");
        	zosGcp.setLevel(8);

           	BufferedInputStream bisGcp = null;
           	
    		HashMap<String, String> fileUpName = new HashMap<String, String>();
    		
    		String FileCheck = "";
    		String ScanDate = ""; 

    		for(int i=0; i<scanList.size(); i++) {
    			
    			HashMap<String, String> getInfo = scanList.get(i);
    			
    			FileCheck = String.valueOf(getInfo.get("imageFilePath"));
    			ScanDate = String.valueOf(getInfo.get("usageTime")).replace(":", "").replace("-", "").replace(".", "").replace(" ", "");
    			ScanDate = ScanDate + ".pdf";
    			
        		File sourceFileGcp = new File (FileCheck);
        	    
        		FileInputStream in= new FileInputStream(FileCheck);
        		
        		
        		if (sourceFileGcp.exists()) {
      		
        			bisGcp = new BufferedInputStream(new FileInputStream(sourceFileGcp));
        			
	        		ZipArchiveEntry zentryCv = new ZipArchiveEntry(ScanDate);
	        		
	        		zentryCv.setTime(sourceFileGcp.lastModified());
	        		zosGcp.putArchiveEntry(zentryCv);
	        		
	        		byte[] bufferGcp = new byte[bufferSize];
	        		int cnt = 0;
	        		while ((cnt = bisGcp.read(bufferGcp, 0, bufferSize)) != -1 ) {
	        			zosGcp.write(bufferGcp, 0 , cnt);
	        		}
	        		zosGcp.closeArchiveEntry();
        		}
        	}
        	zosGcp.close();
        	bisGcp.close();
        }
        catch (Exception e)
        {
            log.error(e.toString());
        }
        
        return null;
    }
	
	
	
	 /**
	  * 시작 설정
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	 public String getCurrentSDt(int mday){
		String result = "";
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		cal.add(cal.DATE, -mday);
		
		String currentDt = df.format(cal.getTime());

		result = currentDt;
		
		return result;
	}
	 
	 /**
	  * 종료일 설정
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	 public String getCurrentEDt(int mday){
		String result = "";
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		cal.add(cal.DATE, mday);
		
		String currentDt = df.format(cal.getTime());

		result = currentDt;
		
		return result;
	}
}
