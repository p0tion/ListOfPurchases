<%--
  User: Acer
  Date: 28.07.2015
  Time: 7:37
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log in</title>
  <link rel="stylesheet" href="../css/normalize.css">
  <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<%--<form id="example7" method="POST" action="#example7">--%>
  <%--<fieldset>--%>
    <%--<legend>Change Password</legend>--%>
    <%--<label for="field_username7">Username</label><span><input id="field_username7" title="Username must not be blank and contain only letters, numbers and underscores." type="text" required="" pattern="\w+" name="username"></span>--%>
    <%--<label for="field_pwd7">Password</label><span><input id="field_pwd7" title="Password must contain at least 6 characters, including UPPER/lowercase and numbers." type="password" required="" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}" name="pwd1"></span>--%>
    <%--<label for="field_pwd8">Confirm Password</label><span><input id="field_pwd8" title="Please enter the same Password as above." type="password" required="" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}" name="pwd2"></span>--%>
    <%--<span><input type="submit" value="Submit"></span>--%>
  <%--</fieldset>--%>
<%--</form>--%>
<section class="signinform cf">
  <form name="login" action="/signIn" method="post"
        accept-charset="utf-8">
      <label
              style="position: absolute; top: 220px; left: 600px; color: red;">${errorMessage}</label>
    <ul>
      <li>
        <label for="login">Login</label>
        <input type="text"
               name="login"
               id="login"
               placeholder="login"
               required
               autofocus="true"
               value="${login}">
      </li>
      <li>
        <label for="password">Password</label>
        <input type="password"
               name="password"
               id="password"
               placeholder="password"
               required
               value="${password}">
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
