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
  <style>
    input:required:invalid,input:focus:invalid,textarea:required:invalid,textarea:focus:invalid{background-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAwAAAAMCAYAAABWdVznAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAT1JREFUeNpi/P//PwMpgImBRMACY/x7/uDX39sXt/67cMoDyOVgMjBjYFbV/8kkqcCBrIER5KS/967s+rmkXxzI5wJiRSBm/v8P7NTfHHFFl5mVdIzhGv4+u///x+xmuAlcdXPB9KeqeLgYd3bDU2ZpRRmwH4DOeAI07QXIRKipYPD35184/nn17CO4p/+cOfjl76+/X4GYAYThGn7/g+Mfh/ZZwjUA/aABpJVhpv6+dQUjZP78Z0YEK7OezS2gwltg64GmfTu6i+HL+mUMP34wgvGvL78ZOEysf8M1sGgZvQIqfA1SDAL8iUUMPIFRQLf+AmMQ4DQ0vYYSrL9vXDz2sq9LFsiX4dLRA0t8OX0SHKzi5bXf2HUMBVA0gN356N7p7xdOS3w5fAgcfNxWtn+BJi9gVVBOQfYPQIABABvRq3BwGT3OAAAAAElFTkSuQmCC);background-position:right top;background-repeat:no-repeat;box-shadow:none}
    input:required:valid,textarea:required:valid{background-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAwAAAAMCAYAAABWdVznAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAZZJREFUeNpi/P//PwMpgImBRMAy58QshrNPTzP8+vOLIUInisFQyYjhz98/DB9/fmT48/+35v7H+8KNhE2+WclZd+G0gZmJmYGThUNz1fUVMZtvbWT59eUXG9wGZIWMUPj993eJ5VeWxuy8veM/CzPL3yfvH/9H0QBSBDYZyOVm4mGYfn6q4cory5lYmFh+MrEwM/76/YsR7mk2ZjbWP///WP37/y8cqIDhx58fjvtu7XV6//ndT34G/v8FasUsDjKO/+A2PP3wpGLd+TVsfOz8XH6KAT+nHpokcu7h6d9q/BoMxToVbBYqlt9///+1GO4/WVdpXqY/zMqXn13/+vTjI9mj94/y//v9/3e9ZRObvYbDT0Y2xnm///x+wsfHB3GSGLf41jb3rv0O8nbcR66d+HPvxf2/+YZFTHaqjl8YWBnm/vv37yly5LL8+vuLgYuVa3uf/4T/Kd8SnSTZpb6FGUXwcvJxbAPKP2VkZESNOBDx8+9PBm4OwR1TwmYwcfzjsBUQFLjOxs52A2YyKysrXANAgAEA7buhysQuIREAAAAASUVORK5CYII=);background-position:right top;background-repeat:no-repeat;box-shadow: none}
  </style>
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
        <label
                style="position: absolute; top: 252px; left: 740px; color:
                red">${cardNumberError}</label>
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
          <label
                  style="position: absolute; top: 303px; left: 740px; color:
                red">${loginError}</label>
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
<script>
    var firstName = document.getElementById("firstName");
    var lastName = document.getElementById("lastName");
    var cardNumber = document.getElementById("cardNumber");
    var login = document.getElementById("login");
    var password = document.getElementById("password");
    var confirm_password = document.getElementById("confirm_password");

    function validateForm(){
        var re = /[A-Za-z]{2,15}/;
        if (re.test(firstName.value)) {
            firstName.setCustomValidity('');
        } else {
            firstName.setCustomValidity("Wrong first name");
        }
        if (re.test(lastName.value)) {
            lastName.setCustomValidity('');
        } else {
            lastName.setCustomValidity("Wrong last name");
        }
        re = /[0-9]{16}/;
        if (re.test(cardNumber.value)) {
            cardNumber.setCustomValidity('');
        } else {
            cardNumber.setCustomValidity("Wrong card number");
        }
        re = /[A-Za-z0-9_]{2,15}/;
        if (re.test(login.value)) {
            lastName.setCustomValidity('');
        } else {
            lastName.setCustomValidity("Wrong login");
        }
        re = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}/;
        if (re.test(password.value)) {
            password.setCustomValidity('');
        } else {
            password.setCustomValidity("Wrong password");
        }
        if (re.test(confirm_password.value)) {
            confirm_password.setCustomValidity('');
        } else {
            confirm_password.setCustomValidity("Wrong password");
        }
        if(password.value != confirm_password.value) {
            confirm_password.setCustomValidity("Passwords Don't Match");
        }
    }

  confirm_password.onchange = validateForm
</script>
</html>
