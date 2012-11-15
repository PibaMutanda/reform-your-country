<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<head>
<script type="text/javascript" src="/ckeditor/ckeditor.js"></script>
</head>
<ryctag:pageheadertitle title="goodexamplelist" >
Insert breadcrumb elements here
</ryctag:pageheadertitle>

<div id="list">
<c:forEach items="${article.goodExamples}" var="goodExample">
<div id="${goodExample.id}">
    <%@include file="goodexampledisplay.jsp" %>
</div>
</c:forEach>
</div>

<hr/>
<h3>Nouvel exemple</h3>
<form id="create" action="/ajax/goodexample/create">
    <label for="titre">titre:</label><input type="text" id="titre" name="title" /><br /> 
    <label for="description">description:</label><textarea id="description" name="description"></textarea><br /> 
    <input type="hidden" name="articleId" value="${article.id}" />
    <input type="submit" />
</form>
<script type="application/javascript"> 
$('#create').submit(function() {
    $.post( $('#create').attr('action'), $('#create').serialize(), function(data) {
        $('#list').append(data);
    });
    
      return false;
    });
</script>


