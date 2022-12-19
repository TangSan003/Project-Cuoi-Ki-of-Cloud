package controllers.grade;

import models.services.grade.GradeService;
import models.services.grade.IGradeService;
import models.services.student.StudentService;
import models.services.subject_group.SubjectGroupService;
import models.view_models.grade.GradeViewModel;
import models.view_models.lecture.LectureViewModel;
import models.view_models.student.StudentViewModel;
import models.view_models.subject_group.SubjectGroupViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetGrades", value = "/admin/grades")
public class GetGrades extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        LectureViewModel lecture = null;
        if(session != null) {
            lecture = (LectureViewModel) session.getAttribute("lecture");
        }
        ArrayList<GradeViewModel> grades;
        ArrayList<SubjectGroupViewModel> subjectGroups;
        if(lecture != null){
            grades = GradeService.getInstance().retrieveGradeByLectureId(lecture.getLectureId());
            subjectGroups = SubjectGroupService.getInstance().retrieveSubjectGroupByLectureId(lecture.getLectureId());
        }
        else{
            grades = GradeService.getInstance().retrieveAll();
            subjectGroups = SubjectGroupService.getInstance().retrieveAll();
        }
        ArrayList<StudentViewModel> students = StudentService.getInstance().retrieveAll();
        String error = request.getParameter("error");
        if(error != null && !error.equals("") || (grades == null || students == null || subjectGroups == null)){
            request.setAttribute("error",error);
            if(grades == null)
                grades = new ArrayList<>();
            if(students == null)
                students = new ArrayList<>();
            if(subjectGroups == null)
                subjectGroups = new ArrayList<>();
        }
        students.removeIf(x -> x.getDeleted() == 1);
        subjectGroups.removeIf(x -> x.getDeleted() == 1);
        request.setAttribute("grades",grades);
        request.setAttribute("students",students);
        request.setAttribute("subjectGroups",subjectGroups);

        ServletUtils.forward(request,response,"/views/admin/grade/grade.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
