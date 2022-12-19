<%@ page import="utils.constants.GENDER" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="subjectGroups" type="java.util.ArrayList<models.view_models.subject_group.SubjectGroupViewModel>" scope="request"/>
<jsp:useBean id="subjects" type="java.util.ArrayList<models.view_models.subject.SubjectViewModel>" scope="request"/>
<jsp:useBean id="lectures" type="java.util.ArrayList<models.view_models.lecture.LectureViewModel>" scope="request"/>
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
                        <span><i class="mdi mdi-chevron-right"></i></span>Lớp học phần</p>
                </div>
                <div class="row">
                    <div class="col-xl-4 col-lg-12">
                        <div class="ec-cat-list card card-default mb-24px">
                            <div class="card-body">
                                <div class="ec-cat-form">
                                    <h4>Thêm lớp học phần</h4>
                                    <form id="form-add"
                                            <c:choose>
                                                <c:when test="${subjectGroup == null}">
                                                    action="<%=request.getContextPath()%>/admin/subjectGroup/add"
                                                </c:when>
                                                <c:when test="${subjectGroup != null}">
                                                    action="<%=request.getContextPath()%>/admin/subjectGroup/edit"
                                                </c:when>
                                            </c:choose>
                                          method="post"
                                    >
                                        <div class="row">
                                            <label for="subjectGroupId" class="col-12 col-form-label">Mã lớp học phần</label>
                                            <div class="col-12">
                                                <input <c:if test="${subjectGroup != null}">readonly</c:if> id="subjectGroupId" name="subjectGroupId" class="form-control here slug-title" type="text" value="${subjectGroup.subjectGroupId}" required/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <label for="subjectGroupName" class="col-12 col-form-label">Tên lớp học phần</label>
                                            <div class="col-12">
                                                <input id="subjectGroupName" name="subjectGroupName" class="form-control here slug-title" type="text" value="${subjectGroup.subjectGroupName}" required/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <label for="subjectId" class="col-12 col-form-label">Tên học phần</label>
                                            <div class="col-12">
                                                <select name="subjectId" class="form-select" id="subjectId" required>
                                                    <c:forEach var="f" items="${subjects}">
                                                        <option value="${f.subjectId}" <c:if test="${f.subjectId == subjectGroup.subjectId}">selected</c:if>>${f.subjectName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <label for="lectureId" class="col-12 col-form-label">Tên giảng viên</label>
                                            <div class="col-12">
                                                <select name="lectureId" class="form-select" id="lectureId" required>
                                                    <c:forEach var="f" items="${lectures}">
                                                        <option value="${f.lectureId}" <c:if test="${f.lectureId == subjectGroup.lectureId}">selected</c:if>>${f.lectureName}</option>
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
                                            <th>Mã nhóm lớp</th>
                                            <th>Tên nhóm lớp</th>
                                            <th>Mã giảng viên</th>
                                            <th>Tên giảng viên</th>
                                            <th>Mã môn học</th>
                                            <th>Tên môn học</th>
                                            <th>Đã xoá</th>
                                            <th>Action</th>
                                        </tr>
                                        </thead>

                                        <tbody>
                                        <c:if test="${subjectGroups != null}">
                                            <c:forEach var="subjectGroup" items="${subjectGroups}">
                                                <tr>
                                                    <td>${subjectGroup.subjectGroupId}</td>
                                                    <td>${subjectGroup.subjectGroupName}</td>
                                                    <td>${subjectGroup.lectureId}</td>
                                                    <td>${subjectGroup.lectureName}</td>
                                                    <td>${subjectGroup.subjectId}</td>
                                                    <td>${subjectGroup.subjectName}</td>
                                                    <td>${subjectGroup.deleted}</td>
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
                                                                <a class="dropdown-item btn btn-info" href="<%=request.getContextPath()%>/admin/subjectGroup/edit?subjectGroupId=${subjectGroup.subjectGroupId}">Sửa</a>
                                                                <a type="button" class="dropdown-item btn btn-danger" data-bs-toggle="modal"
                                                                   data-bs-target="#modal-delete-subjectGroup" data-id="${subjectGroup.subjectGroupId}" href="#modal-delete-subjectGroup">Xoá
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
                <div class="modal fade" id="modal-delete-subjectGroup" tabindex="-1" role="dialog" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
                        <div class="modal-content">
                            <h3 class="modal-header border-bottom-0">Bạn có muốn xoá nhóm lớp này</h3>
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
        $('#modal-delete-subjectGroup').on('show.bs.modal', function (event) {
            let id = $(event.relatedTarget).attr('data-id');
            document.getElementById('link-delete').href = "<%=request.getContextPath()%>/admin/subjectGroup/delete?subjectGroupId=" + id;
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
