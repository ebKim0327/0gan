<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>0gan 관리자 - 문의게시판</title>

    <!-- Custom fonts for this template -->
    <link href="../resources/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
    href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
    rel="stylesheet">

    <!-- Custom styles for this template -->
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

            <!-- Divider -->
            <hr class="sidebar-divider">

            <!-- Heading -->


            <!-- Nav Item - Pages Collapse Menu -->
            <li class="nav-item active">
                <a class="nav-link" href="#"><span> 회원 정보 관리 </span></a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="#"><span> 공간 정보 관리 </span></a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="#"><span> 예약 정보 관리 </span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo"
                aria-expanded="true" aria-controls="collapseTwo">
                <span> 매출 관리 </span>
            </a>
            <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <a class="collapse-item" href="#"> 전체 매출 </a>
                    <a class="collapse-item" href="#"> 공간별 매출 </a>
                    <a class="collapse-item" href="#"> 호스트별 매출 </a>
                </div>
            </div>
        </li>
        <li class="nav-item active">
            <a class="nav-link" href="#" data-toggle="collapse" data-target="#collapseUtilities"
            aria-expanded="true" aria-controls="collapseUtilities">
            <span> 게시판 관리 </span>
        </a>
        <div id="collapseUtilities" class="collapse show" aria-labelledby="headingUtilities"
        data-parent="#accordionSidebar">
        <div class="bg-white py-2 collapse-inner rounded">
            <a class="collapse-item" href="adminNoti.do"> 공지사항 관리 </a>
            <a class="collapse-item" href="adminFaq.do"> 도움말 관리 </a>
            <a class="collapse-item" href="adminTheme.do"> 기획전 관리 </a>
            <a class="collapse-item active" href="adminAnswer.do"> 문의게시판 관리 </a>
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

            <!-- Topbar Navbar -->
            <ul class="navbar-nav ml-auto">


                <!-- Nav Item - User Information -->
                <li class="nav-item dropdown no-arrow">
                    <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span class="mr-2 d-none d-lg-inline text-gray-600 small">{관리자}</span>
                    <img class="img-profile rounded-circle"
                    src="../resources/img/undraw_profile.svg">
                </a>
                <!-- Dropdown - User Information -->
                <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                aria-labelledby="userDropdown">
                <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                    <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                    로그아웃
                </a>
            </div>
        </li>

    </ul>

</nav>
<!-- End of Topbar -->

<!-- Begin Page Content -->
<div class="container-fluid">

    <!-- Page Heading -->
    <h1 class="h3 mb-4 text-gray-800">문의게시판</h1>
    <nav style="--bs-breadcrumb-divider: url(&#34;data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='8' height='8'%3E%3Cpath d='M2.5 0L1 1.5 3.5 4 1 6.5 2.5 8l4-4-4-4z' fill='currentColor'/%3E%3C/svg%3E&#34;);" aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="#">게시판 관리</a></li>
            <li class="breadcrumb-item active" aria-current="page">문의게시판 관리</li>
            <li class="breadcrumb-item active" aria-current="page">답변 수정</li>
        </ol>
    </nav>
</div>
<div class="card container-fluid w-75 shadow mt-4 mb-4">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary"> 문의게시판 관리 </h6>
    </div>
    <div>
        <div class="card-body">
            <div class="card container-fluid w-75 mb-5">
                <div class="col-md-12">
                    <label class="mt-4">제목</label> <label class="mt-4">${question.adm_que_title }</label>
                </div>
                <hr>
                <label class="col-md-12 mb-3">내용</label>
                <textarea class="form-control" rows="7" readonly="readonly" style="resize: none;">${question.adm_que_content }</textarea>
                <div class="col-md-12 mt-4">
                    <div class="filebox">
                        <lable class="mt-4">첨부파일</lable> &nbsp;
                        <input class="upload-name" value="${question.adm_que_file }" disabled="disabled">
                    </div>
                    <br>
                </div>
            </div>
        </div>
        <hr style="width: 75%;" >
        <div class="card-body">
            <div class="card container-fluid w-75 mb-5">
	            <form action="adminAnswerUpdate.do" method="post" enctype="multipart/form-data">
	                <div class="col-md-12">
	                <input type="hidden" name="adm_ans_num" value="${answer.adm_ans_num }">
	                <input type="hidden" name="user_num" value="${answer.user_num }">
	                <input type="hidden" name="adm_que_num" value="${answer.adm_que_num }">
	                    <label class="mt-4">제목</label>
	                    <input type="text" class="form-control-text" value="${answer.adm_ans_title }" name="adm_ans_title">
	                </div>
	                <hr>
	                <label class="col-md-12 mb-3">내용</label>
	                <textarea class="form-control" rows="7" name="adm_ans_content" style="resize: none;">${answer.adm_ans_content }</textarea>
	                <div class="col-md-12 mt-4">
	                    <div class="filebox">
	                        <lable class="mt-4">첨부파일</lable> &nbsp;
	                        <input class="upload-name" value="${answer.adm_ans_file }" disabled="disabled">
	                        <label for="ex_filename"><i class="fas fa-folder-plus fa-2x"></i></label>
	                        <input type="file" id="ex_filename" class="upload-hidden" name="uploadFile">
	                        <input type="hidden" name="adm_ans_file" value="${answer.adm_ans_file }">
	                    </div>
	                </div>
	                <hr>
	                <div class="d-grid gap-2 d-md-flex mb-3 justify-content-md-end">
	                    <button class="btn btn-primary me-md-2" type="submit">수정</button>
	                </div>
                </form>
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
            <span>Copyright &copy; 0gan</span>
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