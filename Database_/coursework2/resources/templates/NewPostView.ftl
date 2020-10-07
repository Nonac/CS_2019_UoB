<#include "header.html">

<h1>Create new post</h1>

<div class="section">
<form method="post" action="/createpost">

<p><textarea rows="10" cols="80" name="text"></textarea></p>

<input type="hidden" name="topic" value="${data.topic}" />
<p><input type="submit" Value="Post"/></p>
</form>
</div>

<#include "footer.html">

