<%@page language="java" contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
<title>Document Manager</title>


<%@ include file="/WEB-INF/jsp/common/head.jsp" %>

 
<script type="text/javascript" language="javascript">

</script>
</head>

<body id="bootstrap-overrides">
<h1 class="hidden">Main List</h1>
<div id="wrapper"> 
 <%@ include file="/WEB-INF/jsp/TopMenu.jsp" %>
 	
		<div class="main_item_row1">
				<div class="main_item1">
					<div class="main_item_name"><a href="${pageContext.request.contextPath}/ScanList.do"><span>스캔리스트</span></a></div>
					<table class="m_tbl main_tbl_info">
						<thead>
							<tr>
								<th>스캔일시</th>
								<th>사용자명</th>
								<th>스캔장수</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${empty scanList}">
									<tr>
										<td colspan="3">검색된 결과가 없습니다.</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="scanList" items="${scanList}">
										<tr>
											<td><fmt:formatDate value="${scanList.usageTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td>${scanList.userName}</td>
											<td>${scanList.pageCount}</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
		
			<div class="main_item2">
				<div class="main_item_name"><a href="${pageContext.request.contextPath}/SendFaxList.do"><span>발신팩스</span></a></div>
				<table class="m_tbl main_tbl_info">
					<thead>
						<tr>
							<th>발신일시</th>
							<th>사용자명</th>
							<th>발신번호</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${empty sendList}">
								<tr>
									<td colspan="3">검색된 결과가 없습니다.</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="sendList" items="${sendList}">
									<tr>
										<td><fmt:formatDate value="${sendList.usageTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<td>${sendList.userName}</td>
										<td>${sendList.faxNumber}</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
		
			<div class="main_item1">
				<div class="main_item_name"><a href="${pageContext.request.contextPath}/ReceiveFaxList.do"><span>수신팩스</span></a></div>
				<table class="m_tbl main_tbl_info">
					<thead>
						<tr>
							<th>수신일시</th>
							<th>팩스번호</th>
							<th>수신장수</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${empty receiveList}">
								<tr>
									<td colspan="3">검색된 결과가 없습니다.</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="receiveList" items="${receiveList}">
									<tr>
										<td><fmt:formatDate value="${receiveList.usageTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<td>${receiveList.faxNumber}</td>
										<td>${receiveList.pageCount}</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
		
			<c:if test="${sessionGd eq '2' || sessionGd eq '3' || sessionGd eq '9'}">
			<div class="main_item2">
				<div class="main_item_name"><a href="${pageContext.request.contextPath}/ConfirmFaxList.do"><span>팩스승인</span></a></div>
				<table class="m_tbl main_tbl_info">
					<thead>
						<tr>
							<th>발신일시</th>
							<th>사용자명</th>
							<th>발신번호</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${empty ConfirmList}">
								<tr>
									<td colspan="3">검색된 결과가 없습니다.</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="ConfirmList" items="${ConfirmList}">
									<tr>
										<td><fmt:formatDate value="${ConfirmList.usageTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<td>${ConfirmList.userName}</td>
										<td>${ConfirmList.faxNumber}</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			</c:if>
		</div>
	
		</div>
	 </div>
	</div>
	
	
	
  <%@ include file="/WEB-INF/jsp/common/Footer.jsp" %>
</div>  
</body>
</html>