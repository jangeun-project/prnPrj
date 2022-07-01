package kr.co.prnserver.prnlist.service;

import java.util.HashMap;
import java.util.List;

import kr.co.prnserver.prnlist.dao.PrnMstDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrnMstService {
	
	private static final Log logger = LogFactory.getLog(PrnMstService.class);

	@Autowired
	private PrnMstDao prnDao; 
	
	public List<HashMap<String, String>> getScanList(HashMap<String, Object> map) throws Exception {
		return prnDao.getScanList(map);
	}
	
	public List<HashMap<String, String>> getReceiveFaxList(HashMap<String, Object> map) throws Exception {
		return prnDao.getReceiveFaxList(map);
	}
	
	public List<HashMap<String, String>> getSendFaxList(HashMap<String, Object> map) throws Exception {
		return prnDao.getSendFaxList(map);
	}
	
	public List<HashMap<String, String>> getConfirmFaxList(HashMap<String, Object> map) throws Exception {
		return prnDao.getConfirmFaxList(map);
	}
	
	public List<HashMap<String, String>> getConfirmFaxUserList(HashMap<String, Object> map) throws Exception {
		return prnDao.getConfirmFaxUserList(map);
	}
	
	public List<HashMap<String, String>> getSendFaxInform(HashMap<String, Object> map) throws Exception {
		return prnDao.getSendFaxInform(map);
	}
	
	public List<HashMap<String, String>> getModifyInform(HashMap<String, Object> map) throws Exception {
		return prnDao.getModifyInform(map);
	}
	
	public List<HashMap<String, String>> getMemberList(HashMap<String, Object> map) throws Exception {
		return prnDao.getMemberList(map);
	}

	public List<HashMap<String, String>> getLogList(HashMap<String, Object> map) throws Exception {
		return prnDao.getLogList(map);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception .class})
	public synchronized boolean setDeleteId(HashMap<String, String> map) throws Exception {
		
		boolean result = false;
		
        if (prnDao.deleteId(map)>-1){
        	result = true;
        }
        
		return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception .class})
	public synchronized boolean setInsertId(HashMap<String, String> map) throws Exception {
		
		boolean result = false;
		
		 Object key = prnDao.insertId(map);
		
		return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception .class})
    public boolean setUpdateId(HashMap<String, String> map) throws Exception 
    {
		boolean result = true;
		
		int cnt = prnDao.selectMemberDetail(map);
		if (cnt > 0) {
			result = prnDao.setUpdateId(map);
		}else{
			prnDao.setInsertId(map);
		}
		
    	return result;
    }
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception .class})
    public boolean setChangeConfirm(HashMap<String, String> map) throws Exception 
    {
		boolean result = true;
		
		String status = map.get("status");
		String userid = String.valueOf((map.get("userid")));
    	
    	int cnt = prnDao.selectMemberDetail(map);
    	
    	//기존 detail 존재 -> update
    	if (cnt > 0) {
	    	if ("3".equals(status)) {
				map.put("grade", "1");
				result = prnDao.setChangeConfirm(map);
			}else{
				map.put("grade", "3");
				result = prnDao.setChangeConfirm(map);
			}
	    //기존 detail 없음 -> insert	
    	}else{
			HashMap<String, String> insertMap = new HashMap<String, String>();
			
			insertMap.put("userid", userid);
    		if ("3".equals(status)) {
    			insertMap.put("grade", "1");
			}else{
				insertMap.put("grade", "3");
			}
    		
			prnDao.setInsertChangeConfirm(insertMap);
    	}
    	
    	//로그 저장
    	insertLog(map);
    	
    	return result;
    }
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception .class})
    public boolean setUpdateConfirm(HashMap<String, String> map) throws Exception 
    {
		boolean result = true;
		
		result = prnDao.setUpdateConfirm(map);
		
    	return result;
    }
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception .class})
    public boolean setUpdateConfirmStatus(HashMap<String, String> map) throws Exception 
    {
		boolean result = true;
		
		result = prnDao.setUpdateConfirmStatus(map);
		
    	return result;
    }
	
	public int getConfigListCnt(HashMap<String, String> map) throws Exception 
	{
		int cnt = 0;
		
		cnt = prnDao.getConfigListCnt(map);
		
		return cnt;
	}
	
	public List<HashMap<String, String>> getConfigList(HashMap<String, Object> map) throws Exception {
		return prnDao.getConfigList(map);
	}
	
	public List<HashMap<String, String>> getExcelList(HashMap<String, Object> map) throws Exception {
		return prnDao.getExcelList(map);
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception .class})
    public boolean updateModify(HashMap<String, String> map) throws Exception 
    {
		boolean result = true;
		
		result = prnDao.updateModify(map);
		
    	return result;
    }
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception .class})
    public boolean insertConfig(HashMap<String, String> map) throws Exception 
    {
		boolean result = true;
		
		prnDao.insertConfig(map);
		
    	return result;
    }
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception .class})
    public boolean setConfig(HashMap<String, String> map) throws Exception 
    {
		boolean result = true;
		
		result = prnDao.setConfig(map);
		
    	return result;
    }
	
	public int getTranConfirmCnt(HashMap<String, String> map) throws Exception 
	{
		int cnt = 0;
		
		cnt = prnDao.getTranConfirmCnt(map);
		
		return cnt;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception .class})
	public synchronized boolean insertLog(HashMap<String, String> map) throws Exception {
		
		boolean result = false;
		
		 Object key = prnDao.insertLog(map);
		
		return result;
	}
	
}
