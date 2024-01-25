package blog.domain;

import blog.PostApplication;
import blog.domain.PostCreated;
import blog.domain.PostDeleted;
import blog.domain.PostEdited;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.transaction.Transactional;

import lombok.Data;

@Entity
@Table(name = "Post_table")
@Data
//<<< DDD / Aggregate Root
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String content;

    private Long userId;

    @ElementCollection
    private List<Long> commentList;

    private String nickname;

    @PostPersist
    public void onPostPersist() {
        PostCreated postCreated = new PostCreated(this);
        postCreated.publishAfterCommit();
    }

    @PreUpdate
    public void onPreUpdate() {
        PostEdited postEdited = new PostEdited(this);
        postEdited.publishAfterCommit();
    }

    @PreRemove
    public void onPreRemove() {
        PostDeleted postDeleted = new PostDeleted(this);
        postDeleted.publishAfterCommit();
    }

    public static PostRepository repository() {
        PostRepository postRepository = PostApplication.applicationContext.getBean(
            PostRepository.class
        );
        return postRepository;
    }

    //<<< Clean Arch / Port Method
    @Transactional
    public static void commentCreateOnPost(CommentCreated commentCreated) {

        
        repository().findById(commentCreated.getPostId()).ifPresent(post->{ // repository에서 postId 와 일치하는 값을 post에 가져온다.  
            
            List<Long> newCommentList = new ArrayList<>(post.getCommentList());     // 새로운 arrayList에 생성하고, 기존에 있던 commnetList를 넣는다. 
            newCommentList.add(commentCreated.getId());                             // newCommenList리스트에 commectCreated의id를 추가한다.
            post.setCommentList(newCommentList);                                    // 기존의 commentList를 newCommentList로 set한다.
            
            repository().save(post);                           // 저장한다.

         });
       

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    @Transactional
    public static void commentDeleteOnPost(CommentDeleted commentDeleted) {
     
        
        repository().findById(commentDeleted.getId()).ifPresent(post->{
            
            List<Long> newCommentList = new ArrayList<>(post.getCommentList());
            newCommentList.remove(commentDeleted.getId());
            post.setCommentList(newCommentList);  
            repository().save(post);

         });

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateUser(UserUpdated userUpdated) {
        //implement business logic here:

        /** Example 1:  new item 
        Post post = new Post();
        repository().save(post);

        */

        /** Example 2:  finding and process
        
        repository().findById(userUpdated.get???()).ifPresent(post->{
            
            post // do something
            repository().save(post);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
