
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>

<title>Enter your comment</title>
<script src="js/jquery-2.1.3.js"></script>

</head>
<body>

	<p style="font-size: medium">Enter your Status</p>

	<jsp:include page="error-list.jsp" />


	<form method="POST" id="tweetForm">
		<table>
			<tr>
				<td>Update Status:</td>
				<td><input type="text" name="status" value="" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><a href="javascript:void(0)" id="tweet"><input type="button"  value="Tweet"></a></td>
			</tr>
		</table>
	</form>
<script type="text/javascript">
	$("#tweet").click(function(){
            		$.post( 'ajax_tweet.do', $('form#tweetForm').serialize(), 'json' // I expect a JSON response
            	    );
                });
	</script>

</body>
</html>