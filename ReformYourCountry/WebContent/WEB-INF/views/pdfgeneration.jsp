<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>

<html>

<body>
<p>Nous pouvons générer un document PDF avec les articles que vous désirez. Cela facilite parfois la vie, par exemple en permettant une lecture sans connection internet, ou bien d’imprimer un article et tous ses sous-articles en une opération.</p>
	<form id=pdfoptionsform method="POST"
		action="/ajax/pdfgenerationsubmit" target="_self">
	
		<table id="pdfoptions">
		
			<tr>
				<td><input type="checkbox" name="iscover" value="true" <c:if test="${isfromgenerallist}"> checked </c:if> />inclure
					une page de couverture</td>
			</tr>
			<tr>
				<td><input type="checkbox" name="istoc" value="true" <c:if test="${isfromgenerallist}"> checked </c:if> />inclure
					une table des matières</td>
			</tr>
			<ryc:conditionDisplay privilege="VIEW_UNPUBLISHED_ARTICLE">
				<tr>
					<td><input type="checkbox" name="isnotpublished" value="true" />inclure
						les articles non-publiés</td>
				</tr>
			</ryc:conditionDisplay>
			<tr>
				<td><input type="checkbox" name="isonlysummary" value="true" />imprimer
					uniquement le résumé</td>
			</tr>
	
				<c:if test="${hasSubArticle == true}">
					<tr>
						<td><input type="checkbox" name="issubarticles" />inclure
							les sous-articles</td>
					</tr>
				</c:if>
		</table>
		<input type="hidden" name="idarticle" value="${id}">
		
	</form>
</body>
</html>