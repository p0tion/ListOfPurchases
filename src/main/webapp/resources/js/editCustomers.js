function appendRow(tableId) {
    var table = document.getElementById(tableId);
    var row = table.insertRow(table.rows.length);

    // text cell for ID
    var cellText = row.insertCell(0);
    var textNode = document.createTextNode("");
    cellText.appendChild(textNode);

    // input text cell for first name
    var cellInputText = row.insertCell(1);
    var el = document.createElement('input');
    el.setAttribute('type', 'text');
    el.setAttribute('name', 'firstName');
    el.setAttribute('id', 'firstName');
    el.setAttribute('size', '15');
    el.setAttribute('title', 'Latin chars only. Size from 2 to 20');
    el.setAttribute('placeholder', 'first name');
    el.setAttribute('required', "");
    el.setAttribute('pattern', "[A-Za-z]{2,20}");
    el.setAttribute('value', "");
    cellInputText.appendChild(el);

    // input text cell for last name
    var cellInputText2 = row.insertCell(2);
    var el2 = document.createElement('input');
    el2.setAttribute('type', 'text');
    el2.setAttribute('name', 'lastName');
    el2.setAttribute('id', 'lastName');
    el2.setAttribute('size', '15');
    el2.setAttribute('title', 'Latin chars only. Size from 2 to 20');
    el2.setAttribute('placeholder', 'last name');
    el2.setAttribute('required', "");
    el2.setAttribute('pattern', "[A-Za-z]{2,20}");
    el2.setAttribute('value', "");
    cellInputText2.appendChild(el2);

    // input text cell for card number
    var cellInputText3 = row.insertCell(3);
    var el3 = document.createElement('input');
    el3.setAttribute('type', 'text');
    el3.setAttribute('name', 'cardNumber');
    el3.setAttribute('id', 'cardNumber');
    el3.setAttribute('size', '15');
    el3.setAttribute('title', '16 digits');
    el3.setAttribute('placeholder', 'card number');
    el3.setAttribute('required', "");
    el3.setAttribute('pattern', "[0-9]{16}");
    el3.setAttribute('value', "");
    cellInputText3.appendChild(el3);

    // input text cell for login
    var cellInputText4 = row.insertCell(4);
    var el4 = document.createElement('input');
    el4.setAttribute('type', 'text');
    el4.setAttribute('name', 'login');
    el4.setAttribute('id', 'login');
    el4.setAttribute('size', '15');
    el4.setAttribute('title', 'Latin chars, digits or underscore. Size from 2 to 15');
    el4.setAttribute('placeholder', 'login');
    el4.setAttribute('required', "");
    el4.setAttribute('pattern', "[A-Za-z0-9_]{2,15}");
    el4.setAttribute('value', "");
    cellInputText4.appendChild(el4);

    // input text cell for password
    var cellInputText5 = row.insertCell(5);
    var el5 = document.createElement('input');
    el5.setAttribute('type', 'text');
    el5.setAttribute('name', 'password');
    el5.setAttribute('id', 'password');
    el5.setAttribute('size', '15');
    el5.setAttribute('title', 'At least 6 chars including UPPER/lower case and digits');
    el5.setAttribute('placeholder', 'password');
    el5.setAttribute('required', "");
    el5.setAttribute('pattern', "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}");
    el5.setAttribute('value', "");
    cellInputText5.appendChild(el5);

    // select cell for checkbox
    var cellChck = row.insertCell(6);
    cellChck.setAttribute("class", "hiddenTd");
    var chck = document.createElement('input');
    chck.setAttribute('type', 'checkbox');
    chck.setAttribute('name', 'child');
    chck.setAttribute('id', 'checkBox');
    chck.setAttribute('title', 'Check to delete');
    chck.setAttribute('tabindex', '-1');
    cellChck.appendChild(chck);

    // scroll the window to the bottom
    window.scrollTo(0, document.body.scrollHeight);

    // place caret into first name input area
    el.select();
}

function deleteCustomer(tableID) {
    var table = document.getElementById(tableID).tBodies[0];
    var rowCount = table.rows.length;
    for(var i=1; i<rowCount; i++) {
        var row = table.rows[i];
        var chkbox = row.cells[6].getElementsByTagName('input')[0];
        if(null != chkbox && true == chkbox.checked) {
            table.deleteRow(i);
            rowCount--;
            i--;
        }
    }
}

function restore() {
    location.reload();
}

function checkAll() {
    var parent = document.getElementById("mainCheckBox");
    var child = document.getElementsByName('child');
    for (var i = 0; i < child.length; i++) {
        child[i].checked = parent.checked;
    }
}