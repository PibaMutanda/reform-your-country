var ckEditorUniqueInstance = null;  // We only want one CkEditor instance per page, and it's here.

function destroyCkEditor() {
    ckEditorUniqueInstance.destroy(true);
    ckEditorUniqueInstance = null;
}

// Will replace the text area by the ck editor.
function createCkEditor(textAreaId) {
    if (ckEditorUniqueInstance != null) {
        console.error("Bug: the ckEditorUniqueInstance should be null at this stage.");
        destroyCkEditor();
    }
    ckEditorUniqueInstance = CKEDITOR.replace( textAreaId, { 
        customConfig : '/js/ext/ckeditor_config.js',
        toolbar : 'goodExample'
            });
}


function getContentFromCkEditor() {
    if (ckEditorUniqueInstance == null) {
        console.error("Bug: the ckEditorUniqueInstance should not be null at this stage.");
        return;
    }
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

    // Convert to URL-encoded string
    return jQuery.param(values);
}