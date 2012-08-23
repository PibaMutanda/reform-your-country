<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>

<%@ attribute name="book"
	required="true"
	fragment="false"
	rtexprvalue="true"
	type="reformyourcountry.model.Book"
	description="id book"
%>
	<h5>Titre: ${book.title}</h5>
	<h5>Auteur: ${book.author} Année ${book.pubYear}</h5>
	<img alt=""
		src="http://site.enseignement2.be/bibliographie/McKinsey2007.png"
		height="150" width="150">
	<p>${book.description}</p>
	<ryc:conditionDisplay privilege="EDIT_BOOK">
		<form action="bookedit" method="GET">
			<input type="hidden" name="id" value="${book.id}" /> <input
				type="submit" value="Editer" />
		</form>
	</ryc:conditionDisplay>

