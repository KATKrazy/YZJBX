package com.kat.action;

import com.kat.service.YZJExportService;
import com.kat.service.YZJLoginService;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class HandleAction extends Action {

    private CloseableHttpClient httpclient = HttpClients.createDefault();

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String email = request.getParameter("exampleInputEmail");
        String password = request.getParameter("exampleInputPassword");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        YZJLoginService yzjLoginService = new YZJLoginService();
        String id = yzjLoginService.login(httpclient, email, password);
        if("".equals(id)) {
            response.getWriter().write("login failed!check the username and password");
            return null;
        }
        YZJExportService export = new YZJExportService();
        String path = export.export(httpclient, email, startDate, endDate, id);

        response.setContentType("application/x-msdownload; charset=utf-8");
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(email + startDate + endDate + ".csv", "gbk"));
        File file = new File(path);
        InputStream in = new FileInputStream(file);
        OutputStream out = response.getOutputStream();
        int len = 0;
        byte[] buffer = new byte[8192];
        while((len = in.read(buffer)) > 0) {
            out.write(buffer,0,len);
        }
        in.close();
        out.close();
        out.flush();

        return null;
    }
}
