/*
 * Created on 2011. 3. 22.
 *
 * 
 * 
 */
package kr.co.prnserver.template.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateDao
{
    @Autowired
    private SqlMapClientTemplate sqlMapClientTemplate;
    
    /**
     * 
     * @param map
     * @return
     * @throws Exception
     */
    public List<HashMap<String, String>> selectAll(HashMap<String, Object> map) throws Exception
    {
        return sqlMapClientTemplate.queryForList("template.select-all", map);
    }
    
    
    /**
     * 
     * @param map
     * @return
     * @throws Exception
     */
    public HashMap<String, String> select(HashMap<String, String> map) throws Exception
    {
        List<HashMap<String, String>> sampleList = sqlMapClientTemplate.queryForList("template.selectTemp", map);
        if (sampleList == null || sampleList.size() == 0)
        {
            return null;
        }
        else
        {
            return (HashMap<String, String>)sampleList.get(0);
        }
    }
    

    public HashMap<String, String> selectQc(HashMap<String, String> map) throws Exception
    {
        List<HashMap<String, String>> sampleList = sqlMapClientTemplate.queryForList("template.select-qc", map);
        if (sampleList == null || sampleList.size() == 0)
        {
            return null;
        }
        else
        {
            return (HashMap<String, String>)sampleList.get(0);
        }
    }
    

    /**
     * 
     * @param map
     * @return
     * @throws Exception
     */
    public int update(HashMap<String, String> map) throws Exception
    {
        return sqlMapClientTemplate.update("template.update", map);
    }
    
    
    public int deleteQcTemplate(HashMap<String, String> map) throws Exception
    {
        return sqlMapClientTemplate.update("template.delete-qc-template", map);
    }
    
    
    public int updateQcTemplate(HashMap<String, String> map) throws Exception 
    {
        return sqlMapClientTemplate.update("template.update-qc-template", map);
    }
    
    public int insertQcTemplate(HashMap<String, String> map) throws Exception
    {
        return sqlMapClientTemplate.update("template.insert-qc-template", map);
    }
    
    
    /**
     * 
     * @param template
     * @return
     * @throws Exception
     */
    public int insert(HashMap<String, String> template) throws Exception
    {
        return sqlMapClientTemplate.update("template.insert", template);
    }
    
    
    public int sendSms(HashMap<String, String> param) throws Exception
    {
        return sqlMapClientTemplate.update("template.sms-send", param);
    }

    public int logSms(HashMap<String, String> param) throws Exception
    {
        return sqlMapClientTemplate.update("template.sms-log", param);
    }

}
