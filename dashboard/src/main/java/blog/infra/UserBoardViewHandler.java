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
public class UserBoardViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private UserBoardRepository userBoardRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenUserCreated_then_CREATE_1(
        @Payload UserCreated userCreated
    ) {
        try {
            if (!userCreated.validate()) return;

            // view 객체 생성
            UserBoard userBoard = new UserBoard();
            // view 객체에 이벤트의 Value 를 set 함
            userBoard.setUserId(userCreated.getId());
            userBoard.setEmail(userCreated.getEmail());
            userBoard.setName(userCreated.getName());
            userBoard.setPassword(userCreated.getPassword());
            // view 레파지 토리에 save
            userBoardRepository.save(userBoard);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenUserUpdated_then_UPDATE_1(
        @Payload UserUpdated userUpdated
    ) {
        try {
            if (!userUpdated.validate()) return;
            // view 객체 조회

            List<UserBoard> userBoardList = userBoardRepository.findByUserId(
                userUpdated.getId()
            );
            for (UserBoard userBoard : userBoardList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                userBoard.setEmail(userUpdated.getEmail());
                userBoard.setName(userUpdated.getName());
                userBoard.setPassword(userUpdated.getPassword());
                // view 레파지 토리에 save
                userBoardRepository.save(userBoard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenUserDeleted_then_DELETE_1(
        @Payload UserDeleted userDeleted
    ) {
        try {
            if (!userDeleted.validate()) return;
            // view 레파지 토리에 삭제 쿼리
            userBoardRepository.deleteByUserId(userDeleted.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
