<#include "header-admin.ftlh">

<div class="container">
    <div class="jumbotron jumbotron-opacity">
        <h1 class="display-4">Znajdź klienta</h1>
        <hr class="my-4">

        <form method="get" action="/customer-search">

            <div class="form-group">
                <label for="Select1">Szukaj klienta po:</label>
                <select name="searchOption" class="form-control" id="Select1" required>
                    <option value="1">Nazwisku</option>
                    <option value="2">Numerze telefonu</option>
                    <option value="3">Emailu</option>
                </select>
            </div>

            <div class="form-group">
                <label for="searchFor">Wpisz czego szukasz</label>
                <input name="searchFor" type="text" class="form-control" id="searchFor"
                       title="Szukana fraza">
            </div>

            <br>
            <input class="btn btn-primary btn-lg btn-dark" type="submit" value="Szukaj">
        </form>

        <#if customerList??>
            <#if customerList?has_content>
        <h1 class="display-4">Lista znalezionych klientów</h1>
        <hr class="my-4">
        <table class="table">
            <thead class="thead-dark">
            <tr>
                <th>Imię</th>
                <th>Nazwisko</th>
                <th>Numer telefonu</th>
                <th>Email</th>
            </tr>
            </thead>
            <tbody>
            <#list customerList as c>
                <tr>
                    <td>${c.customerName}</td>
                    <td><a href="/customer-details?id=${c.customerId}" class="btn btn-dark">${c.customerSurname}</a></td>
                    <td>${c.customerPhone}</td>
                    <td>${c.customerEmail}</td>
                </tr>
            </#list>
            </tbody>
        </table>
                <#else >
                    <h1 class="display-4">Nie znaleziono żadnego pasującego klienta</h1>
                </#if>
            </#if>
    </div>
</div>

<#include "footer.ftlh">