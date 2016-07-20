<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--http://stackoverflow.com/questions/10327390/how-should-i-get-root-folder-path-in-jsp-page--%>
<header><a href="${pageContext.request.contextPath}"><fmt:message key="app.home"/></a>&nbsp;&nbsp;<a href="meals"><fmt:message key="app.title"/></a></header>