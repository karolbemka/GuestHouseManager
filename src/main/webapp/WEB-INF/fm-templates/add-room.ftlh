<#include "header-admin.ftlh">

<div class="container">
    <div class="jumbotron jumbotron-opacity">
        <h1 class="display-4">Dodaj pokój</h1>
        <hr class="my-4">

        <#if error??>
                <div class="alert alert-danger">
                    <strong>${error}</strong>
                </div>
        </#if>

        <form method="post" action="/add-room">

            <div class="form-group">
                <label for="roomName">Jak chcesz nazwać nowy pokój?</label>
                <input name="roomName" type="text" class="form-control" id="roomName" required
                       title="Wpisz nazwę lub nr">
            </div>

            <div class="form-group">
                <label for="roomSlots">Maksymalna liczba miejsc w pokoju</label>
                <input name="roomSlots" type="text" class="form-control" id="roomName" required
                       title="Liczba miejsc" pattern="[1-9]">
            </div>
            <br>
            <input class="btn btn-primary btn-lg btn-dark" type="submit" value="Dodaj pokój">
        </form>
    </div>
</div>


<#include "footer.ftlh">