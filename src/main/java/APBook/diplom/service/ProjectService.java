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

    public List<Project> getProjects(){
        return projectRepository.findAll();
    }

    public Project getProject(long id){
        return projectRepository.findById(id).get();
    }

    public void addProject(Project project){
        projectRepository.save(project);
    }

    public void updateProject(long id, Project project){
        Project uProject = projectRepository.findById(id).get();
        uProject.setName(project.getName());
        uProject.setDescription(project.getDescription());
        projectRepository.save(uProject);
    }

}
