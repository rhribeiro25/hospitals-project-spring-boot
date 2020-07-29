package br.com.rhribeiro25.manageLabs.service;

import br.com.rhribeiro25.manageLabs.model.ExaminationModel;
import br.com.rhribeiro25.manageLabs.model.LaboratoryModel;
import br.com.rhribeiro25.manageLabs.model.enums.ExaminationStatusEnum;
import br.com.rhribeiro25.manageLabs.model.enums.LaboratoryStatusEnum;
import br.com.rhribeiro25.manageLabs.repository.ExaminationRepository;
import br.com.rhribeiro25.manageLabs.utils.Formatting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * @author Renan Ribeiro
 * @date 15/07/2020.
 */

@Service
@Slf4j
public class ExaminationServiceImpl implements ExaminationService {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;
	
	@Autowired
	private ExaminationRepository examinationRepository;

	@Override
	public Set<ExaminationModel> findAll() {
		return examinationRepository.findByStatus(ExaminationStatusEnum.ACTIVE);
	}

	@Override
	public ExaminationModel findById(Long id) {
		return examinationRepository.findById(id).orElse(null);
	}

	@Override
	public ExaminationModel findActiveById(Long id) {
		return examinationRepository.findByIdAndStatus(id, ExaminationStatusEnum.ACTIVE);
	}

	@Override
	public Set<ExaminationModel> findByStatus(ExaminationStatusEnum status) {
		return examinationRepository.findByStatus(status);
	}

	@Override
	public Set<ExaminationModel> findLabsByExamName(String name) {
		return examinationRepository.findByName(name);
	}

	@Override
	public Set<ExaminationModel> findByCreatedAtBetween(String from, String to) {
		Date dateFrom = Formatting.stringToDate_yyyy_MM_dd__HH_mm_ss(from);
		Date dateTo = Formatting.stringToDate_yyyy_MM_dd__HH_mm_ss(to);
		return examinationRepository.findByCreatedAtBetween(dateFrom, dateTo);
	}

	@Override
	public ExaminationModel save(ExaminationModel exam) {
		exam.setCreatedAt(new Date());
		exam.setStatus(ExaminationStatusEnum.ACTIVE);
		if(exam.getLaboratories() != null && exam.getLaboratories().size() > 0) {
			exam.getLaboratories().forEach(lab -> setNewLab(lab));
		}
		examinationRepository.save(exam);
		return exam;
	}

	@Override
	public ExaminationModel update(ExaminationModel exam) {
		examinationRepository.save(exam);
		return exam;
	}

	@Override
	public Set<ExaminationModel> saveAll(Set<? extends ExaminationModel> labs) {
		examinationRepository.saveAll(labs);
		return (Set<ExaminationModel>) labs;
	}

	@Override
	public void deleteAll(Set<? extends ExaminationModel> exams) {
		exams.forEach(exam -> exam.setStatus(ExaminationStatusEnum.INACTIVE));
		examinationRepository.saveAll(exams);
	}

	@Override
	public void delete(ExaminationModel exam) {
		exam.setStatus(ExaminationStatusEnum.INACTIVE);
		examinationRepository.save(exam);
	}
	
	@Override
	public boolean existsById(Long id) {
		return examinationRepository.existsById(id);
	}
	
	@Override
	public BatchStatus runBatch(Long now, String labFullPath, String type, String action) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		Map<String, JobParameter> maps = new HashMap<>();
		maps.put("time", new JobParameter(now));
		maps.put("filePath", new JobParameter(labFullPath));
		maps.put("type", new JobParameter(type));
		maps.put("action", new JobParameter(action));
		JobParameters parameters = new JobParameters(maps);
		JobExecution jobExecution = jobLauncher.run(job, parameters);
		BatchStatus status = jobExecution.getStatus();
		return status;
	}

	private void setNewLab(LaboratoryModel lab){
		lab.setCreatedAt(new Date());
		lab.setStatus(LaboratoryStatusEnum.ACTIVE);
	}

}
