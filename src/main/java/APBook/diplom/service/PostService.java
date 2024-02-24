package APBook.diplom.service;

import APBook.diplom.models.Post;
import APBook.diplom.models.Project;
import APBook.diplom.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public Post show(Long id){
        return postRepository.findById(id).orElse(null);
    }

    public List<Post> showNews(Long id){
        List<Post> posts = new ArrayList<>();
        for(Project project: userService.showSubscriptions(id)){
            posts.addAll(project.getPosts());
        }
        return posts;
    }

    public Post add(Post post){
        return postRepository.save(post);
    }

    public void delete(long id){
        postRepository.deleteById(id);
    }
}
