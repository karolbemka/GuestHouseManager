<#include "header.ftlh">

<div class="container">
    <div class="jumbotron jumbotron-opacity">
        <h1 class="display-4">Dodaj rezerwację dla pokoju: ${room.roomName}</h1>
        <hr class="my-4">

        <#if errors['dateTaken']??>
            <div class="alert alert-danger">
                <strong>${errors['dateTaken']}</strong>
            </div>
        </#if>
        <#if errors['wrongDates']??>
            <div class="alert alert-danger">
                <strong>${errors['wrongDates']}</strong>
            </div>
        </#if>
        <#if errors['wrongNumberOfPersons']??>
            <div class="alert alert-danger">
                <strong>${errors['wrongNumberOfPersons']}</strong>
            </div>
        </#if>

        <form method="post" action="/add-reservation">

            <div class="form-group">
                <label for="startDate">Wybierz dzień przyjazdu *</label>
                <input name="startDate" type="date" class="form-control" id="startDate" required
                       title="Dzień przyjazdu">
            </div>

            <div class="form-group">
                <label for="endDate">Wybierz dzień wyjazdu *</label>
                <input name="endDate" type="date" class="form-control" id="endDate" required
                       title="Dzien wyjazdu">
            </div>

            <div class="form-group">
                <label for="numberOfPersons">Wpisz liczbę osób *</label>
                <input name="numberOfPersons" type="number" class="form-control" id="numberOfPersons" required
                       title="Liczba osób">
            </div>

            <div class="form-group">
                <label for="customerName">Imię klienta *</label>
                <input name="customerName" type="text" class="form-control" id="customerName" required
                       title="Imię klienta">
            </div>

            <div class="form-group">
                <label for="customerSurname">Nazwisko klienta *</label>
                <input name="customerSurname" type="text" class="form-control" id="customerSurname" required
                       title="Nazwisko klienta">
            </div>

            <div class="form-group">
                <label for="customerPhone">Telefon kontakowy *</label>
                <input name="customerPhone" type="number" class="form-control" id="customerPhone" required
                       title="Telefon kontakowy">
            </div>

            <div class="form-group">
                <label for="customerEmail">Adres e-mail</label>
                <input name="customerEmail" type="email" class="form-control" id="customerEmail"
                       title="Adres e-mail">
            </div>

            <div style="display: none;">
                <label for="roomId"></label>
                <input name="roomId" type="text" class="form-control" id="roomId" value="${room.roomId}">
            </div>

            <br>
            <input class="btn btn-primary btn-lg btn-dark" type="submit" value="Dodaj rezerwację">
        </form>
    </div>
</div>

<#include "footer.ftlh">