package blog.infra;

import blog.domain.*;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "userBoards",
    path = "userBoards"
)
public interface UserBoardRepository
    extends PagingAndSortingRepository<UserBoard, Long> {
    List<UserBoard> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
