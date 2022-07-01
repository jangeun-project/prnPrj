package kr.co.prnserver.member.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	public HashMap<String, String> selectAdminMember(HashMap<String, String> map) throws Exception
    {
        List<HashMap<String, String>> memberList = sqlMapClientTemplate.queryForList("member.selectAdminMember", map);
        if (memberList == null || memberList.size() == 0)
        {
            return null;
        }
        else
        {
            return (HashMap<String, String>)memberList.get(0);
        }
    }
	
 	public Object insertAdminMember(HashMap<String, String> map) throws Exception
 	{
 		return  sqlMapClientTemplate.insert("member.insertAdminMember", map);
 	}
 	
	public HashMap<String, String> selectMember(HashMap<String, String> map) throws Exception
    {
        List<HashMap<String, String>> memberList = sqlMapClientTemplate.queryForList("member.selectMember", map);
        if (memberList == null || memberList.size() == 0)
        {
            return null;
        }
        else
        {
            return (HashMap<String, String>)memberList.get(0);
        }
    }
	
    public int selectMemberDetail(HashMap<String, String> map) throws Exception
    {
    	return (Integer)sqlMapClientTemplate.queryForObject("member.selectMemberDetail", map);	
    }
    
}
