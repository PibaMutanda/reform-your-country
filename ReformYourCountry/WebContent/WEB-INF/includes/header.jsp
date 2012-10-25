
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="header">
	<div class="header-holder">
		<div class="rays">
			<div class="header-area">
				<!-- ***************** - LOGO - ***************** -->
				<div class="logodiv" >
					<a href="/">
						<!--<img src="images/logo/logo.png"/>  --><font size="26px" style="font-weight: bold">RYC</font>
					</a>
					<div style=" font-size:10px; padding-top:16px;">
						<a href="/about/about-us.jsp">Reform your country</a>
					</div>
				</div>
				<!-- ***************** - END LOGO - ***************** -->	
				
				
				<div style="float:right">
				    <!-- ***************** - REGISTER - ***************** -->
					<div style="width:100%; ">
					  	<div class="login-link">
						      <c:choose>
						        <c:when test="${current.user!=null}">
						         Connecté en tant que 
						          <a href="/user/${current.user.userName}">${current.user.userName}</a> 
						          <c:choose>
						          <c:when test="${sessionScope.providersignedin == 'FACEBOOK'}">
						           avec <img src="images/features-icons/facebook.jpg"/>
						          </c:when>
						            <c:when test="${sessionScope.providersignedin == 'TWITTER'}">
						           avec <img src="images/features-icons/twitter.jpg"/>
						          </c:when>
						            <c:when test="${sessionScope.providersignedin == 'GOOGLE'}">
						           avec <img src="images/features-icons/google.jpg"/>
						          </c:when>
						            <c:when test="${sessionScope.providersignedin == 'LINKEDIN'}">
						           avec <img src="images/features-icons/linkedin.jpg"/>
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
						<div style="float:right;padding-top: 30px; ">
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
	</div><!-- end header-holder -->
</div><!-- end header -->
 