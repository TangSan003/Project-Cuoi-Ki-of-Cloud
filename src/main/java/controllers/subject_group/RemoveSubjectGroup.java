package controllers.subject_group;

import models.services.subject_group.SubjectGroupService;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RemoveSubjectGroup", value = "/admin/subjectGroup/delete")
public class RemoveSubjectGroup extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectGroupId = request.getParameter("subjectGroupId");

        boolean isSuccess = SubjectGroupService.getInstance().delete(subjectGroupId, "");
        String error = "";
        if(!isSuccess){
            error = "?error=true";
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/subjectGroups" +error);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
