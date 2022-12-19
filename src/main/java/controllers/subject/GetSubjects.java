package controllers.subject;

import models.services.subject.SubjectService;
import models.view_models.subject.SubjectViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetSubjects", value = "/admin/subjects")
public class GetSubjects extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<SubjectViewModel> subjects = SubjectService.getInstance().retrieveAll();

        String error = request.getParameter("error");
        if(error != null && !error.equals("") || (subjects == null)){
            request.setAttribute("error",error);
            if(subjects == null)
                subjects = new ArrayList<>();
        }
        request.setAttribute("subjects",subjects);
        ServletUtils.forward(request,response,"/views/admin/subject/subject.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
