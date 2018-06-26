$(document).ready(function() {

    $.getJSON("/userApi/getCurrentUserName").always(function(response) {
    $('#welcomeUser').html(response + "'s profile"); });

});