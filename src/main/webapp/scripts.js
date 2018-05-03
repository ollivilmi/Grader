$(document).ready(function() {

    $.getJSON("/loadSession", function(grader){
        if (grader !== null)
        {
            $('#gradeMin').val(grader.grade.min);
            $('#gradeMax').val(grader.grade.max);
            $('#gradeInterval').val(grader.grade.interval);
            $('#examMin').val(grader.exam.min);
            $('#examMax').val(grader.exam.max);
            $('#preset').val(grader.exam.preset);
        }
    });
    
    updateSession();
    
    function updateSession()
    {
        let updateSession = "/updateSession?gradeMin="+$('#gradeMin').val()
        +"&gradeMax="+$('#gradeMax').val()+"&gradeInterval="+$('#gradeInterval').val()
        +"&examMin="+$('#examMin').val()+"&examMax="+$('#examMax').val()+"&preset="+$('#preset').val();

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
    }
    
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

        $('.studentResults')
    }

    function addStudent()
    {
        $.getJSON("/addStudent?studentId="+$('#studentId').val()+"&studentName="+$('#studentName').val(), function(success)
        {
        });
        getResults();
        return false;
    }

    function getResults()
    {
        $.getJSON("/getResults", function(students)
        {
            let results = "";
            for (let student of students)
            {
                results += '<tr><td>'+student.id+'</td>'
                +'<td>'+student.name+'</td>'
                +'<td><input type="number" step="0.5" class="studentResults number" value="'+student.points+'" /></td>'
                +'<td>'+student.grade+'</td></tr>';
            }
            $('#results').html(results);
        });
        return false;
    }

    $('#config').change(updateSession);
    $('#getThresholds').click(function() {
        updateSession();
        updateThresholds("/getThresholds");
    });
    $('#addStudent').click(addStudent);
    $('#getResults').click(getResults);
});