<#include "header.ftlh">

<div class="container">
    <div class="jumbotron jumbotron-opacity">
        <h1 class="display-4">Rezerwacje dla pokoju: ${room.roomName} <a href="/add-reservation?id=${room.roomId}" class="btn btn-dark">Dodaj rezerwację</a></h1>
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
                <tr>
                    <td>${r.startDate}</td>
                    <td>${r.endDate}</td>
                    <td>${r.numberOfPersons}</td>
                    <td><a href="/customer-details?id=${r.reservationCustomer.customerId}" class="btn btn-dark">${r.reservationCustomer.customerSurname}</a></td>
                    <td>
                        <a href="/reservation-details?id=${r.reservationId}" class="btn btn-dark">Szczegóły</a>
                    </td>
                    <td>
                        <a href="/reservations?id=${r.reservationId}" class="btn btn-dark">Edytuj</a>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger" data-toggle="modal"
                                data-target="#exampleModal-${r.reservationId}">Usuń</button>

                        <!-- Modal -->
                        <div class="modal fade" id="exampleModal-${r.reservationId}" tabindex="-1" role="dialog"
                             aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Uwaga!</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <p>Czy na pewno chcesz usunąć tą rezerwację?</p>
                                        <p>Zmiany będą nieodwracalne!</p>
                                    </div>
                                    <div class="modal-footer">
                                        <form method="post" action="/reservation-details?id=${r.reservationId}">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Zamknij
                                            </button>
                                            <button type="submit" class="btn btn-danger">Usuń</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>

<#include "footer.ftlh">