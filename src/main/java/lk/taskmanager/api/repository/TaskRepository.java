package lk.taskmanager.api.repository;

import lk.taskmanager.api.enums.ActiveStatus;
import lk.taskmanager.api.enums.TaskStatus;
import lk.taskmanager.api.model.Task;
import lk.taskmanager.api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findAllByActiveStatus(ActiveStatus activeStatus, Pageable pageable);

    Page<Task> findAllByAssigneeAndActiveStatus(User assignee, ActiveStatus activeStatus, Pageable pageable);

    Page<Task> findAllByCreatedByAndActiveStatus(User createdBy, ActiveStatus activeStatus, Pageable pageable);

    List<Task> findAllByStatusAndActiveStatus(TaskStatus status, ActiveStatus activeStatus);

    Optional<Task> findByIdAndActiveStatus(Long id, ActiveStatus activeStatus);

    @Query("SELECT t FROM Task t WHERE t.activeStatus = :status AND " +
           "(LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Task> searchTasks(@Param("keyword") String keyword,
                           @Param("status") ActiveStatus status,
                           Pageable pageable);

    long countByAssigneeAndStatus(User assignee, TaskStatus status);

    long countByCreatedByAndActiveStatus(User createdBy, ActiveStatus activeStatus);
}
