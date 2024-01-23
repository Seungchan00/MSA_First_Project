package blog.domain;

import blog.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class UserDeleted extends AbstractEvent {

    private Long id;
    private String password;
    private String name;
    private String nickname;
    private String email;
}
