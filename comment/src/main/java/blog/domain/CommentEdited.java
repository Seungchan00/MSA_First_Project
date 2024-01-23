package blog.domain;

import blog.domain.*;
import blog.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class CommentEdited extends AbstractEvent {

    private Long id;
    private String content;
    private Long postId;

    public CommentEdited(Comment aggregate) {
        super(aggregate);
    }

    public CommentEdited() {
        super();
    }
}
//>>> DDD / Domain Event
