package blog.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Date;
import lombok.Data;
import java.time.LocalDate;


//<<< EDA / CQRS
@Entity
@Table(name="PostBoard_table")
@Data
public class PostBoard {

        @Id
        //@GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;
        private Long postId;
        private String postContent;
        private String postTitle;
        private List&lt;Long&gt; commentList;


}
