package softuni.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.demo.domain.entities.Branch;

@Repository
public interface BranchRepository  extends JpaRepository<Branch, Integer> {
    Branch findBranchByName(String name);
}
