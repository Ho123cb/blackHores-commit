package com.heima.comment;

import com.heima.comment.service.CommentReplyService;
import com.heima.common.constants.ApCommentConstants;
import com.heima.model.comment.dtos.CommentRepaySaveDto;
import com.heima.model.comment.pojos.ApComment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import java.time.Instant;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = CommentApplication.class)
public class test {
    @Resource
    private CommentReplyService commentReplyService;

    @Resource
    private MongoTemplate mongoTemplate;
    @Test
    public void testSaveApComment() {
        Instant minus = Instant.now().minus(50000000000L, ChronoUnit.MINUTES);
        Date date = Date.from(minus);
        Query query = new Query();
        query.addCriteria(Criteria.where("entryId").is("1302864436297482242"))
                .addCriteria(Criteria.where("type").is(ApCommentConstants.COMMENT_STATUS_OPEN))
                .addCriteria(Criteria.where("createdTime").gt(date));
        List<ApComment> apComments = mongoTemplate.find(query, ApComment.class);
        System.out.println(apComments);
    }

}
