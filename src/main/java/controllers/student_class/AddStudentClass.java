package controllers.student_class;

import models.services.student_class.StudentClassService;
import models.view_models.student_class.StudentClassCreateRequest;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AddStudentClass", value = "/admin/studentClass/add")
public class AddStudentClass extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        StudentClassCreateRequest createReq = new StudentClassCreateRequest();

        createReq.setStudentClassId(request.getParameter("studentClassId"));
        createReq.setStudentClassName(request.getParameter("studentClassName"));
        createReq.setFacultyId(request.getParameter("facultyId"));
        createReq.setDeleted(request.getParameter("deleted"));
        boolean success = StudentClassService.getInstance().insert(createReq);
        String error = "";
        if(!success){
            error = "?error=true";
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/studentClasses" + error);
    }
}
