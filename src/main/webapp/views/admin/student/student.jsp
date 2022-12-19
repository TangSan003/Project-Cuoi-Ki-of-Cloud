<%@ page import="utils.constants.GENDER" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="students" type="java.util.ArrayList<models.view_models.student.StudentViewModel>" scope="request"/>
<jsp:useBean id="studentClasses" type="java.util.ArrayList<models.view_models.student_class.StudentClassViewModel>" scope="request"/>
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
            <span><i class="mdi mdi-chevron-right"></i></span>Sinh viên</p>
        </div>
        <div class="row">
          <div class="col-xl-4 col-lg-12">
            <div class="ec-cat-list card card-default mb-24px">
              <div class="card-body">
                <div class="ec-cat-form">
                  <h4>Thêm sinh viên</h4>
                  <form id="form-add"
                          <c:choose>
                            <c:when test="${student == null}">
                              action="<%=request.getContextPath()%>/admin/student/add"
                            </c:when>
                            <c:when test="${student != null}">
                              action="<%=request.getContextPath()%>/admin/student/edit"
                            </c:when>
                          </c:choose>
                        method="post"
                        enctype="multipart/form-data"
                  >
                    <div class="row">
                      <label for="studentId" class="col-12 col-form-label">Mã sinh viên</label>
                      <div class="col-12">
                        <input <c:if test="${student != null}">readonly</c:if> id="studentId" name="studentId" class="form-control here slug-title" type="text" value="${student.studentId}" required/>
                      </div>
                    </div>
                    <div class="row">
                      <label for="studentName" class="col-12 col-form-label">Tên sinh viên</label>
                      <div class="col-12">
                        <input id="studentName" name="studentName" class="form-control here slug-title" type="text" value="${student.studentName}" required/>
                      </div>
                    </div>
                    <div class="row">
                      <label for="studentClassId" class="col-12 col-form-label">Mã lớp sinh viên</label>
                      <div class="col-12">
                        <select name="studentClassId" class="form-select" id="studentClassId" required>
                          <c:forEach var="f" items="${studentClasses}">
                            <option value="${f.studentClassId}" <c:if test="${f.studentClassId == student.studentClassId}">selected</c:if>>${f.studentClassName}</option>
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
                    <div class="row">
                      <label for="dob" class="col-12 col-form-label">Ngày sinh</label>
                      <div class="col-12">
                        <input id="dob" name="dob" class="form-control here slug-title" type="date" value="${student.dob}" required/>
                      </div>
                    </div>
                    <div class="row">
                      <label for="address" class="col-12 col-form-label">Địa chỉ</label>
                      <div class="col-12">
                        <input id="address" name="address" class="form-control here slug-title" type="text" value="${student.address}" required/>
                      </div>
                    </div>
                    <div class="row">
                      <label for="gender" class="col-12 col-form-label">Giới tính</label>
                      <div class="col-12">
                        <select name="gender" class="form-select" id="gender" required>
                          <c:forEach var="g" items="<%=GENDER.Gender%>">
                            <option value="${g.value}" <c:if test="${g.value == student.gender}">selected</c:if>>${g.key}</option>
                          </c:forEach>
                        </select>
                      </div>
                    </div>
                    <div class="row">
                      <label for="phone" class="col-12 col-form-label">Số điện thoại</label>
                      <div class="col-12">
                        <input id="phone" name="phone" class="form-control here slug-title" type="text" value="${student.phone}" pattern="[0-9]{10}" required/>
                      </div>
                    </div>
                    <div class="row ec-vendor-uploads">
                      <label class="col-12 col-form-label" for="student-image">Logo</label>
                      <div class="ec-vendor-img-upload">
                        <div class="ec-vendor-main-img">
                          <div class="thumb-upload-set col-md-12">
                            <div class="thumb-upload">
                              <div class="thumb-edit">
                                <input type='file' id="student-image" name="student-image"
                                       class="ec-image-upload"
                                       accept=".png, .jpg, .jpeg" <c:if test="${student == null}"> required</c:if>/>
                                <label for="student-image"><img
                                        src="<%=request.getContextPath()%>/assets/admin/img/icons/edit.svg"
                                        class="svg_img header_svg" alt="edit"/>
                                </label>
                              </div>
                              <div class="thumb-preview ec-preview">
                                <div class="image-thumb-preview">
                                  <img class="image-thumb-preview ec-image-preview clear-img"
                                  <c:choose>
                                          <c:when test="${student == null}">
                                            src="<%=request.getContextPath()%>/assets/admin/img/products/vender-upload-thumb-preview.jpg"
                                          </c:when>
                                  <c:when test="${student != null}">
                                       src="${student.image}"/>"
                                  </c:when>
                                  </c:choose>
                                  alt="edit" />
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
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
                      <th>Thumb</th>
                      <th>Mã sinh viên</th>
                      <th>Tên sinh viên</th>
                      <th>Mã lớp sinh viên</th>
                      <th>Tên lớp sinh viên</th>
                      <th>Tên khoa</th>
                      <th>Ngày sinh</th>
                      <th>Địa chỉ</th>
                      <th>Giới tính</th>
                      <th>Số điện thoại</th>
                      <th>Đã xoá</th>
                      <th>Action</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:if test="${students != null}">
                      <c:forEach var="student" items="${students}">
                        <tr>
                          <td><img class="cat-thumb" src="${student.image}" alt="Student Image" /></td>
                          <td>${student.studentId}</td>
                          <td>${student.studentName}</td>
                          <td>${student.studentClassId}</td>
                          <td>${student.studentClassName}</td>
                          <td>${student.facultyName}</td>
                          <td>${student.dob}</td>
                          <td>${student.address}</td>
                          <td>${student.gender}</td>
                          <td>${student.phone}</td>
                          <td>${student.deleted}</td>
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
                                <a class="dropdown-item btn btn-info" href="<%=request.getContextPath()%>/admin/student/edit?studentId=${student.studentId}">Sửa</a>
                                <a type="button" class="dropdown-item btn btn-danger" data-bs-toggle="modal"
                                   data-bs-target="#modal-delete-student" data-id="${student.studentId}" href="#modal-delete-student">Xoá
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
        <div class="modal fade" id="modal-delete-student" tabindex="-1" role="dialog" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
            <div class="modal-content">
              <h3 class="modal-header border-bottom-0">Bạn có muốn xoá sinh viên này</h3>
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
    $('#modal-delete-student').on('show.bs.modal', function (event) {
      let id = $(event.relatedTarget).attr('data-id');
      document.getElementById('link-delete').href = "<%=request.getContextPath()%>/admin/student/delete?studentId=" + id;
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
