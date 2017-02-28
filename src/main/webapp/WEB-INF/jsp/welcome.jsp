<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>welcome</title>
		<style type="text/css">
		</style>
	</head>
	<body>
		Language: <a href="?lang=zh_CN">中文简体</a> - 
		<a href="?lang=zh_TW">中文繁体</a>
	    <h3><spring:message code="screen.welcome.welcome"></spring:message></h3>
	    Locale: ${pageContext.response.locale}
	</body>
</html>