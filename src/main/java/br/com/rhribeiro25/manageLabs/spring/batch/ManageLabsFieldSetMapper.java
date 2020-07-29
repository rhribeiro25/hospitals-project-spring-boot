package br.com.rhribeiro25.manageLabs.spring.batch;

import br.com.rhribeiro25.manageLabs.model.ExaminationModel;
import br.com.rhribeiro25.manageLabs.model.LaboratoryModel;
import br.com.rhribeiro25.manageLabs.model.enums.ExaminationStatusEnum;
import br.com.rhribeiro25.manageLabs.model.enums.ExaminationTypeEnum;
import br.com.rhribeiro25.manageLabs.model.enums.LaboratoryStatusEnum;
import br.com.rhribeiro25.manageLabs.utils.Formatting;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.util.Date;

/**
 * @author Renan Ribeiro
 * @date 15/07/2020.
 */

public class ManageLabsFieldSetMapper implements FieldSetMapper<Object> {

    @Override
    public Object mapFieldSet(FieldSet fieldSet) {
        Object obgMapped = null;
        try {
            String obj = fieldSet.readString(0);
            if (obj.equalsIgnoreCase("EXAM")) {
                Long id = fieldSet.readLong(1);
                Date createdAt = Formatting.stringToDate_yyyy_MM_dd__HH_mm_ss(fieldSet.readString(2));
                String name = fieldSet.readString(3);
                ExaminationTypeEnum type = ExaminationTypeEnum.valueOf(fieldSet.readString(4));
                ExaminationStatusEnum status = ExaminationStatusEnum.valueOf(fieldSet.readString(5));
                obgMapped = ExaminationModel.builder()
                        .id(id == 0 ? null : id)
                        .createdAt(createdAt)
                        .name(name)
                        .type(type)
                        .status(status)
                        .laboratories(null)
                        .build();
            } else if (obj.equalsIgnoreCase("LAB")) {
                Long id = fieldSet.readLong(1);
                Date createdAt = Formatting.stringToDate_yyyy_MM_dd__HH_mm_ss(fieldSet.readString(2));
                String name = fieldSet.readString(3);
                String address = fieldSet.readString(4);
                LaboratoryStatusEnum status = LaboratoryStatusEnum.valueOf(fieldSet.readString(5));
                obgMapped = LaboratoryModel.builder()
                        .id(id == 0 ? null : id)
                        .createdAt(createdAt)
                        .name(name)
                        .address(address)
                        .status(status)
                        .examinations(null)
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obgMapped;
    }
}
