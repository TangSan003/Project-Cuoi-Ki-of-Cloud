package controllers.faculty;

import models.services.faculty.FacultyService;
import models.view_models.faculty.FacultyViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetFaculties", value = "/admin/faculties")
public class GetFaculties extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<FacultyViewModel> faculties = FacultyService.getInstance().retrieveAll();

        String error = request.getParameter("error");
        if(error != null && !error.equals("")){
            request.setAttribute("error",error);
        }
        if(faculties == null){
            faculties = new ArrayList<>();
        }
        request.setAttribute("faculties",faculties);
        ServletUtils.forward(request,response,"/views/admin/faculty/faculty.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
