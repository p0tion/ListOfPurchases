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

//firstName.onkeyup = validateForm();
//lastName.onkeyup = validateForm;
//cardNumber.onchange = validateForm();
//login.onchange = validateForm();
//password.onchange = validateForm;
confirm_password.onchange = validateForm;
