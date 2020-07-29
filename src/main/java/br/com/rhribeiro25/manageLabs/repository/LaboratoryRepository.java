package br.com.rhribeiro25.manageLabs.repository;

import br.com.rhribeiro25.manageLabs.model.LaboratoryModel;
import br.com.rhribeiro25.manageLabs.model.enums.LaboratoryStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Renan Ribeiro
 * @date 15/07/2020.
 */
@Repository
public interface LaboratoryRepository extends JpaRepository<LaboratoryModel, Long> {

	public LaboratoryModel findByIdAndStatus(Long id, LaboratoryStatusEnum status);

	public Set<LaboratoryModel> findByStatus(LaboratoryStatusEnum status);
	
	public Set<LaboratoryModel> findByCreatedAtBetween(Date from, Date to);

}
