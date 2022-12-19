package controllers.student_class;

import models.services.faculty.FacultyService;
import models.services.student_class.StudentClassService;
import models.view_models.faculty.FacultyViewModel;
import models.view_models.student_class.StudentClassViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetStudentClasses", value = "/admin/studentClasses")
public class GetStudentClasses extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<StudentClassViewModel> studentClasses = StudentClassService.getInstance().retrieveAll();
        ArrayList<FacultyViewModel> faculties = FacultyService.getInstance().retrieveAll();
        String error = request.getParameter("error");
        if(error != null && !error.equals("") || (studentClasses == null || faculties == null)){
            request.setAttribute("error",error);
            if(faculties == null)
                faculties = new ArrayList<>();
            if(studentClasses == null)
                studentClasses = new ArrayList<>();
        }
        faculties.removeIf(x -> x.getDeleted() == 1);
        request.setAttribute("studentClasses",studentClasses);
        request.setAttribute("faculties",faculties);

        ServletUtils.forward(request,response,"/views/admin/student_class/student_class.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
