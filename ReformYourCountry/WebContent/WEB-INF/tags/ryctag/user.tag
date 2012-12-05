<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>


<%@ attribute name="user" type="reformyourcountry.model.User"%>
<%@tag import="reformyourcountry.util.FileUtil" %>

<div class="user" id="${user.id}">

	<div class="avatar">
	<c:if test="${user.picture}"> 
       <img src="gen<%=FileUtil.USER_SUB_FOLDER%><%=FileUtil.USER_RESIZED_SUB_FOLDER%><%=FileUtil.USER_RESIZED_SMALL_SUB_FOLDER %>/${user.id}.jpg" /> 
	</c:if> 
	</div>
	<a href="/user/${user.userName}" class="a-name">
      <div class="lien">
		<span>${user.getFullName()}</span>
		<c:if test="${user.certificationDate != null }">
			<img src="images/green-check.png" />
		</c:if>
<!-- 		certification -->
      </div>
      <div class="usertitle"> ${user.title}</div>
	</a>
</div>