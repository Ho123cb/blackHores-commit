package com.heima.common.constants;

public class ApCommentConstants {
    public static final Integer COMMENT_CONTENT_LENGTH = 140;
    //评论状态 0:待审核 1:审核不通过 2：已发布 3：已下架 4：已删除
    public static final Short COMMENT_STATUS_WAIT = 0;
    public static final Short COMMENT_STATUS_REJECT = 1;
    public static final Short COMMENT_STATUS_OPEN = 2;
    public static final Short COMMENT_STATUS_OFF = 3;
    public static final Short COMMENT_STATUS_DELETE = 4;
}
