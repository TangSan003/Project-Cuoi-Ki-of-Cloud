package controllers.lecture;

import models.services.lecture.LectureService;
import models.services.lecture.LectureService;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RemoveLecture", value = "/admin/lecture/delete")
public class RemoveLecture extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lectureId = request.getParameter("lectureId");

        boolean isSuccess = LectureService.getInstance().delete(lectureId, "");
        String error = "";
        if(!isSuccess){
            error = "?error=true";
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/lectures" +error);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
