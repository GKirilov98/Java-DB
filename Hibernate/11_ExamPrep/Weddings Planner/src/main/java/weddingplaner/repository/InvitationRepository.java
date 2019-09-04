package weddingplaner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weddingplaner.domain.entity.Invitation;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Integer> {
}
