<%@page language="java" contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
<title>Document Manager</title>


<%@ include file="/WEB-INF/jsp/common/lefthead.jsp" %>

 
<script type="text/javascript" language="javascript">

</script>
</head>

<body id="bootstrap-overrides">
<h1 class="hidden">Main List</h1>
<div id="wrapper"> 
 <!-- %@ include file="/WEB-INF/jsp/TopMenu.jsp" % -->
 
 
 	<div class="layout__body">
 	<div class="layout__main-sidebar">
 		
 		<div class="sidebar">
 		<div class="sidebar__logo-panel">
             <a href="http://211.196.190.93:81/">
			  <img src="/prnserver/imagesL/logo.svg" class="sidebar__logo">
             </a>
        </div>
        <ul class="nav nav-sidebar">
		<li>
			<a href="http://211.196.190.93:81/web/Dashboard" id="menu-dashboard">
				<span class="menu__icon actions--icon fa fa-fw fa-th-large" aria-hidden="true"></span>
				<span class="menu__label">Dashboard</span>
				</a>
		</li>
		<li class="active">
			<a href="http://211.196.190.93:81/web/JobSearch" id="menu-reports">
				<span class="menu__icon actions--icon fa fa-fw fa-bar-chart" aria-hidden="true"></span>
				<span class="menu__label">Reports</span>
				<span class="sr-only">(current)</span></a>
		</li>
		<li>
			<a href="http://211.196.190.93:81/devices/" id="menu-devices">
				<span class="menu__icon actions--icon fa fa-fw fa-print" aria-hidden="true"></span>
				<span class="menu__label">Devices</span>
				</a>
		</li>
		<li>
			<a href="http://211.196.190.93:81/web/BillingCodes" id="menu-billing">
				<span class="menu__icon actions--icon fa fa-fw fa-tags" aria-hidden="true"></span>
				<span class="menu__label">Billing</span>
				</a>
		</li>
		</ul>
						
						
		<div class="layout__main"> 
		  <div class="layout__main-panel layout__main-header" id="layout-header">
			<nav class="navbar navbar-default navbar-default--ysoft">
			  <div class="container-fluid container-fluid--header">
				<div class="navbar-header pull-sm-left">
					<span class="fa fa-exclamation-circle navbar__javascript-error hidden--soft" id="page-error-warning" onmouseover="overlib(&quot;There was an error on the page. Please contact customer support.&quot;)" onmouseout="nd()"></span>
				</div>
		      </div>
			</nav>
			
			<div class="navbar-header pull-sm-left layout__main-navbar-header" id="navbar-header" style="width: 700px;">
				<button type="button" class="navbar-toggler hidden-sm-up pull-xs-right navbar-toggle--ysoft" data-toggle="collapse" data-target="#navbar-right-collapse">
					<span class="sr-only">Toggle navigation</span>
				</button>
				<div class="js--dropdown-notifications dropdown header__notifications header__notifications--small hidden-sm-up" id="dropdown-notifications-small">
					<a class="dropdown-toggle dropdown-toggle__no-caret dropdown-toggle__notifications dropdown-toggle__notifications--small" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
						<span class="fa fa-bell"></span>
					</a>

					<span class="label label-pill notifications-icon__badge js--notifications-unread-count" id="small-notifications-unread-count"></span>

					<div class="dropdown-menu dropdown-menu-right dropdown-menu--no-hover notifications" role="menu">
						<div class="dropdown-item">
							<div class="notifications--item__heading">Notifications</div>
							<div class="js--notifications-bar" id="small-notifications-bar"></div>
						</div>
					</div>
				</div>
				<ol class="breadcrumb">
					<li class="breadcrumb__item hidden-sm-down"><a href="http://211.196.190.93:81/web/JobSearch">Reports</a></li>
					<li class="breadcrumb__item active">Job list</li>
				</ol>
			</div>
			
			<div class="collapse navbar-toggleable-xs mobile-navigation pull-sm-right" id="navbar-right-collapse" style="max-height: 623px;">
				<ul class="nav navbar-nav">

					<li class="nav-item dropdown">
						<a href="http://211.196.190.93:81/web/JobSearch#" class="nav-link dropdown-toggle dropdown-toggle__no-caret" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" id="switch-language">
							<span class="flag-icon flag-icon-us flag-icon__current-language"></span>
							<span class="language-switch__current-language hidden-sm-up"> English</span>
						</a>
						<div class="dropdown-menu dropdown-menu-right dropdown-menu--header dropdown-menu__scrollable">
						  <a class="dropdown-item" href="http://211.196.190.93:81/web/JobSearch?lang=tr_TR" id="action--switch-language-tr_TR">
    						<span class="flag-icon flag-icon-tr flag-icon__language-selection"></span>Turkce</a>			
						</div>
					</li>

			
			
			
		  </div>
		</div>						
              
        <!--       
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
	 --> 
	
	
		</div>
	 </div>
	</div>
	
	
	
  <!-- %@ include file="/WEB-INF/jsp/common/Footer.jsp" %-->
</div>  
</body>
</html>