<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Social Travelling</title>
<link
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"
	rel="stylesheet">
<link href="css/bootstrap-table.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/tweetmain.css">



<link href="css/bootstrap-table.min.css" rel="stylesheet">

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<link href="css/1.css" rel="stylesheet" type="text.css" />
<link rel="stylesheet" type="text/css"
	href="css/jquery.fancybox-1.3.4.css" media="screen" />
<style type="text/css">
body {
	padding-top: 50px;
}

.starter-template {
	padding: 40px 15px;
	/*text-align: center;*/
}
</style>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript"
	src="scripts/jquery.fancybox-1.3.4.pack.js"></script>
<script type="text/javascript"
	src="scripts/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="scripts/flickrgetpoto.js"></script>
<script src="js/jquery-2.1.3.js"></script>

</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Coast and Beach</span> <span class="icon-bar"></span>
					<span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Social Travelling</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<!-- <li class="active"><a href="#"></a></li> -->
				</ul>
				<div class="col-sm-6 col-md-6 pull-left">
					<form class="navbar-form" role="search" method="post" id="from">
						<div class="input-group col-lg-8">
							<input type="text" class="form-control" placeholder="Search"
								name="q" id="search">
							<div class="input-group-btn">
								<button class="btn btn-default" type="submit" id="search-btn">
									<i class="glyphicon glyphicon-search"></i>
								</button>
							</div>
						</div>
					</form>
				</div>
				<div class="col-sm-1 col-md-1 pull-right">
					<div class="collapse navbar-collapse">
						<a class="navbar-brand" href="logout.do">Logout</a>
					</div>
				</div>
				<div class="col-sm-1 col-md-1 pull-right">
					<div class="collapse navbar-collapse">
						<a class="navbar-brand" href="#">${name}</a>
					</div>
				</div>

			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>


	<div class="navbar-collapse collapse navbar-responsive-collapse"
		id="hot">
		<div class="nav navbar-nav">
			<span style="color: DarkCyan; font: 12"><strong>Search
					History :</strong></span>
		</div>
		<div class="nav navbar-nav" id="con"></div>
	</div>


	<div class="mainContent">

		<div id="com"></div>
		<div class="navbar-collapse collapse navbar-responsive-collapse"
			id="cc" style="align: center">
			<ul class="nav navbar-nav">
				<li class="active"><img src="images/u.jpg" width="160"
					height="100" border-radius:"8"/>
				<li class="active"><img src="images/u.jpg" width="160"
					height="100" /></li>
				<li class="active"><img src="images/u.jpg" width="160"
					height="100" /></li>
				<li class="active"><img src="images/u.jpg" width="160"
					height="100" /></li>
				<li class="active"><img src="images/u.jpg" width="160"
					height="100" /></li>
				<li class="active"><img src="images/u.jpg" width="160"
					height="100" /></li>
				<li class="active"><img src="images/u.jpg" width="160"
					height="100" /></li>
			</ul>
		</div>
	</div>




	<div class="container">
		<div class="starter-template">
			<div class="col-lg-4">
				<div class="comment">
					<form method="post" id="comform">
						<span style="color: DarkCyan"><strong>Write Your
								Comment :</strong></span>
						<input type="text" id="comtag" name="status"/>
 					<br><input type="button" class="btn btn-primary" value="comment" id="comsub">
					</form>
					<script type="text/javascript">
						$("#comsub").click(
								function() {
									$.post('ajax_tweet.do', $('form#comform')
											.serialize(), 'json' // I expect a JSON response
									);
								});
					</script>
				</div>
				<div id="tweet-frame"></div>
			</div>
			<div class="col-lg-1"></div>
			<div class="col-lg-7">
				<div id="chart_area"
					style="position: relative; height: 400px; overflow: hidden; transform: translateZ(0px);"></div>
				<br />
				<div id="map_area"
					style="position: relative; height: 400px; overflow: hidden; transform: translateZ(0px); background-color: rgb(229, 227, 223);"></div>
			</div>
		</div>
	</div>

	<script
		src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="scripts/moment.js"></script>
	<script src="http://fb.me/react-0.12.2.js"></script>
	<script type="text/javascript" src="scripts/tweetwatch.js"></script>
	<script type="text/javascript" src="scripts/tweetscript.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#search").val(q);
			searchTrigger(q);
			searchPics(q);
		});
	</script>
</body>
</html>