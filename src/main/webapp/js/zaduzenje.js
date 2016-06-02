/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    if (getCookie('token') === "") {
        window.location.href = '../index.html';
    }
//    ucitajDobavljace();
    dugmici("DDD");
    dugmici("XXX");
    ucitajZaduzenja();
});

function ucitajZaduzenja() {
    $.ajax({
        type: "GET",
        url: getCookie("basicURL") + "rest/zaduzenja/" + getCookie("dobavljac"),
        dataType: "json",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getCookie('token')
        },
        success: function (response) {

//            table = '<table class="table table-condensed" id="tbl"><tr  id="act"><th>JMBG</th><th>Ime</th><th>Prezime</th><th>Mesto</th></tr>';
//            $.each(response, function (index, value) {
//                table += '<tr><td>' + value.jmbg + '</td><td>' + value.ime + '</td><td data-type="text" data-placement="right" data-title="Enter username">' + value.prezime + '</td><td data-type="text" data-placement="right" data-title="Enter username">' + value.mesto.naziv + '</td></tr>';
//            });
//            table += '</table>';
//            document.getElementById('dobavljaci').innerHTML = table;

            napuniTabelu(response);
        },
        error: function (response) {
            alert(JSON.parse(response.responseText).errorMessage);
        }
    });
}

function napuniTabelu(zaduzenja) {
    if (typeof zaduzenja !== "undefined") {
        var table = document.getElementById('tblZaduzenja');
        table.innerHTML = "";
        var table_body = document.createElement('TBODY');
        table.appendChild(table_body);
        var tHead = document.createElement('THEAD');
        var arrayHeader = ["Vrsta zaduzenja", "Datum zaduženja", "Datum razduženja", "Zadužio", "Razdužio"];

        for (var i = 0; i < arrayHeader.length; i++) {
            tHead.appendChild(document.createElement("TH")).appendChild(document.createTextNode(arrayHeader[i]));
        }
        tHead.appendChild(document.createElement("TH")).appendChild(document.createTextNode(""));
        tHead.appendChild(document.createElement("TH")).appendChild(document.createTextNode(""));
        for (var x = 0; x < zaduzenja.length; x++) {
            var tr = document.createElement('TR');
            table_body.appendChild(tr);

            for (var j = 0; j < 7; j++) {
                var td = document.createElement('TD');
                switch (j) {
                    case 0:
                        td.id = "td_id_" + zaduzenja[x].id;
                        if (zaduzenja[x].kompost === true) {
                            td.appendChild(document.createTextNode("Kompost"));
                        }
                        if (zaduzenja[x].prevoz === true) {
                            td.appendChild(document.createTextNode("Prevoz"));
                        }
                        break;
                    case 1:
                        var d = new Date(zaduzenja[x].datumzaduzenja);
                        var y = d.getUTCFullYear();
                        var da = d.getUTCDate() + 1;
                        var m = d.getUTCMonth() + 1;
                        td.appendChild(document.createTextNode(da + "." + m + "." + y + "."));
                        break;
                    case 2:
                        if (zaduzenja[x].datumrazduzenja === null) {
                            td.appendChild(document.createTextNode("Nije razduženo!"));
                        }
                        else {
                            var d = new Date(zaduzenja[x].datumrazduzenja);
                            var y = d.getUTCFullYear();
                            var da = d.getUTCDate();
                            var m = d.getUTCMonth() + 1;
                            td.appendChild(document.createTextNode(da + "." + m + "." + y + "."));
                        }
                        break;
                    case 3:
                        td.appendChild(document.createTextNode(zaduzenja[x].zaduzio.ime + " " + zaduzenja[x].zaduzio.prezime));
                        break;
                    case 4:
                        if (zaduzenja[x].datumrazduzenja === null) {
                            td.appendChild(document.createTextNode("Nije razduženo!"));
                        } else {
                            td.appendChild(document.createTextNode(zaduzenja[x].razduzio.ime + " " + zaduzenja[x].razduzio.prezime));
                        }
                        break;
                    case 5:
                        if (zaduzenja[x].datumrazduzenja === null) {
                            var b = document.createElement('BUTTON');
                            b.className = "btn btn-info";
                            b.appendChild(document.createTextNode("Razduži"));
                            b.id = "XXX" + zaduzenja[x].zaduzenjePK.zaduzenjeid;
                            td.appendChild(b);
                        } else {
                            var b = document.createElement('BUTTON');
                            b.className = "btn btn-info";
                            b.appendChild(document.createTextNode("Razduži"));
                            b.disabled = true;
                            td.appendChild(b);
                        }
                        break;
                    case 6:
                        var b = document.createElement('BUTTON');
                        b.className = "btn btn-danger";
                        b.appendChild(document.createTextNode("Obriši"));
                        b.id = "DDD" + zaduzenja[x].zaduzenjePK.zaduzenjeid;
                        td.appendChild(b);
                        break;
                    default:
                }
                tr.appendChild(td);
            }
        }
        table.appendChild(tHead);
    }
}

$("#btnDodajZaduzenje").click(function () {
    var kompost = document.getElementById('k').checked;
    var prevoz = document.getElementById('p').checked;
    var brojVreca = document.getElementById('brojVreca').value;
    if (brojVreca === "") {
        brojVreca = 0;
    } else {
        brojVreca = parseInt(brojVreca);
    }
    var jsonS = {
        "brojvreca": brojVreca,
        "kompost": kompost,
        "prevoz": prevoz
    };
    var json = JSON.stringify(jsonS);
    $.ajax({
        type: "POST",
        url: getCookie("basicURL") + "rest/zaduzenja/" + getCookie("dobavljac"),
        data: json,
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getCookie('token')
        },
        success: function (response) {
            refresh();
        },
        error: function (response) {
            alert(JSON.parse(response.responseText).errorMessage);
        }
    });
});

function dugmici(delimiter) {
    $(function () {
        $(document).on('click', '[id^=' + delimiter + "]", function () {
            var id = jQuery(this).attr("id");
            var niz = id.split(delimiter);
            var id1 = niz[1];
            if (delimiter === 'XXX') {
                var r = confirm("Da li ste sigurni?");
                if (r === true) {
                    $.ajax({
                        type: "PUT",
                        url: getCookie("basicURL") + "rest/zaduzenja/" + id1,
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': getCookie('token')
                        },
                        success: function (response) {

                            refresh();
                        },
                        error: function (response) {
                            alert(JSON.parse(response.responseText).errorMessage);
                        }
                    });
                }
            } else {
                var r = confirm("Da li ste sigurni?");
                if (r === true) {
                    $.ajax({
                        type: "DELETE",
                        url: getCookie("basicURL") + "rest/zaduzenja/" + id1,
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': getCookie('token')
                        },
                        success: function (response) {
                            refresh();
                        },
                        error: function (response) {
                            alert(JSON.parse(response.responseText).errorMessage);
                        }
                    });
                }
            }
        });
    });
}

function refresh() {
    ucitajZaduzenja();
    var id = 0;
    id = window.setInterval(ucitajZaduzenja, 100);
    window.clearInterval(id);
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ')
            c = c.substring(1);
        if (c.indexOf(name) == 0)
            return c.substring(name.length, c.length);
    }
    return "";
}