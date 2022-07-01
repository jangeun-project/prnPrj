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
			frm.action = "/prnserver/SendFaxList.do";
			frm.submit();
		}
		
		function Go(pageNo)
		{
			var frm = document.searchForm;
			frm.page.value = pageNo;
			frm.target = "_self";
			frm.action = "/prnserver/SendFaxList.do";
			frm.submit();
		}
		
		function modify(logId)
		{
			var audit_trail_win = window.open("/prnserver/ModifySendFaxPop.do?logId=" + logId, "popup","toolbar=no, location=no, status=yes, scrollbars=no, width=500px, height=300px, left=200px, top=200px");
			audit_trail_win.focus();

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
			//console.log(" f :" + $('#viewPg'+logId).val() );
			
			if (parseInt(currntPg) < parseInt(count)) {
				
				var imageId= parseInt($('#viewPg'+logId).val())+parseInt(1);
				
				$('#viewPg'+logId).val(imageId);
				
				//console.log(" e :" + $('#viewPg'+logId).val() );
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
			
			//console.log(" qq :" + $('#viewPg'+logId).val() );
		}
	</script>
</head>

<body>
<h1 class="hidden">SendFax List</h1>

<div id="wrapper"> 
	
    <%@ include file="/WEB-INF/jsp/TopMenu.jsp" %>
	<!-- CONTENTS START -->
	<div id="contents_box">
		<h3 class="h3_style">발신팩스 리스트</h3>

        <form id="searchForm" name="searchForm" method="post">
		<input type="hidden" name="page" value="">
		<input type="hidden" name="seq" value="">
		<input type="hidden" name="logId" value="">
		<input type="hidden" name="title" value="">
		<input type="hidden" name="receiveName" value="">
		<input type="hidden" name="pageType" value="">

<c:if test="${sessionGd eq '9'}">				
			<table class="t_st01" summary="">
			<colgroup>
				<col class="t_item"  width="90">
				<col class="t_con" width="90">
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
	
			<table class="t_st02" summary="">
			<colgroup>
				<col width="100px" />
				<col width="90px" />
				<col width="150px" />
				<col width="90px" />
				<col width="100px" />
				<col width="100px" />
				<col width="60px" />
				<col width="150px" />
				<col width="70px" />
				<col width="60px" />
				<col width="100px" />
				<col width="60px" />
				<col width="60px" />
			</colgroup>
			<thead>
			<tr>
				<th>요청일시</th>
				<th>사용자명</th>
				<th>부서명</th>
				<th>모델명</th>
				<th>발신제목</th>
				<th>수신처</th>
				<th>수신자</th>
				<th>발신번호</th>
				<th>발신장수</th>
				<th>상태</th>
				<th>승인일시</th>
				<th>변경</th>
				<th>파일</th>
			</tr>
	
			</thead>
			<tbody>
			<c:choose>
			 		<c:when test="${empty list}">
			 			<tr>
			 				<td colspan="12">검색된 결과가 없습니다.</td>
			 			</tr>
			 		</c:when>
			 		<c:otherwise>
						<c:forEach var="list" items="${list}" varStatus="status">
						<tr id="item_${status.count}" class="item_${status.count}">
							<td><fmt:formatDate value="${list.usageTime}" pattern="yyyy-MM-dd HH:mm:ss" /></a></td>
							<td class="f_blod"><a href="javascript:itemClick('${status.count}');"><c:out value="${list.userName}"/></a></td>
				    		<td><a href="javascript:itemClick('${status.count}');"><c:out value="${list.fullDeptName}"/></a></td>
							<td><a href="javascript:itemClick('${status.count}');"><c:out value="${list.modelName}"/></a></td>
							<td><a href="javascript:itemClick('${status.count}');"><c:out value="${list.faxTitle}"/></a></td>
							<td><a href="javascript:itemClick('${status.count}');"><c:out value="${list.receiveName}"/></a></td>
							<td><a href="javascript:itemClick('${status.count}');"><c:out value="${list.receiver}"/></a></td>
							<td><a href="javascript:itemClick('${status.count}');"><c:out value="${list.faxNumber}"/></a></td>
							<td><c:out value="${list.pageCount}"/></td>
							<td><c:choose>
									<c:when test="${list.confirmYn eq 'F'}"><span style="font-weight:bold;color:red">실   패</span></c:when>
									<c:when test="${list.confirmYn eq 'Y'}"><span style="font-weight:bold;">완   료</span></c:when>
									<c:when test="${list.confirmYn eq 'C'}"><span style="font-weight:bold;color:red">마감완료</span></c:when>
									<c:when test="${list.confirmYn eq 'W'}"><span style="font-weight:bold;color:red">반   려</span></c:when>
									<c:otherwise><span style="font-weight:bold;color:blue">대   기</span></c:otherwise>
							</c:choose>
							</td>
							<td>
								<strong><fmt:formatDate value="${list.confirmTime}" pattern="yyyy-MM-dd HH:mm:ss" /></strong>
							</td>
							<td>
								<c:if test="${list.confirmYn ne 'Y'}">
								<span class="tgray_btn"><a href="javascript:modify('${list.logId}');">수정</a><span class="bg_left"></span><span class="bg_right"></span></span>
								</c:if>
							</td>
							<td>
								<c:if test="${list.DisYn eq 'Y'}">
								<span class="tgray_btn"><a href="${pageContext.request.contextPath}/downloadPdf.do?imagePath=${list.imageFilePath}">저장</a><span class="bg_left"></span><span class="bg_right"></span></span>
								</c:if>
							</td>
						</tr>
						
						<tr class="logcontent">
				 			 <td colspan="12">
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
		 				<td colspan="12">검색된 결과가 없습니다.</td>
		 			</tr>
		 		</c:when>
		 		<c:otherwise>
					<c:forEach var="list" items="${list}" varStatus="status">
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
									<th>부 서 명</th><td title="${list.fullDeptName}">${list.fullDeptName}</td>
								</tr>
								<tr>
									<th>사 용 자</th><td title="${list.loginId}">${list.userName}</td>
								</tr>
								<tr>
									<th>팩스제목</th><td title="${list.faxTitle}">${list.faxTitle}</td>
								</tr>
								<tr>
									<th>수 신 처</th><td title="${list.receiveName}">${list.receiveName}</td>
								</tr>
								<tr>
									<th>수 신 자</th><td title="${list.receiver}">${list.receiver}</td>
								</tr>
								<tr>
									<th>발신번호</th><td title="${list.faxNumber}">${list.faxNumber}</td>
								</tr>
								<tr>
									<th>장&nbsp;&nbsp;&nbsp;수</th><td>${list.pageCount}장</td>
								</tr>
								<tr>
									<th>상&nbsp;&nbsp;&nbsp;태</th>
									<td><c:choose>
									<c:when test="${list.confirmYn eq 'F'}"><span style="font-weight:bold;color:red">실   패</span></c:when>
									<c:when test="${list.confirmYn eq 'Y'}"><span style="font-weight:bold;color:blue">완   료</span></c:when>
									<c:when test="${list.confirmYn eq 'C'}"><span style="font-weight:bold;color:red">마감완료</span></c:when>
									<c:when test="${list.confirmYn eq 'W'}"><span style="font-weight:bold;color:red">반   려</span></c:when>
									<c:otherwise><span style="font-weight:bold;color:red">대   기</span></c:otherwise>
									</c:choose>
									</td>
								</tr>
								<tr>
									<td class="btn_icon" colspan="2">
									<c:if test="${list.confirmYn ne 'Y'}">
										<span class="tgray_btn"><a href="javascript:modify('${list.logId}');">수정</a><span class="bg_left"></span><span class="bg_right"></span></span>
									</c:if>
										<c:if test="${list.DisYn eq 'Y'}">
										<span class="tgray_btn"><a href="${pageContext.request.contextPath}/downloadPdf.do?imagePath=${list.imageFilePath}">파일저장</a><span class="bg_left"></span><span class="bg_right"></span></span>
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