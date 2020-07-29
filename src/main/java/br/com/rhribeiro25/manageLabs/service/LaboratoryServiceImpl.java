package br.com.rhribeiro25.manageLabs.service;

import br.com.rhribeiro25.manageLabs.model.ExaminationModel;
import br.com.rhribeiro25.manageLabs.model.LaboratoryModel;
import br.com.rhribeiro25.manageLabs.model.enums.ExaminationStatusEnum;
import br.com.rhribeiro25.manageLabs.model.enums.LaboratoryStatusEnum;
import br.com.rhribeiro25.manageLabs.repository.ExaminationRepository;
import br.com.rhribeiro25.manageLabs.repository.LaboratoryRepository;
import br.com.rhribeiro25.manageLabs.utils.Formatting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * @author Renan Ribeiro
 * @date 15/07/2020.
 */

@Service
@Slf4j
public class LaboratoryServiceImpl implements LaboratoryService {

	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	Job job;
	
	@Autowired
	private LaboratoryRepository laboratoryRepository;

	@Autowired
	private ExaminationRepository examinationRepository;

	@Override
	public Set<LaboratoryModel> findAll() {
		return laboratoryRepository.findByStatus(LaboratoryStatusEnum.ACTIVE);
	}

	@Override
	public LaboratoryModel findById(Long id) {
		return laboratoryRepository.findById(id).orElse(null);
	}

	@Override
	public Set<LaboratoryModel> findByStatus(LaboratoryStatusEnum status) {
		return laboratoryRepository.findByStatus(status);
	}

	@Override
	public LaboratoryModel findActiveById(Long id) {
		return laboratoryRepository.findByIdAndStatus(id, LaboratoryStatusEnum.ACTIVE);
	}

	@Override
	public Set<LaboratoryModel> findByCreatedAtBetween(String from, String to) {
		Date dateFrom = Formatting.stringToDate_yyyy_MM_dd__HH_mm_ss(from);
		Date dateTo = Formatting.stringToDate_yyyy_MM_dd__HH_mm_ss(to);
		return laboratoryRepository.findByCreatedAtBetween(dateFrom, dateTo);
	}

	@Override
	public LaboratoryModel save(LaboratoryModel lab) {
		lab.setCreatedAt(new Date());
		lab.setStatus(LaboratoryStatusEnum.ACTIVE);
		if(lab.getExaminations() != null && lab.getExaminations().size() > 0) {
			lab.getExaminations().forEach(exam -> saveNewExam(exam, lab));
		}
		laboratoryRepository.save(lab);
		return lab;
	}

	@Override
	public LaboratoryModel update(LaboratoryModel lab) {
		laboratoryRepository.save(lab);
		return lab;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveAll(Set<? extends LaboratoryModel> labs) {
		labs.forEach(lab -> setNewLab(lab));
		laboratoryRepository.saveAll(labs);
	}

	@Override
	public void deleteAll(Set<? extends LaboratoryModel> labs) {
		labs.forEach(lab -> lab.setStatus(LaboratoryStatusEnum.INACTIVE));
		laboratoryRepository.saveAll(labs);
	}

	@Override
	public void delete(LaboratoryModel lab) {
		lab.setStatus(LaboratoryStatusEnum.INACTIVE);
		laboratoryRepository.save(lab);
	}
	
	@Override
	public boolean existsById(Long id) {
		return laboratoryRepository.existsById(id);
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

	private void saveNewExam(ExaminationModel exam, LaboratoryModel lab){
		exam.setLaboratories(new HashSet<>());
		exam.getLaboratories().add(lab);
		exam.setCreatedAt(new Date());
		exam.setStatus(ExaminationStatusEnum.ACTIVE);
		examinationRepository.save(exam);
	}

	private void setNewLab(LaboratoryModel lab){
		lab.setExaminations(new HashSet<>());
		lab.setCreatedAt(new Date());
		lab.setStatus(LaboratoryStatusEnum.ACTIVE);
	}

}
