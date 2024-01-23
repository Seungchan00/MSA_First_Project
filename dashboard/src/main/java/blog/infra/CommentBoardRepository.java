package blog.infra;

import blog.domain.*;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "commentBoards",
    path = "commentBoards"
)
public interface CommentBoardRepository
    extends PagingAndSortingRepository<CommentBoard, Long> {
    List<CommentBoard> findByCommentId(String commentId);

    void deleteByCommentId(Long commentId);
}
