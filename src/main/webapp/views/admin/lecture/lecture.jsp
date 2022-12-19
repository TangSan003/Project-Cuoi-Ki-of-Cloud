<%@ page import="utils.constants.GENDER" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="lectures" type="java.util.ArrayList<models.view_models.lecture.LectureViewModel>" scope="request"/>
<jsp:useBean id="faculties" type="java.util.ArrayList<models.view_models.faculty.FacultyViewModel>" scope="request"/>
<jsp:useBean id="roles" type="java.util.ArrayList<models.view_models.role.RoleViewModel>" scope="request"/>
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
                        <span><i class="mdi mdi-chevron-right"></i></span>Giảng viên</p>
                </div>
                <div class="row">
                    <div class="col-xl-4 col-lg-12">
                        <div class="ec-cat-list card card-default mb-24px">
                            <div class="card-body">
                                <div class="ec-cat-form">
                                    <h4>Thêm giảng viên</h4>
                                    <form id="form-add"
                                            <c:choose>
                                                <c:when test="${lecture == null}">
                                                    action="<%=request.getContextPath()%>/admin/lecture/add"
                                                </c:when>
                                                <c:when test="${lecture != null}">
                                                    action="<%=request.getContextPath()%>/admin/lecture/edit"
                                                </c:when>
                                            </c:choose>
                                          method="post"
                                          enctype="multipart/form-data"
                                    >
                                        <div class="row">
                                            <label for="lectureId" class="col-12 col-form-label">Mã giảng viên</label>
                                            <div class="col-12">
                                                <input <c:if test="${lecture != null}">readonly</c:if> id="lectureId" name="lectureId" class="form-control here slug-title" type="text" value="${lecture.lectureId}" required/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <label for="lectureName" class="col-12 col-form-label">Tên giảng viên</label>
                                            <div class="col-12">
                                                <input id="lectureName" name="lectureName" class="form-control here slug-title" type="text" value="${lecture.lectureName}" required/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <label for="facultyId" class="col-12 col-form-label">Mã khoa</label>
                                            <div class="col-12">
                                                <select name="facultyId" class="form-select" id="facultyId" required>
                                                    <c:forEach var="f" items="${faculties}">
                                                        <option value="${f.facultyId}" <c:if test="${f.facultyId == lecture.facultyId}">selected</c:if>>${f.facultyName}</option>
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
                                                <input id="dob" name="dob" class="form-control here slug-title" type="date" value="${lecture.dob}" required/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <label for="address" class="col-12 col-form-label">Địa chỉ</label>
                                            <div class="col-12">
                                                <input id="address" name="address" class="form-control here slug-title" type="text" value="${lecture.address}" required/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <label for="lectureId" class="col-12 col-form-label">Giới tính</label>
                                            <div class="col-12">
                                                <select name="gender" class="form-select" id="gender" required>
                                                    <c:forEach var="g" items="<%=GENDER.Gender%>">
                                                            <option value="${g.value}" <c:if test="${g.value == lecture.gender}">selected</c:if>>${g.key}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <label for="phone" class="col-12 col-form-label">Số điện thoại</label>
                                            <div class="col-12">
                                                <input id="phone" name="phone" class="form-control here slug-title" type="text" value="${lecture.phone}" pattern="[0-9]{10}" required/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <label for="username" class="col-12 col-form-label">Tên tài khoản</label>
                                            <div class="col-12">
                                                <input id="username" <c:if test="${lecture != null}">readonly</c:if> name="username" class="form-control here slug-title" type="text" value="${lecture.username}" required/>
                                            </div>
                                            <p id="userValidateMessage"></p>
                                        </div>
                                        <div class="row">
                                            <label for="password" class="col-12 col-form-label">Mật khẩu</label>
                                            <div class="col-12">
                                                <input id="password" name="password" class="form-control here slug-title" type="password"/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-12">
                                                <div class="dropdown button-group">
                                                    <button type="button" class="btn btn-default btn-sm dropdown-toggle"  data-bs-toggle="dropdown">Roles</button>
                                                    <ul class="role dropdown-menu">
                                                        <c:forEach var="role" items="${roles}">
                                                            <li>
                                                                <a href="#" class="small" data-value="${role.roleId}" tabIndex="-1">
                                                                    <input type="checkbox" class="roleCheckBox" id="roleCheckBox-${role.roleId}" name="roleCheckBox"  value="${role.roleId}" data-roleId="${role.roleId}" <c:if test="${lecture.roleIds.contains(role.roleId)}">checked</c:if>/>&nbsp;${role.roleName}
                                                                </a>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
                                                    <p id="roleEmpty" class="mt-3"></p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row ec-vendor-uploads">
                                            <label class="col-12 col-form-label" for="lecture-image">Logo</label>
                                            <div class="ec-vendor-img-upload">
                                                <div class="ec-vendor-main-img">
                                                    <div class="thumb-upload-set col-md-12">
                                                        <div class="thumb-upload">
                                                            <div class="thumb-edit">
                                                                <input type='file' id="lecture-image" name="lecture-image"
                                                                       class="ec-image-upload"
                                                                       accept=".png, .jpg, .jpeg" <c:if test="${lecture == null}"> required</c:if>/>
                                                                <label for="lecture-image"><img
                                                                        src="<%=request.getContextPath()%>/assets/admin/img/icons/edit.svg"
                                                                        class="svg_img header_svg" alt="edit"/>
                                                                </label>
                                                            </div>
                                                            <div class="thumb-preview ec-preview">
                                                                <div class="image-thumb-preview">
                                                                    <img class="image-thumb-preview ec-image-preview clear-img"
                                                                    <c:choose>
                                                                            <c:when test="${lecture == null}">
                                                                                src="<%=request.getContextPath()%>/assets/admin/img/products/vender-upload-thumb-preview.jpg"
                                                                            </c:when>
                                                                    <c:when test="${lecture != null}">
                                                                         src="${lecture.image}"/>"
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
                                            <th>Mã giảng viên</th>
                                            <th>Tên giảng viên</th>
                                            <th>Mã khoa</th>
                                            <th>Tên khoa</th>
                                            <th>Tên tài khoản</th>
                                            <th>Ngày sinh</th>
                                            <th>Địa chỉ</th>
                                            <th>Giới tính</th>
                                            <th>Số điện thoại</th>
                                            <th>Đã xoá</th>
                                            <th>Action</th>
                                        </tr>
                                        </thead>

                                        <tbody>
                                        <c:if test="${lectures != null}">
                                            <c:forEach var="lecture" items="${lectures}">
                                                <tr>
                                                    <td><img class="cat-thumb" src="${lecture.image}" alt="Lecture Image" /></td>
                                                    <td>${lecture.lectureId}</td>
                                                    <td>${lecture.lectureName}</td>
                                                    <td>${lecture.facultyId}</td>
                                                    <td>${lecture.facultyName}</td>
                                                    <td>${lecture.username}</td>
                                                    <td>${lecture.dob}</td>
                                                    <td>${lecture.address}</td>
                                                    <td>${lecture.gender}</td>
                                                    <td>${lecture.phone}</td>
                                                    <td>${lecture.deleted}</td>
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
                                                                <a class="dropdown-item btn btn-info" href="<%=request.getContextPath()%>/admin/lecture/edit?lectureId=${lecture.lectureId}">Sửa</a>
                                                                <a type="button" class="dropdown-item btn btn-danger" data-bs-toggle="modal"
                                                                   data-bs-target="#modal-delete-lecture" data-id="${lecture.lectureId}" href="#modal-delete-lecture">Xoá
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
                <div class="modal fade" id="modal-delete-lecture" tabindex="-1" role="dialog" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
                        <div class="modal-content">
                            <h3 class="modal-header border-bottom-0">Bạn có muốn xoá giảng viên này</h3>
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
        $('#modal-delete-lecture').on('show.bs.modal', function (event) {
            let id = $(event.relatedTarget).attr('data-id');
            document.getElementById('link-delete').href = "<%=request.getContextPath()%>/admin/lecture/delete?lectureId=" + id;
        });
    });

    $(".clear-form").on("click", function(e) {
        e.preventDefault();
        document.getElementById('form-add').reset();
        $("input[type='text']").val('');
        $("input[type='password']").val('');
    })

    var options = [];
    let allCheckBox = document.querySelectorAll('.roleCheckBox:checked')
    allCheckBox.forEach(checkbox => {
        options.push(checkbox.value)
    })
    $( '.role.dropdown-menu a' ).on( 'click', function( event ) {

        var $target = $( event.currentTarget ),
            val = $target.attr( 'data-value' ),
            $inp = $target.find( 'input' ),
            idx;

        if ( ( idx = options.indexOf( val ) ) > -1 ) {
            options.splice( idx, 1 );
            setTimeout( function() { $inp.prop( 'checked', false ) }, 0);
        } else {
            options.push( val );
            setTimeout( function() { $inp.prop( 'checked', true ) }, 0);
        }

        $( event.target ).blur();

        console.log( options );
        return false;
    });

    function validateForm(e, context){
        let noError = true;
        e.preventDefault()
        let roleEmpty = $('#roleEmpty')
        if(options.length === 0){
            roleEmpty.html('Vui lòng chọn role').css('color', 'red');
            noError = false;
        }else {
            roleEmpty.html('')
        }
        let url = context + `/admin/users/check-add`;
        if(!window.location.href.includes("edit")){
            $.ajax({
                url: url,
                method: "POST",
                data: {
                    'username': $('#username').val(),
                },
                async: false,
                success: function (data) {
                    console.log(data)
                    let str = data.toString()
                    let arr = str.substring(0, str.lastIndexOf(']')).replace('[','').replace(']','').split(', ');
                    console.log(arr)
                    if (arr.includes('user')) {
                        $('#userValidateMessage').html('Tên tài khoản đã tồn tại').css('color', 'red')
                        noError = false;
                    } else {
                        $('#userValidateMessage').html('')
                    }
                },
                error: function (error) {
                    noError = false;
                }
            })
        }

        if (noError) {
            $('#form-add').unbind('submit').submit();
        }
    }

    $('#form-add').submit(function (e){
        validateForm(e, `<%=request.getContextPath()%>`)
    })
</script>
</body>
</html>
