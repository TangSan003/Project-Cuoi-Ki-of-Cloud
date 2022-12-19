<%@ page import="utils.constants.GENDER" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<jsp:useBean id="lecture" scope="request" class="models.view_models.lecture.LectureViewModel"/>
<jsp:useBean id="roles" scope="request" type="java.util.ArrayList<models.view_models.role.RoleViewModel>"/>
<jsp:useBean id="faculties" scope="request" type="java.util.ArrayList<models.view_models.faculty.FacultyViewModel>"/>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Quản lý thông tin sinh viên</title>
    <jsp:include page="/views/admin/common/common_css.jsp"/>
</head>
<body class="ec-header-fixed ec-sidebar-fixed ec-sidebar-dark ec-header-light" id="body">
<div class="wrapper">
    <jsp:include page="/views/admin/common/sidebar.jsp"/>
    <div class="ec-page-wrapper" >
        <jsp:include page="/views/admin/common/header.jsp"/>
        <div class="ec-content-wrapper">
            <div class="content">
                <div class="breadcrumb-wrapper breadcrumb-contacts">
                    <div>
                        <h1>User Profile</h1>
                        <p class="breadcrumbs"><span><a href="<%=request.getContextPath()%>/admin/home">Home</a></span>
                            <span><i class="mdi mdi-chevron-right"></i></span>Profile
                        </p>
                    </div>
                    <div>
                        <a href="<%=request.getContextPath()%>/admin/users" class="btn btn-primary">Danh sách người dùng</a>
                    </div>
                </div>
                <div class="card bg-white profile-content">
                    <div class="row">

                        <div class="col-lg-4 col-xl-12">
                            <div class="profile-content-left profile-left-spacing">
                                <div class="text-center widget-profile px-0 border-0">
                                    <div class="card-img mx-auto rounded-circle">
                                        <img src="${lecture.image}" alt="user image">
                                    </div>
                                    <div class="card-body">
                                        <h4 class="py-2 text-dark">${lecture.lectureName}</h4>
                                        <p>Khoa: ${lecture.facultyName}</p>
                                        <p>Điện thoại: ${lecture.phone}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-8 col-xl-12">
                            <div class="profile-content-right profile-right-spacing py-5">
                                <ul class="nav nav-tabs px-3 px-xl-5 nav-style-border" id="myProfileTab" role="tablist">
                                    <li class="nav-item" role="presentation">
                                        <button class="nav-link active" id="profile-tab" data-bs-toggle="tab"
                                                data-bs-target="#profile" type="button" role="tab"
                                                aria-controls="profile" aria-selected="true">Profile</button>
                                    </li>
                                </ul>
                                <div class="tab-content px-3 px-xl-5" id="myTabContent">
                                    <div class="tab-pane fade show active" id="profile" role="tabpanel"
                                         aria-labelledby="profile-tab">
                                        <div class="tab-widget mt-5">
                                            <form id="form-add" action="<%=request.getContextPath()%>/admin/lecture/edit" method="post" enctype="multipart/form-data">
                                                <input type="hidden" name="lectureId" id="lectureId" value="${lecture.lectureId}"/>
                                                <div class="row ec-vendor-uploads mb-4">
                                                    <label class="col-12 col-form-label" for="avatar">Avatar</label>
                                                    <div class="ec-vendor-img-upload">
                                                        <div class="ec-vendor-main-img">
                                                            <div class="thumb-upload-set col-md-12">
                                                                <div class="thumb-upload">
                                                                    <div class="thumb-edit">
                                                                        <input type='file' id="avatar" name="lecture-image"
                                                                               class="ec-image-upload"
                                                                               accept=".png, .jpg, .jpeg"/>
                                                                        <label for="avatar">
                                                                            <img src="<%=request.getContextPath()%>/assets/admin/img/icons/edit.svg"
                                                                                 class="svg_img header_svg" alt="edit"/>
                                                                        </label>
                                                                    </div>
                                                                    <div class="thumb-preview ec-preview">
                                                                        <div class="image-thumb-preview">
                                                                            <img class="image-thumb-preview ec-image-preview clear-img"
                                                                                 src="${lecture.image}"
                                                                                 alt="edit" />
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row mb-2">
                                                    <div class="col-lg-4">
                                                        <div class="form-group">
                                                            <label for="lectureName">Tên giảng viên</label>
                                                            <input type="text" class="form-control" id="lectureName"
                                                                   name="lectureName" value="${lecture.lectureName}" required>
                                                        </div>
                                                    </div>
                                                    <div class="col-lg-4">
                                                        <div class="form-group mb-4">
                                                            <label for="gender">Giới tính</label>
                                                            <select id="gender" name="gender" class="form-select" required>
                                                                <c:forEach var="g" items="<%=GENDER.Gender%>">
                                                                    <option value="${g.value}" <c:if test="${g.value == lecture.gender}">selected</c:if>>${g.key}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="col-lg-4" <c:if test="${sessionScope.lecture != null}">hidden</c:if> >
                                                        <div class="form-group mb-4">
                                                            <label for="facultyId">Khoa</label>
                                                            <div class="col-12">
                                                                <select name="facultyId" class="form-select" id="facultyId" required>
                                                                    <c:forEach var="f" items="${faculties}">
                                                                        <option value="${f.facultyId}" <c:if test="${f.facultyId == lecture.facultyId}">selected</c:if>>${f.facultyName}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row mb-2">
                                                    <div class="col-lg-4">
                                                        <div class="form-group mb-4">
                                                            <label for="username">Tên tài khoản</label>
                                                            <input type="text" class="form-control" id="username"
                                                                   name="username" readonly value="${lecture.username}" required>
                                                            <p class="mt-3" id='userValidateMessage' ></p>
                                                        </div>
                                                    </div>
                                                    <div class="col-lg-4">
                                                        <div class="form-group mb-4">
                                                            <label for="password">Mật khẩu</label>
                                                            <input type="password" class="form-control" id="password"
                                                                   name="password" >
                                                            <p class="mt-3" id='passwordValidateMessage'></p>
                                                        </div>
                                                    </div>
                                                    <div class="col-lg-4" <c:if test="${sessionScope.lecture != null}">hidden</c:if> >
                                                        <div class="form-group mb-4">
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
                                                </div>
                                                <div class="row mb-2">
                                                    <div class="col-lg-6">
                                                        <div class="form-group mb-4">
                                                            <label for="newPassword">Mật khẩu mới</label>
                                                            <input type="password" class="form-control" id="newPassword"
                                                                   name="newPassword">
                                                        </div>
                                                    </div>
                                                    <div class="col-lg-6">
                                                        <div class="form-group">
                                                            <label for="confirmPassword">Xác nhận mật khẩu</label>
                                                            <input type="password" class="form-control" id="confirmPassword"
                                                                   name="confirmPassword">
                                                            <p class="mt-3" id='confirmPasswordNotMatch'></p>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="row mb-2">
                                                    <div class="col-lg-4">
                                                        <div class="form-group">
                                                            <label for="phone">Điện thoại</label>
                                                            <input type="text" class="form-control" id="phone" pattern="[0-9]{10}"
                                                                   name="phone" value="${lecture.phone}" required>
                                                            <p class="mt-3" id='phoneValidateMessage'></p>
                                                        </div>
                                                    </div>
                                                    <div class="col-lg-4">
                                                        <div class="form-group mb-4">
                                                            <label for="dob">Ngày sinh</label>
                                                            <input type="date" class="form-control" id="dob"
                                                                   name="dob" value="${lecture.dob}" required>
                                                        </div>
                                                    </div>
                                                    <div class="col-lg-4">
                                                        <div class="form-group mb-4">
                                                            <label for="address">Địa chỉ</label>
                                                            <input type="text" class="form-control" id="address" name="address" value="${lecture.address}" required/>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="d-flex justify-content-end mt-5">
                                                    <a href="<%=request.getContextPath()%>/admin/users" class="btn btn-secondary btn-pill mr-3" >Huỷ</a>
                                                    <button type="submit" class="btn btn-primary btn-pill">Xác nhận</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
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
    async function hash(string) {
        const utf8 = new TextEncoder().encode(string);
        const hashBuffer = await crypto.subtle.digest('SHA-256', utf8);
        const hashArray = Array.from(new Uint8Array(hashBuffer));
        return hashArray
            .map((bytes) => bytes.toString(16).padStart(2, '0'))
            .join('');
    }
    async function validateForm(e, context) {
        let noError = true;
        e.preventDefault()
        let passMatch = $('#confirmPasswordNotMatch')
        let roleEmpty = $('#roleEmpty')
        let confirmPassword = $('#confirmPassword')
        let newPassword = $('#newPassword')
        let password = $('#password')
        let hashPassword = await hash(password.val())

        //if(newPassword.val().length !== 0) {
            if (confirmPassword != null && newPassword.val() !== confirmPassword.val()) {
                passMatch.html('Mật khẩu không khớp').css('color', 'red');
                noError = false;
            } else {
                passMatch.html('')
            }
            if(${sessionScope.admin != null}){
                if (hashPassword !== '${sessionScope.admin.password}'.toLowerCase()){
                    $("#passwordValidateMessage").html("Mật khẩu sai").css('color', 'red')
                    noError = false;
                }else{
                    $("#passwordValidateMessage").html("")
                }
            }
            else if (${sessionScope.lecture != null}){
                if (hashPassword !== '${sessionScope.lecture.password}'.toLowerCase()){
                    $("#passwordValidateMessage").html("Mật khẩu sai").css('color', 'red')
                    noError = false;
                }else{
                    $("#passwordValidateMessage").html("")
                }
            }
        //}
        if (options.length === 0) {
            roleEmpty.html('Vui lòng chọn role').css('color', 'red');
            noError = false;
        } else {
            roleEmpty.html('')
        }
        if(noError){
            $('#form-add').unbind('submit').submit();
        }
    }
    $('#form-add').submit(function (e){
        validateForm(e, `<%=request.getContextPath()%>`)
    })
</script>
</body>
</html>
