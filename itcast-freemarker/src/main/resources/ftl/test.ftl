<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Freemarker</title>
</head>
<body>
<br>
空值处理：<br>

???的前面两个??表示判断当前变量是否存在如果存在则返回true否则返回false，第3个?则表示函数的调用<br>

<#assign bool2=false>
${bool2???string}<br>

<#if str3??>
    str3 存在
<#else>
    str3 不存在
</#if>



<br><br>

如果为空则可以在变量之后之间加!；表示如果值不存在或者空的话什么都不显示：${emp!}<br>
表示如果值不存在或者空的话显示内容则可以在!之后添加要显示的内容，格式：!"要显示的内容"：${emp!"emp的值为空"}<br>

<br>
<hr>
<br>

内建函数<br><br>

数值直接显示：${number} --- 数值字符串方式显示：${number?c}<br>

日期格式化：<br>
.now表示当前日期时间：${.now}<br>
当前日期：${today?date}<br>
当前时间：${today?time}<br>
当前日期时间：${today?datetime}<br>
格式显示日期：${today?string("yyyy年MM月dd日 HH:mm:ss")}
<br><br>

json字符串转换为Json对象<br>
<#assign jsonStr='{"id":123,"name":"itcast"}'/>
<#assign jsonObj=jsonStr?eval/>
${jsonObj.id} --- ${jsonObj.name}<br/><br>


goodsList的总记录数为:${goodsList?size}<br><br>

list循环控制语句<br>
<#list goodsList as goods>
    ${goods_index}---${goods.name}---${goods.price}<br>
</#list>

<br>
<hr>
<br>
条件控制语句if<br>
<#assign bool=false >
<#if bool>
    bool是true
<#else>
    bool是false
</#if>

<br>
<hr>
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