<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>

<%@ attribute name="book"
	required="true"
	fragment="false"
	rtexprvalue="true"
	type="reformyourcountry.model.Book"
	description="id book"
%>
<%@ attribute name="readOnly"
	required="false"
	fragment="false"
	rtexprvalue="true"
	type="java.lang.Boolean"
	description="choose if we need to display the edit function"
%>
	<h5>Titre: ${book.title}</h5>
	<h5>Auteur: ${book.author} Année ${book.pubYear}</h5>
	<img alt=""
		src="http://site.enseignement2.be/bibliographie/McKinsey2007.png"
		height="150" width="150">
	<p>${book.description}</p>
	<a href = "${book.externalUrl}">Lien externe</a>
	<c:if test="${!readOnly}">
	<ryc:conditionDisplay privilege="EDIT_BOOK">
		<form action="bookedit" method="GET">
			<input type="hidden" name="id" value="${book.id}" /> <input
				type="submit" value="Editer" />
		</form>
	</ryc:conditionDisplay>
	</c:if>

