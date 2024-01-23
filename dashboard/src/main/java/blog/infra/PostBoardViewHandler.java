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
public class PostBoardViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private PostBoardRepository postBoardRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPostCreated_then_CREATE_1(
        @Payload PostCreated postCreated
    ) {
        try {
            if (!postCreated.validate()) return;

            // view 객체 생성
            PostBoard postBoard = new PostBoard();
            // view 객체에 이벤트의 Value 를 set 함
            postBoard.setPostId(postCreated.getId());
            postBoard.setPostTitle(postCreated.getTitle());
            postBoard.setPostContent(postCreated.getContent());
            postBoard.setCommentList(
                Long.valueOf(postCreated.getCommentList())
            );
            // view 레파지 토리에 save
            postBoardRepository.save(postBoard);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPostEdited_then_UPDATE_1(@Payload PostEdited postEdited) {
        try {
            if (!postEdited.validate()) return;
            // view 객체 조회

            List<PostBoard> postBoardList = postBoardRepository.findByPostId(
                postEdited.getId()
            );
            for (PostBoard postBoard : postBoardList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                postBoard.setPostContent(postEdited.getContent());
                postBoard.setPostTitle(postEdited.getTitle());
                // view 레파지 토리에 save
                postBoardRepository.save(postBoard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPostDeleted_then_DELETE_1(
        @Payload PostDeleted postDeleted
    ) {
        try {
            if (!postDeleted.validate()) return;
            // view 레파지 토리에 삭제 쿼리
            postBoardRepository.deleteByPostId(postDeleted.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
