<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<%@ attribute name="title" required="false" type="java.lang.String"%>

   <table style="border-spacing:0px;">   <%-- Table with 3 cells: left (border image), center (text), right (border image) --%>
	 <tr> <%-- First row = top background image --%>
	    <td style="background-image:url(/images/paper/paper2tapesheader.png); background-repeat: no-repeat; 
                width:397px; height:56px;  <%-- must be the same width as the bg image. --%> 
                padding:0; " colspan="3"></td>
	 </tr> 
     <tr>
       <td style="background-image:url(/images/paper/paper2tapesleft.png); background-repeat: repeat-y; width:39px; padding:0"></td>
       <td style="width:314px; padding: 0 10px 0 10px; background-color: #F8F8F8;">
            
            <div class="frametitle" style="margin-top:-16px;">${title}</div>
            
	        <jsp:doBody/>

  	   </td>
  			   	 
   	   <%-- right border --%>
       <td style="background-image:url(/images/paper/paper2tapesright.png); background-repeat: repeat-y; width:24px; padding:0"></td>
	 </tr>
	 <tr> <%-- Last row = bottom background image --%>
	    <td style="background-image:url(/images/paper/paper2tapesfooter.png); background-repeat: no-repeat; 
                height:23px;  <%-- must be the same width as the bg image. --%> 
                padding:0; " colspan="3"></td>
	 </tr> 
   </table>						
