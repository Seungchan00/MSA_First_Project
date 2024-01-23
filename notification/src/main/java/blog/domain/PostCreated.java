package blog.domain;

import blog.domain.*;
import blog.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class PostCreated extends AbstractEvent {

    private Long id;
    private String title;
    private String content;
    private Object commentList;
}
