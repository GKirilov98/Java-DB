package weddingplaner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weddingplaner.domain.entity.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Integer> {
}
