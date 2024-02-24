package APBook.diplom.service;

import APBook.diplom.models.Photo;
import APBook.diplom.models.Post;
import APBook.diplom.models.Project;
import APBook.diplom.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Slf4j
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final PhotoService photoService;
    private final PostService postService;
    private final UserService userService;

    public ProjectService(ProjectRepository projectRepository, PhotoService photoService, PostService postService, UserService userService) {
        this.projectRepository = projectRepository;
        this.photoService = photoService;
        this.postService = postService;
        this.userService = userService;
    }

    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Project get(long id) {
        Project project = projectRepository.findById(id).orElse(null);
        if(project == null){
            log.error("Проект не найден");
            return null;
        }
        return project;
    }

    public List<Photo> getAllPhotos(long id) {
        Project project = projectRepository.findById(id).orElse(null);
        return project.getPhotos();
    }

    public Project add(Project project, Long userId) {
        List<Photo> photos = project.getPhotos();
        if (photos != null) {
            for (Photo photo : photos) {
                photo.setProject(project);
            }
        }
        project.setAuthor(userService.show(userId));
        projectRepository.save(project);
        return project;
    }

    public List<Post> getAllPosts(long id) {
        Project project = projectRepository.findById(id).orElse(null);
        return project.getPosts();
    }

    public Photo addPhoto(Long id, Photo photo) {
        Project project = this.get(id);
        photo.setProject(project);
        photoService.add(photo);
        return photo;
    }

    public Post addPost(Long id, Post post) {
        Project project = this.get(id);
        post.setProject(project);
        postService.add(post);
        return post;
    }

    public Project update(long id, Project project) {
        Project uProject = projectRepository.findById(id).get();
        uProject.setName(project.getName());
        uProject.setDescription(project.getDescription());
        uProject.setAddress(project.getAddress());
        uProject.setAuthor(project.getAuthor());
        uProject.setStartDate(project.getStartDate());
        uProject.setEndDate(project.getEndDate());
        uProject.setLogo(project.getLogo());
        uProject.setIsOnline(project.getIsOnline());
        uProject.setPhotos(project.getPhotos());
        uProject.setPosts(project.getPosts());
        uProject.setSubscribers(project.getSubscribers());
        projectRepository.save(uProject);

        return uProject;
    }

    public void delete(long id) {
        projectRepository.deleteById(id);
    }

}
