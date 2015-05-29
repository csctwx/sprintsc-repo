<%@ page contentType="text/html"%>
<%@ taglib prefix="ex" uri="/WEB-INF/custom.tld"%>
<html>
<head>
<title>Login UC 7 - simulation</title>
</head>
<body>
	
	<%
		// redirect jsp - isGuest.jsp logic
		//~ New location to be redirected
		//~ String site = new String("http://www.google.com");
		//~ response.setStatus(response.SC_MOVED_TEMPORARILY);
		//~ response.setHeader("Location", site);
	%>
	
	<ex:invokeAdaptrOp mdn="1234567890"/>
	
	${msg}
</body>
</html>
