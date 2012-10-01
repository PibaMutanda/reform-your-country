//<![CDATA[
$(document).ready(function() {
	function onAfter(curr, next, opts, fwd) {
var index = opts.currSlide;
$('#prev,#prev2,#prev3,#prev4,#prev5')[index == 0 ? 'hide' : 'show']();
$('#next,#next2,#next3,#next4,#next5')[index == opts.slideCount - 1 ? 'hide' : 'show']();
//get the height of the current slide
var $ht = $(this).height();
//set the container's height to that of the current slide
$(this).parent().animate({height: $ht});
}
    $('.testimonials').after('<div class="testimonial-pager">&nbsp;</div>').cycle({
		fx: 'fade',
		timeout: 8000,
		height: 'auto',
		pause: 1,
		pager: '.testimonial-pager',
		before: onAfter,
		cleartypeNoBg: true

	});
});
//]]>