<#include "header.html">

<h1>List of people</h1>

<div class="section">
<#list data.data as p>
<p><span class="key"><a href="/person/${p.key}">${p.value} [${p.key}]</a></span></p>
<p><a href="/login/${p.key}">(log in as ${p.value})</a></p>
</#list>
<p><a href="/login">(log out)</a></p>
</div>

<div class="section alt">
<p><a href="/newperson">add person</a></p>
</div>

<#include "footer.html">

