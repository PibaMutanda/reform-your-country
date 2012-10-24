<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${!empty sessionScope.notification}">
<script type="text/javascript">

$(document).ready(function(){
	var message = "${sessionScope.notification}" ;
	
	var stack_bar_top = {"dir1": "down", "dir2": "right", "push": "top", "spacing1": 0, "spacing2": 0};
	
	$.pnotify({
		delay :20000,
		text: message,
		addclass: "stack-bar-top",
        cornerclass: "",
        width: "100%",
        styling: "jqueryui",
        hide: true,
        sticker: true,
        stack: stack_bar_top
	});
  
});
</script>
<c:set var="notification" scope="session" value=""/>
</c:if>
