<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Freemarker</title>
</head>
<body>
<br>

include指令：引入其他模版的内容<br>
<#include "header.ftl"/>

<br>
<hr>
<br>
<#--assign可以指定本地变量-->
<#assign strtest="itcast"/>
${strtest}<br>
<#assign linkMan={"mobile":"13333333333", "address":"吉山村"} />
${linkMan.mobile}----${linkMan.address}


<br>
<hr>
<br>
${name}----${msg}


</body>
</html>