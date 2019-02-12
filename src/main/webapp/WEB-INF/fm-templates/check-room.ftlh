<#include "header.ftlh">


<div class="container">
    <div class="jumbotron jumbotron-opacity">
        <h2 class="display-4"><span class="badge badge-dark">${room.roomName}</span></h2>
        <hr class="my-4">

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
        <#if free??>
            <#if free>
                <div class="alert alert-success">
                    <strong>Pokój w terminie ${newReservation.startDate} - ${newReservation.endDate} jest wolny</strong>
                </div>
            <#else >
                <div class="alert alert-danger">
                    <strong>Pokój w terminie ${newReservation.startDate} - ${newReservation.endDate} jest zajęty</strong>
                </div>
            </#if>
        </#if>


        <form method="post" action="/check-room">

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

            <div style="display: none;">
                <label for="roomId"></label>
                <input name="roomId" type="text" class="form-control" id="roomId" value="${room.roomId}">
            </div>

            <br>
            <input class="btn btn-primary btn-lg btn-dark" type="submit" value="Sprawdź wybrany termin">
        </form>
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