package blog.infra;

import blog.config.kafka.KafkaProcessor;
import blog.domain.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class CommentBoardViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private CommentBoardRepository commentBoardRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenCommentCreated_then_CREATE_1(
        @Payload CommentCreated commentCreated
    ) {
        try {
            if (!commentCreated.validate()) return;

            // view 객체 생성
            CommentBoard commentBoard = new CommentBoard();
            // view 객체에 이벤트의 Value 를 set 함
            commentBoard.setCommentId(String.valueOf(commentCreated.getId()));
            commentBoard.setCommentContent(commentCreated.getContent());
            // view 레파지 토리에 save
            commentBoardRepository.save(commentBoard);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenCommentEdited_then_UPDATE_1(
        @Payload CommentEdited commentEdited
    ) {
        try {
            if (!commentEdited.validate()) return;
            // view 객체 조회

            List<CommentBoard> commentBoardList = commentBoardRepository.findByCommentId(
                String.valueOf(commentEdited.getId())
            );
            for (CommentBoard commentBoard : commentBoardList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                commentBoard.setCommentContent(commentEdited.getContent());
                // view 레파지 토리에 save
                commentBoardRepository.save(commentBoard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenCommentDeleted_then_DELETE_1(
        @Payload CommentDeleted commentDeleted
    ) {
        try {
            if (!commentDeleted.validate()) return;
            // view 레파지 토리에 삭제 쿼리
            commentBoardRepository.deleteByCommentId(commentDeleted.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
