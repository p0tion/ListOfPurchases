function appendRow(tableId, nameTitle, namePlaceholder, descriptionTitle,
                    descriptionPlaceholder, priceTitle, pricePlaceholder,
                    selectTitle) {

    var table = document.getElementById(tableId);
    var row = table.insertRow(table.rows.length);

    // text cell for ID
    var cellText = row.insertCell(0);
    var textNode = document.createTextNode("");
    cellText.appendChild(textNode);

    // input text cell for name
    var cellInputText = row.insertCell(1);
    var el = document.createElement('input');
    el.setAttribute('type', 'text');
    el.setAttribute('name', 'name');
    el.setAttribute('id', 'nameCell');
    el.setAttribute('size', '15');
    el.setAttribute('title', nameTitle);
    el.setAttribute('placeholder', namePlaceholder);
    el.setAttribute('required', "");
    el.setAttribute('pattern', "[A-Za-z .,!?:;+&0-9()/\\@\'&#34;<>$-]{2,15}");
    el.setAttribute('value', "");
    cellInputText.appendChild(el);

    // input text cell for description
    var cellInputText2 = row.insertCell(2);
    var el2 = document.createElement('input');
    el2.setAttribute('type', 'text');
    el2.setAttribute('name', 'description');
    el2.setAttribute('id', 'descriptionCell');
    el2.setAttribute('size', '25');
    el2.setAttribute('title', descriptionTitle);
    el2.setAttribute('placeholder', descriptionPlaceholder);
    el2.setAttribute('required', "");
    el2.setAttribute('pattern', "[A-Za-z .,!?:;+&0-9()/\\@\'&#34;<>$-]{2,25}");
    el2.setAttribute('value', "");
    cellInputText2.appendChild(el2);

    // input text cell for price
    var cellInputText3 = row.insertCell(3);
    var el3 = document.createElement('input');
    el3.setAttribute('type', 'text');
    el3.setAttribute('name', 'price');
    el3.setAttribute('id', 'priceCell');
    el3.setAttribute('size', '3');
    el3.setAttribute('title', priceTitle);
    el3.setAttribute('placeholder', pricePlaceholder);
    el3.setAttribute('required', "");
    el3.setAttribute('pattern', "[0-9]{1,3}[.][0-9]{1,2}");
    el3.setAttribute('value', "");
    cellInputText3.appendChild(el3);

    // select cell for checkbox
    var cellChck = row.insertCell(4);
    cellChck.setAttribute("class", "hiddenTd");
    var chck = document.createElement('input');
    chck.setAttribute('type', 'checkbox');
    chck.setAttribute('name', 'child');
    chck.setAttribute('id', 'checkBox');
    chck.setAttribute('title', selectTitle);
    chck.setAttribute('tabindex', '-1');
    cellChck.appendChild(chck);

    // scroll the window to the bottom
    window.scrollTo(0, document.body.scrollHeight);

    // place caret into name input area
    el.select();
}

function deleteProduct(tableID) {
    var table = document.getElementById(tableID).tBodies[0];
    var rowCount = table.rows.length;
    for(var i=1; i<rowCount; i++) {
        var row = table.rows[i];
        var chkbox = row.cells[4].getElementsByTagName('input')[0];
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
