package controllers.subject_group;

import models.services.subject_group.SubjectGroupService;
import models.view_models.subject_group.SubjectGroupCreateRequest;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AddSubjectGroup", value = "/admin/subjectGroup/add")
public class AddSubjectGroup extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        SubjectGroupCreateRequest createReq = new SubjectGroupCreateRequest();

        createReq.setSubjectGroupId(request.getParameter("subjectGroupId"));
        createReq.setSubjectGroupName(request.getParameter("subjectGroupName"));
        createReq.setSubjectId(request.getParameter("subjectId"));
        createReq.setLectureId(request.getParameter("lectureId"));
        createReq.setDeleted(request.getParameter("deleted"));
        boolean success = SubjectGroupService.getInstance().insert(createReq);
        String error = "";
        if(!success){
            error = "?error=true";
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/subjectGroups" + error);
    }
}
