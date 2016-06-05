/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    ucitajDobavljace();
    if (getCookie('token') === "") {
        $('#bla').click(function () {
            window.location.href = 'index.html';
        });
    }
    ucitajMesta();
    dugmici("DDD");
    dugmici("XXX");

    $(".search").keyup(function () {
        var searchTerm = $(".search").val();
//        var listItem = $('.results tbody').children('tr');
        var searchSplit = searchTerm.replace(/ /g, "'):containsi('");

        $.extend($.expr[':'], {'containsi': function (elem, i, match, array) {
                return (elem.textContent || elem.innerText || '').toLowerCase().indexOf((match[3] || "").toLowerCase()) >= 0;
            }
        });

        $(".results tbody tr").not(":containsi('" + searchSplit + "')").each(function (e) {
            $(this).attr('visible', 'false');
        });

        $(".results tbody tr:containsi('" + searchSplit + "')").each(function (e) {
            $(this).attr('visible', 'true');
        });

        if (searchTerm === "") {
            refresh();
        }
    });
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
            napuniTabelu(response);
        },
        error: function (response) {
            var p = document.getElementById('message');
            p.innerHTML = JSON.parse(response.responseText).errorMessage;
            $('#messageModal').modal('show');
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
                        var span = document.createElement('SPAN');
                        span.className = "glyphicon glyphicon-edit";
                        b.appendChild(span);
                        b.appendChild(document.createTextNode(" Radi"));
                        b.id = "XXX" + dobavljaci[x].jmbg;
                        b.onclick = function () {
                            vratiDobavljaca($('td:first', $(this).parents('tr')).text());
                        }
                        td.appendChild(b);
                        break;
                    case 5:
                        var b = document.createElement('BUTTON');
                        b.className = "btn btn-danger";
                        var span = document.createElement('SPAN');
                        span.className = "glyphicon glyphicon-trash";
                        b.appendChild(span);
                        b.appendChild(document.createTextNode(" Obriši"));
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
                $('#obrisiModal').modal('show');
                $('#daObrisi').click(function () {
                    $.ajax({
                        url: 'rest/dobavljac/' + id1,
                        method: 'DELETE',
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': getCookie('token')
                        },
                        success: function (response) {
                            var p = document.getElementById('message');
                            p.innerHTML = response;
                            $('#messageModal').modal('show');
                            refresh();
                        },
                        error: function (response) {
                            var p = document.getElementById('message');
                            p.innerHTML = JSON.parse(response.responseText).errorMessage;
                            $('#messageModal').modal('show');
                        }
                    });
                });
            } else {
                document.cookie = "dobavljac=" + id1;
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
                    var p = document.getElementById('message');
                    p.innerHTML = response;
                    $('#messageModal').modal('show');
                    flag = true;
                    $('#jmbg').prop("disabled", false);
                    refresh();
                    dialog.dialog("close");
                    ovaj.dialog("close");
                },
                error: function (response) {
                    var p = document.getElementById('message');
                    p.innerHTML = JSON.parse(response.responseText).errorMessage;
                    $('#messageModal').modal('show');
                }
            });
        }
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
        var p = document.getElementById('message');
        p.innerHTML = "Datum OD je vice od datuma DO!";
        $('#messageModal').modal('show');
    }

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
        var p = document.getElementById('message');
        p.innerHTML = "Datum OD je vice od datuma DO!";
        $('#messageModal').modal('show');
    }
});

$('#logOut').click(function () {
    $('#odjaviModal').modal('show');
    $('#daOdjavi').click(function () {
        $.ajax({
            url: getCookie("basicURL") + "rest/authorization/logout",
            method: 'POST',
            headers: {
                'Authorization': getCookie('token')
            },
            success: function (response) {
                document.cookie = 'token=';
                var p = document.getElementById('message');
                p.innerHTML = response;
                $('#messageModal').modal('show');
                $('#bla').click(function () {
                    window.location.href = "index.html";
                });
            }
        });
    });
});