package edu.cpp.cs499.p2.repository;

import edu.cpp.cs499.p2.domain.Lecturer;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Lecturer entity.
 */
@SuppressWarnings("unused")
public interface LecturerRepository extends JpaRepository<Lecturer,Long> {

    @Query("select distinct lecturer from Lecturer lecturer left join fetch lecturer.teaches")
    List<Lecturer> findAllWithEagerRelationships();

    @Query("select lecturer from Lecturer lecturer left join fetch lecturer.teaches where lecturer.id =:id")
    Lecturer findOneWithEagerRelationships(@Param("id") Long id);

}
