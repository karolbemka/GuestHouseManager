<#if admin??>
    <#include "header-admin.ftlh">

    <div class="container">
        <div class="jumbotron jumbotron-opacity">
            <h1 class="display-4">Wszystkie pokoje</h1>
            <hr class="my-4">
            <h5>Liczba pokoi: ${roomsList?size}</h5>
            <table class="table">
                <thead class="thead-dark">
                <tr>
                    <th>Nazwa pokoju</th>
                    <th>Liczba miejsc</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list roomsList as room>
                    <tr>
                        <td>${room.roomName}</td>
                        <td>${room.roomSlots}</td>
                        <td>
                            <a href="/reservations?id=${room.roomId}" class="btn btn-dark">Zobacz rezerwacje</a>
                        </td>
                        <td>
                            <a href="/add-reservation?id=${room.roomId}" class="btn btn-dark">Dodaj rezerwację</a>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
<#else >
    <#include "header.ftlh">

    <div class="container">
        <div class="jumbotron jumbotron-opacity">
            <h1 class="display-4">Wszystkie pokoje</h1>
            <hr class="my-4">
            <table class="table">
                <thead class="thead-dark">
                <tr>
                    <th>Nazwa pokoju</th>
                    <th>Liczba miejsc</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list roomsList as room>
                    <tr>
                        <td>${room.roomName}</td>
                        <td>${room.roomSlots}</td>
                        <td>
                            <a href="/check-room?id=${room.roomId}" class="btn btn-dark">Sprawdź dostępność</a>
                        </td>

                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</#if>

<#include "footer.ftlh">