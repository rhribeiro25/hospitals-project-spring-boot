package br.com.rhribeiro25.manageLabs.service;

import br.com.rhribeiro25.manageLabs.model.ExaminationModel;
import br.com.rhribeiro25.manageLabs.model.enums.ExaminationStatusEnum;
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

public interface ExaminationService {

    public Set<ExaminationModel> findAll();

    public ExaminationModel findById(Long id);

    public Set<ExaminationModel> findLabsByExamName(String name);

    public ExaminationModel save(ExaminationModel ExaminationModel);

    public ExaminationModel update(ExaminationModel ExaminationModel);

    public void delete(ExaminationModel exam);

    public Set<ExaminationModel> saveAll(Set<? extends ExaminationModel> exams);

    public void deleteAll(Set<? extends ExaminationModel> exams);

    public Set<ExaminationModel> findByCreatedAtBetween(String from, String to);

    public boolean existsById(Long id);

    public ExaminationModel findActiveById(Long id);

    public Set<ExaminationModel> findByStatus(ExaminationStatusEnum status);

    public BatchStatus runBatch(Long now, String examFullPath, String type, String action) throws JobExecutionAlreadyRunningException, JobRestartException,
            JobInstanceAlreadyCompleteException, JobParametersInvalidException;

}
