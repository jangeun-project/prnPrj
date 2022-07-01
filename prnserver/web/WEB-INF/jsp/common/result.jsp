<%@page language="java" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>SIT</title>
</head>
<body>
<script type="text/javascript" language="javascript">


<c:if test="${message_funcion == 'Y'}">
alert("${message}");
history.back();
</c:if>

<c:if test="${go_function == 'Y'}">
alert("${message}");
self.location.replace("${goUrl}");
</c:if>

<c:if test="${self_close == 'Y'}">
self.close();
</c:if>

<c:if test="${opener_function == 'Y'}">
alert("${message}");
opener.document.location.reload();
</c:if>

<c:if test="${location_function == 'Y'}">
alert("${message}");
location.reload();
</c:if>

<c:if test="${reload_function == 'Y'}">
alert("${message}");
history.back();
opener.document.location.reload();
</c:if>


</script>
</body>
</html>
