<meta http-equiv="Content-Type" content="text/html; charset=utf-8"> 
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.net.URLEncoder,java.util.*,java.text.*" %>
<%
	Date currentDate = new Date(); 
    SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
	String toDay = formatter.format(currentDate);
    
	response.setHeader("Content-Type", "application/vnd.ms-excel; charset=utf-8"); 

	response.setHeader("Content-Disposition", "attachment;fileName=SendFaxList_" + toDay + ".xls" );
	response.setHeader("Content-Description", "JSP Generated Data"); 
	

	
	response.setHeader("Content-Transfer-Encoding", "binary");
	response.setHeader("Pragma", "no-cache;");
	response.setHeader("Expires", "-1;");

%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>

<body>

<h1 class="hidden">상용 FAX 발송기록부</h1>

<div id="wrapper"> 

	<div id="contents_box">
	
		<table border="1" summary="">
		<colgroup>
			<col width="50"/>
			<col width="120"/>
			<col width="100"/>
			<col width="150"/>
			
			<col width="100"/>
			<col width="80"/>
			<col width="80"/>
			
			<col width="100"/>
			<col width="80"/>
			<col width="80"/>
			
			<col width="80"/>
			
		</colgroup>
		<thead>
		<tr>
			<td  style="text-align:center" rowspan="2">연번</td>
			<td  style="text-align:center" rowspan="2">일시</td>
			<td  style="text-align:center" rowspan="2">문서번호</td>
			<td  style="text-align:center" rowspan="2">제목</td>
			
			<td  style="text-align:center" colspan="4">송신</td>
			<td  style="text-align:center" colspan="4">수신</td>
			
			<td  style="text-align:center" rowspan="2">원안인수자</td>
		</tr>
		<tr>	
			<td  style="text-align:center">기안부서</td>
			<td  style="text-align:center">매수</td>
			<td  style="text-align:center">송신자</td>
			<td  style="text-align:center">송신번호</td>
			
			<td  style="text-align:center">수신부서</td>
			<td  style="text-align:center">참조부서</td>
			<td  style="text-align:center">수신자</td>
			<td  style="text-align:center">수신번호</td>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="excelList" items="${excelList}">
		<tr>
			<td style="text-align:center"><c:out value="${excelList.ROWNO}"/></td>
			<td style="text-align:center"><c:out value="${excelList.usageTime}"/></td>
			<td style="mso-number-format:'\@';">${excelList.docuNum}</td>
			<td style="text-align:center"><c:out value="${excelList.faxTitle}"/></td>
			
			<td style="text-align:center"><c:out value="${excelList.fullDeptName}"/></td>
			<td style="text-align:center"><c:out value="${excelList.pageCount}"/></td>
			<td style="text-align:center"><c:out value="${excelList.userName}"/></td>
			<td style="mso-number-format:'\@';"><c:out value="${excelList.faxNumber}"/></td>
			
			<td style="text-align:center"><c:out value="${excelList.receiveName}"/></td>
			<td style="text-align:center"><c:out value="${excelList.referName}"/></td>
			<td style="text-align:center"><c:out value="${excelList.receiver}"/></td>
			<td style="mso-number-format:'\@';"><c:out value="${excelList.TXNumber}"/></td>
			
			<td style="text-align:center"><c:out value="${excelList.confirmName}"/></td>
		</tr>
		</c:forEach>
		</tbody>
		</table>
	</div>

</div>
</body>
</html>
