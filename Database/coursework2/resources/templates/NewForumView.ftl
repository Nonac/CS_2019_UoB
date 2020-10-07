<#include "header.html">

<h1>Create new forum</h1>

<div class="section">
<form method="POST" action="/createforum" enctype="multipart/form-data">

<p>Forum name:</p>
<p><input type="text" name="title" size="80" /></p>

<p><input type="submit" Value="Create"/></p>
</form>
</div>

<#include "footer.html">

