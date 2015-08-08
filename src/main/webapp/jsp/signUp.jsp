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
  <link rel="stylesheet" href="../resources/css/signUp.css">
</head>
<body>
<section class="signupform cf">
  <form method="post" action="/signUp" name="login"
        onsubmit="validateForm()">
    <ul>
      <li>
        <label for="firstName">First Name</label>
        <input type="text"
               name="firstName"
               id="firstName"
               size="15"
               title="Latin chars only. Size from 2 to 15"
               placeholder="first name"
               pattern="[A-Za-z]{2,15}"
               required
               autofocus="true"
               value="${firstName}"/>
      </li>
      <li>
        <label for="lastName">Last Name</label>
        <input type="text"
               name="lastName"
               id="lastName"
               size="15"
               title="Latin chars only. Size from 2 to 15"
               placeholder="last name"
               pattern="[A-Za-z]{2,15}"
               required
               value="${lastName}"/>
      </li>
      <li>
        <label for="cardNumber">Card Number</label>
        <input type="text"
               name="cardNumber"
               id="cardNumber"
               size="15"
               title="16 digits"
               placeholder="card number"
               pattern="[0-9]{16}"
               required
               value="${cardNumber}"/>
      </li>
        <label id="cardNumberErrMsg">${cardNumberError}</label>
      <li>
        <label for="login">Login</label>
        <input type="text"
               name="login"
               id="login"
               size="15"
               title="Latin chars, digits or underscore. Size from 2 to 15"
               placeholder="login"
               pattern="[A-Za-z0-9_]{2,15}"
               required
               value="${login}"/>
          <label id="signUpLoginErrMsg">${loginError}</label>
      </li>
      <li>
        <label for="password">Password</label>
        <input type="password"
               name="password"
               id="password"
               size="15"
               title="At least 6 chars including UPPER/lower case and digits"
               placeholder="password"
               pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}"
               required
               value="${password}"/>
      </li>
      <li>
        <label for="confirm_password">Confirm Password</label>
        <input type="password"
               name="confirm_password"
               id="confirm_password"
               size="15"
               title="Must be the same password as above"
               placeholder="confirm password"
               pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}"
               required
               value="${password}"/>
      </li>
      <li>
        <input type="submit" title="Sign up" value="Sign up"/>
        <a id="sign_href" href="/signIn">Or sign in</a>
      </li>
    </ul>
  </form>
</section>
</body>
</html>
