<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ attribute name="book" required="true" fragment="false"
	rtexprvalue="true" type="reformyourcountry.model.Book"
	description="id book"%>
<%@ attribute name="readOnly" required="false" fragment="false"
	rtexprvalue="true" type="java.lang.Boolean"
	description="choose if we need to display the edit function"%>
	
<div>
	<img alt="${book.title}" src="/ReformYourCountry/gen/book/resized/${book.id}.jpg"
	style="float: left" />

	<h5>Titre:
		<c:choose>
			<c:when test="${readOnly}">  <%-- In pop-up tooltip: link to showbooklist --%>
				<a href="showbooklist" name="${book.abrev}">${book.title}</a>
			</c:when>
			<c:otherwise> <%-- Within showbooklist, no link, just an <a> tag to define an anchor to be able to directly scroll to a specific book --%>
				<a id="${book.abrev}">${book.title}</a>
			</c:otherwise>
		</c:choose>
	</h5>

	<h5>Auteur: ${book.author} Année ${book.pubYear}</h5>
	<p>${book.description}</p>
	<a href="${book.externalUrl}" target="_blank">Lien externe</a>
	<c:if test="${!readOnly}">
		<ryc:conditionDisplay privilege="EDIT_BOOK">
			<form action="bookedit" method="GET">
				<input type="hidden" name="id" value="${book.id}" /> <input
					type="submit" value="Editer" />
			</form>
		</ryc:conditionDisplay>

	</c:if>
</div>

