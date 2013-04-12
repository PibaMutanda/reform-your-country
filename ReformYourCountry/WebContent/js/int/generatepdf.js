$(document).ready(function() {	
	
	
	$(".generatepdfarticle,.generatepdflist,.generatepdfgroup").click(function(){  // To open the dialog box when clicking on the upper right corner login link.
		// 1. initialise jquery ui dialog box. It is empty and does not open.
		
	   
		var hasSubArticle = false;
		var isfromGeneralList = false;
		var titlebox ;
		if(typeof $('meta[name=title]').attr("content") == 'undefined'){
			titlebox = "Generation PDF - Tout les articles";
		}
		else{
			titlebox = "Generation PDF - "+$('meta[name=title]').attr("content");
		}
		$('#pdfdialog').dialog({
			title :   titlebox,
			autoOpen: false,
			width: 450, 
			buttons: {
				"Générer le pdf": function() {  // Called on button click;
					generatePdf();
				},
				"Annuler": function() {
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
		if($(this).hasClass('generatepdflist')){
			isfromGeneralList = true;
			
		}
		
		
		
		console.log(hasSubArticle);
		
		// 2. ills the dialog with content from the server (PdfGeneratorController) whent the user clicks the link
		
		$('#pdfdialog').load("ajax/pdfgeneration",{hassubarticle:hasSubArticle,id:id,isfromgenerallist:isfromGeneralList}, function(){
			
			$('#pdfdialog').dialog('open');
			
			
		
			
		});
	
	
	
	});
	
	function generatePdf(){
		console.log("generatepdf");
		$("#pdfoptionsform").submit();
			
		$('#pdfdialog').html("<p>Votre pdf est en cours de préparation. Ensuite débutera le téléchargement, veuillez regarder dans votre bar de telechargement</p>");
		// reinit buttons with new values
		$('#pdfdialog').dialog( "option", "buttons", [{text : "Fermer",click:function(){$('#pdfdialog').dialog('close');}}]);
		setTimeout(function(){
			$('#pdfdialog').dialog('close');  
		},10000);
		
		
	}
	
	
	
});