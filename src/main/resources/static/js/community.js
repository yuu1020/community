

function post(){
    let questionId = $("#question_id").val();
    let content = $("#comment_content").val();

    comment2target(questionId,1,content);
}
function show(){
    $("#comment_section").show();
}
//展开二级评论
function collapseComments(e){
    var id=e.getAttribute("data-id");
    var subCommentContainer = $("#comment-" + id);
    //console.log(subCommentContainer.children().length);
    var comments= $("#comment-"+id);
    var commenticon=$("#commenticon-"+id);
    if (subCommentContainer.children().length != 1) {
        //展开二级评论
        $(comments).toggleClass("in");
        $(commenticon).toggleClass("active");
    }else{
        $.getJSON("/comment/" + id, function (data) {
            $.each(data.data.reverse(), function (index, comment) {
                var mediaLeftElement = $("<div/>", {
                    "class": "media-left"
                }).append($("<img/>", {
                    "class": "media-object img-rounded/ comment-icon",
                    "src": comment.user.avatarUrl,
                    "style":"width:43px;height:43px;font-size: 15px;cursor: pointer;"
                }));
                var mediaBodyElement = $("<div/>", {
                    "class": "media-body "
                }).append($("<h5/>", {
                    "class": "media-heading",
                    "html": 'AAAAb'+comment.user.name
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
                    "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments comment-sp"
                }).append(mediaElement);
                subCommentContainer.prepend(commentElement);
            });
            $(comments).toggleClass("in");
            $(commenticon).toggleClass("active");
        });
    }
}
function comment2target(targetId,type,content){
    if (!content) {
        alert("不能回复空内容~~~");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data:JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }) ,
        success: function (response){
            console.log(response.message);
            if(response.message=="成功"){
                $(comment_section).hide();
                window.location.reload();
            }else{
                alert(response.message);
            }
        },
        dataType: "json"
    });

}
function comment(e){
    var commentId=e.getAttribute("data-id")
    var content=$("#input-"+commentId).val();
    comment2target(commentId,2,content);
}