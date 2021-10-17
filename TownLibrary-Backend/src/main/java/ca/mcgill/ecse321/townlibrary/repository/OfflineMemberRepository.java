package ca.mcgill.ecse321.townlibrary.repository;

import ca.mcgill.ecse321.townlibrary.model.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfflineMemberRepository extends CrudRepository<OfflineMember, Integer> {
    
/**
 * Find all instances of OfflineMember if present
 * from a given name
 * @param name The given name
 * @return instances of OfflineMember or optional 
 */
    List<OfflineMember> findByAddress(String name);
}
