package br.com.rhribeiro25.manageLabs.spring.batch;

import br.com.rhribeiro25.manageLabs.model.ExaminationModel;
import br.com.rhribeiro25.manageLabs.model.LaboratoryModel;
import br.com.rhribeiro25.manageLabs.service.ExaminationService;
import br.com.rhribeiro25.manageLabs.service.LaboratoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Renan Ribeiro
 * @date 15/07/2020.
 */

@Component
@StepScope
public class ManageLabsItemWriter implements ItemWriter<Object> {

    private static final Logger log = LoggerFactory.getLogger(ManageLabsItemWriter.class);

    @Autowired
    private ExaminationService examoratoryService;

    @Autowired
    private LaboratoryService laboratoryService;

    @Value("#{jobParameters[action]}")
    private String action;

    @Value("#{jobParameters[type]}")
    private String type;

    @Override
    public void write(List<? extends Object> objs) throws Exception {
        if (type.equalsIgnoreCase("EXAM")) {
            Set<ExaminationModel> examsSet = new HashSet<>();
            if (action.equalsIgnoreCase("CREATE")) {
                log.info("Creating Examinations in lot!");
                examsSet.addAll((Collection<? extends ExaminationModel>) objs);
                examoratoryService.saveAll(examsSet);
            } else if (action.equalsIgnoreCase("UPDATE")) {
                log.info("Updating Examinations in lot!");
                examsSet.addAll((Collection<? extends ExaminationModel>) objs);
                examoratoryService.saveAll(examsSet);
            } else if (action.equalsIgnoreCase("DELETE")) {
                log.info("Deleting Examinations in lot!");
                examsSet.addAll((Collection<? extends ExaminationModel>) objs);
                examoratoryService.deleteAll(examsSet);
            }
        } else if (type.equalsIgnoreCase("LAB")) {
            Set<LaboratoryModel> labsSet = new HashSet<>();
            if (action.equalsIgnoreCase("CREATE")) {
                log.info("Creating Laboratories in lot!");
                labsSet.addAll((Collection<? extends LaboratoryModel>) objs);
                laboratoryService.saveAll(labsSet);
            } else if (action.equalsIgnoreCase("UPDATE")) {
                log.info("Updating Laboratories in lot!");
                labsSet.addAll((Collection<? extends LaboratoryModel>) objs);
                laboratoryService.saveAll(labsSet);
            } else if (action.equalsIgnoreCase("DELETE")) {
                log.info("Deleting Laboratories in lot!");
                labsSet.addAll((Collection<? extends LaboratoryModel>) objs);
                laboratoryService.deleteAll(labsSet);
            }
        }
    }
}
