
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h1>Please upload a file</h1>
<!--  Simple version without the nice jQuery drag & drop component -->
<c:choose>
<c:when test="${!empty errorMsg}"> <div class="error">Error:${errorMsg}</div></c:when>
</c:choose>
<div class=""></div>(Attention l'image doit faire moins de 1.5Mo)</div>
<form method="post" action="imageuploadsubmit" enctype="multipart/form-data">
    <input type="file" name="file" value="${file.getInputStream()}"/>
    <input type="submit" />
    
    <input type="hidden" name="type" value=""/>
</form>
