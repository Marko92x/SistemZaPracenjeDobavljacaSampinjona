$(document).ready(function () {
    ucitajDnevneBerbe();
    dugmici();
});

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

function ucitajDnevneBerbe() {
    $.ajax({
        type: "GET",
        url: getCookie("basicURL") + "rest/dnevnaBerba/" + getCookie("dobavljac"),
        dataType: "json",
        headers: {
            'Authorization': getCookie("token"),
            'Content-Type': 'application/json'
        },
        success: function (response) {

            napuniTabelu(response);
        },
        error: function (response) {
            if (JSON.parse(response.responseText).errorMessage === "Nema dnevne berbe u bazi") {
                var dnevneBerbe = [];
                napuniTabelu(dnevneBerbe);
                var empty = document.getElementById("empty");
                empty.innerHTML = "<h3 style=\"color: white;\">Prazno</h3>";
            } else {
                alert(JSON.parse(response.responseText).errorMessage);
                window.location.href = "dobavljaci.html";
            }

        }
    });
}



function refresh() {
    ucitajDnevneBerbe();
    var id = 0;
    id = window.setInterval(ucitajDnevneBerbe, 100);
    window.clearInterval(id);
}

function napuniTabelu(dnevneberbe) {
    if (typeof dnevneberbe !== "undefined") {
        var table = document.getElementById('tblDnevneBerbe');
        table.innerHTML = "";
        var table_body = document.createElement('TBODY');
        table.appendChild(table_body);
        var tHead = document.createElement('THEAD');
        var arrayHeader = ["Ime i Prezime", "Datum"];

        for (var i = 0; i < arrayHeader.length; i++) {
            tHead.appendChild(document.createElement("TH")).appendChild(document.createTextNode(arrayHeader[i]));
        }
        tHead.appendChild(document.createElement("TH")).appendChild(document.createTextNode(""));
        tHead.appendChild(document.createElement("TH")).appendChild(document.createTextNode(""));
        for (var x = 0; x < dnevneberbe.length; x++) {
            var tr = document.createElement('TR');
            table_body.appendChild(tr);

            var d = new Date(dnevneberbe[x].datum);
            var y = d.getUTCFullYear();
            var da = d.getUTCDate() + 1;
            var m = d.getUTCMonth() + 1;
            var datum = da + "." + m + "." + y + ".";
            for (var j = 0; j < 3; j++) {
                var td = document.createElement('TD');
                switch (j) {
                    case 0:
                        td.id = "td_id_" + dnevneberbe[x].dobavljac.jmbg;
                        td.appendChild(document.createTextNode(dnevneberbe[x].dobavljac.ime + " " + dnevneberbe[x].dobavljac.prezime));
                        break;
                    case 1:

                        td.appendChild(document.createTextNode(datum));
                        break;
                    case 2:
                        var b = document.createElement('BUTTON');
                        b.className = "button btn-info";
                        b.appendChild(document.createTextNode("Radi"));
                        b.id = "XXX" + dnevneberbe[x].dnevnaberbaPK.dnevnaberbaid + "XXX" + datum;
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

var dnevnaBerbaId;
var listaStavkiJson = [];

$(function () {

    var dialog, form;

    function checkTrue(x, y) {
        if ((x.val() === "" && y.val() !== "") || (x.val() !== "" && y.val() === "")) {
            return false;
        }
        return true;
    }


    function dodaj() {
        var valid = true;

        valid = valid && checkTrue($('#tacne'), $('#cenaTacne'));
        valid = valid && checkTrue($('#prvaKlasa'), $('#cenaPrveKlase'));
        valid = valid && checkTrue($('#drugaKlasa'), $('#cenaDrugeKlase'));
        valid = valid && checkTrue($('#trecaKlasa'), $('#cenaTreceKlase'));
        valid = valid && !($('#tacne').val() === "" &&
                $('#cenaTacne').val() === "" &&
                $('#prvaKlasa').val() === "" &&
                $('#cenaPrveKlase').val() === "" &&
                $('#drugaKlasa').val() === "" &&
                $('#cenaDrugeKlase').val() === "" &&
                $('#trecaKlasa').val() === "" &&
                $('#cenaTreceKlase').val() === "");

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

        if (valid === false) {
            alert("Niste ispravno uneli podatke");
        } else {
            var jsonS = {
                "tacne": tacne,
                "prvaklasa": prvaKlasa,
                "drugaklasa": drugaKlasa,
                "trecaklasa": trecaKlasa,
                "cenatacne": cenaTacne,
                "cenaprvaklasa": cenaPrveKlase,
                "cenadrugaklasa": cenaDrugeKlase,
                "cenatrecaklasa": cenaTreceKlase,
                "dnevnaberbaid": dnevnaBerbaId,
                "jmbg": getCookie("dobavljac")
            };

            //listaStavkiJson.push(jsonS);
            var json = JSON.stringify(jsonS);

            $.ajax({
                type: "POST",
                url: getCookie("basicURL") + "stavka",
                data: json,
                headers: {
                    'Content-Type': 'application/json'
                },
                success: function (response) {
                    alert("Uspesno ste kreirali stavku!");

                    refresh();
                },
                error: function (response) {
                    refresh();
                }
            });

            $('#tacne').val("");
            $('#cenaTacne').val("");
            $('#prvaKlasa').val("");
            $('#cenaPrveKlase').val("");
            $('#drugaKlasa').val("");
            $('#cenaDrugeKlase').val("");
            $('#trecaKlasa').val("");
            $('#cenaTreceKlase').val("");

        }
        return valid;
    }



    dialog = $("#dialog-form").dialog({
        autoOpen: false,
        height: 700,
        width: 510,
        modal: true,
        buttons: {
            "Dodaj stavku": dodaj,
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
        dodaj();
    });


    $('#btnDodajDnevnuBerbu').click(function () {

        listaStavkiJson = [];

        var jsonS = {
            "datum": todayDate(),
            "dnevnaberbaPK": {
                "jmbg": getCookie('dobavljac')
            }
        };
        var json = JSON.stringify(jsonS);

        $.ajax({
            type: "POST",
            url: getCookie("basicURL") + "rest/dnevnaBerba",
            data: json,
            headers: {
                'Authorization': getCookie('token'),
                'Content-Type': 'application/json'
            },
            success: function (response) {
                alert("Uspesno");
                refresh();
                var empty = document.getElementById("empty");
                empty.innerHTML = "";
            },
            error: function (response) {
                alert("Neuspesno");
                alert(JSON.parse(response.responseText).errorMessage);
            }
        });



//        dialog.dialog("open");
    });

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

function dugmici() {
    $(function () {
        $(document).on('click', '[id^=XXX]', function () {
            var id = jQuery(this).attr("id");
            var niz = id.split("XXX");
            var id1 = niz[1];
            var datum = niz[2];
            document.cookie = "dnevnaberba=" + id1;
            document.cookie = "datumDB=" + datum;
            window.location.href = "stavke-dnevne-berbe.html";

        });
    });
}