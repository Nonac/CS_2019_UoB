<#include "header.html">

<h1>Create new person</h1>

<div class="section">
<form method="POST" action="/createperson" enctype="multipart/form-data">

<p><span class="key">Name:</span>
<input type="text" name="name" size="40" /></p>

<p><span class="key">Username:</span>
<input type="text" name="username" size="40" /></p>

<p><span class="key">Student id:</span>
<input type="text" name="stuid" size="40" /></p>

<p><span class="key">&nbsp;</span><input type="submit" Value="Create"/></p>
</form>
</div>

<#include "footer.html">

