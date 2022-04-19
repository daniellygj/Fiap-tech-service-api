package br.com.fiap.abctechservice.repository;

import br.com.fiap.abctechservice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Orders o JOIN o.tasks t WHERE :orderId IN t")
    List<Task> findAllByOrderId(Long orderId);
}
