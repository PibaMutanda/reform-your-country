<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ page import="reformyourcountry.util.FileUtil" %>
<html>
<body>
    <ryctag:pageheadertitle title="${book.title}"> <ryctag:breadcrumbelement label="bibliographie" link="/book"/>ce livre </ryctag:pageheadertitle>
    
    <div class="book">
        ${book.author}<br /> ${book.pubYear}<br />
        <c:if test="${book.hasImage}">
            <img
                src="gen<%=FileUtil.BOOK_SUB_FOLDER%><%=FileUtil.BOOK_RESIZED_SUB_FOLDER%>/${book.id}.jpg"
                alt="${book.title}" class="imgbook" />
        </c:if>
        <p>${book.description}</p>
        <a href="${book.externalUrl}">Lien externe</a>
    </div>
    
    <ryc:conditionDisplay privilege="EDIT_BOOK">
	  <ryctag:submit entity="${book}" value="Editer" action="book/edit"/>	
		<form method="post" action="book/imageadd" enctype="multipart/form-data">
			<input type="file" name="file" />
			<input type="hidden" name="id" value="${book.id}" /><input type="submit" value="Uploader une image" />
		</form>
		<form method="post" action="book/imagedelete">
			<input type="hidden" name="id" value="${book.id}" />  <br><input type="submit" value="Supprimer l'image" />
		</form>
		<form method="post" action="book/remove">
			<input type="hidden" name="id" value="${book.id}" />  <br><input type="submit" value="Supprimer ce livre" />
		</form>
	  </ryc:conditionDisplay>
</body>
</html>