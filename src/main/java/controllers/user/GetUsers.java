package controllers.user;

import models.services.user.UserService;
import models.view_models.user.UserViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetUsers", value = "/admin/users")
public class GetUsers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<UserViewModel> users = UserService.getInstance().retrieveAll();
        String error = request.getParameter("error");
        if((error != null && !error.equals("")) || (users == null )){
            request.setAttribute("error",error);
            if(users == null)
                users = new ArrayList<>();
        }

        request.setAttribute("users",users);
        ServletUtils.forward(request,response,"/views/admin/user/user.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
