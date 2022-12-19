package controllers.lecture;

import models.services.lecture.LectureService;
import models.services.user.UserService;
import models.services.user_role.UserRoleService;
import models.view_models.lecture.LectureCreateRequest;
import models.view_models.user.UserCreateRequest;
import models.view_models.user_role.UserRoleCreateRequest;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "AddLecture", value = "/admin/lecture/add")
@MultipartConfig(
        fileSizeThreshold = 1024*1024*2, // 2MB
        maxFileSize = 1024*1024*10, // 10MB
        maxRequestSize = 1024*1024*11   // 11MB
)
public class AddLecture extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        LectureCreateRequest createReq = new LectureCreateRequest();

        String lectureId = request.getParameter("lectureId");
        String lectureName = request.getParameter("lectureName");
        String facultyId = request.getParameter("facultyId");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String dob = request.getParameter("dob");
        createReq.setLectureId(lectureId);
        createReq.setLectureName(lectureName);
        createReq.setFacultyId(facultyId);
        createReq.setAddress(address);
        createReq.setDob(dob);
        createReq.setPhone(phone);
        createReq.setGender(gender);
        createReq.setFile(request.getPart("lecture-image"));
        createReq.setDeleted(request.getParameter("deleted"));
        boolean success = LectureService.getInstance().insert(createReq);
        String error = "";
        if(!success){
            error = "?error=true";
        }else{
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String[] values = request.getParameterValues("roleCheckBox");
            UserCreateRequest reqCreate = new UserCreateRequest();

            reqCreate.setLectureId(lectureId);
            reqCreate.setUsername(username);
            reqCreate.setPassword(password);
            boolean res = UserService.getInstance().insert(reqCreate);
            if(res) {
                for (String v : values) {
                    UserRoleCreateRequest r = new UserRoleCreateRequest();
                    r.setUsername(username);
                    r.setRoleId(v);
                    boolean s = UserRoleService.getInstance().insert(r);
                    if(!s){
                        error = "?error=true";
                        break;
                    }
                }
            }else{
                error = "?error=true";
            }
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/lectures" + error);
    }
}
