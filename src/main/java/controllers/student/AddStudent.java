package controllers.student;

import models.services.student.StudentService;
import models.view_models.student.StudentCreateRequest;
import models.view_models.student.StudentCreateRequest;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AddStudent", value = "/admin/student/add")
@MultipartConfig(
        fileSizeThreshold = 1024*1024*2, // 2MB
        maxFileSize = 1024*1024*10, // 10MB
        maxRequestSize = 1024*1024*11   // 11MB
)
public class AddStudent extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        StudentCreateRequest createReq = new StudentCreateRequest();

        String studentId = request.getParameter("studentId");
        String studentName = request.getParameter("studentName");
        String studentClassId = request.getParameter("studentClassId");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String dob = request.getParameter("dob");

        createReq.setStudentId(studentId);
        createReq.setStudentName(studentName);
        createReq.setStudentClassId(studentClassId);
        createReq.setAddress(address);
        createReq.setDob(dob);
        createReq.setPhone(phone);
        createReq.setGender(gender);
        createReq.setFile(request.getPart("student-image"));
        createReq.setDeleted(request.getParameter("deleted"));

        boolean success = StudentService.getInstance().insert(createReq);
        String error = "";
        if(!success){
            error = "?error=true";
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/students" + error);
    }
}
