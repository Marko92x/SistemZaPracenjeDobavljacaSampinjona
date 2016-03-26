/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function uspeh() {
    alert(getCookie("token"));
}

$(document).ready(function () {
    if (getCookie('token') === "") {
        window.location.href = '../index.html';
    }
    ucitajDobavljace();
    dugmici("DDD");
    dugmici("XXX");
});

function ucitajDobavljace() {
    $.ajax({
        type: "GET",
        url: getCookie("basicURL") + "rest/dobavljac",
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
        }
    });
}

function refresh() {
    ucitajDobavljace();
    var id = 0;
    id = window.setInterval(ucitajDobavljace, 100);
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

function napuniTabelu(dobavljaci) {
    if (typeof dobavljaci !== "undefined") {
        var table = document.getElementById('tblDobavljaci');
        table.innerHTML = "";
        var table_body = document.createElement('TBODY');
        table.appendChild(table_body);
        var tHead = document.createElement('THEAD');
        var arrayHeader = ["JMBG", "Ime", "Prezime", "Mesto"];

        for (var i = 0; i < arrayHeader.length; i++) {
            tHead.appendChild(document.createElement("TH")).appendChild(document.createTextNode(arrayHeader[i]));
        }
        tHead.appendChild(document.createElement("TH")).appendChild(document.createTextNode(""));
        tHead.appendChild(document.createElement("TH")).appendChild(document.createTextNode(""));
        for (var x = 0; x < dobavljaci.length; x++) {
            var tr = document.createElement('TR');
            table_body.appendChild(tr);

            for (var j = 0; j < 7; j++) {
                var td = document.createElement('TD');
                switch (j) {
                    case 0:
                        td.id = "td_id_" + dobavljaci[x].id;
                        td.appendChild(document.createTextNode(dobavljaci[x].jmbg));
                        break;
                    case 1:
                        td.appendChild(document.createTextNode(dobavljaci[x].ime));
                        break;
                    case 2:
                        td.appendChild(document.createTextNode(dobavljaci[x].prezime));
                        break;
                    case 3:
                        td.appendChild(document.createTextNode(dobavljaci[x].mesto.naziv));
                        break;
                    case 4:
                        var b = document.createElement('BUTTON');
                        b.className = "button btn-info";
                        b.appendChild(document.createTextNode("Radi"));
                        b.id = "XXX" + dobavljaci[x].jmbg;
                        td.appendChild(b);
                        break;
                    case 5:
                        var b = document.createElement('BUTTON');
                        b.className = "button btn-danger";
                        b.appendChild(document.createTextNode("Obriši"));
                        b.id = "DDD" + dobavljaci[x].jmbg;
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

function dugmici(delimiter) {
    $(function () {
        $(document).on('click', '[id^=' + delimiter + "]", function () {
            var id = jQuery(this).attr("id");
            var niz = id.split(delimiter);
            var id1 = niz[1];

            if (delimiter === 'DDD') {
                var r = confirm("Da li ste sigurni?");
                if (r === true) {
                    $.ajax({
                        url: 'rest/dobavljac/' + id1,
                        method: 'DELETE',
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': getCookie('token')
                        },
                        success: function (response) {
//                            var table = document.getElementById('tblDobavljaci');
//                            table.innerHTML = "";
//                            ucitajDobavljace();
                            refresh();
                            alert("Usprešno ste obrisali člana!");
                        },
                        error: function (response) {
                            refresh();
                        }
                    });

                }
            } else {
                      document.cookie = "dobavljac=" + id1;
                    //window.location.href = "radi.html";

                $(function() {
                    $( "#dialog-confirm" ).dialog({
                        resizable: false,
                        height:140,
                        modal: true,
                        buttons: {
                            "Zaduzenja": function() {
                                window.location.href = "radi.html";
                            },
                            "Dnevne berbe": function() {
                                window.location.href = "dnevne-berbe.html"
                            }
                        }
                    });
                });
                
            }

        });
    });
}


//case 5:
//                        var b = document.createElement('BUTTON');
//                        b.className = "button btn-info";
//                        b.appendChild(document.createTextNode("Izmeni"));
//                        b.id = "XXX" + clanovi[x].id;
//                        td.appendChild(b);
//                        break;
//                    case 6:
//                        var b = document.createElement('BUTTON');
//                        b.className = "button btn-danger";
//                        b.appendChild(document.createTextNode("Obriši"));
//                        b.id = "DDD" + clanovi[x].id;
//                        td.appendChild(b);
//                        break;