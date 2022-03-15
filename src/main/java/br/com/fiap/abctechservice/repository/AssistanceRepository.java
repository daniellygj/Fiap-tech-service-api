package br.com.fiap.abctechservice.repository;

import br.com.fiap.abctechservice.model.Assistance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssistanceRepository extends JpaRepository<Assistance, Long> {

}
