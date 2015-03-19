var q = "hawaii"; //Set as initial search query.
var tweetFeed;
var tweets = []; //Container for all Tweets.
var MAX_COUNT_VISIBLE = 10; //Set max number of Tweets to display at a time.
google.load('visualization', '1.1', { 'packages': ['map', 'annotationchart', 'line'] });
 
function retrieveTweets (data) {
    // Format all tweets.
    for (var i = data.length - 1; i >= 0; i--) {
      tweets[i] = TweetWatch.formatTweet(tweets[i]); 
    };

    // Append received data at beginning of our Array.
    /*data.forEach(function (t){ 
      tweets.unshift(t._source);
    });*/
    
    TweetWatch.setTweetList(tweets);
}

$("#search-btn").click(function (e) {
    e.preventDefault(); //Prevent the page submit/refresh on enter or clicking Search button.
    q = $("#search").val();
    searchTrigger(q);
})

function triggerReact () {
    // Pass only fixed number of element in Array to display.
    var truncatedData = [];
    if (tweets.length > MAX_COUNT_VISIBLE) {
          truncatedData = tweets.slice(0,MAX_COUNT_VISIBLE);
    }
    else
    {
      // Otherwise send the whole data.
      truncatedData = tweets;
    }

    TweetWatch.setTweetList(truncatedData); //Update UI, by passing updated array to React.
    drawMap();
}

function searchTrigger(queryString) {
    if (typeof tweetFeed === 'object') { tweetFeed.close(); }

    var searchString = "*";
    if (queryString.length > 0) {
        searchString = queryString;
    }
    else
      return;
//clear tweet widget,array(for new topics)---clear ui and tweet data

     $.ajax({
      url: "http://54.172.83.108:9000/search?q="+searchString,
      cache: false,
      dataType: "json",
    })
    .done(function( data ) {
      tweets = data.hits.hits.map(function (t) { return t._source; }).map(TweetWatch.formatTweet);
      console.log(tweets);
      triggerReact();
    })
    .fail(function  (argument) {
      alert("Fetching tweets failed.")
    })

    var cachedCallback = function(msg) {
        var newTweet = TweetWatch.formatTweet(JSON.parse(msg.data)); //Parse received message and Format it.
        tweets.unshift(newTweet); //Add at top of Global tweet data array.
        triggerReact(); //Trigger UI update, by letting React know that data updated.
    };

    // Listens for new Tweets, related to our Search string.
    tweetFeed = new EventSource("http://54.172.83.108:9000/tweetfeed?q=" + searchString);
    tweetFeed.onmessage = function  (data) {
      console.log("Tweet Received." + data);
      //prints the line on the console
    }
    tweetFeed.addEventListener("message", cachedCallback, false);
}

function drawMap() {
      var data = new google.visualization.DataTable();
      data.addColumn('string', 'Location');
      data.addColumn('string', 'Number');
      var mapdata = [['New York', 'New York: 1']];
      var stat = {};

      var dateStat = {};
      var dateRows = [];

      var len = tweets.length > 100 ? 100 : tweets.length;
      for(var i=0; i<tweets.length; i++) {
        var city = tweets[i].user.location;
        if(city == null || city == "") {
          city = "Pittsburgh";
        }
        if(stat[city] == null) {
          stat[city] = 1;
        } else {
          stat[city] = stat[city] + 1;
        }

        var createdAt = new Date(tweets[i].created_at);
        var day = createdAt.getDate();// + '/' + (createdAt.getMonth()+1);
        // var day = new Date(2015, createdAt.getMonth(), createdAt.getDate());
        dateStat[day] = dateStat[day] == null ? 1 : dateStat[day] + 1;
      }

      for(var cityName in stat) {
        mapdata.push([cityName, cityName + ": " + stat[cityName]]);
      }
      for(var day in dateStat) {
        dateRows.push([day, dateStat[day]]);
        // dateRows.push([new Date(day), dateStat[day]]);
      }

      data.addRows(mapdata);

      var options = {showTip: true, mapType: 'normal', zoomLevel: 2};
      var map = new google.visualization.Map(document.getElementById('map_area'));
      map.draw(data, options);

      drawChart(dateRows);
      // drawAnnotation(dateRows);
  };

function drawChart(dateRows) {
      var data = new google.visualization.DataTable();
      data.addColumn('string', 'Date');
      data.addColumn('number', 'Comment Number');

      data.addRows(dateRows);

      var options = {
        chart: {
          title: 'Comment Statistics by Date and Location'
        },
        legend: { position: 'none', alignment: 'start' },
        width: 600,
        height: 400,
        pointSize: 5
      };

      var chart = new google.charts.Line(document.getElementById('chart_area'));

      chart.draw(data, options);
    }
function drawAnnotation(dateRows) {
      var data = new google.visualization.DataTable();
      data.addColumn('date', 'Date');
      data.addColumn('number', 'Comment Number');

      data.addRows(dateRows);

      var options = {
        chart: {
          title: 'Comment Statistics by Date and Location'
        },
        legend: { position: 'none', alignment: 'start' },
        width: 600,
        height: 400,
        pointSize: 5
      };

      var chart = new google.visualization.AnnotationChart(document.getElementById('chart_area'));

      chart.draw(data, options);
    }