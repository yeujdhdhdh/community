function post() {
    var questionId=$("#questionId").val();
    var commentContent=$("#commentContent").val();
    var str={"parentId":questionId,"content":commentContent,"type":1}
    if(commentContent==null||commentContent=='') {
        alert("输入内容不能为空");
    }else {
    $.ajax({
        contentType:"application/json",
        type:"POST",
        url:"/comment",
        data:JSON.stringify(str),
        success:function (response){
            if (response.code==200){
                window.location.reload();
            }else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType:"json"
    }); }
}
/**

 * 文本框根据输入内容自适应高度

 * @param                {HTMLElement}        输入框元素

 * @param                {Number}                设置光标与输入框保持的距离(默认0)

 * @param                {Number}                设置最大高度(可选)

 */

var autoTextarea = function (elem, extra, maxHeight) {

    extra = extra || 0;

    var isFirefox = !!document.getBoxObjectFor || 'mozInnerScreenX' in window,

        isOpera = !!window.opera && !!window.opera.toString().indexOf('Opera'),

        addEvent = function (type, callback) {

            elem.addEventListener ?

                elem.addEventListener(type, callback, false) :

                elem.attachEvent('on' + type, callback);

        },

        getStyle = elem.currentStyle ? function (name) {

            var val = elem.currentStyle[name];



            if (name === 'height' && val.search(/px/i) !== 1) {

                var rect = elem.getBoundingClientRect();

                return rect.bottom - rect.top -

                    parseFloat(getStyle('paddingTop')) -

                    parseFloat(getStyle('paddingBottom')) + 'px';

            };



            return val;

        } : function (name) {

            return getComputedStyle(elem, null)[name];

        },

        minHeight = parseFloat(getStyle('height'));



    elem.style.resize = 'none';



    var change = function () {

        var scrollTop, height,

            padding = 0,

            style = elem.style;



        if (elem._length === elem.value.length) return;

        elem._length = elem.value.length;



        if (!isFirefox && !isOpera) {

            padding = parseInt(getStyle('paddingTop')) + parseInt(getStyle('paddingBottom'));

        };

        scrollTop = document.body.scrollTop || document.documentElement.scrollTop;



        elem.style.height = minHeight + 'px';

        if (elem.scrollHeight > minHeight) {

            if (maxHeight && elem.scrollHeight > maxHeight) {

                height = maxHeight - padding;

                style.overflowY = 'auto';

            } else {

                height = elem.scrollHeight - padding;

                style.overflowY = 'hidden';

            };

            style.height = height + extra + 'px';

            scrollTop += parseInt(style.height) - elem.currHeight;

            document.body.scrollTop = scrollTop;

            document.documentElement.scrollTop = scrollTop;

            elem.currHeight = parseInt(style.height);

        };

    };



    addEvent('propertychange', change);

    addEvent('input', change);

    addEvent('focus', change);

    change();

};

function collapseComment(e){
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    // 获取一下二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        // 折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object",
                        "style":"width: 25px;height: 25px",
                        "src": comment.user.imgUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}
function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容~~~");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        $('#myModal').modal({});
                        // window.open("https://github.com/login/oauth/authorize?client_id=7f316909bf70d1eaa2b2&redirect_uri=" + document.location.origin + "/callback&scope=user&state=1");
                        // window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
}
function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();

    if (previous) {
        var index = 0;
        var appear = false; //记录value是否已经作为一个独立的标签出现过
        while (true) {
            index = previous.indexOf(value, index); //value字符串在previous中出现的位置
            if (index == -1) break;
            //判断previous中出现的value是否是另一个标签的一部分
            //即value的前一个和后一个字符都是逗号","或者没有字符时，才说明value是一个独立的标签
            if ((index == 0 || previous.charAt(index - 1) == ",")
                && (index + value.length == previous.length || previous.charAt(index + value.length) == ",")
            ) {
                appear = true;
                break;
            }
            index++; //用于搜索下一个出现位置
        }
        if (!appear) {
            //若value没有作为一个独立的标签出现过
            $("#tag").val(previous + ',' + value);
        }
    }
    else {
        $("#tag").val(value);
    }
}
function showSelectTag() {
    $("#select-tag").show();
}

function userChange(){

    sessionStorage.setItem("judge",1);
    let item = sessionStorage.getItem("judge");
    console.log(item)
    window.location.reload();

}
function userChangePost(){
    var username=$("#username").val();
    var bio=$("#bio").val();
    var password=$("#password").val();
    var str={"username":username,"bio":bio,"password":password}
    if (username==null||bio==null){
        alert("输入内容不能为空");
    }else {
        $.ajax({
            contentType:"application/json",
            type:"POST",
            url:"/userChange",
            data:JSON.stringify(str),
            success:function (response){
                if (response.code==200){
                    window.location.reload();
                }else {
                    alert(response.message);
                }
                console.log(response);
            },
            dataType:"json"
        })
    }
}