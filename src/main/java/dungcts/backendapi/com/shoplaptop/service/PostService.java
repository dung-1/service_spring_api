package dungcts.backendapi.com.shoplaptop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dungcts.backendapi.com.shoplaptop.common.ResourceNotFoundException;
import dungcts.backendapi.com.shoplaptop.dto.PostDTO;
import dungcts.backendapi.com.shoplaptop.entity.Post;
import dungcts.backendapi.com.shoplaptop.responsitory.CategoryRepository;
import dungcts.backendapi.com.shoplaptop.responsitory.PostRepository;
import dungcts.backendapi.com.shoplaptop.responsitory.UserRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public PostDTO addPost(PostDTO postDTO, MultipartFile file) throws IOException {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setFromDate(postDTO.getFromDate());
        post.setToDate(postDTO.getToDate());
        post.setStatus(postDTO.getStatus());
        post.setViewcount(postDTO.getViewcount());
        post.setCategory(categoryRepository.findById(postDTO.getCategoryId()).orElse(null));
        post.setCreatedBy(userRepository.findById(postDTO.getCreatedBy()).orElse(null));
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        // Xử lý tải lên hình ảnh
        if (file != null && !file.isEmpty()) {
            String imageUrl = fileStorageService.storeFile(file);
            post.setExcerptImage(imageUrl);
        }

        post = postRepository.save(post);
        return toDTO(post);
    }

    public PostDTO updatePost(Long postId, PostDTO postDTO, MultipartFile file) throws IOException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setFromDate(postDTO.getFromDate());
        post.setToDate(postDTO.getToDate());
        post.setStatus(postDTO.getStatus());
        post.setViewcount(postDTO.getViewcount());
        post.setCategory(categoryRepository.findById(postDTO.getCategoryId()).orElse(null));
        post.setUpdatedAt(LocalDateTime.now());

        // Xử lý cập nhật ảnh
        if (file != null && !file.isEmpty()) {
            String imageUrl = fileStorageService.storeFile(file);
            post.setExcerptImage(imageUrl);
        } else if (postDTO.getExcerptImage() != null && !postDTO.getExcerptImage().isEmpty()) {
            // Giữ nguyên URL hình ảnh hiện tại nếu không có file mới được tải lên
            post.setExcerptImage(postDTO.getExcerptImage());
        }

        post = postRepository.save(post);
        return toDTO(post);
    }

    public void deletePost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found");
        }
        postRepository.deleteById(postId);
    }

    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> postDTOs = posts.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return postDTOs;
    }

    public PostDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        return toDTO(post);
    }

    private PostDTO toDTO(Post post) {
        String categoryName = (post.getCategory() != null) ? post.getCategory().getName() : "";
        Long categoryId = (post.getCategory() != null) ? post.getCategory().getCategoryId() : null;
        return new PostDTO(
                post.getPostId(),
                post.getTitle(),
                post.getExcerptImage(),
                post.getContent(),
                (post.getCreatedBy() != null) ? post.getCreatedBy().getUserId() : null,
                post.getFromDate(),
                post.getToDate(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getStatus(),
                post.getViewcount(),
                categoryId,
                categoryName);
    }
}