package br.com.rhribeiro25.manageLabs.service;

import br.com.rhribeiro25.manageLabs.model.LaboratoryModel;
import br.com.rhribeiro25.manageLabs.model.enums.LaboratoryStatusEnum;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import java.util.Set;

/**
 * @author Renan Ribeiro
 * @date 15/07/2020.
 */

public interface LaboratoryService {

	public Set<LaboratoryModel> findAll();
	
	public LaboratoryModel findById(Long id);

	public LaboratoryModel findActiveById(Long id);
	
	public LaboratoryModel save(LaboratoryModel LaboratoryModel);

	public LaboratoryModel update(LaboratoryModel LaboratoryModel);
	
	public void delete(LaboratoryModel lab);

	public void saveAll(Set<? extends LaboratoryModel> labs);

	public void deleteAll(Set<? extends LaboratoryModel> labs);

	public Set<LaboratoryModel> findByCreatedAtBetween(String from, String to);
	
	public boolean existsById(Long id);

	public BatchStatus runBatch(Long now, String labFullPath, String type, String action) throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException;

	public Set<LaboratoryModel> findByStatus(LaboratoryStatusEnum status);

}
