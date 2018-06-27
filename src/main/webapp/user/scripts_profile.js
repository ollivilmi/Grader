$(document).ready(function() {

    $.getJSON("/userApi/getCurrentUserName").always(function(response) {
        let currentUser = response["username"];
        $('#welcomeUser').html(currentUser + "'s profile");
        $('#registerForm').find('input[name="username"]').val(currentUser);
     });

    $('#registerForm').submit(function(event) {

            event.preventDefault();
            let username = $('#registerForm').find('input[name="username"]').val();
            let password = $('#registerForm').find('input[name="password"]').val();
            let passwordConfirmation = $('#registerForm').find('input[name="passwordConfirmation"]').val();
            let change = JSON.stringify({ "username":username,"password":password, "passwordConfirmation":passwordConfirmation });

            $.ajax({
                url: "/userApi/updateUserInformation",
                type: "POST",
                contentType:"application/json; charset=utf-8",
                dataType:"json",
                data: change
            }).always(function(response) { $('#messageHandler').html(response.responseText); console.log(response); });
        });

        $('#openChange').click(function() {
            let change = document.querySelector("#registerForm");

            if (change.style.display == "none")
                change.style.display = "block";
            else
                change.style.display = "none";
        });

});