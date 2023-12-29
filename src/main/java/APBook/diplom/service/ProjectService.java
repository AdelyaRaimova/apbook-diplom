package APBook.diplom.service;

import APBook.diplom.models.Photo;
import APBook.diplom.models.Project;
import APBook.diplom.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final PhotoService photoService;

    public ProjectService(ProjectRepository projectRepository, PhotoService photoService) {
        this.projectRepository = projectRepository;
        this.photoService = photoService;
    }

    public List<Project> getAll(){
        return projectRepository.findAll();
    }

    public Project get(long id){
        return projectRepository.findById(id).get();
    }

    public List<Photo> getAllPhotos(long id){
        Project project = projectRepository.findById(id).orElse(null);
        return project.getPhotos();
    }

    public Project add(Project project){
        List<Photo> photos = project.getPhotos();
        if(photos != null){
            for (Photo photo: photos){
                photo.setProject(project);

            }
        }
        projectRepository.save(project);
        return project;
    }

    public void update(long id, Project project){
        Project uProject = projectRepository.findById(id).get();
        uProject.setName(project.getName());
        uProject.setDescription(project.getDescription());
        projectRepository.save(uProject);
    }

    public void delete(long id){
        projectRepository.deleteById(id);
    }

}
