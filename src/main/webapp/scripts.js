$(document).ready(function() {

    updateSession();
    updateThresholds("/getThresholds");

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

    function updateThresholds(url)
    {
       $.getJSON(url, function(grades)
       {
        let results = "";
            for (let g of grades)
            {
                results += "<tr><th scope='row'>"+g.grade+"</th>"
                +"<td><input type='number' step='0.5' class='points number' value='"+g.points+"'/></td>"
                +"<td><input type='number' step='0.5' class='percentages number' value='"+Math.round(g.percentage*100)+"'/>%</td></tr>";
            }
            $('#gradeTable').html(results);
            updateListeners();
       });
       return false;
    };
    
    function updateListeners()
    {
        $('.points').change(function()
        {
            let url = "/setByPoints?grade="+this.parentNode.previousSibling.innerHTML+"&points="+this.value;
            updateThresholds(url);
        });
    
        $('.percentages').change(function()
        {
            let url = "/setByPercentage?grade="+this.parentNode.previousSibling.previousSibling.innerHTML+"&percentage="+this.value;
            updateThresholds(url);
        });
    }

    $('#getThresholds').click(updateThresholds("/getThresholds"));
    $('#updateSession').click(updateSession);
});