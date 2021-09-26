package com.example.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND("你找的問題不見了",2001),
    TARGET_PARAM_NOT_FOUND("未选中任何问题或评论",2002),
    NO_LOGIN("未登录不能发表评论",2003),
    TYPE_PARAM_WORRY("评论类型不正确",2004),
   COMMENT_NOT_FOUND("你操作的评论不存在",2005),
    SYS_ERROR("系统繁忙",2006),
    CONTENT_IS_EMPTY("评论内容不能为空",2007);
    @Override
    public String getMessage() {
        return message;
    }
    @Override
    public Integer getCode()
    {
        return code;
    }
    private String message;
    private Integer code;

    CustomizeErrorCode(String message, Integer code) {
        this.message = message;
        this.code = code;
    }
}
