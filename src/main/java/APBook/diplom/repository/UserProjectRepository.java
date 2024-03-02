package APBook.diplom.repository;

import APBook.diplom.models.User;
import APBook.diplom.models.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, Long> {
    UserProject findUserProjectByUserIdAndProjectId(Long userId, Long projectId);
}
