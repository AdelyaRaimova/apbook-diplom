package APBook.diplom.service;

import APBook.diplom.models.Project;
import APBook.diplom.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAll(){
        return projectRepository.findAll();
    }

    public Project get(long id){
        return projectRepository.findById(id).get();
    }

    public void add(Project project){
        projectRepository.save(project);
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
