<#include "header.ftlh">


<div class="container">
    <div class="jumbotron jumbotron-opacity">
        <h1 class="display-4">Dodaj nowe konto administratora</h1>
        <hr class="my-4">

        <#if error??>
            <div class="alert alert-danger">
                <strong>${error}</strong>
            </div>
        </#if>

        <form method="post" action="/add-admin">

            <div class="form-group">
                <label for="adminLogin">Login</label>
                <input name="adminLogin" type="text" class="form-control" id="adminLogin" required
                       title="Login">
            </div>

            <div class="form-group">
                <label for="adminPassword">Hasło</label>
                <input name="adminPassword" type="text" class="form-control" id="adminPassword" required
                       title="Hasło">
            </div>
            <br>
            <input class="btn btn-primary btn-lg btn-dark" type="submit" value="Stwórz konto">
        </form>
    </div>
</div>

<#include "footer.ftlh">