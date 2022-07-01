<%@page language="java" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<link rel="stylesheet" type="text/css" href="/prnserver/css/login.css" />

<head>
<title>Login Page</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">

	
	<script type="text/javascript">
	$(document).ready(function()
	{
		<c:if test="${loginId != null}">
		$( ':input[type=text][name=userid]' ).val( '${userid}' );
		$( ':input[type=checkbox][name=saveLoginId]' ).attr( 'checked', true );
		</c:if>
	
		$( '.login_box > input' ).each( function() {		
			if ( $( this ).val().length > 0 ) {
				$( this ).prev().addClass( 'blind' );
			}
		} );
	
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
	
	$( '.login_box > input' ).focusin( function() {
		$( this ).prev().addClass( 'blind' );
	} );

	$( '.login_box > input' ).focusout( function() {	
		if ( $( this ).val().length < 1 )
			$( this ).prev().removeClass();
	} );
	</script>
	
</head>
<body>

<div class="login_zone">
	<form id="frmLogin" method="post" action="/prnserver/enterlogin.do">
		<fieldset>
			<div class="login_box">
				<label for="loginId" >아이디</label>
				<input type="text" id="userid" class="tx_id" name="userid" />
				<label for="password">비밀번호</label>
				<input type="password" id="userpw" class="tx_pw" name="userpw" />
			</div>
			<div class="login_opt">
				<div class="opt1">
					<input type="checkbox" name="saveLoginId" value="true" /><span>아이디저장</span>
				</div>
			</div>
			<div class="btn_box">
				<button type="submit">로그인</button>
			</div>
		</fieldset>
	</form>
</div>
	
</body>
</html>