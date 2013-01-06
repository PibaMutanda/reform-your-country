﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ page import="reformyourcountry.util.DateUtil" %>
<%@ page import="java.util.Date" %>
<%@ page import="reformyourcountry.model.User" %>

<html>

<head>


<script>
    $(function() {
        $( "#tabs" ).tabs();
    });
</script>


<!-- you can set variables starting with "p_" in the file named website_content.properties -->
<link rel="canonical" href="${p_website_address}/user/${user.userName}"/>
<meta name="description" content="${user.firstName} ${user.lastName}">
</head>   

<body>

<ryctag:pageheadertitle title="${user.fullName}"/>
<div class="user-options" style="font-size:12px">
            <ryc:conditionDisplay privilege="MANAGE_USERS">
				 <a href="user/privilegeedit?id=${user.id}">Privilèges</a>&nbsp-&nbsp
				 <a href="user/usertypeedit?id=${user.id}">Editer le type d'un user</a>	
				 <c:if test="${not(current.user  eq user)}">
				  	&nbsp-&nbsp<a href="user/delete?id=${user.id}">Supprimer le compte</a>
				 </c:if>
			</ryc:conditionDisplay>
			 <c:if test="${canEdit}">
				&nbsp-&nbsp<a href="user/edit?id=${user.id}">Editer le Profil</a>
				&nbsp-&nbsp<a href="user/changepassword?id=${user.id}">Modifier le mot de passe</a>
			 </c:if>
</div>

<br/>

<%-- IMAGE   IMAGE  IMAGE  IMAGE  IMAGE  IMAGE  IMAGE  IMAGE  IMAGE  --%>
<div style="display:inline-block; vertical-align:top;">
	<div class="user-options" style="font-size:12px">
		<c:choose>
			<c:when test="${user.picture}">
				<img src="gen/user/resized/large/${user.id}.jpg<c:if test="${random!=null}">?${random}</c:if>"  /><%-- Random, to force the reload of the image in case it changes (but its name does not change) --%>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${user.isFemale()}">
						<img src="images/Femme_anonyme.jpg" />
					</c:when>
					<c:otherwise>
						<img src="images/Homme_anonyme.jpg" />
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>

		<br/>  <%-- Add/edit links --%>
		<c:if test="${canEdit}">
			<c:choose>
					<c:when test="${user.picture}">
						<a href= "/user/image?id=${user.id}">changer image</a>&nbsp;-
					
						<a href= "/user/imagedelete?id=${user.id}">supprimer image</a><br/>
					</c:when>
				
					<c:otherwise>
						<a href= "/user/image?id=${user.id}">ajouter image</a><br/>
					</c:otherwise>
			</c:choose>			
			<c:if test="${sessionScope.providersignedin != 'LOCAL'}">	
			   <a href ="user/updateusersocialimage?provider=${sessionScope.providersignedin}&id=${user.id}">Mettre à jour mon image depuis ${sessionScope.providersignedin} </a><br/>
			</c:if>
		</c:if>

		<br />
		<br />
	</div>
</div>

<div style="display:inline-block; vertical-align:top;">
		<c:choose><c:when test="${user.firstName ne null}">${user.firstName}</c:when><c:otherwise>?</c:otherwise></c:choose>
		<c:choose><c:when test="${user.lastName ne null}">${user.lastName}</c:when><c:otherwise>?</c:otherwise></c:choose> <br/>
		<c:choose><c:when test="${user.title ne null}">${user.title}</c:when><c:otherwise>?</c:otherwise></c:choose> <br/>
							
		<% 
			 if (((User) pageContext.getRequest().getAttribute("user")).getBirthDate() != null){
			 DateUtil.SlicedTimeInterval sti = DateUtil.sliceDuration(((User) pageContext.getRequest().getAttribute("user")).getBirthDate(), new Date());
  			 out.println(sti.years + " ans");
			}
 		%>	
		<c:if test="${user.role != USER}">
			 ${user.role.name}<br/><br/>
		</c:if>	
		<br />
		<br />
</div>		
		

<br/>


<div id="tabs">
		<ul>
			<li><a href="#tabs-1">Signalétique</a></li>
			<li><a href="#tabs-2">Gommettes</a></li>
			<li><a href="#tabs-3">Rédaction</a></li>
			
		</ul>
		
		
		<div id="tabs-1">		<!--  **************************Signalétique********************* -->
			<div>
				Prénom: <c:choose><c:when test="${user.firstName ne null}">${user.firstName}</c:when><c:otherwise>?</c:otherwise></c:choose> <br/>
				Nom de famille: <c:choose><c:when test="${user.lastName ne null}">${user.lastName}</c:when><c:otherwise>?</c:otherwise></c:choose> <br/>
				Pseudo : ${user.userName}<br/>
				Titre: <c:choose><c:when test="${user.title ne null}">${user.title}</c:when><c:otherwise>?</c:otherwise></c:choose> <br/>
<!-- 				<a href="/action">Mes votes</a><br/> -->
				Genre : <c:choose><c:when test="${user.gender ne null}">${user.gender}</c:when><c:otherwise>?</c:otherwise></c:choose> <br/>
				<c:if test="${canEdit}">
					Né le : <c:choose><c:when test="${user.birthDate ne null}">${user.birthDate}</c:when><c:otherwise>?</c:otherwise></c:choose><br />
					mail : <c:choose><c:when test="${user.mail ne null}">${user.mail}</c:when><c:otherwise>?</c:otherwise></c:choose><br />
					
					Date d'enregistrement : <ryc:datedisplay date="${user.createdOn}" /><br />
					Rôle : ${user.role}<br/>
					
					<c:if test="${user.specialType!='PRIVATE'}">
					  Type : ${user.specialType.name}<br/>
					</c:if>
					
					Dernier accès : <ryc:datedisplay date="${user.lastAccess}" duration="true"/> <br/>
					Depuis l'adresse ${user.lastLoginIp}<br/>
					Status du compte : ${user.accountStatus}<br/>
					<c:if test="${user.lockReason}!= ACTIVE ">
						Raison blocage compte : ${user.lockReason}<br /> 
					</c:if>
					<br/>
					
					<a href ="/socialaccountmanage?id=${user.id}">Gerer mes comptes associés</a><br/>	
						
					Groupes:
					<c:forEach items="${user.groupRegs }" var="groupReg"  >
  						<a href="group?id=${groupReg.group.id}">${groupReg.group.name}</a>
						<%--   <c:if test="${lastGroupReg !eq groupReg}">,</c:if>    --%><%-- no "," after the last one --%>
 						<c:if test="${user.groupRegs.lastIndexOf(groupReg) < (user.groupRegs.size()-1)}">,</c:if>    <%-- no "," after the last one --%>
					</c:forEach>
					&nbsp;&nbsp;&nbsp;
					<c:if test="${canEdit}">
						<a href="manageGroup?id=${user.id}">modifier les groupes</a>
					</c:if>
					<br />		
				</c:if>
			</div>
		</div>
		
		
		<div id="tabs-2">  			<!--  **************************Badges********************* -->
			<c:forEach items="${user.badges}" var="badge">
				<br />
				<ryctag:badge badgeType="${badge.badgeType}" />
			</c:forEach>
			
			<form action="/user/recomputebadge" method="post">
			  <input type="hidden" name="userid" value="${user.id}">
			  <input type="submit" value="Recalculer">
			</form>
			<a href="/badge/">Gommettes disponibles</a>
		</div>

		<div id="tabs-3">		<!--  **************************Rédaction********************* -->
		    <h2>Arguments rédigés</h2>
			<c:forEach items="${arguments}" var="argument">
				<div>
				  <h4><a href="/action/${argument.action.url}">${argument.title}</a> / ${argument.voteCountAgainst}</h4>
				  ${argument.content}
				  <ryc:datedisplay date="${argument.updatedOrCreatedOn}" duration="true" />
				</div>
			</c:forEach>
		</div>
									
</div>
	

</body>
</html>