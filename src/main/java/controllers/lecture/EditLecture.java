package controllers.lecture;

import models.repositories.user_role.UserRoleRepository;
import models.services.lecture.LectureService;
import models.services.user.UserService;
import models.services.user_role.UserRoleService;
import models.view_models.lecture.LectureUpdateRequest;
import models.view_models.lecture.LectureViewModel;
import models.view_models.user.UserCreateRequest;
import models.view_models.user.UserUpdateRequest;
import models.view_models.user_role.UserRoleCreateRequest;
import models.view_models.user_role.UserRoleViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static models.services.lecture.LectureService.*;

@WebServlet(name = "EditLecture", value = "/admin/lecture/edit")
@MultipartConfig(
        fileSizeThreshold = 1024*1024*2, // 2MB
        maxFileSize = 1024*1024*10, // 10MB
        maxRequestSize = 1024*1024*11   // 11MB
)
public class EditLecture extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lectureId = request.getParameter("lectureId");

        LectureViewModel lecture = getInstance().retrieveById(lectureId,"");

        request.setAttribute("lecture", lecture);

        ServletUtils.forward(request,response,"/admin/lectures");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        LectureUpdateRequest updateReq = new LectureUpdateRequest();

        String lectureId = request.getParameter("lectureId");
        String lectureName = request.getParameter("lectureName");
        String facultyId = request.getParameter("facultyId");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String dob = request.getParameter("dob");
        String status = request.getParameter("deleted");
        if(status == null || status.equals("")){
            status = "0";
        }
        updateReq.setLectureId(lectureId);
        updateReq.setLectureName(lectureName);
        updateReq.setFacultyId(facultyId);
        updateReq.setAddress(address);
        updateReq.setDob(dob);
        updateReq.setPhone(phone);
        updateReq.setGender(gender);
        updateReq.setFile(request.getPart("lecture-image"));
        updateReq.setDeleted(status);
        boolean isSuccess = LectureService.getInstance().update(updateReq);
        String error = "";
        if(!isSuccess){
            error = "?error=true";
        }else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String newPassword = request.getParameter("newPassword");
            String[] values = request.getParameterValues("roleCheckBox");
            UserUpdateRequest reqUpdate = new UserUpdateRequest();

            reqUpdate.setLectureId(lectureId);
            reqUpdate.setUsername(username);
            if(password.length() > 0)
                reqUpdate.setPassword(password);
            if(newPassword != null && newPassword.length() > 0 && password.length() > 0)
                reqUpdate.setPassword(newPassword);


            boolean res = UserService.getInstance().update(reqUpdate);
            if(res) {
                ArrayList<UserRoleViewModel> userRoles = UserRoleRepository.getInstance().retrieveAll();
                boolean x = true;
                for(UserRoleViewModel ur:userRoles){
                    if(Objects.equals(ur.getUsername(), username)) {
                        x = UserRoleService.getInstance().delete(username, ur.getRoleId());
                        if(!x)
                            break;
                    }
                }
                if(x) {
                    for (String v : values) {
                        UserRoleCreateRequest r = new UserRoleCreateRequest();
                        r.setUsername(username);
                        r.setRoleId(v);
                        boolean s = UserRoleService.getInstance().insert(r);
                        if (!s) {
                            error = "&error=true";
                            break;
                        }
                    }
                }else{
                    error = "&error=true";
                }
            }else{
                error = "&error=true";
            }
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/user/detail?lectureId=" + lectureId + error);
    }
}
