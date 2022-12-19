<%@ page import="utils.constants.GENDER" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="studentClasses" type="java.util.ArrayList<models.view_models.student_class.StudentClassViewModel>" scope="request"/>
<jsp:useBean id="faculties" type="java.util.ArrayList<models.view_models.faculty.FacultyViewModel>" scope="request"/>
<html>
<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Quản lý thông tin sinh viên</title>
  <jsp:include page="/views/admin/common/common_css.jsp"/>
</head>
<body class="ec-header-fixed ec-sidebar-fixed ec-sidebar-light ec-header-light" id="body">
<div class="wrapper">
  <jsp:include page="/views/admin/common/sidebar.jsp"/>
  <div class="ec-page-wrapper">
    <jsp:include page="/views/admin/common/header.jsp"/>
    <div class="ec-content-wrapper">
      <div class="content">
        <div class="breadcrumb-wrapper breadcrumb-wrapper-2 breadcrumb-contacts">
          <p class="breadcrumbs"><span><a href="<%=request.getContextPath()%>/admin/home">Home</a></span>
            <span><i class="mdi mdi-chevron-right"></i></span>Lớp sinh viên</p>
        </div>
        <div class="row">
          <div class="col-xl-4 col-lg-12">
            <div class="ec-cat-list card card-default mb-24px">
              <div class="card-body">
                <div class="ec-cat-form">
                  <h4>Thêm lớp sinh viên</h4>
                  <form id="form-add"
                          <c:choose>
                            <c:when test="${studentClass == null}">
                              action="<%=request.getContextPath()%>/admin/studentClass/add"
                            </c:when>
                            <c:when test="${studentClass != null}">
                              action="<%=request.getContextPath()%>/admin/studentClass/edit"
                            </c:when>
                          </c:choose>
                        method="post"
                  >
                    <div class="row">
                      <label for="studentClassId" class="col-12 col-form-label">Mã lớp sinh viên</label>
                      <div class="col-12">
                        <input <c:if test="${studentClass != null}">readonly</c:if> id="studentClassId" name="studentClassId" class="form-control here slug-title" type="text" value="${studentClass.studentClassId}" required/>
                      </div>
                    </div>
                    <div class="row">
                      <label for="studentClassName" class="col-12 col-form-label">Tên lớp sinh viên</label>
                      <div class="col-12">
                        <input id="studentClassName" name="studentClassName" class="form-control here slug-title" type="text" value="${studentClass.studentClassName}" required/>
                      </div>
                    </div>
                    <div class="row">
                      <label for="facultyId" class="col-12 col-form-label">Mã khoa</label>
                      <div class="col-12">
                        <select name="facultyId" class="form-select" id="facultyId" required>
                          <c:forEach var="f" items="${faculties}">
                            <option value="${f.facultyId}" <c:if test="${f.facultyId == studentClass.facultyId}">selected</c:if>>${f.facultyName}</option>
                          </c:forEach>
                        </select>
                      </div>
                    </div>

                    <div class="row">
                      <label for="deleted" class="col-12 col-form-label">Trạng thái</label>
                      <div class="col-12">
                        <select name="deleted" id="deleted" class="form-select" required>
                          <option value="1">Đã xoá</option>
                          <option value="0">Đang dùng</option>
                        </select>
                      </div>
                    </div>
                    <div class="row mt-4">
                      <div class="col col-6">
                        <input type="button" class="btn btn-danger clear-form" value="Huỷ">
                      </div>
                      <div class="col col-6">
                        <input name="submit" type="submit" class="btn btn-primary" value="Xác nhận"/>
                      </div>
                    </div>
                  </form>

                </div>
              </div>
            </div>
          </div>
          <div class="col-xl-8 col-lg-12">
            <div class="ec-cat-list card card-default">
              <div class="card-body">
                <div class="table-responsive">
                  <table id="responsive-data-table" class="table">
                    <thead>
                    <tr>
                      <th>Mã lớp sinh viên</th>
                      <th>Tên lớp sinh viên</th>
                      <th>Mã khoa</th>
                      <th>Tên khoa</th>
                      <th>Đã xoá</th>
                      <th>Action</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:if test="${studentClasses != null}">
                      <c:forEach var="studentClass" items="${studentClasses}">
                        <tr>
                          <td>${studentClass.studentClassId}</td>
                          <td>${studentClass.studentClassName}</td>
                          <td>${studentClass.facultyId}</td>
                          <td>${studentClass.facultyName}</td>
                          <td>${studentClass.deleted}</td>
                          <td>
                            <div class="btn-group">
                              <button type="button"
                                      class="btn btn-outline-success">Info</button>
                              <button type="button"
                                      class="btn btn-outline-success dropdown-toggle dropdown-toggle-split"
                                      data-bs-toggle="dropdown" aria-haspopup="true"
                                      aria-expanded="false" data-display="static">
                                <span class="sr-only">Info</span>
                              </button>

                              <div class="dropdown-menu">
                                <a class="dropdown-item btn btn-info" href="<%=request.getContextPath()%>/admin/studentClass/edit?studentClassId=${studentClass.studentClassId}">Sửa</a>
                                <a type="button" class="dropdown-item btn btn-danger" data-bs-toggle="modal"
                                   data-bs-target="#modal-delete-studentClass" data-id="${studentClass.studentClassId}" href="#modal-delete-studentClass">Xoá
                                </a>
                              </div>
                            </div>
                          </td>
                        </tr>
                      </c:forEach>
                    </c:if>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="modal fade" id="modal-delete-studentClass" tabindex="-1" role="dialog" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
            <div class="modal-content">
              <h3 class="modal-header border-bottom-0">Bạn có muốn xoá lớp sinh viên này</h3>
              <div class="modal-footer px-4">
                <button type="button" class="btn btn-secondary btn-pill"
                        data-bs-dismiss="modal">Huỷ</button>
                <a id="link-delete" class="btn btn-danger btn-pill" >Xoá</a>
              </div>
            </div>
          </div>
        </div>
        <div class="modal fade" id="modal-error" tabindex="-1" role="dialog" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
            <div class="modal-content">
              <h3 class="modal-header border-bottom-0 d-flex justify-content-center">Thao tác bị lỗi, vui lòng thực hiện lại</h3>
              <div class="modal-footer px-4">
                <button type="button" class="btn btn-secondary btn-pill d-flex justify-content-center"
                        data-bs-dismiss="modal">Thoát</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <jsp:include page="/views/admin/common/footer.jsp"/>
  </div>
</div>
<jsp:include page="/views/admin/common/common_js.jsp"/>
<script>
  $(window).on('load', function() {
    if(${error != null}){
      $('#modal-error').modal('show');
    }
  });
  $(document).ready(function () {
    $('#modal-delete-studentClass').on('show.bs.modal', function (event) {
      let id = $(event.relatedTarget).attr('data-id');
      document.getElementById('link-delete').href = "<%=request.getContextPath()%>/admin/studentClass/delete?studentClassId=" + id;
    });
  });
  $(".clear-form").on("click", function(e) {
    e.preventDefault();
    document.getElementById('form-add').reset();
    $("input[type='text']").val('');
  })
</script>
</body>
</html>
