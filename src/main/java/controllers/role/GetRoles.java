package controllers.role;

import models.services.role.RoleService;
import models.view_models.role.RoleViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetRoles", value = "/admin/roles")
public class GetRoles extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<RoleViewModel> roles = RoleService.getInstance().retrieveAll();

        String error = request.getParameter("error");
        if(error != null && !error.equals("") || (roles == null)){
            request.setAttribute("error",error);
            if(roles == null)
                roles = new ArrayList<>();
        }
        request.setAttribute("roles",roles);

        ServletUtils.forward(request,response,"/views/admin/role/role.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
