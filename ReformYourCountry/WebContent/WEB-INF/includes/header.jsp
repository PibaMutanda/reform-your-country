
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
$(document).ready(function(){
	$("#notificationArea").slideDown();  // Was hidden at the beginning.
});
</script>

<div id="header">
	<div class="header-holder">

	    <%-- ********** NOTIFICATIONS ************** --%>
        <c:if test="${!empty sessionScope.notifications}">
 		    <div id="notificationArea" class="header-area" style="padding:3px; <%-- padding in style to override header-area class padding  --%> 
                     display:none;">  <%-- display none because will be shown through animation by the docuiment ready javascript --%>
		        <a id="notificationCloser" style="float:right; padding:0px 0px 0px 10px; cursor: pointer;" onclick="$('#notificationArea').slideUp();">×</a>
		        <c:forEach items="${sessionScope.notifications}"   var="notif">
		           ${notif.text}
   	            </c:forEach>
   	            <% session.removeAttribute("notifications"); %>
		   </div>
		</c:if>
		
		
		<div class="rays">
			<div class="header-area">
				<!-- ***************** - LOGO - ***************** -->
				<div class="logodiv" >
					${p_website_logo_tag}
				</div>
				<!-- ***************** - END LOGO - ***************** -->	
				
				
				<div style="float:right">
				    <!-- ***************** - REGISTER - ***************** -->
					<div style="width:100%; ">
					<c:choose>
					<c:when test="${current.user!=null}">
					<div class="login-link" title ="Connecté en tant que ${current.user.userName} avec ${sessionScope.providersignedin}">
					</c:when>
					<c:otherwise>
					<div class="login-link">
					</c:otherwise>
					</c:choose>
					  	
						      <c:choose>
						        <c:when test="${current.user!=null}">
						        
						          <a href="/user/${current.user.userName}">${current.user.userName}</a> 
						          <c:choose>
						          <c:when test="${sessionScope.providersignedin == 'FACEBOOK'}">
						          <img src="images/features-icons/facebook.png"/>
						          </c:when>
						            <c:when test="${sessionScope.providersignedin == 'TWITTER'}">
						          <img src="images/features-icons/twitter.png" alt="Connecté en tant que ${current.user.userName} avec ${sessionScope.providersignedin}"/>
						          </c:when>
						            <c:when test="${sessionScope.providersignedin == 'GOOGLE'}">
						           <img src="images/features-icons/google.png" alt="Connecté en tant que ${current.user.userName} avec ${sessionScope.providersignedin}"/>
						          </c:when>
						            <c:when test="${sessionScope.providersignedin == 'LINKEDIN'}">
						            <img src="images/features-icons/linkedin.png" alt="Connecté en tant que ${current.user.userName} avec ${sessionScope.providersignedin}"/>
						          </c:when>
						          </c:choose>&nbsp;&nbsp;|&nbsp;&nbsp;   
						          <a id="logout" href="logout">déconnexion</a>
						        </c:when>
						        <c:otherwise>
						           <!-- <a class="login"  style="cursor:pointer;">connexion</a>&nbsp;-&nbsp;-->
						            <a href="login">connexion</a>&nbsp;&nbsp;|&nbsp;&nbsp; 
						            <a href="register">créer un compte</a>
						         </c:otherwise>
						     </c:choose>
						</div>
					</div>
					<!-- ***************** - END REGISTER - ***************** -->
					
					<!-- ***************** - Main Navigation - ***************** -->
					<div>
						<div style="float:left;">
							<%@ include file="/WEB-INF/includes/headermenu.jsp"%>
						</div>
						
						<!-- Search -->
						<div style="float:right;padding-top: 30px;">
						  <form method="get" id="searchform" action="search" class="search-form"> <%-- TODO: implement seach page. --%>
								<fieldset>
								<span class="text">
								 <input type="submit" class="submit" value="search" id="searchsubmit" />
								 <input type="text" name="searchtext" id="s" value="Rechercher" onfocus="this.value=(this.value=='Rechercher') ? '' : this.value;" onblur="this.value=(this.value=='') ? 'Rechercher' : this.value;" />
								</span>
								</fieldset>
						  </form>
						</div>
						</div>
						<!-- ***************** - END Main Navigation - ***************** -->
						<!-- Hidden div that JavaScript will move in a dialog box when we press the login link -->
						<div id ="logindialog" style = "display:none;">
						
					</div>
				</div>
			</div><!-- end header-area -->
			
		</div><!-- end rays -->
		<div id="pdfdialog" style="display:hidden"></div>
	</div><!-- end header-holder -->
</div><!-- end header -->
 