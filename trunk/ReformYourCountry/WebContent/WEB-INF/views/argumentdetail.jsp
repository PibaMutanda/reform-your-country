<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div id="arg${arg.id}">
<ryctag:argument id="${arg.id}" color="${color}" title="${arg.title}" author="${arg.user.firstName} ${arg.user.lastName}" content="${arg.content}" totalvote="${arg.getTotal()}" voteselected="${arg.getVoteValueByUser(current.user)}"/>     
</div>    