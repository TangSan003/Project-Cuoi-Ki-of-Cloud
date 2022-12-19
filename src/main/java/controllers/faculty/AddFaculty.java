package controllers.faculty;

import models.services.faculty.FacultyService;
import models.view_models.faculty.FacultyCreateRequest;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

@WebServlet(name = "AddFaculty", value = "/admin/faculty/add")
@MultipartConfig(
        fileSizeThreshold = 1024*1024*2, // 2MB
        maxFileSize = 1024*1024*10, // 10MB
        maxRequestSize = 1024*1024*11   // 11MB
)
public class AddFaculty extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        FacultyCreateRequest createReq = new FacultyCreateRequest();
        createReq.setFile(request.getPart("faculty-logo"));
        createReq.setFacultyId(request.getParameter("facultyId"));
        createReq.setFacultyName(request.getParameter("facultyName"));
        createReq.setDeleted(request.getParameter("deleted"));
        boolean success = FacultyService.getInstance().insert(createReq);
        String error = "";
        if(!success){
            error = "?error=true";
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/faculties" + error);
    }
}
