<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>0gan 관리자 페이지</title>

    <!-- Custom fonts for this template-->
    <link href="../resources/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
    href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
    rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="../resources/css/sb-admin-2.min.css" rel="stylesheet">

    <!-- Custom styles for this page -->
    <link href="../resources/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">


</head>

<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">

        <!-- Sidebar -->
        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

            <!-- Sidebar - Brand -->
            <a class="sidebar-brand d-flex align-items-center justify-content-center" href="index.html">
                <div class="sidebar-brand-text mx-3">0gan logo</div>
            </a>

            <!-- Divider -->
            <hr class="sidebar-divider my-0">

            <!-- Nav Item - Dashboard -->
            <li class="nav-item active">
                <a class="nav-link" href="userList.do"><span> 회원 정보 관리 </span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="placeList.do"><span> 공간 정보 관리 </span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="rsvtList.do"><span> 예약 정보 관리 </span></a>
            </li>



            <!-- Nav Item - Pages Collapse Menu -->
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo"
                aria-expanded="true" aria-controls="collapseTwo">
                <span> 매출 관리 </span>
            </a>
            <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <a class="collapse-item" href="totalSales.do"> 전체 매출 </a>
                    <a class="collapse-item" href="placeSales.do"> 공간별 매출 </a>
                    <a class="collapse-item" href="hostSales.do"> 호스트별 매출 </a>
                </div>
            </div>
        </li>

        <!-- Nav Item - Utilities Collapse Menu -->
        <li class="nav-item">
            <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUtilities"
            aria-expanded="true" aria-controls="collapseUtilities">
            <span> 게시판 관리 </span>
        </a>
        <div id="collapseUtilities" class="collapse" aria-labelledby="headingUtilities"
        data-parent="#accordionSidebar">
        <div class="bg-white py-2 collapse-inner rounded">
            <a class="collapse-item" href="#"> 공지사항 관리 </a>
            <a class="collapse-item" href="#"> 도움말 관리 </a>
            <a class="collapse-item" href="#"> 이벤트 관리 </a>
            <a class="collapse-item" href="#"> 문의게시판 관리 </a>
        </div>
    </div>
</li>
</ul>
<!-- End of Sidebar -->

<!-- Content Wrapper -->
<div id="content-wrapper" class="d-flex flex-column">

    <!-- Main Content -->
    <div id="content">

        <!-- Topbar -->
        <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

            <!-- Sidebar Toggle (Topbar) -->
            <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                <i class="fa fa-bars"></i>
            </button>



            <!-- Topbar Navbar -->
            <ul class="navbar-nav ml-auto">



                <!-- Nav Item - User Information -->
                <li class="nav-item dropdown no-arrow">
                    <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span class="mr-2 d-none d-lg-inline text-gray-600 small"> {관리자} </span>
                    <img class="img-profile rounded-circle"
                    src="../resources/img/undraw_profile.svg">
                </a>
                <!-- Dropdown - User Information -->
                <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                aria-labelledby="userDropdown">


                <a class="dropdown-item" href="#">
                    로그아웃
                </a>
            </div>
        </li>

    </ul>

</nav>
<!-- End of Topbar -->

<!-- 위의 코드는 고정이고 밑에 코드는 좌측 메뉴 클릭시 출력되는 페이지 내용을 채워가시면 되겠습니다. -->

<!-- Begin Page Content -->
<div class="container-fluid">

    <!-- Page Heading -->
    <h1 class="h3 mb-2 text-gray-800">회원 상세 정보</h1>

    <nav style="--bs-breadcrumb-divider: url(&#34;data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='8' height='8'%3E%3Cpath d='M2.5 0L1 1.5 3.5 4 1 6.5 2.5 8l4-4-4-4z' fill='currentColor'/%3E%3C/svg%3E&#34;);" aria-label="breadcrumb">
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="userList.do"> 회원 정보 관리 </a></li>
        <li class="breadcrumb-item active" aria-current="page"> 회원 상세 정보 </li>
    </ol>
</nav>


<!-- DataTales Example -->
<div class="card shadow mt-4 mb-4">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">회원 상세 정보</h6>
    </div>
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-bordered" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th>회원번호</th>
                        <th>이름</th>
                        <th>이메일</th>
                        <th>연락처</th>
                        <th>권한</th>
                        <th>탈퇴여부</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${uVo.user_num}</td>
                        <td>${uVo.user_name}</td>
                        <td>${uVo.user_email}</td>
                        <td>${uVo.user_tel}</td>
                        <td>${uVo.user_grade}</td>
                        <td>${uVo.user_del}</td>
                    </tr>
                </tbody>
            </table>
            <table class="table table-bordered" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th>로그인일자</th>
                        <th>로그인상태</th>
                        <th>소설로그인번호</th>
                        <th>소설로그인종류</th>
                        <th>회원가입일자</th>
                        <th>회원정보수정일자</th>
                        <th>마켓팅수신동의여부</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${login.LOGIN_STATE}</td>
                        <td>${login.LOGIN_DATE}</td>
                        <td>${login.SNS_LOGIN_NUM}</td>
                        <td>${login.SNS_VER}</td>
                        <td>${login.USER_MK_DATE}</td>
                        <td>${login.USER_MODE_DATE}</td>
                        <td>${login.USER_AGREE}</td>
                    </tr>
                </tbody>
            </table>
            <table class="table table-bordered" width="100%" cellspacing="0">
             <thead>
                <tr>
                    <th>예약번호</th>
                    <th>예약기간</th>
                    <th>공간이름</th>
                    <th>공간유형</th>
                    <th>공간주소</th>
                    <th>예약상태</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>${rsvt.RSVT_NUM}</td>
                    <td>${rsvt.RSVT_TIME}</td>
                    <td>${rsvt.PLACE_NAME}</td>
                    <td>${rsvt.PLACE_CATEGORY}</td>
                    <td>${rsvt.PLACE_ADDR}</td>
                    <td>${rsvt.RSVT_STATE}</td>
                </tr>
            </tbody>
        </table>
    </div>
    <span class="form-row float-right">
        <button class="btn btn-circle pull-right" id="update"><i class="fa fa-edit" id="btnSubmit" 
	            onclick="location.href='userListUpdate.do?user_num=${uVo.user_num}'"></i></button>
    </span>
</div>
</div>

</div>
<!-- /.container-fluid -->

</div>
<!-- End of Main Content -->

<!-- Footer -->
<footer class="sticky-footer bg-white">
    <div class="container my-auto">
        <div class="copyright text-center my-auto">
            <span>Copyright &copy; 0gan Corp. All rights reserved.</span>
        </div>
    </div>
</footer>
<!-- End of Footer -->

</div>
<!-- End of Content Wrapper -->

</div>
<!-- End of Page Wrapper -->

<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
</a>

<!-- Logout Modal-->
<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
aria-hidden="true">
<div class="modal-dialog" role="document">
    <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
            </button>
        </div>
        <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
        <div class="modal-footer">
            <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
            <a class="btn btn-primary" href="login.html">Logout</a>
        </div>
    </div>
</div>
</div>

<!-- Bootstrap core JavaScript-->
<script src="../resources/vendor/jquery/jquery.min.js"></script>
<script src="../resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="../resources/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="../resources/js/sb-admin-2.min.js"></script>

<!-- Page level plugins -->
<script src="../resources/vendor/datatables/jquery.dataTables.min.js"></script>
<script src="../resources/vendor/datatables/dataTables.bootstrap4.min.js"></script>

<!-- Page level custom scripts -->
<script src="../resources/js/demo/datatables-demo.js"></script>

</body>

</html>