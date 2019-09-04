package weddingplaner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weddingplaner.domain.entity.Agency;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Integer> {
    Agency findAgencyByName(String name);
}
