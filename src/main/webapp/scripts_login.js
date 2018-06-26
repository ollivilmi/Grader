$(document).ready(function() {

    $('#registerForm').submit(function(event) {
    
        event.preventDefault();
        let username = $('#registerForm').find('input[name="username"]').val();
        let password = $('#registerForm').find('input[name="password"]').val();
        let passwordConfirmation = $('#registerForm').find('input[name="passwordConfirmation"]').val();
        let registration = JSON.stringify({ "username":username,"password":password, "passwordConfirmation":passwordConfirmation });

        $.ajax({
            url: "/userApi/register",
            type: "POST",
            contentType:"application/json; charset=utf-8",
            dataType:"json",
            data: registration
        }).always(function(data) { $('#messageHandler').html(data.responseText); console.log(data); });
    });


});