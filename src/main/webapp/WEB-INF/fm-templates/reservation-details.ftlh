<#include "header-admin.ftlh">

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
                <th class="thead-dark">Nazwa pokoju</th>
                <td>${reservation.reservedRoom.roomName}</td>
            </tr>
            <tr>
                <th class="thead-dark">Data przyjazdu</th>
                <td>${reservation.startDate}</td>
            </tr>
            <tr>
                <th class="thead-dark">Data wyjazdu</th>
                <td>${reservation.endDate}</td>
            </tr>
            <tr>
                <th class="thead-dark">Liczba osób</th>
                <td>${reservation.numberOfPersons}</td>
            </tr>
            <tr>
                <th class="thead-dark">Imię i nazwisko klienta</th>
                <td>${reservation.reservationCustomer.customerName} ${reservation.reservationCustomer.customerSurname}</td>
            </tr>
            <tr>
                <th class="thead-dark">Telefon kontakowy</th>
                <td>${reservation.reservationCustomer.customerPhone}</td>
            </tr>
            <tr>
                <th class="thead-dark">Adres e-mail</th>
                <td>${reservation.reservationCustomer.customerEmail}</td>
            </tr>
            <tr>
                <th class="thead-dark">Opłata klimatyczna za pobyt</th>
                <td>${tax}zł</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>


<#include "footer.ftlh">