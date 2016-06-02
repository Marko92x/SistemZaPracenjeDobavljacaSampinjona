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
    ucitajMesta();
    ucitajDobavljace();
    dugmici("DDD");
    dugmici("XXX");
});
var dobavljac;

$(function () {
    $("#datepicker").datepicker();
    $("#datepicker1").datepicker();
});

function vratiDobavljaca(search) {
    $.ajax({
        type: "GET",
        url: getCookie("basicURL") + "rest/dobavljac?search=" + search,
        dataType: "json",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getCookie('token')
        },
        success: function (response) {
            dobavljac = response[0];
        }
    });
}

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
                        td.id = "td_id_" + dobavljaci[x].jmbg;
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
                        b.className = "btn btn-info";
                        b.appendChild(document.createTextNode("Radi"));
                        b.id = "XXX" + dobavljaci[x].jmbg;
                        b.onclick = function () {
                            vratiDobavljaca($('td:first', $(this).parents('tr')).text());
                        }
                        td.appendChild(b);
                        break;
                    case 5:
                        var b = document.createElement('BUTTON');
                        b.className = "btn btn-danger";
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

var ovaj;
var flag = true;

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
                            alert("Greska");
                        }
                    });

                }
            } else {
                document.cookie = "dobavljac=" + id1;
                //window.location.href = "radi.html";

                ovaj =
                        $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 140,
                    width: 500,
                    modal: true,
                    buttons: {
                        "Zaduzenja": function () {
                            window.location.href = "zaduzenja.html";
                        },
                        "Dnevne berbe": function () {
                            window.location.href = "dnevne-berbe.html";
                        },
                        "Izmeni dobavljača": function () {
                            popuni();
                            flag = false;
                            dialog.dialog("open");
                        }
                    }
                });

            }

        });
    });
}

function popuni() {
    $('#jmbg').val(dobavljac.jmbg).prop("disabled", true);
    $('#ime').val(dobavljac.ime);
    $('#prezime').val(dobavljac.prezime);
    $('#licnaKarta').val(dobavljac.brojlicnekarte);
    $('#brojGazdinstva').val(dobavljac.brojgazdinstva);
    $('#tekuciRacun').val(dobavljac.tekuciracun);
    $('#selectMesto').val(dobavljac.mesto.ptt);
    $('#selectMesto').change();
    $('#ulica').val(dobavljac.ulica);
    $('#broj').val(dobavljac.broj);
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

function ucitajMesta() {
    $.ajax({
        type: "GET",
        url: getCookie("basicURL") + "rest/mesto",
        dataType: "json",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getCookie('token')
        },
        success: function (response) {

            napuniComboBoxMesto(response);
        }
    });
}

function napuniComboBoxMesto(mesta) {

    var options = $("#selectMesto");
    options.find('option')
            .remove()
            .end();
    if (mesta) {
        $.each(mesta, function () {
            options.append($("<option />").val(this.ptt).text(this.ptt + " " + this.naziv));
        });
    } else {
        options.append($("<option />").val('').text(''));
    }
}

var dialog, form;


$(function () {









    function sacuvaj() {

        var JMBG = $('#jmbg').val();
        var ime = $('#ime').val();
        var prezime = $('#prezime').val();
        var licnaKarta = $('#licnaKarta').val();
        var brojGazdinstva = $('#brojGazdinstva').val();
        var tekuciRacun = $('#tekuciRacun').val();
        var mesto = $('#selectMesto').val();
        var ulica = $('#ulica').val();
        var broj = $('#broj').val();

        var jsonS = {
            "jmbg": JMBG,
            "ime": ime,
            "prezime": prezime,
            "brojgazdinstva": brojGazdinstva,
            "brojlicnekarte": licnaKarta,
            "tekuciracun": tekuciRacun,
            "ulica": ulica,
            "broj": broj,
            "mesto": {
                "ptt": mesto
            }
        };

        if (flag) {

            $.ajax({
                type: "POST",
                url: getCookie("basicURL") + "rest/dobavljac",
                data: JSON.stringify(jsonS),
                headers: {
                    'Authorization': getCookie("token"),
                    'Content-Type': 'application/json'
                },
                success: function (response) {
                    var p = document.getElementById('message');
                    p.innerHTML = response;
                    $('#messageModal').modal('show');
                    refresh();
                    dialog.dialog("close");
                },
                error: function (response) {
                    var p = document.getElementById('message');
                    p.innerHTML = JSON.parse(response.responseText).errorMessage;
                    $('#messageModal').modal('show');
                }
            });
        } else {
            $.ajax({
                type: "PUT",
                url: getCookie("basicURL") + "rest/dobavljac",
                data: JSON.stringify(jsonS),
                headers: {
                    'Authorization': getCookie("token"),
                    'Content-Type': 'application/json'
                },
                success: function (response) {
                    alert("Uspesno ste izmenili dobavljača!");
                    refresh();
                    dialog.dialog("close");
                    ovaj.dialog("close");

                },
                error: function (response) {
                    alert(JSON.parse(response.responseText).errorMessage);
                }
            });
        }
        flag = true;
    }



    dialog = $("#dialog-form").dialog({
        autoOpen: false,
        height: 700,
        width: 510,
        modal: true,
        buttons: {
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


    $('#btnDodajDobavljaca').click(function () {
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

$("#idKreirajObracun").click(function () {
    var datumOd = $("#datepicker").datepicker("getDate");
    var danOd = datumOd.getDate();
    var mesecOd = datumOd.getMonth();
    var godinaOd = datumOd.getFullYear();
    var datumOdString = godinaOd + '-' + mesecOd + '-' + danOd;

    var datumDo = $("#datepicker1").datepicker("getDate");
    var danDo = datumDo.getDate();
    var mesecDo = datumDo.getMonth();
    var godinaDo = datumDo.getFullYear();
    var datumDoString = godinaDo + '-' + mesecDo + '-' + danDo;

//    window.open("http://localhost:8084/SistemZaPracenjeDobavljacaSampinjona/rest/kreiraj/obracun/" + datumOdString + "/" + datumDoString);

    if (datumOd <= datumDo) {
        window.location.href = "http://localhost:8084/SistemZaPracenjeDobavljacaSampinjona/rest/kreiraj/obracun/" + datumOdString + "/" + datumDoString;
    } else {
        alert("Datum OD je vice od datuma DO!")
    }
//    $.ajax({
//        type: "GET",
//        url: getCookie("basicURL") + "rest/kreiraj/obracun/" + datumOdString + "/" + datumDoString,
//        headers: {
//            'Authorization': getCookie("token")
//        },
//        success: function (response) {
//
//            window.location.href = "http://localhost:8084/SistemZaPracenjeDobavljacaSampinjona/rest/kreiraj/obracun/"+ datumOdString + "/" + datumDoString;
//        },
//        error: function (response) {
//            alert(JSON.parse(response.responseText).errorMessage);
//        }
//    });
//    $.ajax({
//        type: "GET",
//        url: getCookie("basicURL") + "rest/kreiraj/obracun/" + datumOdString + "/" + datumDoString,
//        success: function (response, status, xhr) { // check for a filename
//            var filename = "";
//            var disposition = xhr.getResponseHeader('Content-Disposition');
//            if (disposition && disposition.indexOf('attachment') !== -1) {
//                var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
//                var matches = filenameRegex.exec(disposition);
//                if (matches != null && matches[1])
//                    filename = matches[1].replace(/['"]/g, '');
//            }
//
//            var type = xhr.getResponseHeader('Content-Type');
//            var blob = new Blob([response], {type: type});
//
//            if (typeof window.navigator.msSaveBlob !== 'undefined') {
//                // IE workaround for "HTML7007: One or more blob URLs were revoked by closing the blob for which they were created. These URLs will no longer resolve as the data backing the URL has been freed."
//                window.navigator.msSaveBlob(blob, filename);
//            } else {
//                var URL = window.URL || window.webkitURL;
//                var downloadUrl = URL.createObjectURL(blob);
//
//                if (filename) {
//                    // use HTML5 a[download] attribute to specify filename
//                    var a = document.createElement("a");
//                    // safari doesn't support this yet
//                    if (typeof a.download === 'undefined') {
//                        window.location = downloadUrl;
//                    } else {
//                        a.href = downloadUrl;
//                        a.download = filename;
//                        document.body.appendChild(a);
//                        a.click();
//                    }
//                } else {
//                    window.location = downloadUrl;
//                }
//
//                setTimeout(function () {
//                    URL.revokeObjectURL(downloadUrl);
//                }, 100); // cleanup
//            }
//        }
//    });
});

$("#idKreirajStatistiku").click(function () {
    var datumOd = $("#datepicker").datepicker("getDate");
    var danOd = datumOd.getDate();
    var mesecOd = datumOd.getMonth();
    var godinaOd = datumOd.getFullYear();
    var datumOdString = godinaOd + '-' + mesecOd + '-' + danOd;

    var datumDo = $("#datepicker1").datepicker("getDate");
    var danDo = datumDo.getDate();
    var mesecDo = datumDo.getMonth();
    var godinaDo = datumDo.getFullYear();
    var datumDoString = godinaDo + '-' + mesecDo + '-' + danDo;

    if (datumOd <= datumDo) {
        window.location.href = "http://localhost:8084/SistemZaPracenjeDobavljacaSampinjona/rest/kreiraj/statistika/" + datumOdString + "/" + datumDoString;
    } else {
        alert("Datum OD je vice od datuma DO!")
    }
    //    window.open("http://localhost:8084/SistemZaPracenjeDobavljacaSampinjona/rest/kreiraj/statistika/" + datumOdString + "/" + datumDoString);
});