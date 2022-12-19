package controllers.lecture;

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

@WebServlet(name = "GetLectures", value = "/admin/lectures")
public class GetLectures extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<LectureViewModel> lectures = LectureService.getInstance().retrieveAll();
        ArrayList<FacultyViewModel> faculties = FacultyService.getInstance().retrieveAll();
        ArrayList<RoleViewModel> roles = RoleService.getInstance().retrieveAll();

        String error = request.getParameter("error");
        if((error != null && !error.equals("")) || (faculties == null || lectures == null || roles == null)){
            request.setAttribute("error",error);
            if(faculties == null)
                faculties = new ArrayList<>();
            if(lectures == null)
                lectures = new ArrayList<>();
            if(roles == null)
                roles = new ArrayList<>();
        }
        faculties.removeIf(c -> c.getDeleted() == 1);
        roles.removeIf(c -> c.getDeleted() == 1);

        request.setAttribute("faculties",faculties);
        request.setAttribute("lectures",lectures);
        request.setAttribute("roles",roles);

        ServletUtils.forward(request,response,"/views/admin/lecture/lecture.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
