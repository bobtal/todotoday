package com.teamtreehouse.todotoday.dao;

import com.teamtreehouse.todotoday.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    User findByUsername(String username);

    // If you want to inject user-specific data using the authentication object for
    // INSERT statements, in the same way that we did for SELECT statements (in TaskDao),
    // you'll need to use a native query. Here is an example of a Spring Data JPA
    // interface method that you could use:

    // This is using Spring Expression Language (SpEL)? to get to the values of
    // the fields of each entity?
//    @Modifying
//    @Transactional
//    @Query(
//        nativeQuery = true,
//        value = "insert into task (user_id,description,complete) " +
//                "values (:#{principal.id},:#{#task.description},:#{#task.complete})"
//    )
//    void saveForCurrentUser(@Param("task") Task task);
}
