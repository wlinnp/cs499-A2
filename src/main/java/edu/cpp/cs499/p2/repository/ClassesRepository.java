package edu.cpp.cs499.p2.repository;

import edu.cpp.cs499.p2.domain.Classes;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Classes entity.
 */
@SuppressWarnings("unused")
public interface ClassesRepository extends JpaRepository<Classes,Long> {

}
