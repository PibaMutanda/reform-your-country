<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<%@taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ attribute name="book" type="reformyourcountry.model.Book"%>
<%@tag import="reformyourcountry.util.FileUtil" %>
<div class="book">
	<div style="display:block;">
    <a>${book.title}</a>
    <p class="bookInfo"> ${book.author} -  ${book.pubYear}</p>
    </div>
    
	<div style="display:block">
    <c:if test="${book.hasImage}">
		    <div class="imgbook">
		        <img src="gen<%=FileUtil.BOOK_SUB_FOLDER%><%=FileUtil.BOOK_RESIZED_SUB_FOLDER%>/${book.id}.jpg" title="${book.title}"  alt="${book.title}" class="realshadow"/>
		    </div>
	    </c:if> 
	<!--     <div style="float:right;width:230px"> -->
	    <p  >
	    ${book.description}
	    </p>
	    <center>
	   		<a href="${book.externalUrl}"target="_blank">
	   			<img  alt="Lien externe" title="Lien externe"src="/images/_global/links.png" width="32px" height="32px"/>
	   		</a>
    		<ryc:conditionDisplay privilege="EDIT_BOOK">
    			<a href="book/${book.url}" >
    				<img  alt="Editer" title="Editer"src="/images/_global/edit.png" width="32px" height="32px"/>
    			</a>
	   	 	</ryc:conditionDisplay>
	    </center>
   </div>
</div>