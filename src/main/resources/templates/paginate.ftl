<#import "parts/common.ftl" as c>
<#import "parts/down.ftl" as e>
<#import "parts/filter.ftl" as f>

<@f.page>
</@f.page>


<@c.page>

<table id=customers>
    <tr>
        <th class="col1">ID</th>
        <th class="col2">TEXT</th>
        <th class="col3">ISDONE</th>
        <th class="col4">DATE</th>
        <th class="col3">COMMENT</th>
        <th class="col5">DELETE</th>
        <th class="col6">EDIT</th>
    </tr>
    <#if (notes)??>
        <#list notes as note>
        <tr>
            <td class="col1">${note.getId()!}</td>
            <td class="col2">${note.getText()!}</td>
            <td class="col3">${note.getDone()!?then('выполнена', 'не выполнена')}</td>
            <td class="col4">${note.getDate()!?datetime?string('dd-MM-yyyy')}</td> <!--sometimes OK -->
            <td class="col3">${note.getComment()!}</td>
            <td class="col5"><a href="/remove/${note.getId()!}"
                                onclick='confirm("Вы подтверждаете удаление?");'>Удалить</a></td>
            <td class="col6"><a href="/getNoteForEdit/${note.getId()!}">Изменить</a></td>
        </tr>
        </#list>
    <#else>
    <div class="al"> Страница с такми номером отсутсвует, пожалуйста, введите корректный номер</div>
    </#if>
    <#if (newNote)??>
    <div> Добавлена новая заметка</div>
        <tr>
            <td class="col1">${newNote.getId()!}</td>
            <td class="col2">${newNote.getText()!}</td>
            <td class="col3">${newNote.getDone()!?then('выполнена', 'не выполнена')}</td>
            <td class="col4">${newNote.getDate()!?datetime?string('dd-MM-yyyy')}</td> <!--sometimes OK -->
            <td class="col3">${newNote.getComment()!}</td>
            <td class="col5"><a href="/remove/${newNote.getId()!}" onclick='confirm("Вы подтверждаете удаление?");'>Удалить</a>
            </td>
            <td class="col6"><a href="/getNoteForEdit/${newNote.getId()!}">Изменить</a></td>
        </tr>
    <#else>
    </#if>
</table>
 <br>
<div align="center">
    <a href="/prev " class="c">previous page</a>Страница ${countForPages!"1"} из ${quantityOfPages!} <a href="/next "
                                                                                                        class="c">next
    page</a>
</div>
<div class="pag">
    <form method="post" action="page">
        <input type="number" name="numberOfPage" min="1" max=${quantityOfPages!} required="required"
               placeholder="Перейти на стр. №">
        <button type="submit">Перейти</button>
</div>
<br>
<br>

</@c.page>
<@e.page>
</@e.page>