<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>


<%@ attribute name="badgeType" type="reformyourcountry.model.BadgeType"%>


<span>
          	<span class="badge" >
       			<c:choose> 
          			<c:when test="${badgeType.badgeTypeLevel.name=='Or'}"><span class="badge1"></span></c:when>
          			<c:when test="${badgeType.badgeTypeLevel.name=='Argent'}"><span class="badge2"></span></c:when>
          			<c:when test="${badgeType.badgeTypeLevel.name=='Bronze'}"><span class="badge3"></span></c:when>
           		</c:choose>
           	&nbsp;${badgeType.name}
          	</span>
</span>