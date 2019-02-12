<#include "header-admin.ftlh">

<div class="container">
    <div class="jumbotron jumbotron-opacity">
        <h2 class="display-4"><span class="badge badge-dark">${room.roomName}</span> <a
                    href="/add-reservation?id=${room.roomId}"
                    class="btn btn-dark">Dodaj rezerwację</a></h2>
        <hr class="my-4">
        <table class="table">
            <thead class="thead-dark">
            <tr>
                <th>Dzień przyjazdu</th>
                <th>Dzień wyjazdu</th>
                <th>Liczba gości</th>
                <th>Nazwisko klienta</th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list roomReservations as r>
                <#if r?index gt 0>
                    <#if roomReservations[r?index-1].endDate==r.startDate>
                        <#include "includeReservationList.ftlh">
                    <#else >
                        <tr class="table-success">
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <#include "includeReservationList.ftlh">
                    </#if>
                <#else >
                    <#include "includeReservationList.ftlh">
                </#if>
            </#list>
            </tbody>
        </table>
    </div>
</div>

<div class="container">
    <h1 style="margin-bottom:50px;">Kalendarz rezerwacji</h1>
    <div id="calendar"></div>
</div>
<script src="../../js/jquery-3.3.1.slim.min.js"></script>
<script src="../../js/availability-calendar.js"></script>
<script>
    var unavailableDates = [
        <#list roomReservations as r>
        {start: '${r.startDate}', end: '${r.endDate}'},
        </#list>
        {start: '2015-02-11', end: '2015-02-21'}
    ];

    $('#calendar').availabilityCalendar(unavailableDates);
</script>

<#include "footer.ftlh">