package controllers.student;

import models.services.student.StudentService;
import models.services.student_class.StudentClassService;
import models.view_models.student.StudentViewModel;
import models.view_models.student_class.StudentClassViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetStudents", value = "/admin/students")
public class GetStudents extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<StudentViewModel> students = StudentService.getInstance().retrieveAll();
        ArrayList<StudentClassViewModel> studentClasses = StudentClassService.getInstance().retrieveAll();

        String error = request.getParameter("error");
        if((error != null && !error.equals("")) || (studentClasses == null || students == null)){
            request.setAttribute("error",error);
            if(studentClasses == null)
                studentClasses = new ArrayList<>();
            if(students == null)
                students = new ArrayList<>();
        }
        studentClasses.removeIf(c -> c.getDeleted() == 1);
        request.setAttribute("studentClasses",studentClasses);
        request.setAttribute("students",students);
        ServletUtils.forward(request,response,"/views/admin/student/student.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
