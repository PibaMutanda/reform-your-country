
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
$(document).ready(function(){
	$("#notificationArea").slideDown();  // Was hidden at the beginning.
});
</script>


<%-- ********** NOTIFICATIONS ************** --%>
<c:if test="${!empty sessionScope.notifications}">
 		    <div id="notificationArea"  style="padding:3px; <%-- padding in style to override header-area class padding  --%> 
                     display:none;  <%-- display none because will be shown through animation by the docuiment ready javascript --%>
                     "> 
		        <a id="notificationCloser" style="float:right; padding:0px 0px 0px 10px; cursor: pointer;" onclick="$('#notificationArea').slideUp();">×</a>
		        <c:forEach items="${sessionScope.notifications}"   var="notif">
		           ${notif.text}<br/>
   	            </c:forEach>
   	            <% session.removeAttribute("notifications"); %>
		   </div>
</c:if>
		


<div id="header">
	    <div style="height:20px;">  <%-- WHITE LINE ABOVE THE RIBBON --%>
				<div style="float:right">
				    <!-- ***************** - REGISTER - ***************** -->
					<div style="width:100%; font-size:15px;">
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
				</div>	
			    <!-- ***************** - END REGISTER - ***************** -->
	    </div><%-- end WHITE LINE ABOVE THE RIBBON --%>
	    	
		<div>
			
				<!-- ***************** - LOGO - ***************** -->
				<div class="logodiv" >
					${p_website_logo_tag}
				</div>
				<!-- ***************** - END LOGO - ***************** -->
				
				<%--	
				<!-- ******************* BETA ************************ -->	
				<div class="beta">
						<img src= "images/beta.png" style="margin-left:13px;"
						  alt="Plate-forme en construction, pas encore destinée au grand public. Sortie prévue: 1er trimestre 2013"
						title="Plate-forme en construction, pas encore destinée au grand public. Sortie prévue: 1er trimestre 2013"  />
				</div>
				--%>
					
				<div style="float:right; text-align:right;">
						
						<!-- Search -->
						<div style="display:inline-block; padding-top: 30px;">
						  <form method="get" id="searchform" action="search" class="search-form">
							 <fieldset>
								   <input type="submit" class="submit" value="search" id="searchsubmit" />
								   <input type="text" name="searchtext" id="s" value="Rechercher" onfocus="this.value=(this.value=='Rechercher') ? '' : this.value;" onblur="this.value=(this.value=='') ? 'Rechercher' : this.value;" />
							 </fieldset>
						  </form>
						</div><br/>

						<div style="display:inline-block; margin-right:-30px;">
							<%@ include file="/WEB-INF/includes/headermenu.jsp"%>
						</div>
				

     				    <!-- Hidden div that JavaScript will move in a dialog box when we press the login link (Note JOHN 2013-02: there is no login dialog anymore...)-->
					    <div id ="logindialog" style = "display:none;"></div>
				</div>
		</div>
		
		<div id="pdfdialog" style="display:hidden"></div>
	
</div><!-- end header -->
 