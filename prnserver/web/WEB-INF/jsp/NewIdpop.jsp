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

	function regNewId() {
		var userId = $("input[name='userId']").val();
		if (userId == '') {
			alert("아이디를 입력해주세요.");
			return;
		}
		
		if (userPw == '') {
			alert("비밀번호를 입력해주세요.");
			return;
		}
		
		if (userNm == '') {
			alert("이름를 입력해주세요.");
			return;
		}
		
		if (depart == '') {
			alert("부서명 입력해주세요.");
			return;
		}
		

		
		var frm = document.userSearchForm;
		//frm.target = "_self";
		frm.action = "/prnserver/InsertNewId.do";
		frm.submit();
	}

	function regUpdateId() {
		
		/*
		var userId = $("input[name='userId']").val();
		if (userId == '') {
			alert("아이디를 입력해주세요.");
			return;
		}
		
		
		var userId = $("input[name='userNm']").val();
		if (userNm == '') {
			alert("이름를 입력해주세요.");
			return;
		}
	
		var userId = $("input[name='userId']").val();
		if (userId == 'admin') {
			alert("관리자는 수정이 불가능 합니다.");
			return;
		}
		*/
		
		
		var frm = document.userSearchForm;
		//frm.target = "_self";
		frm.action = "/prnserver/UpdateId.do";
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
		<h3 class="h3_style">사용자 상세 정보</h3>

        <form id="userSearchForm" name="userSearchForm" method="post">
	
		<table class="t_st01" width="600" summary="">
		<colgroup>
			<col class="t_item" width="100">
			<col class="t_con">
		</colgroup>
		<tr>
			<th>아이디</th>
			<td><input name="userId" id="userId" value="${userId}" type="text" ${newUserYn == 'N' ? "readonly" : ""} /></td>
		</tr>
		<tr>
			<th>이름</th>
			<td><input name="userNm" id="userNm" value="${userNm}" type="text" readonly/></td>
		</tr>
		<tr>
			<th>부서명</th>
			<td><input name="depart" id="depart" value="${depart}" type="text" readonly/></td>
		</tr>
		<tr>
			<th>이메일</th>
			<td><input name="email" id="email" value="${email}" type="text" readonly/></td>
		</tr>
		<tr>
			<th>권한등급</th>
			<td><select name="grade" style="width:120px">
				<option value="1" <c:if test="${grade eq 1}">selected</c:if>>사용자</option>
				<option value="2" <c:if test="${grade eq 2}">selected</c:if>>승인자</option>
				<option value="3" <c:if test="${grade eq 3}">selected</c:if>>승인이관자</option>
				</select>
			</td>
		</tr>
		</table>
		
		<div class="btns">
		<span class="gray_btn">
			<c:if test="${newUserYn eq 'N'}" >
			<a href="javascript:regUpdateId();">수정</a><span class="bg_left"></span><span class="bg_right"></span>
			</c:if>
		</span>
		<span class="gray_btn">
			<a href="javascript:window.close();">닫기</a><span class="bg_left"></span><span class="bg_right"></span>
		</span>
		</div>
		</form>
		
</div>
</body>
</html>
