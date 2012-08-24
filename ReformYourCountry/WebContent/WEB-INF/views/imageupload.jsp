
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h1>Please upload a file</h1>
<!--  Simple version without the nice jQuery drag & drop component -->
<c:choose>
<c:when test="${!empty errorMsg}"> <div class="error">Error:${errorMsg}</div></c:when>
</c:choose>
<form method="post" action="imageuploadsubmit" enctype="multipart/form-data">
    <input type="text" name="name" /> <input type="file" name="file" />
    <input type="submit" />
</form>
