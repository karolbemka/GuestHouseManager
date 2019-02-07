<#include "header.ftlh">

<div class="container">
    <div class="jumbotron jumbotron-opacity">
        <h1 class="display-4">Rezerwacje dla pokoju: ${room.roomName} <a href="/add-reservation?id=${room.roomId}"
                                                                         class="btn btn-dark">Dodaj rezerwację</a></h1>
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
                        <tr>
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

<#include "footer.ftlh">