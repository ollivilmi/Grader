$(document).ready(function() {

    $('#registerForm').submit(function(event) {
        event.preventDefault();
        let username = $('#registerForm').find('input[name="username"]').val();
        let password = $('#registerForm').find('input[name="password"]').val();
        let registration = JSON.stringify({ "username":username,"password":password });

        $.ajax({
            url: "/register",
            type: "POST",
            contentType:"application/json; charset=utf-8",
            dataType:"json",
            data: registration
        });
    });
});