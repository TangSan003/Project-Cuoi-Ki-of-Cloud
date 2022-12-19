package controllers.subject_group;

import models.services.lecture.LectureService;
import models.services.subject.SubjectService;
import models.services.subject_group.SubjectGroupService;
import models.view_models.lecture.LectureViewModel;
import models.view_models.subject.SubjectViewModel;
import models.view_models.subject_group.SubjectGroupViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetSubjectGroups", value = "/admin/subjectGroups")
public class GetSubjectGroups extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<LectureViewModel> lectures = LectureService.getInstance().retrieveAll();
        ArrayList<SubjectGroupViewModel> subjectGroups = SubjectGroupService.getInstance().retrieveAll();
        ArrayList<SubjectViewModel> subjects = SubjectService.getInstance().retrieveAll();

        String error = request.getParameter("error");
        if(error != null && !error.equals("") || (subjectGroups == null || lectures == null || subjects == null)){
            request.setAttribute("error",error);
            if(subjectGroups == null)
                subjectGroups = new ArrayList<>();
            if(lectures == null)
                lectures = new ArrayList<>();
            if(subjects == null)
                subjects = new ArrayList<>();
        }
        lectures.removeIf(x -> x.getDeleted() == 1);
        subjects.removeIf(x -> x.getDeleted() == 1);
        request.setAttribute("lectures",lectures);
        request.setAttribute("subjects",subjects);
        request.setAttribute("subjectGroups",subjectGroups);
        ServletUtils.forward(request,response,"/views/admin/subject_group/subject_group.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
