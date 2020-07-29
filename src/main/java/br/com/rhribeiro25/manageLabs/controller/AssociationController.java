package br.com.rhribeiro25.manageLabs.controller;

import br.com.rhribeiro25.manageLabs.error.exception.NotFoundException;
import br.com.rhribeiro25.manageLabs.model.ExaminationModel;
import br.com.rhribeiro25.manageLabs.model.LaboratoryModel;
import br.com.rhribeiro25.manageLabs.service.ExaminationService;
import br.com.rhribeiro25.manageLabs.service.LaboratoryService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author Renan Ribeiro
 * @date 15/07/2020.
 */

@Api(value = "Associação API")
@RestController
@RequestMapping("/manage-labs/associations")
@Slf4j
public class AssociationController {

    @Autowired
    private ExaminationService examinationService;

    @Autowired
    private LaboratoryService laboratoryService;

    @ApiOperation(value = "Associar Exame ATIVO a Laboratório ATIVO", notes = "Associa um exame ATIVO a um laboratório ATIVO")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @PatchMapping("/create/{id-exam}/{id-lab}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> associate(@ApiParam(value = "Id do Exame")
                                       @PathVariable("id-exam") Long idExam,
                                       @ApiParam(value = "Id do Laboratório")
                                       @PathVariable("id-lab") Long idLab) {
        ExaminationModel examUpdate = this.verifyActiveExistsExam(idExam);
        Set<LaboratoryModel> labsAssociated = examUpdate.getLaboratories();

        LaboratoryModel labUpdate = this.verifyActiveExistsLab(idLab);
        Set<ExaminationModel> examsAssociated = labUpdate.getExaminations();

        labsAssociated.add(labUpdate);
        examUpdate.setLaboratories(labsAssociated);
        examinationService.update(examUpdate);

        examsAssociated.add(examUpdate);
        labUpdate.setExaminations(examsAssociated);
        laboratoryService.update(labUpdate);

        return new ResponseEntity<>("Successful to associate examination/laboratory!", HttpStatus.OK);
    }

    @ApiOperation(value = "Desassociar Exame ATIVO a Laboratório ATIVO", notes = "Desassocia um exame ATIVO a um laboratório ATIVO")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @PatchMapping("/delete/{id-exam}/{id-lab}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> disassociate(@ApiParam(value = "Id do Exame")
                                          @PathVariable("id-exam") Long idExam,
                                          @ApiParam(value = "Id do Laboratório")
                                          @PathVariable("id-lab") Long idLab) {
        ExaminationModel examUpdate = this.verifyActiveExistsExam(idExam);
        Set<LaboratoryModel> labsAssociated = examUpdate.getLaboratories();

        LaboratoryModel labUpdate = this.verifyActiveExistsLab(idLab);
        Set<ExaminationModel> examsAssociated = labUpdate.getExaminations();

        labsAssociated.removeIf(lab -> lab.getId() == idLab);
        examUpdate.setLaboratories(labsAssociated);
        examinationService.update(examUpdate);

        examsAssociated.removeIf(exam -> exam.getId() == idExam);
        labUpdate.setExaminations(examsAssociated);
        laboratoryService.update(labUpdate);

        return new ResponseEntity<>("Successful to disassociate examination/laboratory!", HttpStatus.OK);
    }

    protected ExaminationModel verifyActiveExistsExam(Long id) {
        ExaminationModel exam = examinationService.findActiveById(id);
        if (exam == null) {
            throw new NotFoundException("Active Examamination not found by ID: " + id);
        }
        return exam;
    }

    protected LaboratoryModel verifyActiveExistsLab(Long id) {
        LaboratoryModel lab = laboratoryService.findActiveById(id);
        if (lab == null) {
            throw new NotFoundException("Active Laboratory not found by ID: " + id);
        }
        return lab;
    }

}
