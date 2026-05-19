package bake.nest.customcake;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomCakeRequestRepository extends JpaRepository<CustomCakeRequest, Long> {
    List<CustomCakeRequest> findByUserIdOrderByIdDesc(Long userId);
    List<CustomCakeRequest> findAllByOrderByIdDesc();
}
