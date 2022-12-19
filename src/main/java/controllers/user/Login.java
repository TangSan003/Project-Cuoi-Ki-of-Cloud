package controllers.user;

import models.services.lecture.LectureService;
import models.services.user.UserService;
import models.services.user_role.UserRoleService;
import models.view_models.lecture.LectureViewModel;
import models.view_models.user.UserViewModel;
import models.view_models.user_role.UserRoleViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

@WebServlet(name = "Login", value = "/admin/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.forward(request,response,"/views/admin/signin/signin.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean isAdmin = false;
        boolean isLogin = false;
        String url = "/admin/faculties";
        if(UserService.getInstance().login(username, password)){
            isLogin = true;
            ArrayList<UserRoleViewModel> users = UserRoleService.getInstance().retrieveAll();
            UserViewModel user = UserService.getInstance().retrieveById(username, "");
            LectureViewModel lecture = LectureService.getInstance().retrieveById(user.getLectureId(), "");
            HttpSession session = request.getSession();
            for(UserRoleViewModel u:users){
                if(Objects.equals(u.getUsername(), username) && u.getRoleName().equalsIgnoreCase("admin")){
                    isAdmin = true;
                    session.setAttribute("admin",lecture);
                    break;
                }
            }
            if(!isAdmin){
                session.setAttribute("lecture",lecture);
                url = "/admin/grades";
            }
        }

        if(!isLogin){
            out.println("error");
        }else{
           ServletUtils.redirect(response, request.getContextPath() + url);
        }
    }
}
