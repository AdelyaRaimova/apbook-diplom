package APBook.diplom.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long id;

    private String text;

    @JsonIgnore
    private LocalDateTime creationDate;

    @JsonGetter("creationDate")
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Photo> photos;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;
    @JsonGetter("project")
    public String getPostName() {
        return project.getName();
    }

    @JsonGetter("projectLogo")
    public String getProjectLogo() {
        return project.getLogo();
    }
}
