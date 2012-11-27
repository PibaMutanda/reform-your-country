$(document).ready(function() {	
	
	
	$(".generatepdfarticle,.generatepdflist,.generatepdfgroup").click(function(){  // To open the dialog box when clicking on the upper right corner login link.
		// 1. initialise jquery ui dialog box. It is empty and does not open.
		
	   
		var hasSubArticle = false;
		var isfromGeneralList = false;
		$('#pdfdialog').dialog({
			title :   "Generation PDF - "+$('meta[name=title]').attr("content"),
			autoOpen: false,
			width: 450, 
			buttons: {
				"Generer le pdf": function() {  // Called on button click;
					generatePdf();
				},
				"Anuler": function() {
					$('#pdfdialog').dialog("close");
				}
			},
			show:"clip",
			hide:"clip"
		});
		
		if($(this).hasClass('generatepdfgroup')){
			hasSubArticle = true;			
		}
	
		if(!$(this).hasClass('generatepdflist')){
			var id = $('input[name="articleId"]').attr("value");		
		}
		
		console.log(hasSubArticle);
		
		// 2. ills the dialog with content from the server (PdfGeneratorController) whent the user clicks the link
		
		$('#pdfdialog').load("ajax/pdfgeneration",{hassubarticle:hasSubArticle,id:id}, function(){
			
			$('#pdfdialog').dialog('open');
		});
	
	
	
	});
	
	function generatePdf(){
		
		$("#pdfoptionsform").submit();
		$('#pdfdialog').dialog('close');
		
		
		
	}
	
	console.log(" generatedpdf.js ready");
	
});