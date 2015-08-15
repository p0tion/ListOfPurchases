<%--
  User: Acer
  Date: 28.07.2015
  Time: 7:37
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Log in</title>
    <link rel="stylesheet" href="../../resources/css/normalize.css">
    <link rel="stylesheet" href="../../resources/css/signUp.css">
    <script src="../../resources/js/signUp.js" defer="defer"></script>
</head>
<body>
<section class="signupform cf">
  <form:form commandName="signUpForm" method="post" action="/signUp"
             name="login">
           <%--onsubmit="validateForm()">--%>
    <ul>
      <li>
        <label for="firstName">First Name</label>
        <form:input
                path="firstName"
                type="text"
                name="firstName"
                id="firstName"
                size="15"
                title="Latin chars only. Size from 2 to 15"
                placeholder="first name"
                pattern="[A-Za-z]{2,15}"
                required = "true"
                autofocus="true"
                value="${firstName}"/>
      </li>
        <%--<form:errors path="firstName" cssStyle="color: red"/>--%>
        <label id="signUpFirstNameErrMsg"><form:errors path="firstName"/></label>
      <li>
        <label for="lastName">Last Name</label>
        <form:input
                path="lastName"
                type="text"
                name="lastName"
                id="lastName"
                size="15"
                title="Latin chars only. Size from 2 to 15"
                placeholder="last name"
                pattern="[A-Za-z]{2,15}"
                required = "true"
                value="${lastName}"/>
          <%--<form:errors path="lastName"/>--%>
      </li>
        <label id="signUpLastNameErrMsg"><form:errors path="lastName"/></label>
      <li>
        <label for="cardNumber">Card Number</label>
        <form:input
                path="cardNumber"
                type="text"
                name="cardNumber"
                id="cardNumber"
                size="15"
                title="16 digits"
                placeholder="card number"
                pattern="[0-9]{16}"
                required = "true"
                value="${cardNumber}"/>
          <%--<form:errors path="cardNumber"/>--%>
      </li>
        <label
                id="signUpCardNumberErrMsg"><form:errors path="cardNumber"/></label>
      <li>
        <label for="login">Login</label>
        <form:input
                path="login"
                type="text"
                name="login"
                id="login"
                size="15"
                title="Latin chars, digits or underscore. Size from 2 to 15"
                placeholder="login"
                pattern="[A-Za-z0-9_]{2,15}"
                required = "true"
                value="${login}"/>
          <%--<form:errors path="login"/>--%>
      </li>
        <label id="signUpLoginErrMsg"><form:errors path="login"/></label>
      <li>
        <label for="password">Password</label>
        <form:input
                path="password"
                type="password"
                name="password"
                id="password"
                size="15"
                title="At least 6 chars including UPPER/lower case and digits"
                placeholder="password"
                pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}"
                required = "true"
                value="${password}"/>
          <%--<form:errors path="password"/>--%>
      </li>
        <label id="signUpPasswordErrMsg"><form:errors path="password"/></label>
      <li>
        <label for="confirm_password">Confirm Password</label>
        <input  type="password"
                name="confirm_password"
                id="confirm_password"
                size="15"
                title="Must be the same password as above"
                placeholder="confirm password"
                pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}"
                required = "true"
                value="${password}"/>
      </li>
        <label id="signUpConfirmPasswordErrMsg">${confirmPasswordErrMsg}</label>
      <li>
        <input type="submit" formnovalidate="true" value="Sign up"/>
        <a id="sign_href" href="/signIn">Or sign in</a>
      </li>
    </ul>
  </form:form>
</section>
</body>
</html>
