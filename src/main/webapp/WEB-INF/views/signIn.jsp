<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  User: Anton Tulskih
  Date: 28.07.2015
  Time: 7:37
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<spring:message code="signIn.pageTitle" var="pageTitle"/>
<spring:message code="signIn.loginLabel" var="loginLabel"/>
<spring:message code="signIn.loginPlaceholder" var="loginPlaceholder"/>
<spring:message code="signIn.passwordLabel" var="passwordLabel"/>
<spring:message code="signIn.passwordPlaceholder" var="passwordPlaceholder"/>
<spring:message code="signIn.signInButton" var="signInButton"/>
<spring:message code="signIn.orSignUpLink" var="orSignUpLabel"/>
<spring:message code="signIn.incorrectLoginOrPassword" var="errorMsg"/>

<html>
<head>
    <title>${pageTitle}</title>
  <link rel="stylesheet" href="../../resources/css/signIn.css">
</head>
<body>
<div class="localeUrls">
    <a href="?lang=en">en</a> | <a href="?lang=ru">ru</a>
</div>
<section class="signinform cf">
  <form name="login" action="signIn" method="post"
        accept-charset="utf-8">
      <c:if test="${errorMessage eq 'TRUE'}">
          <div id="errorMsgDiv">
              <label id="signInLoginErrMsg">${errorMsg}</label>
          </div>
      </c:if>
    <ul>
      <li>
        <label for="login" id="loginLabel">${loginLabel}</label>
        <input type="text"
               name="login"
               id="login"
               placeholder="${loginPlaceholder}"
               required
               autofocus="true"
               value="${login}"/>
      </li>
      <li>
        <label for="password" id="passwordLabel">${passwordLabel}</label>
        <input type="password"
               name="password"
               id="password"
               placeholder="${passwordPlaceholder}"
               required
               value="${password}"/>
      </li>
      <li>
        <input type="submit" value="${signInButton}">
        <a href="/signUp">${orSignUpLabel}</a>
      </li>
    </ul>
  </form>
</section>
</body>
</html>
