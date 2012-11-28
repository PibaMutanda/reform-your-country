<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<html>

<head>
<!-- you can set variables starting with "p_" in the file named website_content.properties -->
<link rel="canonical" href="${p_website_address}/user/${user.userName}"/>
<meta name="description" content="${user.firstName} ${user.lastName}">
</head>   
<body>

<ryctag:pageheadertitle title="${user.firstName} ${user.lastName}"/>

<div style="display:inline-block;" class="text-big">
	<div style="float:left;">
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
		<br />
		<c:if test="${canEdit}">
			<a href= "/user/image?id=${user.id}">Ajouter image</a><br/>
			
			<c:choose>
			   <c:when test="${sessionScope.providersignedin != 'LOCAL'}">	
			   <a href ="user/updateusersocialimage?provider=${sessionScope.providersignedin}&id=${user.id}">Mettre à jour mon image depuis ${sessionScope.providersignedin} </a><br/>
			   </c:when>
		
			</c:choose>
		
			<a href ="/socialaccountmanage?id=${user.id}">Gerer mes comptes associés</a>
		    </c:if>
	</div>
	
	<div style="float:left; padding-left:50px;">
				Prénom: <c:choose><c:when test="${user.firstName ne null}">${user.firstName}</c:when><c:otherwise>?</c:otherwise></c:choose> <br/>
				Nom de famille: <c:choose><c:when test="${user.lastName ne null}">${user.lastName}</c:when><c:otherwise>?</c:otherwise></c:choose> <br/>
				Pseudo : ${user.userName}<br/>
				Titre: <c:choose><c:when test="${user.title ne null}">${user.title}</c:when><c:otherwise>?</c:otherwise></c:choose> <br/>
				Genre : <c:choose><c:when test="${user.gender ne null}">${user.gender}</c:when><c:otherwise>?</c:otherwise></c:choose> <br/>
				<c:if test="${canEdit}">
					Né le : <c:choose><c:when test="${user.birthDate ne null}">${user.birthDate}</c:when><c:otherwise>?</c:otherwise></c:choose><br />
					mail : ${user.mail}<br />
					
					Date d'enregistrement : ${user.createdOn} <br />
					Rôle : ${user.role}<br/>
					
					<c:if test="${user.specialType!='PRIVATE'}">
					  Type : ${user.specialType.name}<br/>
					</c:if>
					
					Dernier accès : ${user.lastAccess} <br/>
					Depuis l'adresse ${user.lastLoginIp}<br/>
					Status du compte : ${user.accountStatus}<br/>
					<c:if test="${user.lockReason}!= ACTIVE ">
						Raison blocage compte : ${user.lockReason}<br /> <br/>
					</c:if>
										
				</c:if>
	</div>
	
	<div style="float:right; padding-left:50px;" >
		<ul class="list sitemap-list">
			<ryc:conditionDisplay privilege="MANAGE_USERS">
				 <li><a href="user/privilegeedit?id=${user.id}">Privilèges</a></li>
				 <li><a href="user/usertypeedit?id=${user.id}">Editer le type d'un user</a></li>
			</ryc:conditionDisplay>
			
			 <c:if test="${canEdit}">
				 <li><a href="user/edit?id=${user.id}">Editer le Profil</a></li>
				 <li><a href="user/changepassword?id=${user.id}">Modifier le mot de passe</a></li>
				 <li><a href="user/delete?id=${user.id}">Supprimer le compte</a></li>
			 </c:if>
		</ul>	
	</div>
</div>

<!-- ******************** GROUPS ******************** -->
<div  class="text-big">
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
</div>
</body>
</html>