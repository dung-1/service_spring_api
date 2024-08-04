package dungcts.backendapi.com.shoplaptop.responsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dungcts.backendapi.com.shoplaptop.dto.PostDTO;
import dungcts.backendapi.com.shoplaptop.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAll();
}