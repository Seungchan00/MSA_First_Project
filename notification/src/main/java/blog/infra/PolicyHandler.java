package blog.infra;

import blog.config.kafka.KafkaProcessor;
import blog.domain.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    NotifyRepository notifyRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PostCreated'"
    )
    public void wheneverPostCreated_PostCreateAlert(
        @Payload PostCreated postCreated
    ) {
        PostCreated event = postCreated;
        System.out.println(
            "\n\n##### listener PostCreateAlert : " + postCreated + "\n\n"
        );

        // Sample Logic //
        Notify.postCreateAlert(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PostDeleted'"
    )
    public void wheneverPostDeleted_PostDeleteAlert(
        @Payload PostDeleted postDeleted
    ) {
        PostDeleted event = postDeleted;
        System.out.println(
            "\n\n##### listener PostDeleteAlert : " + postDeleted + "\n\n"
        );

        // Sample Logic //
        Notify.postDeleteAlert(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='CommentCreated'"
    )
    public void wheneverCommentCreated_CommentCreateAlert(
        @Payload CommentCreated commentCreated
    ) {
        CommentCreated event = commentCreated;
        System.out.println(
            "\n\n##### listener CommentCreateAlert : " + commentCreated + "\n\n"
        );

        // Sample Logic //
        Notify.commentCreateAlert(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
