<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="grades" type="java.util.ArrayList<models.view_models.grade.GradeViewModel>" scope="request"/>
<jsp:useBean id="students" type="java.util.ArrayList<models.view_models.student.StudentViewModel>" scope="request"/>
<jsp:useBean id="subjectGroups" type="java.util.ArrayList<models.view_models.subject_group.SubjectGroupViewModel>" scope="request"/>
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
                        <span><i class="mdi mdi-chevron-right"></i></span>Điểm</p>
                </div>
                <div class="row">
                    <div class="col-xl-4 col-lg-12">
                        <div class="ec-cat-list card card-default mb-24px">
                            <div class="card-body">
                                <div class="ec-cat-form">
                                    <h4>Thêm điểm lớp học phần</h4>
                                    <form id="form-add"
                                            <c:choose>
                                                <c:when test="${grade == null}">
                                                    action="<%=request.getContextPath()%>/admin/grade/add"
                                                </c:when>
                                                <c:when test="${grade != null}">
                                                    action="<%=request.getContextPath()%>/admin/grade/edit"
                                                </c:when>
                                            </c:choose>
                                          method="post"
                                    >
                                        <div class="row">
                                            <label for="studentId" class="col-12 col-form-label">Mã sinh viên</label>
                                            <div class="col-12">
                                                <select name="studentId" class="form-select" id="studentId" required>
                                                    <c:forEach var="f" items="${students}">
                                                        <option value="${f.studentId}" <c:if test="${f.studentId == grade.studentId}">selected</c:if>>${f.studentId}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <label for="subjectGroupId" class="col-12 col-form-label">Mã lớp học phần</label>
                                            <div class="col-12">
                                                <select name="subjectGroupId" class="form-select" id="subjectGroupId" required>
                                                    <c:forEach var="f" items="${subjectGroups}">
                                                        <option value="${f.subjectGroupId}" <c:if test="${f.subjectGroupId == grade.subjectGroupId}">selected</c:if>>${f.subjectGroupName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <label for="middleGrade" class="col-12 col-form-label">Điểm quá trình</label>
                                            <div class="col-12">
                                                <input id="middleGrade" name="middleGrade" class="form-control here slug-title" type="number" step="0.01" min="0" max="10" value="${grade.middleGrade}" required/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <label for="finalGrade" class="col-12 col-form-label">Điểm cuối kì</label>
                                            <div class="col-12">
                                                <input id="finalGrade" name="finalGrade" class="form-control here slug-title" type="number" step="0.01" min="0" max="10" value="${grade.finalGrade}" required/>
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
                                            <th>Mã sinh viên</th>
                                            <th>Tên sinh viên</th>
                                            <th>Tên lớp học phần</th>
                                            <th>Điểm quá trình</th>
                                            <th>Điểm cuối kì</th>
                                            <th>Tổng điểm</th>
                                            <th>Đã xoá</th>
                                            <th>Action</th>
                                        </tr>
                                        </thead>

                                        <tbody>
                                        <c:if test="${grades != null}">
                                            <c:forEach var="grade" items="${grades}">
                                                <tr>
                                                    <td>${grade.studentId}</td>
                                                    <td>${grade.studentName}</td>
                                                    <td>${grade.subjectGroupName}</td>
                                                    <td>${grade.middleGrade}</td>
                                                    <td>${grade.finalGrade}</td>
                                                    <td>${grade.totalGrade}</td>
                                                    <td>${grade.deleted}</td>
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
                                                                <a class="dropdown-item btn btn-info" href="<%=request.getContextPath()%>/admin/grade/edit?studentId=${grade.studentId}&subjectGroupId=${grade.subjectGroupId}">Sửa</a>
                                                                <a type="button" class="dropdown-item btn btn-danger" data-bs-toggle="modal"
                                                                   data-bs-target="#modal-delete-grade" data-studentId="${grade.studentId}" data-subjectGroupId="${grade.subjectGroupId}" href="#modal-delete-grade">Xoá
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
                <div class="modal fade" id="modal-delete-grade" tabindex="-1" role="dialog" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
                        <div class="modal-content">
                            <h3 class="modal-header border-bottom-0">Bạn có muốn xoá điểm này</h3>
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
        $('#modal-delete-grade').on('show.bs.modal', function (event) {
            let subjectGroupId = $(event.relatedTarget).attr('data-subjectGroupId');
            let studentId = $(event.relatedTarget).attr('data-studentId');
            document.getElementById('link-delete').href = "<%=request.getContextPath()%>/admin/grade/delete?studentId=" + studentId + "&subjectGroupId="+subjectGroupId;
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
