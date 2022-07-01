<%@page language="java" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
    
<script type="text/javascript">
function chagePw(obj) {
	
	var audit_trail_win = window.open("/prnserver/pwchangepop.do?userId="+obj , "popup","toolbar=no, location=no, status=yes, scrollbars=no, width=500px, height=400px, left=200px, top=200px");
	audit_trail_win.focus();
}
</script>    

    <div id="gnb">
    	<div class="gnb_content">
    		<div id="toplogo" onClick="location.href='/prnserver/MainList.do'">
    		</div>
    		<div class="fr">
    			<div class="account">
    				<strong>${sessionScope.session_nm}</strong>님
    				
    			</div>
    			<div class="account">
    			  <a href="javascript:chagePw('${sessionScope.session_id}');">비밀번호변경</a>
    			</div>
    			<div class="account">
    			  <a href="/prnserver/logout.do">로그아웃</a>
    			</div>
    			
    		</div>
    	</div>
    </div>

 <!--    
	<div id="toplogo">
		<p class="p_info"><strong>"${sessionScope.session_nm}"</strong>님</p>
	</div>
 -->	
<!-- 		 
	<div id="templogo"></div>
 -->	
	  
	<div id="topnavi">
		<h2 class="hidden">관리자 메뉴</h2>
		<div id="cssmenu">
		<ul>
			<li class="${menuCur eq "menu01" ? "active" : "last" }"><a href='/prnserver/ScanList.do'><span>스캔리스트</span></a></li>
			<li class="${menuCur eq "menu02" ? "active" : "last" }"><a href='/prnserver/SendFaxList.do'><span>발신팩스</span></a></li>
			<li class="${menuCur eq "menu03" ? "active" : "last" }"><a href='/prnserver/ReceiveFaxList.do'><span>수신팩스</span></a></li>
			
			<!-- 
				슈퍼관리자 grage= 9: 슈퍼관리자, 1:일반사용자, 2:승인권자, 3:승인이관자 
			 -->
	 		 
			<c:if test="${sessionScope.session_gd eq '2' || sessionScope.session_gd eq '3'}">
				<li class="${menuCur eq "menu04" ? "active" : "last" }"><a href='/prnserver/ConfirmFaxList.do'><span>팩스승인</span></a></li>
			</c:if>
			<c:if test="${sessionScope.session_gd eq '9'}">
				<li class="${menuCur eq "menu04" ? "active" : "last" }"><a href='/prnserver/ConfirmFaxList.do'><span>팩스승인</span></a></li>
				<li class="${menuCur eq "menu05" ? "active" : "last" }"><a href='/prnserver/MemberList.do'><span>사용자관리</span></a></li>
				<li class="${menuCur eq "menu06" ? "active" : "last" }"><a href='/prnserver/LogList.do'><span>로그관리</span></a></li>
				<li class="${menuCur eq "menu07" ? "active" : "last" }"><a href='/prnserver/SetConfig.do'><span>설정관리</span></a></li>
			</c:if>
		</ul>
		</div>
	</div>
	
	<hr />
	<!-- 
	<div id="p_info_box">
		<div id="p_info_con">
		<h2 class="hidden">개인정보</h2>
		<p class="p_info"><strong>"${sessionScope.session_nm}"</strong>님</p>
		<p><a href="javascript:chagePw('${sessionScope.session_id}');" class="btn_changepw">비밀번호변경</a></p>
		<p><a href="/prnserver/logout.do" class="btn_logout">로그아웃</a></p>
		</div>
	</div>
	 -->
	<hr />