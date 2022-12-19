package controllers.role;

import models.services.role.RoleService;
import models.view_models.role.RoleCreateRequest;
import models.view_models.role.RoleCreateRequest;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AddRole", value = "/admin/role/add")
public class AddRole extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        RoleCreateRequest createReq = new RoleCreateRequest();

        createReq.setRoleId(request.getParameter("roleId"));
        createReq.setRoleName(request.getParameter("roleName"));
        createReq.setDeleted(request.getParameter("deleted"));

        boolean success = RoleService.getInstance().insert(createReq);
        String error = "";
        if(!success){
            error = "?error=true";
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/roles" + error);
    }
}
