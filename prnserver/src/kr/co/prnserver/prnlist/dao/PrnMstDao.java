package kr.co.prnserver.prnlist.dao;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PrnMstDao {
	
	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	public List<HashMap<String, String>> getScanList(HashMap<String, Object> map) throws Exception
    {
		return sqlMapClientTemplate.queryForList("print.getScanList", map);
    }
	
	public List<HashMap<String, String>> getReceiveFaxList(HashMap<String, Object> map) throws Exception
    {
		return sqlMapClientTemplate.queryForList("print.getReceiveFaxList", map);
    }
	
	public List<HashMap<String, String>> getSendFaxList(HashMap<String, Object> map) throws Exception
    {
		return sqlMapClientTemplate.queryForList("print.getSendFaxList", map);
    }
	
	public List<HashMap<String, String>> getConfirmFaxList(HashMap<String, Object> map) throws Exception
    {
		return sqlMapClientTemplate.queryForList("print.getConfirmFaxList", map);
    }
	
	public List<HashMap<String, String>> getConfirmFaxUserList(HashMap<String, Object> map) throws Exception
    {
		return sqlMapClientTemplate.queryForList("print.getConfirmFaxUserList", map);
    }
	
	public List<HashMap<String, String>> getSendFaxInform(HashMap<String, Object> map) throws Exception
    {
		return sqlMapClientTemplate.queryForList("print.getSendFaxInform", map);
    }
	
	public List<HashMap<String, String>> getModifyInform(HashMap<String, Object> map) throws Exception
    {
		return sqlMapClientTemplate.queryForList("print.getModifyInform", map);
    }

	public List<HashMap<String, String>> getMemberList(HashMap<String, Object> map) throws Exception
    {
		return sqlMapClientTemplate.queryForList("member.getMemberList", map);
    }
	
	public List<HashMap<String, String>> getLogList(HashMap<String, Object> map) throws Exception
    {
		return sqlMapClientTemplate.queryForList("print.getLogList", map);
    }
	
 	public int deleteId(HashMap<String, String> map) throws Exception
 	{
 		return  sqlMapClientTemplate.update("member.deleteMember", map);
 	}
 	
 	public Object insertId(HashMap<String, String> map) throws Exception
 	{
 		return  sqlMapClientTemplate.insert("member.insertMember", map);
 	}
 	
    public boolean setUpdateId(HashMap<String, String> param) throws Exception 
    {
    	return sqlMapClientTemplate.update("member.setUpdateId", param) > 0 ? true : false;
    }
    
    public boolean setChangeConfirm(HashMap<String, String> param) throws Exception 
    {
    	return sqlMapClientTemplate.update("member.setChangeConfirm", param) > 0 ? true : false;
    }
    
 	public Object setInsertChangeConfirm(HashMap<String, String> map) throws Exception
 	{
 		return  sqlMapClientTemplate.insert("member.setInsertChangeConfirm", map);
 	}
 	
    public List<HashMap<String, String>> getConfigList(HashMap<String, Object> map) throws Exception
    {
		return sqlMapClientTemplate.queryForList("print.getConfigList", map);
    }
    
    public List<HashMap<String, String>> getExcelList(HashMap<String, Object> map) throws Exception
    {
		return sqlMapClientTemplate.queryForList("print.getExcelList", map);
    }
    
    public boolean setConfig(HashMap<String, String> param) throws Exception 
    {
    	return sqlMapClientTemplate.update("print.setConfig", param) > 0 ? true : false;
    }

 	public Object insertConfig(HashMap<String, String> map) throws Exception
 	{
 		return  sqlMapClientTemplate.insert("print.insertConfig", map);
 	}
 	
    public boolean updateModify(HashMap<String, String> param) throws Exception 
    {
    	return sqlMapClientTemplate.update("print.updateModify", param) > 0 ? true : false;
    }
    
    public boolean setUpdateConfirm(HashMap<String, String> param) throws Exception 
    {
    	return sqlMapClientTemplate.update("print.setUpdateConfirm", param) > 0 ? true : false;
    }
    
    public boolean setUpdateConfirmStatus(HashMap<String, String> param) throws Exception 
    {
    	return sqlMapClientTemplate.update("print.setUpdateConfirmStatus", param) > 0 ? true : false;
    }
    
 	public Object setInsertId(HashMap<String, String> map) throws Exception
 	{
 		return  sqlMapClientTemplate.insert("member.setInsertId", map);
 	}
 	
    public boolean setUpdatePw(HashMap<String, String> param) throws Exception 
    {
    	return sqlMapClientTemplate.update("member.setUpdatePw", param) > 0 ? true : false;
    }
    
 	public Object setInsertPw(HashMap<String, String> map) throws Exception
 	{
 		return  sqlMapClientTemplate.insert("member.setInsertPw", map);
 	}
 	
    public HashMap<String, String> selectMember(HashMap<String, String> map) throws Exception
    {
    	return (HashMap<String, String>) sqlMapClientTemplate.queryForObject("member.selectMember", map);
    }

    public int selectMemberDetail(HashMap<String, String> map) throws Exception
    {
    	return (Integer)sqlMapClientTemplate.queryForObject("member.selectMemberDetail", map);	
    }
    
    public int getTranConfirmCnt(HashMap<String, String> map) throws Exception
    {
    	return (Integer)sqlMapClientTemplate.queryForObject("print.getTranConfirmCnt", map);	
    }
    
    public int getConfigListCnt(HashMap<String, String> map) throws Exception
    {
    	return (Integer)sqlMapClientTemplate.queryForObject("print.getConfigListCnt", map);	
    }
    
 	public Object insertLog(HashMap<String, String> map) throws Exception
 	{
 		return  sqlMapClientTemplate.insert("print.insertLog", map);
 	}
 
}
