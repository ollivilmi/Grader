$(document).ready(function() {

    $.getJSON("/userApi/getCurrentUserName").always(function(response) {
    $('#welcomeUser').html(response["username"] + "'s profile"); });

});