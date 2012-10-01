<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ attribute name="book" type="reformyourcountry.model.Book"%>
<%@tag import="reformyourcountry.util.FileUtil" %>
<div class="book">
    <a href="book/${book.url}">${book.title}</a><br /> ${book.author}<br />
    ${book.pubYear}<br />
    <c:if test="${book.hasImage}">
        <img src="gen<%=FileUtil.BOOK_SUB_FOLDER%><%=FileUtil.BOOK_RESIZED_SUB_FOLDER%>/${book.id}.jpg" alt="${book.title}" class="imgbook"/>
    </c:if> 
    <p>${book.description}</p>
    <a href="${book.externalUrl}">Lien externe</a>
</div>