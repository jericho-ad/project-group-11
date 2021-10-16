package ca.mcgill.ecse321.townlibrary.repository;

import ca.mcgill.ecse321.townlibrary.model.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeadLibrarianRepository extends CrudRepository<HeadLibrarian, Integer> {

    HeadLibrarian findByLibrary(Library lib);
    HeadLibrarian findByAddress(String address);
}
