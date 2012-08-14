<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/ReformYourCountry/css/test-style.css" />
<link rel="stylesheet" type="text/css" href="/ReformYourCountry/css/template.css" />
<link rel="stylesheet" type="text/css" href="/ReformYourCountry/css/content.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<body>
		<div class="header-template">
			<%@ include file="/WEB-INF/includes/header.jsp"%>
		</div>
		<div class="container-template">
			<div>
				<div class="menu-template">
					<%@ include file="/WEB-INF/includes/menu.jsp"%>
				</div>
				<div class="body-template">
					<decorator:body />
				</div>
			</div>
			<div class="footer-template">
				<%@ include file="/WEB-INF/includes/footer.jsp"%>
			</div>
		</div>
</body>
</html>