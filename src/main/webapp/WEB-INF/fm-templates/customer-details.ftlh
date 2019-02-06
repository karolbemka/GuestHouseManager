<#include "header.ftlh">

<div class="container">
    <div class="jumbotron jumbotron-opacity">
        <h1 class="display-4">Szczegóły rezerwacji</h1>
        <hr class="my-4">
        <table class="table">
            <thead class="thead-dark">
            <tr>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <th class="thead-dark">Imię</th>
                <td>${customer.customerName}</td>
            </tr>
            <tr>
                <th class="thead-dark">Nazwisko</th>
                <td>${customer.customerSurname}</td>
            </tr>
            <tr>
                <th class="thead-dark">Telefon kontakowy</th>
                <td>${customer.customerPhone}</td>
            </tr>
            <tr>
                <th class="thead-dark">Adres e-mail</th>
                <td>${customer.customerEmail}</td>
            </tr>
            </tbody>
        </table>
        <hr class="my-4">
        <h1 class="display-4">Lista rezerwacji które dokonał klient</h1>
        <hr class="my-4">
        <table class="table">
            <thead class="thead-dark">
            <tr>
                <th>Nazwa pokoju</th>
                <th>Data przyjazdu</th>
                <th>Data wyjazdu</th>
                <th>Liczba osób</th>
            </tr>
            </thead>
            <tbody>
            <#list reservationList as r>
                <tr>
                    <td>${r.reservedRoom.roomName}</td>
                    <td>${r.startDate}</td>
                    <td>${r.endDate}</td>
                    <td>${r.numberOfPersons}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>

<#include "footer.ftlh">