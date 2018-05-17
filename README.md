# Grader
Grading tool for teachers (Metropolia Software Structures &amp; Models)

**Currently only supported by google chrome**, (recommended window width over 880px)
> https://graderapp.herokuapp.com/

### Uses Spring boot for REST API
Uses [Grader](https://github.com/ollivilmi/Grader/blob/master/src/main/java/controller/Grader.java), session object to manage grade distribution.

Grader uses [Exam](https://github.com/ollivilmi/Grader/blob/master/src/main/java/controller/component/Exam.java), which contains:
- [Students](https://github.com/ollivilmi/Grader/blob/master/src/main/java/controller/component/Student.java)
- [Grade](https://github.com/ollivilmi/Grader/blob/master/src/main/java/controller/component/Grade.java) settings
- Thresholds

#### Exam uses a preset to generate thresholds based on the configurations.
- Equal distribution (thresholds distributed by the same interval)
- Easy exam (increasing function)
- Medium exam (randomizes results for 0->max points and uses normal distribution)
- Hard exam (decreasing function)

### Grader analyzes results by using the [Apache Math library](http://commons.apache.org/proper/commons-math/).
[Statistics](https://github.com/ollivilmi/Grader/blob/master/src/main/java/controller/model/Statistic.java)
- Mean
- Median
- Deviation
- Min
- Max

Suggests redistribution with a function that is based on Normal Distribution and probability of results.

### [JavaScript](https://github.com/ollivilmi/Grader/blob/master/src/main/webapp/scripts.js) ###
Libraries used
- Jquery
- [Charts.js](https://www.chartjs.org/)
Changing configurations automatically send JSON requests without the user having to send a button.

#### Most important functions
`updateConfig()`
> Updates session object with new configuration

`getResults()`
> Gets results based on thresholds & students, builds tables and a graph. Called when something changes the results.

`getThresholds()`
> Gets thresholds currently in the Exam object, builds table and a graph

`updateThresholds()`
> The exam configuration has been updated, updates the threshold TreeMap

`addStudent()`
> Adds student

`peerDistribution()`
> Calls suggested distribution method in Grader, then updates thresholds.

`resetSession()`
> Resets the current session object to default values.
