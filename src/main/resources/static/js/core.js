function jQueryShowContent(id, url){
    let data = {
         "id": id,
        "url": url
    }

    $("#"+id).load(url);
    history.pushState( data, "", "" );

}

// For browser's back button
function addPopstateEventListener(){
    window.addEventListener('popstate', function(event) {
        let state = event.state;
        if (state == null) {
            history.back();
            return;
        }

        let stateID = state.id;
        let stateUrl = state.url;

        $("#"+stateID).empty();
        $("#"+stateID).load(stateUrl);

    }, false);

}

function showAlertAndDirect(url, message){
    alert(message);
    location.href = url;
}

function isValidateEmail(email)
{
    //Regular expression Testing
    let emailRule = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z]+$/;

    if(email.search(emailRule)!= -1){
        // alert("Valid email address!");
        return true;
    }else{
        // alert("You have entered an invalid email address!");
        return false;
    }

}

function BtnLoading(elem) {
    $(elem).attr("data-original-text", $(elem).html());
    $(elem).prop("disabled", true);
    $(elem).html('<i class="spinner-border spinner-border-sm"></i> Loading...');
}

function BtnReset(elem) {
    $(elem).prop("disabled", false);
    $(elem).html($(elem).attr("data-original-text"));
}

function loadingDialog(message){
    if (message == "" || typeof(message) == "undefined" || message == null) {
        message = '資料處理中...';
    }
    let waitDialog = '<div class="modal fade" id="pleaseWaitDialog" data-bs-backdrop="static" data-bs-keyboard="false">'+
        '	<div class="modal-dialog modal-sm modal-dialog-centered">'+
        '		<div class="modal-content">'+
        '			<div class="modal-header">'+
        '				<h1>'+message+'</h1>'+
        '			</div>'+
        '			<div class="modal-body">'+
        '				<div class="progress">'+
        '				  <div class="progress-bar progress-bar-striped" role="progressbar" style="width: 100%" aria-valuenow="10" aria-valuemin="0" aria-valuemax="100"></div>'+
        '				</div>'+
        '            </div>'+
        '			</div>'+
        '		</div>'+
        '	</div>'+
        '</div>';

    var dialog;
    dialog = dialog || (function () {
        var pleaseWaitDiv = $(waitDialog);
        return {
            showPleaseWait: function() {
                pleaseWaitDiv.modal("show");
            },
            hidePleaseWait: function () {
                pleaseWaitDiv.modal("hide");
            },

        };
    })();

    return dialog;
}

function windowDialog(title, body, footer){

    if (title == "" || typeof(title) == "undefined" || title == null) {
        title = '<h4 class="modal-title">Message</h4>';
    }

    if (body == "" || typeof(body) == "undefined" || body == null) {
        body = '<p>This is a small modal.</p>';
    }

    if (footer == "" || typeof(footer) == "undefined" || footer == null) {
        footer = '<button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>'+
            '<button type="button" class="btn btn-primary" name="dialog_window_ok" id="dialog_window_ok">確定</button>';
    }

    var dialogFramework ='<div class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">'+
        '  <div class="modal-dialog" role="document">'+
        '    <div class="modal-content">'+
        '      <div class="modal-header">'+
        '        <h5 class="modal-title">'+ title +'</h5>'+
        '        <button type="button" class="close" data-dismiss="modal" aria-label="Close">'+
        '          <span aria-hidden="true">&times;</span>'+
        '        </button>'+
        '      </div>'+
        '      <div class="modal-body">'+
        '				'+ body +
        '      </div>'+
        '      <div class="modal-footer">'+
        '			' + footer +
        '      </div>'+
        '    </div>'+
        '  </div>'+
        '</div>';

    var dialog;
    dialog = dialog || (function () {
        var windowDialogDiv = $(dialogFramework);
        return {
            showDialog: function() {
                windowDialogDiv.modal('show');
            },
            hideDialog: function () {
                windowDialogDiv.modal('hide');
            },
            setDialogTitle: function(data){
                $("#dialog_window_title").empty();
                $("#dialog_window_title").append(data);
            }

        };
    })();

    return dialog;
}


function jQueryGetContent(id, url){
    $.get(url,
        function (data) {	// 回傳的data是 字串
            $("#"+id).empty();
            $("#"+id).append(data);
        })
        .fail(function() {
            alert("沒有頁面");
        });
}
