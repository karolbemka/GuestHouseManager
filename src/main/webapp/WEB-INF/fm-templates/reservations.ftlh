<#include "header.ftlh">

<div class="container">
    <div class="jumbotron jumbotron-opacity">
        <h1 class="display-4">Rezerwacje</h1>
        <hr class="my-4">
        <table class="table">
            <thead class="thead-dark">
            <tr>
                <th>Dzień przyjazdu</th>
                <th>Dzień wyjazdu</th>
                <th>Liczba gości</th>
                <th>Nazwisko klienta</th>
            </tr>
            </thead>
            <tbody>
            <#list roomReservations as r>
                <tr>
                    <td>${r.startDate}</td>
                    <td>${r.endDate}</td>
                    <td>${r.numberOfPersons}</td>
                    <td>${r.reservationCustomer.customerSurname}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>

<#include "footer.ftlh">