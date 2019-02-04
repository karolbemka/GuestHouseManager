<#include "header.ftlh">

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
            </tr>
            </thead>
            <tbody>
            <#list roomsList as rooms>
                <tr>
                    <td>${rooms.roomName}</td>
                    <td>${rooms.roomSlots}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>

<#include "footer.ftlh">