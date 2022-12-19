package controllers.grade;

import models.services.grade.GradeService;
import models.view_models.grade.GradeUpdateRequest;
import models.view_models.grade.GradeViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EditGrade", value = "/admin/grade/edit")
public class EditGrade extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        String subjectGroupId = request.getParameter("subjectGroupId");

        GradeViewModel grade = GradeService.getInstance().retrieveById(studentId,subjectGroupId);

        request.setAttribute("grade", grade);

        ServletUtils.forward(request,response,"/admin/grades");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String studentId = request.getParameter("studentId");
        String subjectGroupId = request.getParameter("subjectGroupId");
        String middleGrade = request.getParameter("middleGrade");
        String finalGrade = request.getParameter("finalGrade");

        GradeUpdateRequest updateReq = new GradeUpdateRequest();
        updateReq.setStudentId(studentId);
        updateReq.setSubjectGroupId(subjectGroupId);
        updateReq.setMiddleGrade(Double.parseDouble(middleGrade));
        updateReq.setFinalGrade(Double.parseDouble(finalGrade));
        updateReq.setDeleted(request.getParameter("deleted"));

        updateReq.setTotalGrade(0.5 * (updateReq.getMiddleGrade() + updateReq.getFinalGrade()));

        boolean isSuccess = GradeService.getInstance().update(updateReq);
        String error = "";
        if(!isSuccess){
            error = "?error=true";
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/grades" + error);
    }
}
