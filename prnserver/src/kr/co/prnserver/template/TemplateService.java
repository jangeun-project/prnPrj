/*
 * Created on 2011. 3. 23.
 *
 * 
 * 
 */
package kr.co.prnserver.template;

import java.util.HashMap;
import java.util.List;

import kr.co.prnserver.framework.CommonService;
import kr.co.prnserver.framework.ValidateException;
import kr.co.prnserver.template.dao.TemplateDao;
import kr.co.prnserver.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TemplateService extends CommonService
{
    @Autowired
    private TemplateDao templateDao;
    
    public List<HashMap<String, String>> getTemplateList(HashMap<String, Object> param)
    {
        try
        {
            return templateDao.selectAll(param);
        }
        catch (Exception ex)
        {
            super.error(this.getClass(), "getTemplateList", ex);
            
            return null;
        }
    }
    
    
    @Transactional
    public HashMap<String, String> getTemplate(HashMap<String, String> template)
    {
        try
        {
            return templateDao.select(template);
        }
        catch (Exception ex)
        {
            super.error(this.getClass(), "getTemplate", ex);
            
            return null;
        }
    }
    
    
    public HashMap<String, String> getQcTemplate(HashMap<String, String> template)
    {
        try
        {
            return templateDao.selectQc(template);
        }
        catch (Exception ex)
        {
            super.error(this.getClass(), "getTemplate", ex);
            
            return null;
        }
    }
    
    
    public boolean saveQcTemplate(HashMap<String, String> template)
    {
        try
        {
            String seq = template.get("SEQ");
            
            if (seq == null || "".equals(seq))
            {
                return templateDao.insertQcTemplate(template) > 0 ? true : false;
            }
            else
            {
                return templateDao.updateQcTemplate(template) > 0 ? true : false;
            }
        }
        catch (Exception ex)
        {
            super.error(this.getClass(), "saveQcTemplate", ex);
            
            return false;
        }
    }

    
    public boolean removeQcTemplate(HashMap<String, String> template)
    {
        try
        {
            return templateDao.deleteQcTemplate(template) > 0 ? true : false;
        }
        catch (Exception ex)
        {
            super.error(this.getClass(), "deleteQcTemplate", ex);
            
            return false;
        }
    }
    
    public boolean updateTemplate(HashMap<String, String> template)
    {
        try
        {
            
            return templateDao.update(template) > 0 ? true : false;
        }
        catch (Exception ex)
        {
            super.error(this.getClass(), "updateTemplate", ex);
            
            return false;
        }
    }
    
    
    public boolean insertTemplate(HashMap<String, String> template)
    {
        try
        {
            return templateDao.insert(template) > 0 ? true : false;
        }
        catch (Exception ex)
        {
            super.error(this.getClass(), "insertTemplate", ex);
            
            return false;
        }
    }
    
    public boolean validate(HashMap<String, String> param) throws ValidateException
    {
        if ("".equals(StringUtil.nvl(param.get("id"), "")))
        {
            throw new ValidateException("error");
        }
        if ("".equals(StringUtil.nvl(param.get("title"), "")))
        {
            throw new ValidateException("error");
        }
        
        return true;
    }
}