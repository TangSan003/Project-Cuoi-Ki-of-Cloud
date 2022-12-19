package controllers.student_class;

import models.services.student_class.StudentClassService;
import models.view_models.student_class.StudentClassUpdateRequest;
import models.view_models.student_class.StudentClassViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EditStudentClass", value = "/admin/studentClass/edit")
public class EditStudentClass extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentClassId = request.getParameter("studentClassId");

        StudentClassViewModel studentClass = StudentClassService.getInstance().retrieveById(studentClassId,"");

        request.setAttribute("studentClass", studentClass);

        ServletUtils.forward(request,response,"/admin/studentClasses");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        StudentClassUpdateRequest updateReq = new StudentClassUpdateRequest();

        String studentClassId = request.getParameter("studentClassId");
        String studentClassName = request.getParameter("studentClassName");
        String facultyId = request.getParameter("facultyId");

        updateReq.setStudentClassId(studentClassId);
        updateReq.setFacultyId(facultyId);
        updateReq.setStudentClassName(studentClassName);
        updateReq.setDeleted(request.getParameter("deleted"));

        boolean isSuccess = StudentClassService.getInstance().update(updateReq);
        String error = "";
        if(!isSuccess){
            error = "?error=true";
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/studentClasses" + error);
    }
}
