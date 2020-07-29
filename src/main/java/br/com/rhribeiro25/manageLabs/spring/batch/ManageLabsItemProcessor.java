package br.com.rhribeiro25.manageLabs.spring.batch;

import br.com.rhribeiro25.manageLabs.model.ExaminationModel;
import br.com.rhribeiro25.manageLabs.model.LaboratoryModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Renan Ribeiro
 * @date 15/07/2020.
 */

@Component
@StepScope
public class ManageLabsItemProcessor implements ItemProcessor<Object, Object> {

    @Value("#{jobParameters[type]}")
    private String type;

    private static final Logger log = LoggerFactory.getLogger(ManageLabsItemProcessor.class);

    @Override
    public Object process(Object obj) throws Exception {
        if (type.equalsIgnoreCase("EXAM")) {
            Object examConverted = ExaminationModel.builder()
                    .id(((ExaminationModel) obj).getId())
                    .createdAt(((ExaminationModel) obj).getCreatedAt())
                    .name(((ExaminationModel) obj).getName())
                    .type(((ExaminationModel) obj).getType())
                    .status(((ExaminationModel) obj).getStatus())
                    .laboratories(null)
                    .build();
            log.info("Log converting (" + obj + ") into (" + examConverted + ")");
            return examConverted;
        } else if (type.equalsIgnoreCase("LAB")) {
            Object labConverted = LaboratoryModel.builder()
                    .id(((LaboratoryModel) obj).getId())
                    .createdAt(((LaboratoryModel) obj).getCreatedAt())
                    .name(((LaboratoryModel) obj).getName())
                    .address(((LaboratoryModel) obj).getAddress())
                    .status(((LaboratoryModel) obj).getStatus())
                    .examinations(null)
                    .build();
            log.info("Log converting (" + obj + ") into (" + labConverted + ")");
            return labConverted;
        }
        return null;
    }
}