package blog.domain;

import blog.NotificationApplication;
import blog.domain.CommentCreateAlerted;
import blog.domain.PostCreateAlerted;
import blog.domain.PostDeleteAlerted;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Notify_table")
@Data
//<<< DDD / Aggregate Root
public class Notify {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long postId;

    private String alertMessage;

    private Long commentId;

    @PostPersist
    public void onPostPersist() {
        PostCreateAlerted postCreateAlerted = new PostCreateAlerted(this);
        postCreateAlerted.publishAfterCommit();

        PostDeleteAlerted postDeleteAlerted = new PostDeleteAlerted(this);
        postDeleteAlerted.publishAfterCommit();

        CommentCreateAlerted commentCreateAlerted = new CommentCreateAlerted(
            this
        );
        commentCreateAlerted.publishAfterCommit();
    }

    public static NotifyRepository repository() {
        NotifyRepository notifyRepository = NotificationApplication.applicationContext.getBean(
            NotifyRepository.class
        );
        return notifyRepository;
    }

    //<<< Clean Arch / Port Method
    public static void postCreateAlert(PostCreated postCreated) {
        //implement business logic here:

        /** Example 1:  new item 
        Notify notify = new Notify();
        repository().save(notify);

        PostCreateAlerted postCreateAlerted = new PostCreateAlerted(notify);
        postCreateAlerted.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(postCreated.get???()).ifPresent(notify->{
            
            notify // do something
            repository().save(notify);

            PostCreateAlerted postCreateAlerted = new PostCreateAlerted(notify);
            postCreateAlerted.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void postDeleteAlert(PostDeleted postDeleted) {
        //implement business logic here:

        /** Example 1:  new item 
        Notify notify = new Notify();
        repository().save(notify);

        PostDeleteAlerted postDeleteAlerted = new PostDeleteAlerted(notify);
        postDeleteAlerted.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(postDeleted.get???()).ifPresent(notify->{
            
            notify // do something
            repository().save(notify);

            PostDeleteAlerted postDeleteAlerted = new PostDeleteAlerted(notify);
            postDeleteAlerted.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void commentCreateAlert(CommentCreated commentCreated) {
        //implement business logic here:

        /** Example 1:  new item 
        Notify notify = new Notify();
        repository().save(notify);

        */

        /** Example 2:  finding and process
        
        repository().findById(commentCreated.get???()).ifPresent(notify->{
            
            notify // do something
            repository().save(notify);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
