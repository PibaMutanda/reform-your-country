var ckEditorUniqueInstance = null;  // We only want one CkEditor instance per page, and it's here.

function destroyCkEditor() {
	 var editor = CKEDITOR.instances["contentItem"];
	 
	    if (editor) { editor.destroy(true);
	    delete CKEDITOR.instances["contentItem"];
	    }
	console.log("before destroy");
    ckEditorUniqueInstance.destroy(true);
    delete ckEditorUniqueInstance;
    ckEditorUniqueInstance = null;
    console.log("after destrory");
}

// Will replace the text area by the ck editor.
function createCkEditor(textAreaId) {
    if (ckEditorUniqueInstance != null) {
        console.error("Bug: the ckEditorUniqueInstance should be null at this stage.");
        destroyCkEditor();
    }
    console.log("just before creating instance"+ (ckEditorUniqueInstance != null));
    ckEditorUniqueInstance = CKEDITOR.replace( textAreaId, { 
        customConfig : '/js/ext/ckeditor_config.js',
        toolbar : 'goodExample'
            });
    console.log("just after creating instance");
}


function getContentFromCkEditor() {
	
    if (ckEditorUniqueInstance == null) {
        console.error("Bug: the ckEditorUniqueInstance should not be null at this stage.");
        return;
    }
    console.log("getContentFromCkEditor " + ckEditorUniqueInstance.getData());
    return ckEditorUniqueInstance.getData();
}

// http://stackoverflow.com/questions/5075778/how-do-i-modify-serialized-form-data-in-jquery
function serializeFormWithCkEditorContent($form, ParamName) {
    var values, index;

    // Get the parameters as an array
    values = $form.serializeArray();
    content_val = getContentFromCkEditor();
    
    // Find and replace `content` if there
    for (index = 0; index < values.length; ++index) {
        if (values[index].name == ParamName) {
            values[index].value = content_val;
            break;
        }
    }

    // Add it if it wasn't there
    if (index >= values.length) {
        values.push({
            name: ParamName,
            value: content_val
        });
    }
    console.log("values returned by serializa " + values);
    // Convert to URL-encoded string
    return jQuery.param(values);
}