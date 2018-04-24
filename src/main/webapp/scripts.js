$(document).ready(function() {

    updateSession();

    function updateSession()
    {
        let updateSession = "/updateSession?gradeMin="+$('#gradeMin').val()
        +"&gradeMax="+$('#gradeMax').val()+"&gradeInterval="+$('#gradeInterval').val()
        +"&examMin="+$('#examMin').val()+"&examMax="+$('#examMax').val();

        $.getJSON(updateSession, function(data)
        {
        });
        return false;
    }

    $('#updateSession').click(updateSession);

    $('#getThresholds').click(function()
    {
       $.getJSON("/getThresholds", function(grades)
       {
           let results = "<thead class='thead'>";
            for (let g of grades)
                results += "<th scope='col'>"+g.grade+"</th>";  
            results += "</thead><tbody id='indextable'>";

            for (let g of grades)
                results += "<td>Points: "+g.points+" ("+Math.round(g.percentage*100)+"%)</td>";

            $('#gradeTable').html(results);
       });
       return false;
    });
});