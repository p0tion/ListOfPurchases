<%--
  User: Acer
  Date: 28.07.2015
  Time: 7:37
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log in</title>
  <link rel="stylesheet" href="../resources/css/normalize.css">
  <link rel="stylesheet" href="../resources/css/signIn.css">
</head>
<body>
<section class="signinform cf">
  <form name="login" action="/signIn" method="post"
        accept-charset="utf-8">
      <label id="signInLoginErrMsg">${errorMessage}</label>
    <ul>
      <li>
        <label for="login">Login</label>
        <input type="text"
               name="login"
               id="login"
               placeholder="login"
               required
               autofocus="true"
               value="${login}"/>
      </li>
      <li>
        <label for="password">Password</label>
        <input type="password"
               name="password"
               id="password"
               placeholder="password"
               required
               value="${password}"/>
      </li>
      <li>
        <input type="submit" value="Sign in">
        <a id="sign_href" href="/signUp">Or sign up</a>
      </li>
    </ul>
  </form>
</section>
</body>
</html>
