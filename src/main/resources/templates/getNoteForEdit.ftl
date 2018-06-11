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
           <tr>
               <td class="col1">${notes.getId()!}</td>
               <td class="col2">${notes.getText()!}</td>
               <td class="col3">${notes.getDone()?then('выполнена', 'не выполнена')}</td>
               <td class="col4">${notes.getDate()!?datetime?string('dd-MM-yyyy')}</td>
               <td class="col3">${notes.getComment()!}</td>
               <td class="col5"><a href="/remove/${notes.getId()!}" onclick='confirm("Вы подтверждаете удаление?");'>Удалить</a>
               </td>
               <td class="col6"><a href="/getNoteForEdit/${notes.getId()!}">Изменить</a></td>
           </tr>
   <#elseif (editNote)?? >
       <div class="al">Отредактированная заметка</div>
        <tr>
            <td class="col1">${editNote.getId()!}</td>
            <td class="col2">${editNote.getText()!}</td>
            <td class="col3">${editNote.getDone()?then('выполнена', 'не выполнена')}</td>
            <td class="col4">${editNote.getDate()?datetime?string('dd-MM-yyyy')}</td>
            <td class="col3">${editNote.getComment()!}</td>
            <td class="col5"><a href="/remove/${editNote.getId()!}" onclick='confirm("Вы подтверждаете удаление?");'>Удалить</a>
            </td>
            <td class="col6"><a href="/getNoteForEdit/${editNote.getId()!}">Изменить</a></td>
        </tr>
   <#else>
    <tr>
        <td align="center" class="al">заметка с таким Id отсутсвует</td>
        <td align="center"></td>
        <td align="center"></td>
        <td align="center"></td>
        <td align="center"></td>
        <td align="center"></td>
        <td align="center"></td>
    </tr>
   </#if>

    <#if (notes)??>
<form method="post" onsubmit="return validDate()" action="/edit" id="myform" class="ti"></form>
     <div class="al">Редактирование заметки</div>
    <tr>
        <td align="left">
            <input type="number" form="myform" name="id" value= ${notes.getId()!} class="ti" width="150px" readonly>
        </td>
        <td align="left" class="col2">
            <input type="text" align="left" name="text" form="myform" value= ${notes.getText()!} class="ti">
        </td>
        <td align="left">
            <input type="radio" width="100%" name="isDone" id="bo1" align="center" value="true" form="myform"
                   required="required"> да
            <input type="radio" name="isDone" id="bo2" align="center" value="false" form="myform"> нет <Br>
        </td>
        <td align="left">
            <input type="text" class="ti" name="date" id="dat" pattern="(\d{2})-(\d{2})-(\d{4})"
                   value= ${notes.getDate()?datetime?string('dd-MM-yyyy')} form="myform">
        </td>
        <td align="left">
            <input type="text" class="ti" name="comment" form="myform" value= ${notes.getComment()!} form="myform">
        </td>
        <td align="left"><a href="/remove/${(notes.getId())!}" onclick='confirm("Вы подтверждаете удаление?");'>Удалить</a>
        </td>
        <td align="left">
            <input type="submit" name="edit" value="Редактировать" form="myform"
                   onclick='confirm("Вы подтверждаете редактирование?");'/>
        </td>
    </tr>
    </#if>
</table>


</@c.page>
<@e.page>
</@e.page>