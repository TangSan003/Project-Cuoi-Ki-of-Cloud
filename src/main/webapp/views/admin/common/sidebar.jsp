<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="ec-left-sidebar ec-bg-sidebar">
  <div id="sidebar" class="sidebar ec-sidebar-footer">

    <div class="ec-brand">
      <a href="<%=request.getContextPath()%>/admin/faculties" title="Ekka">
<%--        <img class="ec-brand-icon" src="<%=request.getContextPath()%>/assets/admin/img/logo/ec-site-logo.png" alt="" />--%>
        <span class="ec-brand-name text-truncate">QLSV</span>
      </a>
    </div>

    <!-- begin sidebar scrollbar -->
    <div class="ec-navigation" data-simplebar>
      <!-- sidebar menu -->
      <ul class="nav sidebar-inner" id="sidebar-menu">
      <c:if test="${sessionScope.lecture == null || sessionScope.admin != null}">
          <li class="has-sub">
            <a class="sidenav-item-link" href="<%=request.getContextPath()%>/admin/faculties">
              <i class="mdi mdi-account-group"></i>
              <span class="nav-text">Khoa</span>
            </a>
          </li>
          <li class="has-sub">
            <a class="sidenav-item-link" href="<%=request.getContextPath()%>/admin/lectures">
              <i class="mdi mdi-account-group"></i>
              <span class="nav-text">Giảng viên</span>
            </a>
          </li>
          <li class="has-sub">
            <a class="sidenav-item-link" href="<%=request.getContextPath()%>/admin/roles">
              <i class="mdi mdi-dns-outline"></i>
              <span class="nav-text">Role</span>
            </a>
          </li>
          <li class="has-sub">
            <a class="sidenav-item-link" href="<%=request.getContextPath()%>/admin/subjects">
              <i class="mdi mdi-palette-advanced"></i>
              <span class="nav-text">Học phần</span>
            </a>
          </li>
          <li class="has-sub">
            <a class="sidenav-item-link" href="<%=request.getContextPath()%>/admin/studentClasses">
              <i class="mdi mdi-cart"></i>
              <span class="nav-text">Lớp sinh viên</span>
            </a>
          </li>
          <li>
            <a class="sidenav-item-link" href="<%=request.getContextPath()%>/admin/students" >
              <i class="mdi mdi-star-half"></i>
              <span class="nav-text">Sinh viên</span>
            </a>
          </li>
          <li>
            <a class="sidenav-item-link" href="<%=request.getContextPath()%>/admin/subjectGroups">
              <i class="mdi mdi-tag-faces"></i>
              <span class="nav-text">Lớp học phần</span>
            </a>
          </li>
          <li>
            <a class="sidenav-item-link" href="<%=request.getContextPath()%>/admin/users">
              <i class="mdi mdi-tag-faces"></i>
              <span class="nav-text">Tài khoản</span>
            </a>
          </li>
      </c:if>
        <li>
          <a class="sidenav-item-link" href="<%=request.getContextPath()%>/admin/grades">
            <i class="mdi mdi-sale"></i>
            <span class="nav-text">Điểm</span>
          </a>
          <hr>
        </li>
      </ul>
    </div>
  </div>
</div>
