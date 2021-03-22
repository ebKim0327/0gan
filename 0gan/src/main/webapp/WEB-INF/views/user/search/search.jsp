<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
	<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

<meta charset="EUC-KR">
<title>Insert title here</title>

<style type="text/css">
.container{
	margin:0 auto;
    width: 1000px;
    height: 500px;
}
.item{
    display: inline-block;
    float: left;
    width: 350px;
    height: 400px;
    border: 1px solid gray;
    margin: 50px;
	
}


.imgMap{
	width:800px;
	height: 500px;
	background-color: #ABCDEF;
	margin: 100px;
}

</style>
<script src="//code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">
$(function() {
  $('#btnMap').click( function() {
    if( $(this).html() == '���� on' ) {
      $(this).html('���� off');
      $(".imgMap").css("display","none");
    }
    else {
      $(this).html('���� on');
      $(".imgMap").css("display","block");
    }
  });
});
</script>
</head>
<body>

<div class="result">
	<div class="logo" style="float: left">Logo</div>
	<div class="menu" style="float: right;">menu</div>
    <hgroup class="mb20">
		<h1>Search Results</h1>
		<h2 class="lead"><strong class="text-danger">3</strong> results were found for the search for <strong class="text-danger">Lorem</strong></h2>								
	</hgroup>
	<div id="title"><h1>�˻� ���</h1><hr></div>
	<div class="cccc" style="width: 1000px;">
		<!-- filter ��ư  -->
		<div class="filter">
			<select class="form-control custom-select nav" required="">
			<option value="��������">��������</option><option value="�������ǽ�/�ڿ�ŷ�����̽�/���μ�">�������ǽ�/�ڿ�ŷ�����̽�/���μ�</option><option value="�����ֹ�">�����ֹ�</option><option value="������/���ֽ�/������">������/���ֽ�/������</option><option value="��Ƽ��/���Ӱ���/����̴�����">��Ƽ��/���Ӱ���/����̴�����</option><option value="ȸ�ǽ�/����/���̳���/����Ͻ�����">ȸ�ǽ�/����/���̳���/����Ͻ�����</option><option value="���͵��/���͵�ī��">���͵��/���͵�ī��</option><option value="�����Ͽ콺">�����Ͽ콺</option><option value="�Կ���Ʃ����뿩">�Կ���Ʃ����뿩</option>
			</select>
		</div>
		<!-- filter ��ư  -->
		
		<!-- map ��ư  -->
		<div class="btnMap">
		<button  id="btnMap"  >���� on</button>
		</div>
		<!-- map ��ư  -->
		
		<!-- ���� ��ư  -->
		<div class="sort">
			<select class="selectSort">
				<option >����</option>
				<option value="���ݼ�">���ݼ�</option>
				<option value="�ֽż�">�ֽż�</option>
				<option value="��õ��">��õ��</option>
			</select>
		</div>
		<!-- ���� ��ư  -->

	</div>
	<div class="imgMap">
	<h1>�����Դϴ�</h1>
	</div>
	<div class="container searchResultList">
		<div class="item">
			<div class="mainImg">
				<a href="#" title="Lorem ipsum" class="thumbnail"><img src="http://lorempixel.com/250/140/people" alt="Lorem ipsum" /></a>
			</div>
			<div class="infoList">
				<ul class="meta-search">
					<li><i class="glyphicon glyphicon-calendar"></i> <span>02/15/2014</span></li>
					<li><i class="glyphicon glyphicon-time"></i> <span>4:28 pm</span></li>
					<li><i class="glyphicon glyphicon-tags"></i> <span>People</span></li>
					<h3>list1</h3>
				</ul>
			</div>
		
		</div>	
		<div class="item">
			<div class="mainImg">
				<a href="#" title="Lorem ipsum" class="thumbnail"><img src="http://lorempixel.com/250/140/people" alt="Lorem ipsum" /></a>
			</div>
			<div class="infoList">
				<ul class="meta-search">
					<li><i class="glyphicon glyphicon-calendar"></i> <span>02/15/2014</span></li>
					<li><i class="glyphicon glyphicon-time"></i> <span>4:28 pm</span></li>
					<li><i class="glyphicon glyphicon-tags"></i> <span>People</span></li>
					<h3>list2</h3>
				</ul>
			</div>
		
		</div>	
		<div class="item">
			<div class="mainImg">
				<a href="#" title="Lorem ipsum" class="thumbnail"><img src="http://lorempixel.com/250/140/people" alt="Lorem ipsum" /></a>
			</div>
			<div class="infoList">
				<ul class="meta-search">
					<li><i class="glyphicon glyphicon-calendar"></i> <span>02/15/2014</span></li>
					<li><i class="glyphicon glyphicon-time"></i> <span>4:28 pm</span></li>
					<li><i class="glyphicon glyphicon-tags"></i> <span>People</span></li>
					<h3>list3</h3>
				</ul>
			</div>
		
		</div>	
	
	</div>
	
</div>
</body>
</html>