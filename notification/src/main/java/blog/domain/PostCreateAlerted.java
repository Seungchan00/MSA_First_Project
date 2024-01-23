package blog.domain;

import blog.domain.*;
import blog.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class PostCreateAlerted extends AbstractEvent {

    private Long id;
    private Long postId;
    private String alertMessage;
    private Long commentId;

    public PostCreateAlerted(Notify aggregate) {
        super(aggregate);
    }

    public PostCreateAlerted() {
        super();
    }
}
//>>> DDD / Domain Event
