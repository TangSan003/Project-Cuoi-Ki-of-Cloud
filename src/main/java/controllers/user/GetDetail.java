package controllers.user;

import models.services.faculty.FacultyService;
import models.services.lecture.LectureService;
import models.services.role.RoleService;
import models.view_models.faculty.FacultyViewModel;
import models.view_models.lecture.LectureViewModel;
import models.view_models.role.RoleViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetDetail", value = "/admin/user/detail")
public class GetDetail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LectureViewModel lecture = LectureService.getInstance().retrieveById(request.getParameter("lectureId"), "");
        ArrayList<FacultyViewModel> faculties = FacultyService.getInstance().retrieveAll();
        ArrayList<RoleViewModel> roles = RoleService.getInstance().retrieveAll();

        String error = request.getParameter("error");
        if((error != null && !error.equals("")) || (faculties == null || lecture == null || roles == null)){
            request.setAttribute("error",error);
        }else{
            faculties.removeIf(c -> c.getDeleted() == 1);
            request.setAttribute("faculties",faculties);
            request.setAttribute("lecture",lecture);
            request.setAttribute("roles",roles);
        }
        ServletUtils.forward(request,response,"/views/admin/user/user-detail.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
