$(document).ready(function() {

    $('#getThresholds').submit(function()
    {
       let url = "/getThresholds?gradeMin="+$('#gradeMin').val()
       +"&gradeMax="+$('#gradeMax').val()+"&gradeInterval="+$('#gradeInterval').val()
       +"&examMin="+$('#examMin').val()+"&examMax="+$('#examMax').val();
      
       console.log(url);
       let results = "";
       $.getJSON(url, function(data)
       {
            for (var i = 0; i < data.length; i++)
            {
				results += "<li class='list-group-item'>"
				+"<h5>Grade: "+data[i].grade+"</h5>"
				+"<p>Points: "+data[i].points+"</p>"
				+"<p>Percentage of max points: "+data[i].percentage+"</p>"
            }
            $('#gradeTable').html(results);
       });
       return false;
    });
});