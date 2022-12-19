<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
                        <span><i class="mdi mdi-chevron-right"></i></span>Khoa</p>
                </div>
                <div class="row">
                    <div class="col-xl-4 col-lg-12">
                        <div class="ec-cat-list card card-default mb-24px">
                            <div class="card-body">
                                <div class="ec-cat-form">
                                    <h4>Thêm khoa</h4>
                                    <form id="form-add"
                                            <c:choose>
                                                <c:when test="${faculty == null}">
                                                    action="<%=request.getContextPath()%>/admin/faculty/add"
                                                </c:when>
                                                <c:when test="${faculty != null}">
                                                    action="<%=request.getContextPath()%>/admin/faculty/edit"
                                                </c:when>
                                            </c:choose>
                                          method="post"
                                          enctype="multipart/form-data"
                                    >
                                        <div class="row">
                                            <label for="facultyId" class="col-12 col-form-label">Mã khoa</label>
                                            <div class="col-12">
                                                <input <c:if test="${faculty != null}">readonly</c:if> id="facultyId" name="facultyId" class="form-control here slug-title" type="text" value="${faculty.facultyId}" required/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <label for="facultyName" class="col-12 col-form-label">Tên khoa</label>
                                            <div class="col-12">
                                                <input id="facultyName" name="facultyName" class="form-control here slug-title" type="text" value="${faculty.facultyName}" required/>
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
                                        <div class="row ec-vendor-uploads">
                                            <label class="col-12 col-form-label" for="faculty-logo">Logo</label>
                                            <div class="ec-vendor-img-upload">
                                                <div class="ec-vendor-main-img">
                                                    <div class="thumb-upload-set col-md-12">
                                                        <div class="thumb-upload">
                                                            <div class="thumb-edit">
                                                                <input type='file' id="faculty-logo" name="faculty-logo"
                                                                       class="ec-image-upload"
                                                                       accept=".png, .jpg, .jpeg" <c:if test="${faculty == null}"> required</c:if>/>
                                                                <label for="faculty-logo"><img
                                                                        src="<%=request.getContextPath()%>/assets/admin/img/icons/edit.svg"
                                                                        class="svg_img header_svg" alt="edit"/>
                                                                </label>
                                                            </div>
                                                            <div class="thumb-preview ec-preview">
                                                                <div class="image-thumb-preview">
                                                                    <img class="image-thumb-preview ec-image-preview clear-img"
                                                                            <c:choose>
                                                                                <c:when test="${faculty == null}">
                                                                                    src="<%=request.getContextPath()%>/assets/admin/img/products/vender-upload-thumb-preview.jpg"
                                                                                </c:when>
                                                                                <c:when test="${faculty != null}">
                                                                                    src="${faculty.image}"/>"
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
                                            <th>Mã khoa</th>
                                            <th>Tên khoa</th>
                                            <th>Đã xoá</th>
                                            <th>Action</th>
                                        </tr>
                                        </thead>

                                        <tbody>
                                        <c:if test="${faculties != null}">
                                            <c:forEach var="faculty" items="${faculties}">
                                                <tr>
                                                    <td><img class="cat-thumb" src="${faculty.image}" alt="Faculty Image" /></td>
                                                    <td>${faculty.facultyId}</td>
                                                    <td>${faculty.facultyName}</td>
                                                    <td>${faculty.deleted}</td>
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
                                                                <a class="dropdown-item btn btn-info" href="<%=request.getContextPath()%>/admin/faculty/edit?facultyId=${faculty.facultyId}">Sửa</a>
                                                                <a type="button" class="dropdown-item btn btn-danger" data-bs-toggle="modal"
                                                                   data-bs-target="#modal-delete-faculty" data-id="${faculty.facultyId}" href="#modal-delete-faculty">Xoá
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
                <div class="modal fade" id="modal-delete-faculty" tabindex="-1" role="dialog" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
                        <div class="modal-content">
                            <h3 class="modal-header border-bottom-0">Bạn có muốn xoá khoa này</h3>
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
        $('#modal-delete-faculty').on('show.bs.modal', function (event) {
            let id = $(event.relatedTarget).attr('data-id');
            document.getElementById('link-delete').href = "<%=request.getContextPath()%>/admin/faculty/delete?facultyId=" + id;
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
