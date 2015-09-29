<%--
  User: Acer
  Date: 28.07.2015
  Time: 7:37
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message key="signUp.pageTitle" var="pageTitle"/>
<spring:message key="customer.firstName" var="firstNameLabel"/>
<spring:message key="customer.firstName.small" var="firstNamePlaceholder"/>
<spring:message key="customer.firstName.title" var="firstNameTitle"/>
<spring:message key="customer.lastName" var="lastNameLabel"/>
<spring:message key="customer.lastName.small" var="lastNamePlaceholder"/>
<spring:message key="customer.lastName.title" var="lastNameTitle"/>
<spring:message key="customer.cardNumber" var="cardNumberLabel"/>
<spring:message key="customer.cardNumber.small" var="cardNumberPlaceholder"/>
<spring:message key="customer.cardNumber.title" var="cardNumberTitle"/>
<spring:message key="customer.login" var="loginLabel"/>
<spring:message key="customer.login.small" var="loginPlaceholder"/>
<spring:message key="customer.login.title" var="loginTitle"/>
<spring:message key="customer.password" var="passwordLabel"/>
<spring:message key="customer.password.small" var="passwordPlaceholder"/>
<spring:message key="customer.password.title" var="passwordTitle"/>
<spring:message key="customer.confirmPassword" var="confirmPasswordLabel"/>
<spring:message key="customer.confirmPassword.small"
                var="confirmPasswordPlaceholder"/>
<spring:message key="customer.confirmPassword.title" var="confirmPasswordTitle"/>
<spring:message key="signUp.signUpButton" var="signUpButton"/>
<spring:message key="signUp.orSignInLink" var="orSignInLink"/>
<spring:message key="signUp.passwordsDontMatch" var="passwordsDontMatchErrMsg"/>

<html>
<head>
    <title>${pageTitle}</title>
    <link rel="stylesheet" href="../../resources/css/signUp.css">
    <script src="../../resources/js/signUp.js" defer="defer"></script>
</head>
<body>
<section class="signupform cf">
    <form:form commandName="signUpForm" method="post" action="/signUp"
    name="login">
        <ul>
      <li>
        <label for="firstName">${firstNameLabel}</label>
          <div class="field">
              <form:input
                      path="firstName"
                      type="text"
                      name="firstName"
                      id="firstName"
                      size="15"
                      title="${firstNameTitle}"
                      placeholder="${firstNamePlaceholder}"
                      pattern="[A-Za-z]{2,15}"
                      required = "true"
                      autofocus="true"
                      value="${firstName}"/>
              <label id="signUpErrMsg"><form:errors path="firstName"/></label>
          </div>

        <label for="lastName">${lastNameLabel}</label>
        <div class="field">
            <form:input
                    path="lastName"
                    type="text"
                    name="lastName"
                    id="lastName"
                    size="15"
                    title="${lastNameTitle}"
                    placeholder="${lastNamePlaceholder}"
                    pattern="[A-Za-z]{2,15}"
                    required = "true"
                    value="${lastName}"/>
            <label id="signUpErrMsg"><form:errors path="lastName"/></label>
        </div>
      </li>
      <li>
        <label for="cardNumber">${cardNumberLabel}</label>
        <div class="field">
            <form:input
                    path="cardNumber"
                    type="text"
                    name="cardNumber"
                    id="cardNumber"
                    size="15"
                    title="${cardNumberTitle}"
                    placeholder="${cardNumberPlaceholder}"
                    pattern="[0-9]{16}"
                    required = "true"
                    value="${cardNumber}"/>
            <label id="signUpErrMsg"><form:errors path="cardNumber"/></label>
        </div>
      </li>
      <li>
        <label for="login">${loginLabel}</label>
        <div class="field">
            <form:input
                    path="login"
                    type="text"
                    name="login"
                    id="login"
                    size="15"
                    title="${loginTitle}"
                    placeholder="${loginPlaceholder}"
                    pattern="[A-Za-z0-9_]{2,15}"
                    required = "true"
                    value="${login}"/>
            <label id="signUpErrMsg"><form:errors path="login"/></label>
        </div>
      </li>
      <li>
        <label for="password">${passwordLabel}</label>
        <div class="field">
            <form:input
                    path="password"
                    type="password"
                    name="password"
                    id="password"
                    size="15"
                    title="${passwordTitle}"
                    placeholder="${passwordPlaceholder}"
                    pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}"
                    required = "true"
                    value="${password}"/>
                <label id="signUpErrMsg"><form:errors path="password"/></label>
        </div>
      </li>
      <li>
        <label for="confirm_password">${confirmPasswordLabel}</label>
        <div class="field">
            <input  type="password"
                    name="confirm_password"
                    id="confirm_password"
                    size="15"
                    title="${confirmPasswordTitle}"
                    placeholder="${confirmPasswordPlaceholder}"
                    pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}"
                    required = "true"
                    value="${password}"/>
            <c:if test="${confirmPasswordErrMsg eq 'TRUE'}">
                <label id="signUpErrMsg">${passwordsDontMatchErrMsg}</label>
            </c:if>
        </div>
      </li>
      <li>
        <input type="submit" formnovalidate="true" value="${signUpButton}"/>
        <a href="${pageContext.request.contextPath}/signIn">${orSignInLink}</a>
      </li>
    </ul>
    </form:form>
</section>
<div class="localeUrls">
    <a href="?lang=en">en</a> | <a href="?lang=ru">ru</a>
</div>
</body>
</html>
