<#include "header.html">

<h1>Forums</h1>

<#list data.data as forum>
    <div class="section">
    <p><b><a href="/forum/${forum.id}">${forum.title}</a></b></p>
    </div>
</#list>

<div class="section alt">
<p><a href="/newforum">Create new forum</a></p>
</div>

<#include "footer.html">

