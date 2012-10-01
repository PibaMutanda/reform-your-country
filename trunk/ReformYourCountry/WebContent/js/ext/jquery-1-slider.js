//<![CDATA[
$(window).load(function() {
  $('.home-bnr-jquery ul').css("background-image", "none");
  $('.jqslider').css("display", "block");
  $('.home-bnr-jquery ul').after('<div class="jquery-pager">&nbsp;</div>').cycle({
	  fx: 'fade',
	  timeout: 8000,
	  height: 'auto',
	  pause: 0,
	  pager: '.jquery-pager',
	  cleartypeNoBg: true

  });
});
//]]>