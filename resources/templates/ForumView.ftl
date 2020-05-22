<#include "header.html">

<h1>Forum: ${data.title}</h1>

<#list data.topics as t>
<div class="section">
<p><b><a href="/topic/${t.topicId}">${t.title}</a></b>
</p>
</div>
</#list>

<div class="section alt">
<#if session??>
<p><a href="/newtopic/${data.id}">Create new topic</a></p>
<#else>
<p><a href="/people">Log in</a> to create new topics.</p>
</#if>
</div>

<#include "footer.html">

