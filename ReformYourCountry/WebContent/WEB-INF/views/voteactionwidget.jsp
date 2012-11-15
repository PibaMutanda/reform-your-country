<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">

   var idUser = "${current.user.id}";
</script>
	
<div id="voteContent" style="float:right;vertical-align:text-bottom;">
			<div id="-2" onclick="clicked(this);"
					class="smallvote <c:if test="${vote.value eq -2}">selected
				                </c:if> v-2" title="totalement pour">${resultNumbers.get(0)}
			</div>
			<div id="-1" onclick="clicked(this);"
					class="smallvote <c:if test="${vote.value eq -1}">selected
				                </c:if> v-1" title = "partiellement pour">${resultNumbers.get(1)}
			</div>
			<div id="0" onclick="clicked(this);"
					class="smallvote <c:if test="${vote.value eq 0}">selected
				                </c:if> v0" title = "ni pour ni contre">${resultNumbers.get(2)}
						
			</div>
			<div id="1" onclick="clicked(this);"
					class="smallvote <c:if test="${vote.value eq 1}">selected
				                </c:if> v1" title ="partiellement contre">${resultNumbers.get(3)}
			</div>
			<div id="2" onclick="clicked(this);"
					class="smallvote <c:if test="${vote.value eq 2}">selected
				                </c:if> v2" title ="totalement contre">${resultNumbers.get(4)}
			</div>
	<input type="hidden" value="${id}" name="id"/> 
</div>
	

