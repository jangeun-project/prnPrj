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
			
			$( '#seachPeriod' ).change( function() {
				var selectedValue = $( this ).val();
				
				if ( selectedValue == "1D" ) {
					$( ':input[type=text][name=searchSt]' ).val( calDate( '${searchEt}', 0, 0, -1 ) );
				} else if ( selectedValue == "2D" ) {
					$( ':input[type=text][name=searchSt]' ).val( calDate( '${searchEt}', 0, 0, -2 ) );
				} else if ( selectedValue == "3D" ) {
					$( ':input[type=text][name=searchSt]' ).val( calDate( '${searchEt}', 0, 0, -3 ) );
				} else if ( selectedValue == "1W" ) {
					$( ':input[type=text][name=searchSt]' ).val( calDate( '${searchEt}', 0, 0, -7 ) );
				} else if ( selectedValue == "1M" ) {
					$( ':input[type=text][name=searchSt]' ).val( calDate( '${searchEt}', 0, -1, 0 ) );
				}

			} );
		});
		
		function search()
		{
			var frm = document.searchForm;
			frm.action = "/prnserver/LogList.do";
			frm.submit();
		}
		

		function Go(pageNo)
		{
			var frm = document.searchForm;
			frm.page.value = pageNo;
			frm.target = "_self";
			frm.action = "/prnserver/LogList.do";
			frm.submit();
		}
		
	</script>
</head>

<body>
<h1 class="hidden">Log List</h1>

<div id="wrapper"> 
	
    <%@ include file="/WEB-INF/jsp/TopMenu.jsp" %>
	<!-- CONTENTS START -->
	<div id="contents_box">
		<h3 class="h3_style">로그 정보</h3>

        <form id="searchForm" name="searchForm" method="post">
		<input type="hidden" name="page" value="">
		<input type="hidden" name="seq" value="">
		<input type="hidden" name="selectedId" id="selectedId" value="">
		
		<table class="t_st01" summary="">
		<colgroup>
			<col class="t_item" width="90">
			<col class="t_con"  width="90">
			<col class="t_con">
		</colgroup>
		<tr>
			<th>기간</th>
			<td>
			<select id="seachPeriod" name="seachPeriod">
				<option value="1D" <c:if test="${seachPeriod eq '1D'}">selected</c:if>>1일</option>
				<option value="2D" <c:if test="${seachPeriod eq '2D'}">selected</c:if>>2일</option>
				<option value="3D" <c:if test="${seachPeriod eq '3D'}">selected</c:if>>3일</option>
				<option value="1W" <c:if test="${seachPeriod eq '1W'}">selected</c:if>>1주일</option>
				<option value="1M" <c:if test="${seachPeriod eq '1M'}">selected</c:if>>1개월</option>
			</select>
			</td>	
			<td><input readonly="readonly" name="searchSt" id="searchSt" type="text" value="${searchSt}" style="width:80px;"/>
				                  ~<input name="searchEt" id="searchEt" type="text" value="${searchEt}" style="width:80px;" />
			</td>
		</tr>
		</table>
	
		<div class="btns"> 
			<span class="gray_btn"><a href="javascript:search();">조 회</a><span class="bg_left"></span><span class="bg_right"></span></span> 
		</div>
			
		<p class="board_num">총 <strong>${totalcnt}</strong>건이 있습니다</p>
		<table class="t_st04">
		<colgroup>
		    <col width="20%"/>
			<col width="10%"/>
			<col width="10%"/>
			<col width="10%"/>
			<col width="20%"/>
			<col width="10%"/>
			<col width="10%"/>
			<col width="10%"/>
		</colgroup>
		<thead>
		<tr>
			<th rowspan="2">설정시간</th>
			<th colspan="3">설정자</th>
			<th rowspan="2">권한</th>
			<th colspan="3">대상자</th>
		</tr>
		<tr>	
			<th>부서</th>
			<th>이름</th>
			<th>ID</th>
			<th>부서</th>
			<th>이름</th>
			<th>ID</th>
		</tr>

		</thead>
		<tbody>
		<c:forEach var="logList" items="${logList}" varStatus="status">
		<tr>
			<td><fmt:formatDate value="${logList.setTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
    		<td><c:out value="${logList.setDeName}"/></td>
			<td><strong><c:out value="${logList.setName}"/></strong></td>
			<td><c:out value="${logList.userName}"/></td>
			<td>
				<c:choose>
					<c:when test="${logList.grade == 2}">
						<span style="color:red;">승인자</span>
					</c:when>
					<c:when test="${logList.grade == 3}">
						<span style="color:blue;">승인이관자</span>
					</c:when>
					<c:when test="${logList.grade == 0}">
						슈퍼운영자
					</c:when>
					<c:otherwise>
						사용자
					</c:otherwise>
				</c:choose>
			</td>
			<td><c:out value="${logList.tagetDeName}"/></td>
			<td><strong><c:out value="${logList.tagetName}"/></strong></td>
			<td><c:out value="${logList.targetName}"/></td>
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