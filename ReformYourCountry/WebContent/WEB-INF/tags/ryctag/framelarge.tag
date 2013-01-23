<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>

    <table style="border-spacing:0px;">   <%-- Table with 3 cells: left (border image), center (text), right (border image) --%>
	 <tr> <%-- First row = top background image --%>
	    <td style="background-image:url(/images/paper/summaryheader.png); background-repeat: repeat-x; 
                width:786px; height:30px;  <%-- must be the same width as the bg image. --%> 
                padding:0; " colspan="3"></td>
	 </tr> 
     <tr>
       <td style="background-image:url(/images/paper/summaryleft.png); background-repeat: repeat-y; width:5px; padding:0"></td>
       <td style="width:776px; padding:0; background-color: #F8F8F8;">

	     <jsp:doBody/>

	     
   	   </td>
       <%-- right border --%>
       <td style="background-image:url(/images/paper/summaryright.png); background-repeat: repeat-y; width:5px; padding:0"></td>
	 </tr>
	 <tr> <%-- Last row = bottom background image --%>
	    <td style="background-image:url(/images/paper/summaryfooter.png); background-repeat: repeat-x; 
                height:12px;  <%-- must be the same width as the bg image. --%> 
                padding:0; " colspan="3"></td>
	 </tr> 
	</table>						
   
