jQuery(function($){


    $("form").on('submit', function (e) {
//        alert("jeff was here:" + $('#password').val());
        $.ajax({
            type: 'POST',
            url: "/api/v2/Tenant/auth", //- action form
            data: {username:$('#username').val(),password:$('#password').val()},
        }).always(function(data){
            $('#response').text(data.responseText);
        });

        e.preventDefault();
    });

});

