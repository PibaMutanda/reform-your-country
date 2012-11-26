<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">

   var idUser = "${current.user.id}";
</script>
	
<div id="voteContent" style="float:right;vertical-align:text-bottom;">
			<div id="-2" onclick="clicked(this);"
					class="smallvote <c:if test="${vote.value eq -2}">selectedsmall
				                </c:if> v-2" title="totalement pour">
			</div>
			<div id="-1" onclick="clicked(this);"
					class="smallvote <c:if test="${vote.value eq -1}">selectedsmall
				                </c:if> v-1" title = "partiellement pour">
			</div>
			<div id="0" onclick="clicked(this);"
					class="smallvote <c:if test="${vote.value eq 0}">selectedsmall
				                </c:if> v0" title = "ni pour ni contre">
						
			</div>
			<div id="1" onclick="clicked(this);"
					class="smallvote <c:if test="${vote.value eq 1}">selectedsmall
				                </c:if> v1" title ="partiellement contre">
			</div>
			<div id="2" onclick="clicked(this);"
					class="smallvote <c:if test="${vote.value eq 2}">selectedsmall
				                </c:if> v2" title ="totalement contre">
			</div>
	<input type="hidden" value="${id}" name="id"/> 
</div>
	

