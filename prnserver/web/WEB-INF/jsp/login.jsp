<%@page language="java" contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Login Page</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
 	
	<link rel="icon" type="image/png" href="images/icons/favicon.ico"/>

	<link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">

	<link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">

	<link rel="stylesheet" type="text/css" href="fonts/iconic/css/material-design-iconic-font.min.css">

	<link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
	
	<link rel="stylesheet" type="text/css" href="vendor/css-hamburgers/hamburgers.min.css">

	<link rel="stylesheet" type="text/css" href="vendor/animsition/css/animsition.min.css">

	<link rel="stylesheet" type="text/css" href="vendor/select2/select2.min.css">
	
	<link rel="stylesheet" type="text/css" href="vendor/daterangepicker/daterangepicker.css">

	<link rel="stylesheet" type="text/css" href="css/util.css">
	<link rel="stylesheet" type="text/css" href="css/main.css">

	<script type="text/javascript" src="/prnserver/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function()
	{
		$("#frmLogin").bind("submit", function()
		{
			if ($(this.userid).val() == "")
			{
				alert('ID를 입력하세요.');
				$(this.userid).focus();
				return false;
			}
			if (($(this.userpw).val() == ""))
			{
				alert('비밀번호를  입력하세요.');
				$(this.userpw).focus();
				return false;
			}	
		})
		
		$("#frmLogin input").each(function()
		{

		});

		$("#frmLogin input[name='userid']").val() == "" ? $("#frmLogin input[name='userid']").focus() : $("#frmLogin input[name='userpw']").focus();

	});
	</script>
	
</head>
<body>

	<form id="frmLogin" method="post" action="/prnserver/enterlogin.do">
	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100 p-t-85 p-b-20">
				<form class="login100-form validate-form">

					<span class="login100-form-avatar">
 
 <!-- 테스트시 삭제 -->
						<img src="/prnserver/images/login2.jpg" alt="">
	
	
					</span>
			 
					<div class="wrap-input100 validate-input m-t-85 m-b-35" data-validate = "Enter username">
						<input class="input100" type="text" name="userid" id="userid">
						<span class="focus-input100" data-placeholder="ID"></span>
					</div>

					<div class="wrap-input100 validate-input m-b-50" data-validate="Enter password">
						<input class="input100" type="password" name="userpw" id="userpw">
						<span class="focus-input100" data-placeholder="Password"></span>
					</div>
					
					<div class="container-login100-form-btn">
						<button class="login100-form-btn">
							Login
						</button>
					</div>
					
					<div class="input100 validate-input m-t-30 m-b-0">
						<div class="input100">
						<input type="checkbox" name="savedId" value="true" /><span>아이디저장</span>
						</div>
					</div>
		
				</form>
			</div>
		</div>
	</div>
	</form>
 
	<div id="dropDownSelect1"></div>
	

	<script src="vendor/jquery/jquery-3.2.1.min.js"></script>

	<script src="vendor/animsition/js/animsition.min.js"></script>

	<script src="vendor/bootstrap/js/popper.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.min.js"></script>

	<script src="vendor/select2/select2.min.js"></script>

	<script src="vendor/daterangepicker/moment.min.js"></script>
	<script src="vendor/daterangepicker/daterangepicker.js"></script>

	<script src="vendor/countdowntime/countdowntime.js"></script>
 
	<script src="js/main.js"></script>

	
</body>
</html>