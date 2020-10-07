<#include "header.html">

<h1>Create new topic</h1>

<div class="section">
<form method="post" action="/createtopic">
<p>Title: </p>
<p><input type="text" name="title" size="80" /></p>
<p>Contents: </p>
<p><textarea rows="10" cols="80" name="text"></textarea></p>

<input type="hidden" name="forum" value="${data.forum}" />
<p><input type="submit" Value="Post"/></p>
</form>
</div>

<#include "footer.html">

