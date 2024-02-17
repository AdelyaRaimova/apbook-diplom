package APBook.diplom.service;

import APBook.diplom.models.Post;
import APBook.diplom.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post show(Long id){
        return postRepository.findById(id).orElse(null);
    }

    public Post add(Post post){
        return postRepository.save(post);
    }

    public void delete(long id){
        postRepository.deleteById(id);
    }
}
