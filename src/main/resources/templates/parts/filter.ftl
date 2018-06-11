<#macro page>
<div class="wrapper">
    <div class="head">В базе данных ${quantity} сообщений, из них ${quantityOfNotesIsDone} выполненных
        и ${quantityOfNotesNotDone} невыполненных.
    </div>
    <article class="main">
        <form method="post" action="/paginator" id="sortForm">
            Выберите количество отображаемых на странице записей:
            <input type="number" id="nmb" name="quantityOfNotesPerPage" value="10"
                   placeholder="Количество записей" min="1" max=${quantity!} required="required">
            <div>
                <label for="filе">Сортировка по выполнению</label>
                <select name="filter">
                    <option>все</option>
                    <option value="true">по выполненным</option>
                    <option value="false">по невыполненным</option>
                </select>
                <div class="select-arrow"></div>
            </div>
            <div>
                <label for="sortirov"  >Сортировка по дате/id</label>
                <select name="sort">
                    <option >по id</option>
                    <option value="up">по возрастанию даты</option>
                    <option value="down">по убыванию даты</option>
                </select>
                <div class="select-arrow"></div>
            </div>
            <input type="submit" value="Отобразить">
        </form>
             </article>
    <aside class="aside aside-1">
        <div>
            <form method="post" action="/main">
                <input type="text" name="text" class="text" placeholder="Введите новое сообщение" required="required"/>
                <input type="text" name="comment" class="text" placeholder="Примечание"/><br>
                <button type="submit">Сохранить сообщение</button>
            </form>
        </div>
    </aside>
    <aside class="aside aside-2">
        <div class="ti">
            <form method="post" action="getNoteForEdit">
                <input type="number" name="id" class="nmb" placeholder="Введите ID сообщения для его редактирования"
                       required="required"/><br>
                <button type="submit">Редактировать</button>
            </form>
            <#if (notes)??>
                <#if sort!?contains("up")>
                    <#assign name1 = "по возрастанию даты">
                <#elseif sort!?contains("down")>
                    <#assign name1 = "по убыванию даты">
                <#else> <#assign name1 = "по id">

                </#if>
                <#if filter!?contains("true")>
                    <#assign name2 = "по выполненным">
                <#elseif filter!?contains("false")>
                    <#assign name2 = "по невыполненным">
                <#else> <#assign name2 = "все сообщения">
                </#if>

            <div> сортировка дата/id :</div> <div class = "p">${name1}</div>сортировка выполнение : <div class = "p">${name2}</div>
            </#if>
        </div>

    </aside>
    <footer class="footer">
        <div><a href="/main">Переход к общему списку </a></div>
    </footer>
</div>
</#macro>