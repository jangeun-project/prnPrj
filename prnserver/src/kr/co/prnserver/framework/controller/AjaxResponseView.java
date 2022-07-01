package kr.co.prnserver.framework.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.view.AbstractView;

public class AjaxResponseView extends AbstractView {

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        JSONObject jsonObj =  JSONObject.fromObject(map);

        response.setCharacterEncoding("utf-8");  
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().print(jsonObj);
    }
}
