<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="resources/css/css.css">
<title>쪽지 답장하기</title>
<script src="https://code.jquery.com/jquery-3.6.3.min.js"></script>
<style>
</style>
</head>
<body>
	<h3>쪽지 답장하기</h3>
	<form action="msgReply.do" method="get">
		<table>
			<tr>
				<th>받는 사람</th>
				<td><input type="text" name="id_receive" value="${id_send}" readonly/></td>
			</tr>
			<tr>
				<th>보내는 사람</th>
				<td><input type="text" name="id_send" value="${sessionScope.loginId}" readonly/></td>
			</tr>
			<tr>
				<th>쪽지 내용</th>
				<td><input type="text" name="msg_content" value=""></td>
			</tr>
			<tr>
				<th>답장 하기</th>
				<td><button>답장 하기</button></td>
			</tr>
		</table>
	</form>
</body>
<script>
</script>
</html>