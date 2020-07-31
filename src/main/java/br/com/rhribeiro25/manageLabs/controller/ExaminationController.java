package br.com.rhribeiro25.manageLabs.controller;

import br.com.rhribeiro25.manageLabs.error.exception.NotFoundException;
import br.com.rhribeiro25.manageLabs.model.ExaminationModel;
import br.com.rhribeiro25.manageLabs.model.LaboratoryModel;
import br.com.rhribeiro25.manageLabs.model.dtos.ExaminationDTORequest;
import br.com.rhribeiro25.manageLabs.model.dtos.ExaminationDTOResponse;
import br.com.rhribeiro25.manageLabs.model.dtos.LaboratoryDTOResponse;
import br.com.rhribeiro25.manageLabs.model.enums.ExaminationStatusEnum;
import br.com.rhribeiro25.manageLabs.service.ExaminationService;
import br.com.rhribeiro25.manageLabs.utils.FilesIO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Renan Ribeiro
 * @date 15/07/2020.
 */

@RestController
@RequestMapping("/manage-labs/examinations")
@Slf4j
public class ExaminationController {

    @Autowired
    private ExaminationService examinationService;

    @Value("${files.csv.path}")
    private String csvFilesPath;

    @ApiOperation(value = "Salvar Exames em lote", notes = "Utilizar um arquivo CSV no seguinte modelo: EXAM|0|2020-01-01 00:00:11.763|Examination Name test 1|CLINIC|ACTIVE")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "CREATED")})
    @PostMapping("/create-by-batch")
    @PreAuthorize("hasRole('ADMIN')")
    @Async
    public ResponseEntity<Object> saveExamsFromFile(@ApiParam(value = "Arquivo CSV de Exames")
                                                    @RequestParam MultipartFile exam) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Long now = System.currentTimeMillis();
        String examName = "exam_" + now + ".csv";
        String examFullPath = csvFilesPath + examName;
        // Save File
        FilesIO.salvar(csvFilesPath, examName, exam);
        // Run Job
        BatchStatus status = examinationService.runBatch(now, examFullPath, "EXAM", "CREATE");
        if (status == BatchStatus.COMPLETED)
            return new ResponseEntity<>(status.toString(), HttpStatus.CREATED);
        else if (status == BatchStatus.FAILED)
            return new ResponseEntity<>(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, status.toString()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        else
            return new ResponseEntity<>(new ErrorPage(HttpStatus.BAD_REQUEST, status.toString()),
                    HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Atualizar Exames em lote", notes = "Utilizar um arquivo CSV no seguinte modelo: EXAM|1|2020-01-01 00:00:11.763|Examination Name test 1|CLINIC|ACTIVE")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @PutMapping("/update-by-batch")
    @PreAuthorize("hasRole('ADMIN')")
    @Async
    public ResponseEntity<Object> updateExamsFromFile(@ApiParam(value = "Arquivo CSV de Exames")
                                                      @RequestParam MultipartFile exam) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Long now = System.currentTimeMillis();
        String examName = "exam_" + now + ".csv";
        String examFullPath = csvFilesPath + examName;
        // Save File
        FilesIO.salvar(csvFilesPath, examName, exam);
        // Run Job
        BatchStatus status = examinationService.runBatch(now, examFullPath, "EXAM", "UPDATE");
        if (status == BatchStatus.COMPLETED)
            return new ResponseEntity<>(status.toString(), HttpStatus.OK);
        else if (status == BatchStatus.FAILED)
            return new ResponseEntity<>(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, status.toString()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        else
            return new ResponseEntity<>(new ErrorPage(HttpStatus.BAD_REQUEST, status.toString()),
                    HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Deletar Exames em lote", notes = "Utilizar um arquivo CSV no seguinte modelo: EXAM|1|2020-01-01 00:00:11.763|Examination Name test 1|CLINIC|ACTIVE")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @DeleteMapping("/delete-by-batch")
    @PreAuthorize("hasRole('ADMIN')")
    @Async
    public ResponseEntity<Object> deleteExamsFromFile(@ApiParam(value = "Arquivo CSV de Exames")
                                                      @RequestParam MultipartFile exam) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Long now = System.currentTimeMillis();
        String examName = "exam_" + now + ".csv";
        String examFullPath = csvFilesPath + examName;
        // Save File
        FilesIO.salvar(csvFilesPath, examName, exam);
        // Run Job
        BatchStatus status = examinationService.runBatch(now, examFullPath, "EXAM", "DELETE");
        if (status == BatchStatus.COMPLETED)
            return new ResponseEntity<>(status.toString(), HttpStatus.OK);
        else if (status == BatchStatus.FAILED)
            return new ResponseEntity<>(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, status.toString()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        else
            return new ResponseEntity<>(new ErrorPage(HttpStatus.BAD_REQUEST, status.toString()),
                    HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Buscar Exame por ID", notes = "Busca um exame por ID, podendo ele ser ATIVO ou INATIVO")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = ExaminationDTOResponse.class)})
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<?> findById(@ApiParam(value = "Id do Exame")
                                      @PathVariable("id") Long id) {
        ExaminationModel exam = this.returnExistsExam(id);
        return new ResponseEntity<>(ExaminationDTOResponse.returnDtoToShow(exam), HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar todos os Exames ATIVOS", notes = "Busca todos os exames que estão ATIVOS")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = ExaminationDTOResponse.class, responseContainer = "List")})
    @GetMapping("/find-all")
    public ResponseEntity<?> findAll() {
        Set<ExaminationModel> exams = examinationService.findAll();
        this.verifyExistsExams(exams);
        return new ResponseEntity<>(ExaminationDTOResponse.returnDtosToShow(exams), HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar Exames por STATUS", notes = "Busca todos os exames, passando ATIVO ou INATIVO como patâmetro")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = ExaminationDTOResponse.class, responseContainer = "List")})
    @GetMapping("/find-by-status/{statusExam}")
    public ResponseEntity<?> findByStatus(@ApiParam(value = "Status do Exame")
                                          @PathVariable("statusExam") ExaminationStatusEnum statusExam) {
        Set<ExaminationModel> exams = examinationService.findByStatus(statusExam);
        this.verifyExistsExams(exams);
        return new ResponseEntity<>(ExaminationDTOResponse.returnDtosToShow(exams), HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar Laboratórios por NOME de Exame", notes = "Busca todos os laboratórios, passando NOME do exame como patâmetro")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = ExaminationDTOResponse.class, responseContainer = "List")})
    @GetMapping("/find-labs-by-exam-name/{name}")
    public ResponseEntity<?> findLabsByExamName(@ApiParam(value = "Nome do Exame")
                                                @PathVariable("name") String name) {
        Set<ExaminationModel> exams = examinationService.findLabsByExamName(name);
        this.verifyExistsExams(exams);
        Set<LaboratoryModel> labsResult = new HashSet<>();
        exams.forEach(exam -> labsResult.addAll(exam.getLaboratories()));
        return new ResponseEntity<>(LaboratoryDTOResponse.returnDtosToShow(labsResult), HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar Exames por intervalo de data de CRIAÇÃO", notes = "Busca todos os exames em um intervalo de data de criação, exemplo: 2020-01-01 00:13:48.478 / 2020-31-12 00:13:48.478")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = ExaminationDTOResponse.class, responseContainer = "List")})
    @GetMapping("/find-by-createdat-between/{from}/{to}")
    public ResponseEntity<?> findByCreatedAtBetween(@ApiParam(value = "Data de criação inicial")
                                                    @PathVariable("from") String from,
                                                    @ApiParam(value = "Data de criação final")
                                                    @PathVariable("to") String to) {
        Set<ExaminationModel> exams = examinationService.findByCreatedAtBetween(from, to);
        this.verifyExistsExams(exams);
        return new ResponseEntity<>(ExaminationDTOResponse.returnDtosToShow(exams), HttpStatus.OK);
    }

    @ApiOperation(value = "Salvar Exames", notes = "Salva exame e laboratórios se necessário")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "CREATE", response = ExaminationDTOResponse.class)})
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(@ApiParam(value = "Json para Exame")
                                  @Valid @RequestBody ExaminationDTORequest examDTO) {
        ExaminationModel exam = examinationService.save(examDTO.returnExamToSave());
        return new ResponseEntity<>(ExaminationDTOResponse.returnDtoToShow(exam), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Atualizar Exame", notes = "Atualiza exame e laboratórios se necessário")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = ExaminationDTOResponse.class)})
    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> update(@ApiParam(value = "Json de Exame")
                                    @Valid @RequestBody ExaminationDTORequest examDTO) {
        this.verifyExistsExam(examDTO.getId());
        ExaminationModel exam = examinationService.update(examDTO.returnExamToUpdate());
        return new ResponseEntity<>(ExaminationDTOResponse.returnDtoToShow(exam), HttpStatus.OK);
    }

    @ApiOperation(value = "Deletar logicamente Exame", notes = "Deleta logicamente exame")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> delete(@ApiParam(value = "Id do Exame")
                                    @PathVariable("id") Long id) {
        ExaminationModel exam = this.returnExistsExam(id);
        examinationService.delete(exam);
        return new ResponseEntity<>("Successful to delete examination!", HttpStatus.OK);
    }

    protected ExaminationModel returnExistsExam(Long id) {
        ExaminationModel exam = examinationService.findById(id);
        if (exam == null)
            throw new NotFoundException("Examination not found by ID: " + id);
        return exam;
    }

    protected void verifyExistsExam(Long id) {
        if (examinationService.findById(id) == null)
            throw new NotFoundException("Examination not found by ID: " + id);
    }

    protected void verifyExistsExams(Set<ExaminationModel> exams) {
        if (exams == null || exams.size() == 0)
            throw new NotFoundException("Examinations not found");
    }

}
