package filter;

import models.view_models.lecture.LectureViewModel;
import models.view_models.user.UserViewModel;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AdminFilter", urlPatterns = {
        "/admin/*"
})
public class AdminFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;
        httpReq.setCharacterEncoding("UTF-8");
        httpResp.setCharacterEncoding("UTF-8");
        HttpSession session = httpReq.getSession(false);
        LectureViewModel admin = null;
        LectureViewModel lecture = null;
        if(session != null) {
            admin = (LectureViewModel) session.getAttribute("admin");
            lecture = (LectureViewModel) session.getAttribute("lecture");
        }
        boolean isLogin = false;
        if(admin != null){
            isLogin = true;
        }
        if(lecture != null){
            String url = httpReq.getRequestURL().toString();
            if(url.contains("user/detail")){
                if(httpReq.getQueryString().contains("lectureId=" + lecture.getLectureId()))
                    isLogin = true;
            }
            if(url.contains("lecture/edit"))
                isLogin = true;
            if(url.contains("grades")){
                isLogin = true;
            }
        }
        if(!isLogin) {
            httpReq.setAttribute("error", "error");
            ServletUtils.forward(httpReq, httpResp, "/admin/login");
        }else {
            chain.doFilter(request, response);
        }
    }
}
