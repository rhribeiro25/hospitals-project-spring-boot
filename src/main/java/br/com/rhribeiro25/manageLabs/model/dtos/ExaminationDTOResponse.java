package br.com.rhribeiro25.manageLabs.model.dtos;

import br.com.rhribeiro25.manageLabs.model.ExaminationModel;
import br.com.rhribeiro25.manageLabs.model.LaboratoryModel;
import br.com.rhribeiro25.manageLabs.model.enums.ExaminationStatusEnum;
import br.com.rhribeiro25.manageLabs.model.enums.ExaminationTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Renan Ribeiro
 * @date 15/07/2020.
 */

@Getter
@Builder
@ApiModel(value = "Exame Resposta")
public class ExaminationDTOResponse {

    private Long id;

    @ApiModelProperty(value = "Nome do Exame", required=true)
    @NotBlank(message = "{name.not.blank}")
    @Size(min = 7, max = 63, message = "{name.size}")
    private String name;

    @ApiModelProperty(value = "Tipo do Exame", required=true)
    @NotNull(message = "{type.not.null}")
    private ExaminationTypeEnum type;

    @ApiModelProperty(value = "Status do Exame")
    private ExaminationStatusEnum status;

    @ApiModelProperty(value = "Laboratórios Associados ao Exame")
    private Set<LaboratoryModel> laboratories;

    public static ExaminationDTOResponse returnDtoToShow(ExaminationModel exam) {
        return ExaminationDTOResponse.builder()
                .id(exam.getId())
                .name(exam.getName())
                .type(exam.getType())
                .status(exam.getStatus())
                .laboratories(exam.getLaboratories())
                .build();
    }

    public static Set<ExaminationDTOResponse> returnDtosToShow(Set<ExaminationModel> exams) {
        Set<ExaminationDTOResponse> examsDTO = new HashSet<>();
        exams.forEach(exam -> examsDTO.add(
                ExaminationDTOResponse.builder()
                        .id(exam.getId())
                        .name(exam.getName())
                        .type(exam.getType())
                        .status(exam.getStatus())
                        .laboratories(exam.getLaboratories())
                        .build()
        ));
        return examsDTO;
    }
}
