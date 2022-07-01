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
					$( ':input[type=text][name=searchSt]' ).val( calDate( '${searchEt}', 0, 0, 0 ) );
				} else if ( selectedValue == "2D" ) {
					$( ':input[type=text][name=searchSt]' ).val( calDate( '${searchEt}', 0, 0, -1 ) );
				} else if ( selectedValue == "3D" ) {
					$( ':input[type=text][name=searchSt]' ).val( calDate( '${searchEt}', 0, 0, -2 ) );
				} else if ( selectedValue == "1W" ) {
					$( ':input[type=text][name=searchSt]' ).val( calDate( '${searchEt}', 0, 0, -7 ) );
				} else if ( selectedValue == "1M" ) {
					$( ':input[type=text][name=searchSt]' ).val( calDate( '${searchEt}', 0, -1, 0 ) );
				}

			} );
			
			if ('${pageType}' == "list" ) {
				$("input[name='pageType']").val('${pageType}');
				$( '.t_st02' ).show();$( '.t_thumb' ).hide();
			}else{
				$("input[name='pageType']").val('${pageType}');
				$( '.t_st02' ).hide();$( '.t_thumb' ).show();
			};
		});
		
		function itemClick(obj){
			var row = $('#item_' + obj);
			
			$('#t_st02 .item').css('background-color','#FFFFFF');
			if($(row).next('.logcontent').css('display')=='table-row'){
				$(row).next('.logcontent').hide();
				$(row).css('background-color','#FFFFFF');
			}else{
				$('.logcontent').hide(); 
				$(row).css('background-color','#F6F6F6');
				$(row).next('.logcontent').show();
			}
		}
		
		function search()
		{
			var frm = document.searchForm;
			frm.action = "/prnserver/ReceiveFaxList.do";
			frm.submit();
		}
		
		function Go(pageNo)
		{
			var frm = document.searchForm;
			frm.page.value = pageNo;
			frm.target = "_self";
			frm.action = "/prnserver/ReceiveFaxList.do";
			frm.submit();
		}
		
		
		function goDetail(seq)
		{
			var frm = document.searchForm;
			frm.page.value = "${paging.page}";
			frm.SEQ.value = seq;
			frm.action = "/prnserver/ReceiveFaxList.do";
			frm.submit();
		}
		
		function setPageType(obj) {
			$("input[name='pageType']").val(obj);
		}
		
		function goDownPDF( logId ) {
			$_( 'downloadPdf' ).params( { logId: logId } ).go();
		}
		
		//다음 이벤트
		function page_next(logId, imagePath, count){
			
			if($('#viewPg'+logId).val()==''){
				$('#viewPg'+logId).val(0);
			}
			
			var currntPg = $('#viewPg'+logId).val();
			if (parseInt(currntPg) < parseInt(count)) {
				var imageId= parseInt($('#viewPg'+logId).val())+parseInt(1);
				$('#viewPg'+logId).val(imageId);
				viewImage($('#viewPg'+logId).val(),logId,imagePath);
			}
		}
		
		//이전 이벤트
		function page_prev(logId, imagePath, count){
			if($('#viewPg'+logId).val()==''){
					$('#viewPg'+logId).val(2);
			}
			
			var currntPg = $('#viewPg'+logId).val();
			if (currntPg > 1) {
				var imageId= parseInt($('#viewPg'+logId).val())-parseInt(1);
				$('#viewPg'+logId).val(imageId);
				viewImage($('#viewPg'+logId).val(),logId,imagePath);
			}
		}
		
		function viewImage(imageId,logId, imagePath ) {	
			if( imageId ==0){
				alert('페이지 없습니다.');
			$('#viewPg'+logId).val('');
			}else{
				$('#logview_cont'+logId).attr('src','${pageContext.request.contextPath}/viewImage.do?imagePath='+imagePath+'&imageId='+imageId);
				
			}
			if("${log.pageCount}"==imageId){
			}
		}
	</script>
</head>

<body>
<h1 class="hidden">ReceiveFax List</h1>

<div id="wrapper"> 
	
    <%@ include file="/WEB-INF/jsp/TopMenu.jsp" %>
	<!-- CONTENTS START -->
	<div id="contents_box">
		<h3 class="h3_style">수신팩스 리스트</h3>

        <form id="searchForm" name="searchForm" method="post">
		<input type="hidden" name="page" value="">
		<input type="hidden" name="pageType" value="">

<c:if test="${sessionGd eq '9'}">				
		<table class="t_st01" summary="">
		<colgroup>
			<col class="t_item"  width="90">
			<col class="t_con" width="90">
			<col class="t_con" >
			<col class="t_item" width="90">
			<col class="t_con"  width="90">
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
			
			<th>수신팩스번호</th>
			<td><input type="text" id="seachNumber" name="seachNumber" value="${seachNumber}"/></td>
		</tr>
		</table>
	
		<div class="btns"> 
			<span class="gray_btn"><a href="javascript:search();">조 회</a><span class="bg_left"></span><span class="bg_right"></span></span> 
		</div>
</c:if>
	
		<table width="100%">		
			<colgroup>
				<col width="*"/>
				<col width="7%"/>
				<col width="7%"/>
			</colgroup>
			<thead>
				<tr>
					<td align="left"><p class="board_num">총 <strong>${totalcnt}</strong>건이 있습니다</p></td>
					<td align="right" style="text-align:center">
						<span class="audit" onclick="$( '.t_st02' ).show();$( '.t_thumb' ).hide();setPageType('list');" style="text-decoration:underline;color:#E35E1F;">리스트보기</span>
					</td>
					<td align="right" style="text-align:center">	
						<span class="audit" onclick="$( '.t_st02' ).hide();$( '.t_thumb' ).show();setPageType('image');" style="text-decoration:underline;color:#E35E1F;">이미지보기</span>
					</td>
				</tr>
			</thead>
		</table>
	
		<table class="t_st02">
		<colgroup>
			<col width="180px" />
			<col width="290px" />
			<col width="160px" />
			<col width="120px" />
			<col width="140px" />
			<col width="90px" />
		</colgroup>
		<thead>
		<tr>
			<th>수신일시</th>
			<th>팩스번호</th>
			<th>모델명</th>
			<th>수신장수</th>
			<th>용량</th>
			<th>사본</th>
		</tr>

		</thead>
		<tbody>
		<c:choose>
	 		<c:when test="${empty list}">
	 			<tr>
	 				<td colspan="6">검색된 결과가 없습니다.</td>
	 			</tr>
	 		</c:when>
	 		<c:otherwise>
				<c:forEach var="list" items="${list}" varStatus="status">
				<tr id="item_${status.count}" class="item_${status.count}">
					<td><a href="javascript:itemClick('${status.count}');"><fmt:formatDate value="${list.usageTime}" pattern="yyyy-MM-dd HH:mm:ss" /></a></td>
		    		<td class="f_blod"><a href="javascript:itemClick('${status.count}');">${list.faxNumber}</a></td>
					<td><a href="javascript:itemClick('${status.count}');"><c:out value="${list.modelName}"/></a></td>
					<td><c:out value="${list.pageCount}"/></td>
					<td><fmt:formatNumber value="${list.imageFileSize/1024/1024}" pattern="0.00" /> MB</td>
					<td>
						<c:if test="${list.DisYn eq 'Y'}">
						<span class="tgray_btn"><a href="${pageContext.request.contextPath}/downloadPdf.do?imagePath=${list.imageFilePath}">저장</a><span class="bg_left"></span><span class="bg_right"></span></span>
						</c:if>
					</td>
				</tr>
		                    
		        <tr class="logcontent">
			 			 <td colspan="8">
							<div class="logview">
								<div class="topbar">
									<fieldset>
										<div class="fl_left">
											<label>페이지</label>
											<input type="hidden" id="page_count${list.logId}" value="${list.pageCount}">
											<input id="viewPg${list.logId}" value="1" type="text" class="ipt_pg" Onkeyup="viewImage(this.value, '${list.logId}','${list.imageLogPath}')">
											<label>/${list.pageCount}</label>	
										</div>
										<div class="topbar_btn_box2">
											<button id="btn_prev" onClick="page_prev('${list.logId}','${list.imageLogPath}','${list.pageCount}')" type="button">이전</button>
											<button id="btn_next" onClick="page_next('${list.logId}','${list.imageLogPath}','${list.pageCount}')" type="button">다음</button>
										</div>
									</fieldset>
								</div>
								<div class="logview_item">
									<img id="logview_cont${list.logId}" class="logview_content" src="${pageContext.request.contextPath}/viewImage.do?imagePath=${list.imageLogPath}&imageId=1"/>
								</div>
							</div>
						</td>
				 	</tr>
				</c:forEach>
			</c:otherwise>	
		</c:choose>
		</tbody>
		</table>
			
		<div class="t_thumb" style="display:none">
			<c:choose>
		 		<c:when test="${empty list}">
		 			<tr>
		 				<td colspan="6">검색된 결과가 없습니다.</td>
		 			</tr>
		 		</c:when>
		 		<c:otherwise>
					<c:forEach var="list" items="${list}" varStatus="status">
						<c:set var="destination" value="${list.destination}" />
				 		<c:set var="splitDestination" value="${fn:split(destination, ',')}" />
						<c:if test="${status.count % 3 == 1}"><div class="srch_thumb_row"></c:if>
						<div class="thumb_wrap">
							<div class="thumb_img">
							<c:if test="${list.DisYn eq 'Y'}">
							<a href="${pageContext.request.contextPath}/downloadPdf.do?imagePath=${list.imageFilePath}"><img src="${pageContext.request.contextPath}/viewImage.do?imagePath=${list.imageLogPath}&imageId=1" /></a>
							</c:if>
							<c:if test="${list.DisYn eq 'N'}">
							<img src="${pageContext.request.contextPath}/viewImage.do?imagePath=${list.imageLogPath}&imageId=1" />
							</c:if>
							</div>
							<table class="m_tbl tbl_thumb_info">
								<tr>
									<th>일&nbsp;&nbsp;&nbsp;시</th><td><fmt:formatDate value="${list.usageTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								</tr>
								<tr>
									<th>번&nbsp;&nbsp;&nbsp;호</th><td>${list.faxNumber}</td>
								</tr>
								<tr>
								<th>장&nbsp;&nbsp;&nbsp;수</th><td>${list.pageCount}장</td>
								</tr>
								<tr>
								<td class="btn_icon" colspan="2">
									<c:if test="${list.DisYn eq 'Y'}">
									<span class="tgray_btn"><a href="${pageContext.request.contextPath}/downloadPdf.do?imagePath=${list.imageFilePath}">저장</a><span class="bg_left"></span><span class="bg_right"></span></span>
									</c:if>
								</td>
								</tr>
							</table>
						</div>
						<c:if test="${status.count % 3 == 0}"></div></c:if>
					</c:forEach>
				</c:otherwise>	
			</c:choose>
		
			</form>
			</div>
	</div>
		
		 
		<div class="paging_box">
			<%@ include file = "/WEB-INF/jsp/pagging.jsp" %>
		</div>
		 

	    <%@ include file="/WEB-INF/jsp/common/Footer.jsp" %>
</div>
</body>
</html>

