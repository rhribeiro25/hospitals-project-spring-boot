package br.com.rhribeiro25.manageLabs.controller;

import br.com.rhribeiro25.manageLabs.error.exception.NotFoundException;
import br.com.rhribeiro25.manageLabs.model.LaboratoryModel;
import br.com.rhribeiro25.manageLabs.model.dtos.ExaminationDTORequest;
import br.com.rhribeiro25.manageLabs.model.dtos.ExaminationDTOResponse;
import br.com.rhribeiro25.manageLabs.model.dtos.LaboratoryDTORequest;
import br.com.rhribeiro25.manageLabs.model.dtos.LaboratoryDTOResponse;
import br.com.rhribeiro25.manageLabs.model.enums.LaboratoryStatusEnum;
import br.com.rhribeiro25.manageLabs.service.LaboratoryService;
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
import java.util.Set;

/**
 * @author Renan Ribeiro
 * @date 15/07/2020.
 */

@RestController
@RequestMapping("/manage-labs/laboratories")
@Slf4j
public class LaboratoryController {

    @Autowired
    private LaboratoryService laboratoryService;

    @Value("${files.csv.path}")
    private String csvFilesPath;

    @ApiOperation(value = "Salvar Laboratórios em lote", notes = "Utilizar um arquivo CSV no seguinte modelo: LAB|0|2020-01-01 00:00:11.763|Laboratory Name test 1|Laboratory Address test 1|ACTIVE")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "CREATED")})
    @PostMapping("/create-by-batch")
    @PreAuthorize("hasRole('ADMIN')")
    @Async
    public ResponseEntity<Object> saveLabsFromFile(@ApiParam(value = "Arquivo CSV de Laboratórios")
                                                   @RequestParam MultipartFile lab) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Long now = System.currentTimeMillis();
        String labName = "lab_" + now + ".csv";
        String labFullPath = csvFilesPath + labName;
        // Save File
        FilesIO.salvar(csvFilesPath, labName, lab);
        // Run Job
        BatchStatus status = laboratoryService.runBatch(now, labFullPath, "LAB", "CREATE");
        if (status == BatchStatus.COMPLETED)
            return new ResponseEntity<>(status.toString(), HttpStatus.CREATED);
        else if (status == BatchStatus.FAILED)
            return new ResponseEntity<>(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, status.toString()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        else
            return new ResponseEntity<>(new ErrorPage(HttpStatus.BAD_REQUEST, status.toString()),
                    HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Atualizar Laboratórios em lote", notes = "Utilizar um arquivo CSV no seguinte modelo: LAB|1|2020-01-01 00:00:11.763|Laboratory Name test 1|Laboratory Address test 1|ACTIVE")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @PutMapping("/update-by-batch")
    @PreAuthorize("hasRole('ADMIN')")
    @Async
    public ResponseEntity<Object> updateLabsFromFile(@ApiParam(value = "Arquivo CSV de Laboratórios")
                                                     @RequestParam MultipartFile lab) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Long now = System.currentTimeMillis();
        String labName = "lab_" + now + ".csv";
        String labFullPath = csvFilesPath + labName;
        // Save File
        FilesIO.salvar(csvFilesPath, labName, lab);
        // Run Job
        BatchStatus status = laboratoryService.runBatch(now, labFullPath, "LAB", "UPDATE");
        if (status == BatchStatus.COMPLETED)
            return new ResponseEntity<>(status.toString(), HttpStatus.OK);
        else if (status == BatchStatus.FAILED)
            return new ResponseEntity<>(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, status.toString()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        else
            return new ResponseEntity<>(new ErrorPage(HttpStatus.BAD_REQUEST, status.toString()),
                    HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Deletar Laboratórios em lote", notes = "Utilizar um arquivo CSV no seguinte modelo: LAB|1|2020-01-01 00:00:11.763|Laboratory Name test 1|Laboratory Address test 1|ACTIVE")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @DeleteMapping("/delete-by-batch")
    @PreAuthorize("hasRole('ADMIN')")
    @Async
    public ResponseEntity<Object> deleteLabsFromFile(@ApiParam(value = "Arquivo CSV de Laboratórios")
                                                     @RequestParam MultipartFile lab) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Long now = System.currentTimeMillis();
        String labName = "lab_" + now + ".csv";
        String labFullPath = csvFilesPath + labName;
        // Save File
        FilesIO.salvar(csvFilesPath, labName, lab);
        // Run Job
        BatchStatus status = laboratoryService.runBatch(now, labFullPath, "LAB", "DELETE");
        if (status == BatchStatus.COMPLETED)
            return new ResponseEntity<>(status.toString(), HttpStatus.OK);
        else if (status == BatchStatus.FAILED)
            return new ResponseEntity<>(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, status.toString()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        else
            return new ResponseEntity<>(new ErrorPage(HttpStatus.BAD_REQUEST, status.toString()),
                    HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Buscar Laboratório por ID", notes = "Busca um Laboratório por ID, podendo ele ser ATIVO ou INATIVO")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response= LaboratoryDTOResponse.class)})
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<?> findById(@ApiParam(value = "Id do Laboratório")
                                      @PathVariable("id") Long id) {
        LaboratoryModel lab = this.returnExistsLab(id);
        return new ResponseEntity<>(LaboratoryDTOResponse.returnDtoToShow(lab), HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar todos os Laboratórios ATIVOS", notes = "Busca todos os Laboratórios que estão ATIVOS")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response= LaboratoryDTOResponse.class, responseContainer = "List")})
    @GetMapping("/find-all")
    public ResponseEntity<?> findAll() {
        Set<LaboratoryModel> labs = laboratoryService.findAll();
        this.verifyExistsLabs(labs);
        return new ResponseEntity<>(LaboratoryDTOResponse.returnDtosToShow(labs), HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar Laboratórios por STATUS", notes = "Busca todos os Laboratórios, passando ATIVO ou INATIVO como patâmetro")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response= LaboratoryDTOResponse.class, responseContainer = "List")})
    @GetMapping("/find-by-status/{statusLab}")
    public ResponseEntity<?> findByStatus(@ApiParam(value = "Status do Laboratório")
                                          @PathVariable("statusLab") LaboratoryStatusEnum statusLab) {
        Set<LaboratoryModel> labs = laboratoryService.findByStatus(statusLab);
        this.verifyExistsLabs(labs);
        return new ResponseEntity<>(LaboratoryDTOResponse.returnDtosToShow(labs), HttpStatus.OK);
    }

    @ApiOperation(value = "Buscar Laboratórios por intervalo de data de CRIAÇÃO", notes = "Busca todos os Laboratórios em um intervalo de data de criação, exemplo: 2020-01-01 00:13:48.478 / 2020-31-12 00:13:48.478")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response= LaboratoryDTOResponse.class, responseContainer = "List")})
    @GetMapping("/find-by-createdat-between/{from}/{to}")
    public ResponseEntity<?> findByCreatedAtBetween(@ApiParam(value = "Data de criação inicial")
                                                    @PathVariable("from") String from,
                                                    @ApiParam(value = "Data de criação final")
                                                    @PathVariable("to") String to) {
        Set<LaboratoryModel> labs = laboratoryService.findByCreatedAtBetween(from, to);
        this.verifyExistsLabs(labs);
        return new ResponseEntity<>(LaboratoryDTOResponse.returnDtosToShow(labs), HttpStatus.OK);
    }

    @ApiOperation(value = "Salvar Laboratório", notes = "Salva Laboratório")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "CREATED", response = LaboratoryDTOResponse.class)})
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(@ApiParam(value = "Json de Laboratório")
                                  @Valid @RequestBody LaboratoryDTORequest labDTO) {
        LaboratoryModel lab = laboratoryService.save(labDTO.returnLabToSave());
        return new ResponseEntity<>(LaboratoryDTOResponse.returnDtoToShow(lab), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Atualizar Laboratório", notes = "Atualiza Laboratório")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = LaboratoryDTOResponse.class)})
    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> update(@ApiParam(value = "Json de Laboratório")
                                    @Valid @RequestBody LaboratoryDTORequest labDTO) {
        this.verifyExistsLab(labDTO.getId());
        LaboratoryModel lab = laboratoryService.update(labDTO.returnLabToUpdate());
        return new ResponseEntity<>(LaboratoryDTOResponse.returnDtoToShow(lab), HttpStatus.OK);
    }

    @ApiOperation(value = "Deletar logicamente Laboratório", notes = "Deleta logicamente Laboratório")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> delete(@ApiParam(value = "Id do Laboratório")
                                    @PathVariable("id") Long id) {
        LaboratoryModel lab = this.returnExistsLab(id);
        laboratoryService.delete(lab);
        return new ResponseEntity<>("Successful to delete laboratoriy!", HttpStatus.OK);
    }

    protected LaboratoryModel returnExistsLab(Long id) {
        LaboratoryModel lab = laboratoryService.findById(id);
        if (lab == null)
            throw new NotFoundException("Laboratory not found by ID: " + id);
        return lab;
    }

    protected void verifyExistsLab(Long id) {
        if (laboratoryService.findById(id) == null)
            throw new NotFoundException("Laboratory not found by ID: " + id);
    }

    protected void verifyExistsLabs(Set<LaboratoryModel> labs) {
        if (labs == null || labs.size() == 0)
            throw new NotFoundException("Laboratories not found");
    }

}
