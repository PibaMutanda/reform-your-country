<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<%@taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ attribute name="book" type="reformyourcountry.model.Book"%>
<%@tag import="reformyourcountry.util.FileUtil" %>

<div class="book" id="${book.abrev}">
    <div class="bookTitle">${book.title}</div>
     ${book.subtitle}<br/>
    <p class="bookInfo">${book.author} - ${book.pubYear}</p>
    <c:if test="${book.hasImage}">
       <img src="gen<%=FileUtil.BOOK_SUB_FOLDER%><%=FileUtil.BOOK_RESIZED_SUB_FOLDER%>/${book.id}.jpg" title="${book.title}" alt="${book.title}" class="image-frame" />
    </c:if>
    <p>${book.description}</p>
    <div class="center-align">
        <a href="${book.externalUrl}" target="_blank"> 
            <img alt="Lien externe" title="Lien externe" src="/images/_global/links.png" class="icon-32" />
        </a>
    <ryc:conditionDisplay privilege="MANAGE_BOOK">
        <a href="book/${book.url}"> 
            <img alt="Editer" title="Editer" src="/images/_global/edit.png" class="icon-32" />
        </a>
    </ryc:conditionDisplay>
    </div>
</div>