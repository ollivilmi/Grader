$(document).ready(function() {

    // Check for error parameter from invalid login
    if (/[?&]error/.test(location.search))
    {
        $('#authFailure').html("Authentication failed");
    };

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
        }).always(function(data) { $('#messageHandler').html(data.responseText); });
    });

    $('#openRegister').click(function() {
        let register = document.querySelector("#registerForm");

        if (register.style.display == "none")
            register.style.display = "block";
        else
            register.style.display = "none";
    });

});