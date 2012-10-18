<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ page import="reformyourcountry.util.FileUtil" %>
<html>
<body>
    <ryctag:pageheadertitle title="${book.title} ${book.subtitle}"> <ryctag:breadcrumbelement label="bibliographie" link="/book"/>ce livre </ryctag:pageheadertitle>
    <div class="book" style="display: inline-block; width:100%;">
    <div class="bookInfo" style="font-size:1.2em;">${book.author} - ${book.pubYear}</div>
    <div style="min-height:10px;width:100%">
    </div>
        <c:if test="${book.hasImage}">
        <div class="imgbook">
            <img
                src="gen<%=FileUtil.BOOK_SUB_FOLDER%><%=FileUtil.BOOK_RESIZED_SUB_FOLDER%>/${book.id}.jpg"
                alt="${book.title}" class="realshadow" />
        </div>
        </c:if>
        <p class="bookContent">${book.description}</p>
        <a href="${book.externalUrl}"target="_blank"><img  alt="Lien externe" title="Lien externe"src="/images/_global/links.png" width="32px" height="32px"/></a>
         <ryc:conditionDisplay privilege="EDIT_BOOK">
	 		 <ryctag:submit entity="${book}" value="Editer" action="book/edit"/>	
	    <form method="post" action="book/remove">
			<input type="hidden" name="id" value="${book.id}" />  <br><input type="submit" value="Supprimer ce livre" />
		</form>
	  	</ryc:conditionDisplay>
    </div>
    <div style="display: inline-block;">
    <ryc:conditionDisplay privilege="EDIT_BOOK">
		<form method="post" action="book/imagedelete">
			<input type="hidden" name="id" value="${book.id}" />  <br><input type="submit" value="Supprimer l'image" />
		</form>
			Changer l'image 
        <ryctag:imageupload action="book/imageadd" id="${book.id}" rename="false"/>
	  </ryc:conditionDisplay>
	  </div>
</body>
</html>