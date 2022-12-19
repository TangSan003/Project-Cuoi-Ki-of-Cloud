package controllers.role;

import models.services.role.RoleService;
import models.view_models.role.RoleViewModel;
import models.view_models.role.RoleUpdateRequest;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EditRole", value = "/admin/role/edit")
public class EditRole extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roleId = request.getParameter("roleId");

        RoleViewModel role = RoleService.getInstance().retrieveById(roleId,"");

        request.setAttribute("role", role);

        ServletUtils.forward(request,response,"/admin/roles");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        RoleUpdateRequest updateReq = new RoleUpdateRequest();

        String roleId = request.getParameter("roleId");
        String roleName = request.getParameter("roleName");
        updateReq.setRoleId(roleId);
        updateReq.setDeleted(request.getParameter("deleted"));

        updateReq.setRoleName(roleName);

        boolean isSuccess = RoleService.getInstance().update(updateReq);
        String error = "";
        if(!isSuccess){
            error = "?error=true";
        }
        ServletUtils.redirect(response, request.getContextPath() + "/admin/roles" + error);
    }
}
