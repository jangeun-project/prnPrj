<%@page language="java" contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link href="/prnserver/css/liststyle.css" rel="stylesheet" type="text/css" />
	<link href="/prnserver/css/custom-theme/jquery-ui-1.8.10.custom.css" rel="stylesheet" type="text/css" />
	<base target="_self"></base>
</head>

<body>
	<script src="/prnserver/js/jquery-1.5.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="/prnserver/js/jquery-ui-1.8.10.custom.min.js" type="text/javascript" charset="utf-8"></script>
<script lang="javascript">

	function doChangePw() {
		var currentPassword = $("input[name='currentPassword']").val();
		if (currentPassword == '') {
			alert("현재 비밀번호를 입력해주세요.");
			return;
		}
		
		var password = $("input[name='password']").val();
		if (password == '') {
			alert("새로운 비밀번호를 입력해주세요.");
			return;
		}
		
		var confirmPassword = $("input[name='confirmPassword']").val();
		if (confirmPassword == '') {
			alert("새로운 비밀번호를 다시 입력해 주세요.");
			return;
		}
		
		if (password != confirmPassword) {
			alert("비밀번호 확인를 다시 입력해 주세요.");
			return;
		}
		
		var frm = document.chagePwForm;
		//frm.target = "_self";
		frm.action = "/prnserver/doChangePw.do";
		frm.submit();
	}

	
	function close_popup(memInfo) {

		alert(memInfo);
		window.returnValue = memInfo; 
		self.close();

	}
	
</script>

<div id="detail_wrapper"> 
  		<br/>
		<h3 class="h3_style">비밀번호 변경</h3>

        <form id="chagePwForm" name="chagePwForm" method="post">
		<input type="hidden" id="userid" name="userid" value="${userid}"/>
		
		<table class="t_st01" width="600" summary="">
		<colgroup>
			<col class="t_item" width="100">
			<col class="t_con">
		</colgroup>
		<tr>
			<th>현재비밀번호</th>
			<td><input type="password" id="currentPassword" name="currentPassword" /></td>
		</tr>
		<tr>
			<th>변경할 비밀번호</th>
			<td><input type="password" id="password" name="password" /></td>
		</tr>
		<tr>
			<th>비밀번호확인</th>
			<td><input type="password" id="confirmPassword" name="confirmPassword" /></td>
		</tr>
		</table>
		
		<div class="btns">
		<span class="gray_btn">
			<a href="javascript:doChangePw();">변경</a><span class="bg_left"></span><span class="bg_right"></span>
		</span>
		<span class="gray_btn">
			<a href="javascript:window.close();">닫기</a><span class="bg_left"></span><span class="bg_right"></span>
		</span>
		</div>
		</form>
		
</div>
</body>
</html>
