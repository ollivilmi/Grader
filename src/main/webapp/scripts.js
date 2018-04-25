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
        let results = "";
            for (let g of grades)
            {
                results += "<tr><th scope='row'>"+g.grade+"</th>"
                +"<td><input type='number' step='0.5' class='points number' value='"+g.points+"'/></td>"
                +"<td><input type='number' step='0.5' class='percentage number' value='"+Math.round(g.percentage*100)+"'/>%</td></tr>";
            }
            $('#gradeTable').html(results);
       });
       return false;
    });
});