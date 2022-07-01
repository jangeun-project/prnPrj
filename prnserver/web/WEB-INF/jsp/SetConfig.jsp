<%@page language="java" contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
<title>PRINTED MANAGER</title>
<%@ include file="/WEB-INF/jsp/common/head.jsp" %>
	<script type="text/javascript" language="javascript">
	
		$(document).ready(function(){
		});
		
		function saveSet(obj) {
			var frm = document.searchForm;
			
			var setday = $("input[name='setday']").val();
			if (setday == '') {
				alert("조회기간를 입력해주세요.");
				return;
			}
		
			frm.action = "/prnserver/SaveSetConfig.do";
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
		<h3 class="h3_style">설정</h3>

        <form id="searchForm" name="searchForm" method="post">
		<input type="hidden" name="page" value="">
		<input type="hidden" name="seq" value="">
		<input type="hidden" name="selectedId" id="selectedId" value="">
			
		<table class="t_st01" summary="">
		<colgroup>
		    <col class="t_item" width="20%"/>
			<col class="t_con" width="80%"/>
		</colgroup>
		<thead>
		<tr>
			<th>조회기간</th>
			<td>
				<input name="setday" id="setday" value="${setDay}" size="3" type="text" /> 일
				
			</td>
			<!-- 
			<td colspan="3"><select name="setday" style="width:120px">
				<option value="1" <c:if test="${setDay == 1}">selected</c:if>>1일</option>
				<option value="2" <c:if test="${setDay == 2}">selected</c:if>>2일</option>
				<option value="3" <c:if test="${setDay == 3}">selected</c:if>>3일</option>
				<option value="4" <c:if test="${setDay == 4}">selected</c:if>>4일</option>
				</select>
			</td>
			-->
		</tr>
		</table>
		
		<div class="btns"> 
			<span class="gray_btn"><a href="javascript:saveSet();">저 장</a><span class="bg_left"></span><span class="bg_right"></span></span> 
		</div>
		
		</form>
		
		 
		<div class="paging_box">
		
		<!-- %@ include file = "/WEB-INF/jsp/pagging.jsp" % -->
		
		</div>
		 
	</div>

	<%@ include file="/WEB-INF/jsp/common/Footer.jsp" %>
</div>
</body>
</html>