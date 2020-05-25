function getData() {
    let url;
    let category = request("category");
    let columns;
    if (category != null) {
        url = "http://127.0.0.1:8080/api/article/todayCategory/" + category;
    } else {
        url = "http://127.0.0.1:8080/api/article/list";
    }
    $.getJSON(url, function (result) {
            for (let i in result) {
                if (result[i].columns ==null) {
                    result[i].columns="";
                }
                $("#table-list").append(`<tr id="tr${result[i].id}">
                        <td>${result[i].id}</td>
                        <td>${result[i].todayId}</td>
                        <td>${result[i].category}</td>
                        <td>${result[i].columns}</td>
                        <td>${result[i].title}</td>
                        <td>
                            <a class="btn btn-primary" href="/edit.html?id=${result[i].id}">修改</a>
                            <a class="delBtn btn btn-danger" onclick="delArticle(${result[i].id})">删除</a>
                            <a class="articleCopy btn btn-primary" onclick="createCopy(${result[i].id})">创建副本</a>
                            <form id="delForm" method="post">
                                <input type="hidden" name="_method" value="delete"/>
                            </form>
                            <form id="articleCopy" method="post">
                                <input type="hidden" name="_method" value="post"/>
                            </form>
                        </td>
                        <p type="hidden" id="data_category"></p>
                    </tr>`)
            }
        }
    )
}

function createCopy(id) {
    $.ajax({
        type: "post",
        url: "api/article/articleCopy/" + id,
    }).done(function (result) {
        console.log(result);
        $("#table-list").append(`<tr id="tr${result.id}">
                        <td>${result.id}</td>
                        <td>${result.todayId}</td>
                        <td>${result.category}</td>
                        <td>${result.columns}</td>
                        <td>${result.title}</td>
                        <td>
                            <a class="btn btn-primary" href="/edit/${result.id}">修改</a>
                            <a class="delBtn btn btn-danger" onclick="delArticle(${result.id})">删除</a>
                            <a class="articleCopy btn btn-primary" onclick="createCopy(${result.id})">创建副本</a>
                        </td>
                        <p type="hidden" id="data_category"></p>
                    </tr>`)

    })
}

function delArticle(id) {
    $.ajax({
        type: "delete",
        url: "api/article/delete/" + id,
        // data:$('delForm').serialize(),

    }).done(function (result) {
        console.log(result);
        if (result === "0") {
            $("#tr" + id).remove()
        }
    })
}

function jumpToList(){
    window.location.href="/test.html";
}

//提交修改
function submit_update() {
    let id = request("id");
    let todayId = $("#todayId").val();
    let title = $("#title").val();
    let category = $("#category").val();
    let columns = $("#columns").val();
    let content = $("#content").val();
    console.log(todayId, title, category, columns, content);

    $.ajax({
        type: "put",
        url: "/api/article/update",
        data_type: "json",
        data: {
            "id": id,
            "todayId": todayId,
            "title": title,
            "category": category,
            "columns": columns,
            "content": content,
            // "updateDate":new Date()
        }
    }).done(function (result) {
        console.log(result);
        if (result === "0") {
            $(window).attr('location','http://127.0.0.1:8080/test.html');
        }
    })
}

//获取url参数
function request(b) {
    for (var c = {}, g = location.search.substring(1).split("\x26"), f = 0; f < g.length; f++) {
        var e = g[f].indexOf("\x3d");
        if (-1 != e) {
            var a = g[f].substring(0, e)
                , e = g[f].substring(e + 1)
                , e = decodeURIComponent(e);
            c[a] = e
        }
    }
    return c[b]
}


function getEditData() {
    let id = request("id");
    console.log(id);
    $.ajax({
        type: "get",
        url: "api/article/id/" + id,
    }).done(function (result) {

        console.log(result);
        $("#todayId").val(result.todayId);
        $("#title").val(result.title);

        $("#category").val(result.category);
        if (result.category === "伊川手机报") {
            $("#divCategory").append(`
                                <div class="form-group">
                    <label for="columns" id="col">手机报栏目</label>
                    <select class="form-control" id="columns" name="columns">
                        <option value="今日头条" >今日头条</option>
                        <option value="时政新闻" >时政新闻</option>
                        <option value="社会新闻" >社会新闻</option>
                    </select>
                </div>`);
            $("#columns").val(result.columns);

        }

        $("#content").val(result.content);

    });
}

function submit_add() {
    var todayId = $("#todayId").val();
    var title = $("#title").val();
    var category = $("#category").val();
    var columns = $("#columns").val();
    var content = $("#content").val();
    console.log(todayId,title,category,columns,content);
    $.ajax({
        type:"post",
        url: "/api/article/save",
        data_type:"json",
        data: {
            "todayId":todayId,
            "title":title,
            "category":category,
            "columns":columns,
            "content":content,
            // "updateDate":new Date()
        }
    }).done(function (result) {

        console.log(result);
        if (result === "0") {
            $(window).attr('location','http://127.0.0.1:8080/test.html');
        }})
}

$("#category").change(function () {
    let column = $(this).val();
    if (column !=="伊川手机报") {
        $("#col").hide();
        $("#columns").hide().val("");
    } else {
        $("#col").show();
        $("#columns").show().val("今日头条");
    }
})