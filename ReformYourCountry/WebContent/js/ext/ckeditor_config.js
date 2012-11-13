CKEDITOR.editorConfig = function( config )
{
	config.toolbar = 'goodExample';
 
	config.toolbar_goodExample =
	[
		{ name: 'edit', items : [ 'Bold','Italic','-','RemoveFormat' ] },
		{ name: 'paragraph', items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'] },
		{ name: 'links', items : [ 'Link','Unlink' ] }
	];
};