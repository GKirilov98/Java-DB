package weddingplaner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weddingplaner.domain.entity.Wedding;

@Repository
public interface WeddingRepository extends JpaRepository<Wedding, Integer> {
}
