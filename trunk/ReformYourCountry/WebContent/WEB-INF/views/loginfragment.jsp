<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ page import ="reformyourcountry.web.ContextUtil" %>


<!-- This fragment will be displayed in a jQuery dialog box. -->
    <label id ="errorMsg" style ="color:red;"></label>

	<form action="loginsubmit" method="post">
		<label for="identifier">pseudo / adresse e-mail</label><br/>
        <input type="text" name="identifier" required="required"/><br/>
        <label for="password">mot de passe</label><br/>
		<c:choose>
			<c:when test="<%= !ContextUtil.devMode %>">				
				<input type="password" name="password" required="required"/>
			</c:when>
  		    <c:otherwise>
			    <input type="password" name="password" required="required" value="secret"/><br/>
			    Your are in dev mode: password is not verified (type any).<br/>
			</c:otherwise>
		</c:choose>
        J'ai <a href="">oublié mon mot de passe</a><br />
		<br />
		
		 
		<input type="checkbox" name="keepLoggedIn" /><label for="keepLoggedIn"><span class="tooltip" data-tooltip="Si vous cochez cette case, lors de votre prochaine visite vous serez connectés automatiquement">Je souhaite rester connecté</span></label>
		
		<input type="submit" value="me connecter"/>
	</form>
