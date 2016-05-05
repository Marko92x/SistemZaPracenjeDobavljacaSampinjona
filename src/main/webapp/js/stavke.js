/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
//    alert(getCookie("dnevnaberba"));
    ucitajStavke();
    dugmici("DDD");
});

var table = document.getElementById('tblStavke');



function ucitajStavke() {
    $.ajax({
        type: "GET",
        url: getCookie("basicURL") + "rest/stavka/" + getCookie("dnevnaberba"),
        dataType: "json",
        headers: {
            'Authorization': getCookie("token"),
            'Content-Type': 'application/json'
        },
        success: function (response) {
            var empty = document.getElementById("empty");
            empty.innerHTML = "";
            popuniTabelu(response);
        },
        error: function (response) {
            if (JSON.parse(response.responseText).errorMessage === "Nema stavki za ovu dnevnu berbu!") {
                var dnevneBerbe = [];
                popuniTabelu(dnevneBerbe);
                var empty = document.getElementById("empty");
                empty.innerHTML = "<h3 style=\"color: white;\">Prazno</h3>";
            } else {
                alert(JSON.parse(response.responseText).errorMessage);
                window.location.href = "dobavljaci.html";
            }

        }
    });
}



function popuniTabelu(stavke) {
    table.innerHTML = "";
    var table_body = document.createElement('TBODY');
    table.appendChild(table_body);
    var tHead = document.createElement('THEAD');
    var arrayHeader = ["Tacne", "CenaTacni", "PrvaKlasa", "CenaPrveKlase", "DrugaKlasa", "CenaDrugeKlase", "TrecaKlasa", "CenaTreceKlase", ""];

    for (var i = 0; i < arrayHeader.length; i++) {
        tHead.appendChild(document.createElement("TH")).appendChild(document.createTextNode(arrayHeader[i]));
    }
    table.appendChild(tHead);
    for (var x = 0; x < stavke.length; x++) {
        var tr = document.createElement('TR');
        table_body.appendChild(tr);

        for (var j = 0; j < 9; j++) {
            var td = document.createElement('TD');
            switch (j) {
                case 0:
                    td.id = "td_id_" + stavke[x].stavkadnevneberbePK.stavkaid;
                    td.appendChild(document.createTextNode(stavke[x].tacne));
                    break;
                case 1:
                    td.appendChild(document.createTextNode(stavke[x].cenatacne));
                    break;
                case 2:
                    td.appendChild(document.createTextNode(stavke[x].prvaklasa));
                    break;
                case 3:
                    td.appendChild(document.createTextNode(stavke[x].cenaprvaklasa));
                    break;
                case 4:
                    td.appendChild(document.createTextNode(stavke[x].drugaklasa));
                    break;
                case 5:
                    td.appendChild(document.createTextNode(stavke[x].cenadrugaklasa));
                    break;
                case 6:
                    td.appendChild(document.createTextNode(stavke[x].trecaklasa));
                    break;
                case 7:
                    td.appendChild(document.createTextNode(stavke[x].cenatrecaklasa));
                    break;
                case 8:
                    var b = document.createElement('BUTTON');
                    b.className = "button btn-danger";
                    b.appendChild(document.createTextNode("Obriši"));
                    b.id = "DDD" + stavke[x].stavkadnevneberbePK.stavkaid;
                    td.appendChild(b);
                    break;
                default:
            }
            tr.appendChild(td);
        }
    }
    table.appendChild(tHead);
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

var dnevnaBerbaId;
var listaStavkiJson = [];

var dialogTable = document.getElementById('tblDialogStavke');
var dialogTableBody = popuniDialogTabelu();



function popuniDialogTabelu() {
    dialogTable.innerHTML = "";
    var table_body = document.createElement('TBODY');
    dialogTable.appendChild(table_body);
    var tHead = document.createElement('THEAD');
    var arrayHeader = ["Tacne", "PrvaKlasa", "DrugaKlasa", "TrecaKlasa", ""];

    for (var i = 0; i < arrayHeader.length; i++) {
        tHead.appendChild(document.createElement("TH")).appendChild(document.createTextNode(arrayHeader[i]));
    }
    dialogTable.appendChild(tHead);
    return table_body;
}
function dodajUTabelu(json, index) {
    for (var x = 0; x < json.length; x++) {
        var tr = document.createElement('TR');
        dialogTableBody.appendChild(tr);

        for (var j = 0; j < 5; j++) {
            var td = document.createElement('TD');
            switch (j) {
                case 0:
                    td.id = "td_id_" + json[x].tacne;
                    td.appendChild(document.createTextNode(json[x].tacne));
                    break;
                case 1:
                    td.appendChild(document.createTextNode(json[x].prvaklasa));
                    break;
                case 2:
                    td.appendChild(document.createTextNode(json[x].drugaklasa));
                    break;
                case 3:
                    td.appendChild(document.createTextNode(json[x].trecaklasa));
                    break;
                case 4:
                    var b = document.createElement('BUTTON');
                    b.className = "button btn-danger";
                    b.onclick = function () {
                        var p = this.parentNode.parentNode;
                        p.parentNode.removeChild(p);
                        listaStavki.splice(index - 1, 1);
                    }
                    b.appendChild(document.createTextNode("Obriši"));
                    td.appendChild(b);
                default:
            }
            tr.appendChild(td);
        }
    }
}

var listaStavki = [];

$(function () {




    var dialog, form;

    function checkTrue(x, y) {
        if ((x.val() === "" && y.val() !== "") || (x.val() !== "" && y.val() === "")) {
            return false;
        }
        return true;
    }


    function dodaj() {

        var tacne = $('#tacne').val();
        var cenaTacne = $('#cenaTacne').val();
        var prvaKlasa = $('#prvaKlasa').val();
        var cenaPrveKlase = $('#cenaPrveKlase').val();
        var drugaKlasa = $('#drugaKlasa').val();
        var cenaDrugeKlase = $('#cenaDrugeKlase').val();
        var trecaKlasa = $('#trecaKlasa').val();
        var cenaTreceKlase = $('#cenaTreceKlase').val();

        if (tacne === "") {
            tacne = 0;
        } else {
            tacne = parseInt(tacne);
        }
        if (cenaTacne === "") {
            cenaTacne = 0;
        } else {
            cenaTacne = parseInt(cenaTacne);
        }
        if (prvaKlasa === "") {
            prvaKlasa = 0;
        } else {
            prvaKlasa = parseInt(prvaKlasa);
        }
        if (cenaPrveKlase === "") {
            cenaPrveKlase = 0;
        } else {
            cenaPrveKlase = parseInt(cenaPrveKlase);
        }
        if (drugaKlasa === "") {
            drugaKlasa = 0;
        } else {
            drugaKlasa = parseInt(drugaKlasa);
        }
        if (cenaDrugeKlase === "") {
            cenaDrugeKlase = 0;
        } else {
            cenaDrugeKlase = parseInt(cenaDrugeKlase);
        }
        if (trecaKlasa === "") {
            trecaKlasa = 0;
        } else {
            trecaKlasa = parseInt(trecaKlasa);
        }
        if (cenaTreceKlase === "") {
            cenaTreceKlase = 0;
        } else {
            cenaTreceKlase = parseInt(cenaTreceKlase);
        }

        if (isNaN(tacne) || isNaN(cenaTacne) || isNaN(prvaKlasa) || isNaN(cenaPrveKlase) || isNaN(drugaKlasa) || isNaN(cenaDrugeKlase) || isNaN(trecaKlasa) || isNaN(cenaTreceKlase)) {
            alert("Niste ispravno uneli podatke!");
        } else {

            var jsonS = {
                "tacne": tacne,
                "prvaklasa": prvaKlasa,
                "drugaklasa": drugaKlasa,
                "trecaklasa": trecaKlasa,
                "cenatacne": cenaTacne,
                "cenaprvaklasa": cenaPrveKlase,
                "cenadrugaklasa": cenaDrugeKlase,
                "cenatrecaklasa": cenaTreceKlase
            };
            var listaStavkiJson = [];
            listaStavkiJson.push(jsonS);
            var index = listaStavki.push(jsonS);
            dodajUTabelu(listaStavkiJson, index);

//            $.ajax({
//                type: "POST",
//                url: getCookie("basicURL") + "stavka",
//                data: json,
//                headers: {
//                    'Content-Type': 'application/json'
//                },
//                success: function (response) {
//                    alert("Uspesno ste kreirali stavku!");
//
//                    refresh();
//                },
//                error: function (response) {
//                    refresh();
//                }
//            });
            $('#tacne').val("");
            $('#cenaTacne').val("");
            $('#prvaKlasa').val("");
            $('#cenaPrveKlase').val("");
            $('#drugaKlasa').val("");
            $('#cenaDrugeKlase').val("");
            $('#trecaKlasa').val("");
            $('#cenaTreceKlase').val("");
        }
    }



    function sacuvaj() {
        $.ajax({
            type: "POST",
            url: getCookie("basicURL") + "rest/stavka/" + getCookie("dnevnaberba"),
            data: JSON.stringify(listaStavki),
            headers: {
                'Authorization': getCookie("token"),
                'Content-Type': 'application/json'
            },
            success: function (response) {
                alert("Uspesno ste kreirali stavke!");
                refresh();
                listaStavki = [];
                dialog.dialog("close");
//                var new_tbody = document.createElement('tbody');
//                new_tbody = popuniDialogTabelu();
//                dialogTableBody.parentNode.replaceChild(new_tbody, dialogTableBody);
                dialogTableBody = popuniDialogTabelu();
            },
            error: function (response) {
                refresh();
                alert(JSON.parse(response.responseText).errorMessage);
            }
        });
    }



    dialog = $("#dialog-form").dialog({
        autoOpen: false,
        height: 700,
        width: 510,
        modal: true,
        buttons: {
            "Dodaj stavku": dodaj,
            "Sacuvaj": sacuvaj,
            Cancel: function () {
                dialog.dialog("close");
            }
        },
        close: function () {
            form[ 0 ].reset();
        }
    });

    form = dialog.find("form").on("submit", function (event) {
        event.preventDefault();
//        dodaj();
    });


    $('#btnDodajStavku').click(function () {
        dialog.dialog("open");
    });
    function todayDate() {
        var today = new Date();
        var dd = today.getDate();
        var mm = today.getMonth() + 1; //January is 0!
        var yyyy = today.getFullYear();

        if (dd < 10) {
            dd = '0' + dd
        }

        if (mm < 10) {
            mm = '0' + mm
        }
        today = yyyy + '-' + mm + '-' + dd;
        //today = mm+'/'+dd+'/'+yyyy;
        return today;
    }

});

function refresh() {
    ucitajStavke();
    var id = 0;
    id = window.setInterval(ucitajStavke, 100);
    window.clearInterval(id);
}

function dugmici(delimiter) {
    $(function () {
        $(document).on('click', '[id^=' + delimiter + "]", function () {
            var id = jQuery(this).attr("id");
            var niz = id.split(delimiter);
            var id1 = niz[1];
            var r = confirm("Da li ste sigurni?");
            if (r === true) {
                $.ajax({
                    type: "DELETE",
                    url: getCookie("basicURL") + "rest/stavka/" + id1,
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
        });
    });
}
