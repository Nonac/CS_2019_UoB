<#include "header.html">

<h1>Person: ${data.name}</h1>

<div class="section">
<h2>Information</h2>
<div class="grid">
    <div class="col-2"></div>
    <div class="col-4">Name</div>
    <div class="col-4">${data.name}</div>
    <div class="col-2"></div>
</div>
<div class="grid">
    <div class="col-2"></div>
    <div class="col-4">Username</div>
    <div class="col-4">${data.username}</div>
    <div class="col-2"></div>
</div>
<div class="grid">
    <div class="col-2"></div>
    <div class="col-4">Student id</div>
    <div class="col-4">${data.studentId}</div>
    <div class="col-2"></div>
</div>

</div>

<#include "footer.html">
