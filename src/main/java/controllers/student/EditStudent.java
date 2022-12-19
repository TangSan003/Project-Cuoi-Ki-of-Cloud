package controllers.student;

import models.services.student.StudentService;
import models.services.student.StudentService;
import models.view_models.student.StudentUpdateRequest;
import models.view_models.student.StudentUpdateRequest;
import models.view_models.student.StudentViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;


@WebServlet(name = "EditStudent", value = "/admin/student/edit")
@MultipartConfig(
        fileSizeThreshold = 1024*1024*2, // 2MB
        maxFileSize = 1024*1024*10, // 10MB
        maxRequestSize = 1024*1024*11   // 11MB
)
public class EditStudent extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentId = request.getParameter("studentId");

        StudentViewModel student = StudentService.getInstance().retrieveById(studentId,"");

        request.setAttribute("student", student);

        ServletUtils.forward(request,response,"/admin/students");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        StudentUpdateRequest updateReq = new StudentUpdateRequest();

        String studentId = request.getParameter("studentId");
        String studentName = request.getParameter("studentName");
        String studentClassId = request.getParameter("studentClassId");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String dob = request.getParameter("dob");

        updateReq.setStudentId(studentId);
        updateReq.setStudentName(studentName);
        updateReq.setStudentClassId(studentClassId);
        updateReq.setAddress(address);
        updateReq.setDob(dob);
        updateReq.setPhone(phone);
        updateReq.setGender(gender);
        updateReq.setFile(request.getPart("student-image"));
        updateReq.setDeleted(request.getParameter("deleted"));

        boolean isSuccess = StudentService.getInstance().update(updateReq);
        String error = "";
        if(!isSuccess){
            error = "?error=true";
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/students" + error);
    }
}
