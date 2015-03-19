

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<title>LoginPage</title>
</head>
<body>


<jsp:include page="error-list.jsp" />

<p>
	<form method="post" action="login.do">
		<table>
			
						<tr>
				<td colspan="2" align="center">
					<input type="submit" name="button" value="Login"/>
				</td>
			</tr>
		</table>
	</form>
</p>
</body>
</html>
