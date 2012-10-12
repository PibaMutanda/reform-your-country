<%@ tag language="java" pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<%@ attribute name="action" required="true"%>
<%@ attribute name="rename" required="true" type="java.lang.Boolean"%>
<%@ attribute name="id" type="java.lang.Long" required="false" %>
<script type="text/javascript">
	function getfile() {
		document.getElementById('hiddenfile').click();
	}
	function showfile() {
		document.getElementById('selectedfile').value = document.getElementById('hiddenfile').value;
	}
</script>
<p>(Attention l'image doit faire moins de 1.5Mo)</p>
<form method="post" action="${action}" enctype="multipart/form-data" style="width: 45%; display: inline-block; vertical-align:top;">
    <fieldset>
        <legend>à partir de votre ordinateur</legend>
        <input type="button" value="" class="uploadBtn" onmouseout="showfile()" onclick="getfile()" />
        <input type="submit" class="saveBtn" value="" /><br/>
        <input type="file" name="file" id="hiddenfile" style="display:none;" required="required"/>
        <input type="text"  disabled="disabled"  id="selectedfile" width="100%" />
        <input type="hidden" name="id" value="${id}" />
    </fieldset>
</form>
<form method="post" action="${action}fromurl" style="width: 45%; display: inline-block;">
    <fieldset>
        <legend>à partir d'un autre site web</legend>
        <label for="fileurl">URL: </label><input type="url" name="fileurl" required="required" /><br /> 
        <c:if test="${rename}">
        <label for="name">nom de l'image: </label><input type="text" name="name" required="required" /><br /> 
        </c:if>
        <input type="hidden" name="id" value="${id}" />
        <input type="submit" value="Ajouter" /><br />
    </fieldset>
</form>