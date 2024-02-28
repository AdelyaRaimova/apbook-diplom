package APBook.diplom.repository;

import APBook.diplom.models.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProjectRepository extends JpaRepository<UserProject, Long> {
    UserProject findUserProjectByUserIdAndProjectId(Long userId, Long projectId);
}
