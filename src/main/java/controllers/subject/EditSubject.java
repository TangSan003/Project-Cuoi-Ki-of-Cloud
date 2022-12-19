package controllers.subject;

import models.services.subject.SubjectService;
import models.view_models.subject.SubjectUpdateRequest;
import models.view_models.subject.SubjectViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EditSubject", value = "/admin/subject/edit")
public class EditSubject extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectId = request.getParameter("subjectId");

        SubjectViewModel subject = SubjectService.getInstance().retrieveById(subjectId,"");

        request.setAttribute("subject", subject);

        ServletUtils.forward(request,response,"/admin/subjects");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        SubjectUpdateRequest updateReq = new SubjectUpdateRequest();

        String subjectId = request.getParameter("subjectId");
        String subjectName = request.getParameter("subjectName");
        int creditsNo = Integer.parseInt(request.getParameter("creditsNo"));
        int periodsNo = Integer.parseInt(request.getParameter("periodsNo"));

        updateReq.setSubjectId(subjectId);
        updateReq.setSubjectName(subjectName);
        updateReq.setCreditsNo(creditsNo);
        updateReq.setPeriodsNo(periodsNo);
        updateReq.setDeleted(request.getParameter("deleted"));

        boolean isSuccess = SubjectService.getInstance().update(updateReq);
        String error = "";
        if(!isSuccess){
            error = "?error=true";
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/subjects" + error);
    }
}
