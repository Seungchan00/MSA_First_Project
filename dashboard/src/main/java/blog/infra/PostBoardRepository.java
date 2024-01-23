package blog.infra;

import blog.domain.*;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "postBoards",
    path = "postBoards"
)
public interface PostBoardRepository
    extends PagingAndSortingRepository<PostBoard, Long> {
    List<PostBoard> findByPostId(Long postId);

    void deleteByPostId(Long postId);
}
