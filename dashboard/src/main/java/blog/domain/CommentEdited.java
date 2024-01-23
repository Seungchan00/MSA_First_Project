package blog.domain;

import blog.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class CommentEdited extends AbstractEvent {

    private Long id;
    private String content;
    private Long postId;
}
