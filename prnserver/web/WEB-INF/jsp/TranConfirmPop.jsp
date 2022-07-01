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

	function search(){
		var frm = document.chagePwForm;
		frm.target = "_self";
		frm.action = "/prnserver/trnsConfirmPop.do";
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
		<h3 class="h3_style">승인자 이관</h3>

        <form id="chagePwForm" name="chagePwForm" method="post">
			<input type="hidden" id="userId" name="userId" value="${userId}"/>
			<input type="hidden" id="userDp" name="userDp" value="${userDp}"/>
			
			<table class="t_st01" summary="">
				<colgroup>
				    <col class="t_item" width="30%"/>
				    <col class="t_con" width="50%"/>
				    <col class="t_con" width="20%"/>
				</colgroup>   
				<tbody>
				<tr>
					<th>이름</th>
					<td><input name="searchName" id="searchName" type="text" class="txt"></input></td>
					<td>
						<span class="tgray_btn">
						<a href="javascript:search();">검 색</a><span class="bg_left"></span><span class="bg_right"></span>
						</span>
					</td>
				</tr>
				</tbody>	
			</table> 
			
			<table class="t_st02" summary="">
			<colgroup>
			    <col width="20%"/>
				<col width="20%"/>
				<col width="20%"/>
				<col width="20%"/>
				<col width="20%"/>
			</colgroup>
			<thead>
			<tr>
				<th>부서명</th>
				<th>ID</th>
				<th>이름</th>
				<th>권한</th>
				<th></th>
			</tr>
			<tbody>
				<c:forEach var="memberList" items="${memberList}" varStatus="status">
				<tr>
					<td><c:out value="${memberList.deptName}"/></td>
		    		<td><c:out value="${memberList.loginId}"/></td>
					<td><c:out value="${memberList.userName}"/></td>
					<td><c:choose>
						<c:when test="${memberList.grade == 2}">
							<span style="color:red;">승인자</span>
						</c:when>
						<c:when test="${memberList.grade == 3}">
							<span style="color:blue;">승인이관자</span>
						</c:when>
						<c:otherwise>
							사용자
						</c:otherwise>
					</c:choose></td>
					<td style="text-align:center">
						<c:if test="${memberList.grade != 2}">
						<a href="${pageContext.request.contextPath}/doChangeConfirm.do?logId=${memberList.loginId}&status=${memberList.grade}" class="tb_btn01" style="color:red;">권한변경</a>
						</c:if>
					</td>
				</tr>
				</c:forEach>
				</tbody>
			</table>
			
			<div class="btns">
			<span class="gray_btn">
				<a href="javascript:window.close();">닫기</a><span class="bg_left"></span><span class="bg_right"></span>
			</span>
			</div>
		</form>
		
</div>
</body>
</html>
