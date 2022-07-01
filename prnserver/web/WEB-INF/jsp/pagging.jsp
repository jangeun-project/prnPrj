<%@page language="java" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${paging.psize == null}">
	<c:set var="paging.psize" value="10"/>
</c:if>
<c:if test="${paging.count == null}">
	<c:set var="paging.count" value="0"/>
</c:if>
<c:if test="${paging.prows == null}">
	<c:set var="paging.prows" value="10"/>
</c:if>
<c:if test="${paging.page == null}">
	<c:set var="paging.page" value="1"/>
</c:if>
<c:set var="pages"     value="${(paging.count - (paging.count % paging.prows)) / paging.prows}" />
<c:if test="${pages > 0 && paging.count % paging.prows > 0}">
	<c:set var="pages" value="${pages + 1}"/>
</c:if>

<c:set var="restPage"  value="${paging.count - (paging.page * 10)}" />

<c:set var="basePage"  value="${paging.page - 1 - (paging.page - 1) mod paging.psize}" />

<c:set var="startPage" value="${basePage + 1}" />
<c:set var="endPage"   value="${(basePage + paging.psize) < pages ? basePage + paging.psize : (pages == 0 ? 1 : pages)}" />

<c:if test="${paging.count % paging.prows > 0}">
	<c:set var="pages" value="${pages + 1}" />
</c:if>

<!-- 
<c:if test="${paging.count > 0}">
<span class="btn_first" onclick="Go(1)" style="cursor:pointer"></span>
</c:if> 
<c:if test="${paging.count == 0}">
<span class="btn_first"></span>
</c:if> 
-->

<c:if test="${paging.page > 10}">
<span class="btn_prev" onclick="Go(${basePage - paging.psize + 1})" style="cursor:pointer"></span>
</c:if>
<c:if test="${paging.page <= 10}">
<span class="btn_prev"></span>
</c:if>


<c:forEach var="i" begin="${startPage}" end="${endPage}">
	<c:if test="${i == paging.page}">
	<span class="select_num">${i}</span>
	</c:if>
	<c:if test="${i != paging.page}">
	<span class="num" onclick="Go(<fmt:formatNumber type="number" groupingUsed="false" value="${i}"/>)" style="cursor:pointer">${i}</span>
	</c:if>
</c:forEach>

 
<c:if test="${paging.page < pages}">
	<c:if test="${restPage > 90}">
	<span class="btn_next" onclick="Go(<fmt:formatNumber type="number" groupingUsed="false" value="${basePage + paging.psize + 1}"/>)" style="cursor:pointer"></span>
	</c:if>
	<c:if test="${restPage <= 90}">
	<span class="btn_next"></span>
	</c:if>
</c:if>
<c:if test="${paging.page >= pages}">
	<span class="btn_next"></span>
</c:if>

<!-- 
<script type="text/javascript" language="javascript">
	console.log("===========>" + '${restPage}' + ":" +  '${paging.count}');
</script>
 -->
 
<!--
<c:if test="${paging.count >= 0}">
<span class="btn_end" onclick="Go(<fmt:formatNumber type="number" groupingUsed="false" value="${pages}"/>)" style="cursor:pointer"></span>
</c:if>
 -->