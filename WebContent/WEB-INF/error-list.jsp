<!-- Aditi Silawat (asilawat)
	 HW#9
	 December 04, 2014
	 Course 08-600 -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:forEach var= "error" items= "${errors}">
	<p style="font-size:medium; color:red">${error}</p>
</c:forEach>