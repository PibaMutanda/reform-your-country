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
    <div class="small-text">
        <a href="${book.externalUrl}" target="_blank"> 
            <span title="Lien externe vers cet ouvrage">source</span>
        </a>
        <ryc:conditionDisplay privilege="MANAGE_BOOK">
          - <a href="book/${book.url}">éditer</a> 
        </ryc:conditionDisplay>
        <ryc:conditionDisplay privilege="MANAGE_ARTICLE">
          - ${book.abrev}
        </ryc:conditionDisplay>
    </div>
</div>