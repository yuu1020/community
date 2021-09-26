

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
   var comments= $("#comment-"+id);
   var commenticon=$("#commenticon-"+id);
    $(comments).toggleClass("in");
    $(commenticon).toggleClass("active");

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