package controllers.user;

import models.services.user.UserService;
import models.view_models.user.UserViewModel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "CheckAdd", value = "/admin/users/check-add")
public class CheckAdd extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        UserViewModel user = UserService.getInstance().retrieveById(request.getParameter("username"),"");
        PrintWriter out = response.getWriter();
        ArrayList<String> exists = new ArrayList<>();
        if(user != null){
            exists.add("user");
        }
        out.println(exists);
    }
}
