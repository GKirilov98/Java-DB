package photography.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import photography.domain.entity.camera.Camera;

@Repository
public interface CameraRepository extends JpaRepository<Camera, Integer> {
}
