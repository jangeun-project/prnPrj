<%@page language="java" contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
<title>Konica Minolta Document Manager</title>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
	<script type="text/javascript" language="javascript">
	
		$(document).ready(function(){
			$("#searchSt").css("margin-right", "4px").datepicker({buttonText:'날짜선택', dateFormat:'yy-mm-dd', showOn: 'button', buttonImage:'/prnserver/images/ion_cal.gif', buttonImageOnly:true});
			$("#searchEt").css("margin-right", "4px").datepicker({buttonText:'날짜선택', dateFormat:'yy-mm-dd', showOn: 'button', buttonImage:'/prnserver/images/ion_cal.gif', buttonImageOnly:true});
		});
		
		function search()
		{
			var frm = document.searchForm;
			frm.action = "/prnserver/MemberList.do";
			frm.submit();
		}
		
		function deleteId(obj) {
			var frm = document.searchForm;
			
			$("#searchForm input[name='selectedId']").val(obj);
			
			frm.action = "/prnserver/DeleteId.do";
			frm.submit();
		}
		
		function modifyId(obj) {
			
			var audit_trail_win = window.open("/prnserver/ModifyIdPop.do?userId="+obj , "popup","toolbar=no, location=no, status=yes, scrollbars=no, width=500px, height=400px, left=200px, top=200px");
			audit_trail_win.focus();
		}
		
		function insertId() {
			var audit_trail_win = window.open("/prnserver/NewIdpop.do" , "popup","toolbar=no, location=no, status=yes, scrollbars=no, width=500px, height=400px, left=200px, top=200px");
			audit_trail_win.focus();
		}
		
		function Go(pageNo)
		{
			var frm = document.searchForm;
			frm.page.value = pageNo;
			frm.target = "_self";
			frm.action = "/prnserver/MemberList.do";
			frm.submit();
		}
		
	</script>
</head>

<body>
<h1 class="hidden">Printed Manager</h1>

<div id="wrapper"> 
	
    <%@ include file="/WEB-INF/jsp/TopMenu.jsp" %>
	<!-- CONTENTS START -->
	<div id="contents_box">
		<h3 class="h3_style">사용자 정보</h3>

        <form id="searchForm" name="searchForm" method="post">
		<input type="hidden" name="page" value="">
		<input type="hidden" name="seq" value="">
		<input type="hidden" name="selectedId" id="selectedId" value="">
		
		<table class="t_st01" summary="">
		<colgroup>
			<col class="t_item" width="60">
			<col class="t_con"  width="50">
			<col class="t_item" width="60">
			<col class="t_con"  width="50">
			<col class="t_item" width="60">
			<col class="t_con"  width="50">
			<col class="t_item" width="60">
			<col class="t_con"  width="50">
		</colgroup>
		<tr>
			<th>ID</th>
			<td><input type="text" id="searchId" name="searchId" value="${searchId}"/></td>	
			<th>이름</th>
			<td><input type="text" id="searchName" name="searchName" value="${searchName}" /></td>
			<th>부서명</th>
			<td><input type="text" id="searchDept" name="searchDept" value="${searchDept}" /></td>
			<th>권한등급</th>
			<td><select name="searchGrade" id="searchGrade" style="width:120px">
				<option value="" <c:if test="${searchGrade eq 0}">selected</c:if>>-전체-</option>
				<option value="1" <c:if test="${searchGrade eq 1}">selected</c:if>>사용자</option>
				<option value="2" <c:if test="${searchGrade eq 2}">selected</c:if>>승인자</option>
				<option value="3" <c:if test="${searchGrade eq 3}">selected</c:if>>승인이관자</option>
				</select>
			</td>
		</tr>
		</table>
	
		<div class="btns"> 
			<span class="gray_btn"><a href="javascript:search();">조 회</a><span class="bg_left"></span><span class="bg_right"></span></span> 
		</div>
			
		<p class="board_num">총 <strong>${totalcnt}</strong>건이 있습니다</p>
		<table class="t_st02" summary="">
		<colgroup>
		    <col width="10%"/>
			<col width="10%"/>
			<col width="*"/>
			<col width="20%"/>
			<col width="20%"/>
			<col width="10%"/>
		</colgroup>
		<thead>
		<tr>
			<th>번호</th>
			<th>ID</th>
			<th>이름</th>
			<th>부서명</th>
			<th>이메일</th>
			<th>권한등급</th>
			<th></th>
		</tr>

		</thead>
		<tbody>
		<c:forEach var="memberList" items="${memberList}" varStatus="status">
		<tr>
			<td><c:out value="${status.count}"/></td>
    		<td><c:out value="${memberList.loginId}"/></td>
			<td><c:out value="${memberList.userName}"/></td>
			<td><c:out value="${memberList.deptName}"/></td>
			<td><c:out value="${memberList.email}"/></td>
			<td>
				<c:choose>
					<c:when test="${memberList.grade == 2}">
						<span style="color:red;">승인자</span>
					</c:when>
					<c:when test="${memberList.grade == 3}">
						<span style="color:blue;">승인이관자</span>
					</c:when>
					<c:when test="${memberList.grade == 0}">
						슈퍼운영자
					</c:when>
					<c:otherwise>
						사용자
					</c:otherwise>
				</c:choose>
			</td>
			<td><span class="gray_btn"><a href="javascript:modifyId('${memberList.loginId}');">수정</a><span class="bg_left"></span><span class="bg_right"></span>
			</td>
		</tr>
		</c:forEach>
		</tbody>
		</table>
		
		</form>
	</div>
	
	<div class="paging_box">
		<%@ include file = "/WEB-INF/jsp/pagging.jsp" %>
	</div>
		

	<%@ include file="/WEB-INF/jsp/common/Footer.jsp" %>
</div>
</body>
</html>