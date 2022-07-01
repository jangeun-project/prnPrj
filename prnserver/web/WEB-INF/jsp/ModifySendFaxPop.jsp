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

	function doModifySendFax() {
		var faxTitle = $("input[name='faxTitle']").val();
		if (faxTitle == '') {
			alert("발신제목를 입력해주세요.");
			return;
		}
		
		var receiveName = $("input[name='receiveName']").val();
		if (receiveName == '') {
			alert("수신처를 입력해주세요.");
			return;
		}
		
		var receiver = $("input[name='receiver']").val();
		if (receiver == '') {
			alert("수신자를 입력해주세요.");
			return;
		}
		
		var logId = $('#logId').val();
		var frm = document.chagePwForm;
		//frm.target = "_self";
		frm.action = "/prnserver/doModifySendFax.do?logId=" + logId;
		frm.submit();
	}

	
	function close_popup(memInfo) {
		window.returnValue = memInfo; 
		self.close();
	}
	
</script>

<div id="detail_wrapper"> 
  		<br/>
		<h3 class="h3_style">발송 팩스 수정</h3>

        <form id="chagePwForm" name="chagePwForm" method="post">
		<input type="hidden" id="logId" name="logId" value="${logId}"/>
		
		<table class="t_st01" width="600" summary="">
		<colgroup>
			<col class="t_item" width="100">
			<col class="t_con">
		</colgroup>
		<tr>
			<th>발신제목</th>
			<td><input type="text" id="faxTitle" name="faxTitle" value="${faxTitle}" size="40"/></td>
		</tr>
		<tr>
			<th>수신처</th>
			<td><input type="text" id="receiveName" name="receiveName" value="${receiveName}" size="40"/></td>
		</tr>
		<tr>
			<th>수신자</th>
			<td><input type="text" id="receiver" name="receiver" value="${receiver}" size="40"/></td>
		</tr>
		</table>
		
		<div class="btns">
		<span class="gray_btn">
			<a href="javascript:doModifySendFax();">저장</a><span class="bg_left"></span><span class="bg_right"></span>
		</span>
		<span class="gray_btn">
			<a href="javascript:window.close();">닫기</a><span class="bg_left"></span><span class="bg_right"></span>
		</span>
		</div>
		</form>
		
</div>
</body>
</html>
