    $(document).ready(function() {
    
        $('#helpButton').click(function() {
            $('#helpModal').css("display", "block");
        });

        $('#loginButton').click(function() {
            $('#loginModal').css("display", "block");
        });

        window.onclick = function(event) {
            if (event.target == document.getElementById('helpModal')) {
                $('#helpModal').css("display", "none");
            }
            if (event.target == document.getElementById('loginModal')) {
                $('#loginModal').css("display", "none");
            }
        }

        $('#openRegister').click(function() {
            let register = document.querySelector("#registerForm");

            if (register.style.display == "none")
                register.style.display = "block";
            else
                register.style.display = "none";
        });

    });

    
function showResults() {
    $('#resultsContainer').css("display", "block");
    $('#thresholdsContainer').css("display", "none");
  }
  
function showThresholds() {
    $('#resultsContainer').css("display", "none");
    $('#thresholdsContainer').css("display", "block");
}