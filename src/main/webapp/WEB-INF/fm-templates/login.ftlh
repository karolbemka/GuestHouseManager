<#include "header.ftlh">


<div class="container">
    <div class="jumbotron jumbotron-opacity">
        <h1 class="display-4">Logowanie</h1>
        <hr class="my-4">

        <#if error??>
            <div class="alert alert-danger">
                <strong>${error}</strong>
            </div>
        </#if>

        <form method="post" action="/login">

            <div class="form-group">
                <label for="login">Login</label>
                <input name="login" type="text" class="form-control" id="login" required
                       title="Login">
            </div>

            <div class="form-group">
                <label for="password">Hasło</label>
                <input name="password" type="text" class="form-control" id="password" required
                       title="Hasło">
            </div>
            <br>
            <input class="btn btn-primary btn-lg btn-dark" type="submit" value="Zaloguj">
        </form>
    </div>
</div>

<#include "footer.ftlh">