$(document).ready(function() {
	// perform a redirection from error.jsp to home when ErrorController has decided it.
	if (typeof redirectUrl !='undefined' && redirectUrl != "") {  // Has this variable been set in the jsp ?
		/// Countdown
		var waitTimeInSec = 7;
		var interval;
        interval = setInterval(function(){
			$("#count").text((waitTimeInSec));
			waitTimeInSec--;
			if(waitTimeInSec <= 0) {
				clearInterval(interval); // Stop counting down (stop this function)
				location.href = redirectUrl;
			}
		}, 1000);  // 1000 = every second.

	}
});