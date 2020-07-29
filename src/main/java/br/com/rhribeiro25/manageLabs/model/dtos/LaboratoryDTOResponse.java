package br.com.rhribeiro25.manageLabs.model.dtos;

import br.com.rhribeiro25.manageLabs.model.ExaminationModel;
import br.com.rhribeiro25.manageLabs.model.LaboratoryModel;
import br.com.rhribeiro25.manageLabs.model.enums.LaboratoryStatusEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Renan Ribeiro
 * @date 15/07/2020.
 */

@Getter
@Builder
@ApiModel(value = "Laboratório Resposta")
public class LaboratoryDTOResponse {

    private Long id;

    @ApiModelProperty(value = "Nome do Laboratório", required=true)
    @NotBlank(message = "{name.not.blank}")
    @Size(min = 7, max = 63, message = "{name.size}")
    private String name;

    @ApiModelProperty(value = "Nome do Laboratório", required=true)
    @NotBlank(message = "{address.not.blank}")
    @Size(min = 15, max = 255, message = "{address.size}")
    private String address;

    @ApiModelProperty(value = "Nome do Laboratório")
    private LaboratoryStatusEnum status;

    @ApiModelProperty(value = "Exames Associados ao Laboratório", hidden = true)
    @JsonBackReference("laboratories")
    private Set<ExaminationModel> examinations;

    public static LaboratoryDTOResponse returnDtoToShow(LaboratoryModel lab) {
        return LaboratoryDTOResponse.builder()
                .id(lab.getId())
                .name(lab.getName())
                .address(lab.getAddress())
                .status(lab.getStatus())
                .examinations(lab.getExaminations())
                .build();
    }

    public static Set<LaboratoryDTOResponse> returnDtosToShow(Set<LaboratoryModel> labs) {
        Set<LaboratoryDTOResponse> labsDTO = new HashSet<>();
        labs.forEach(lab -> labsDTO.add(
                LaboratoryDTOResponse.builder()
                        .id(lab.getId())
                        .name(lab.getName())
                        .address(lab.getAddress())
                        .status(lab.getStatus())
                        .examinations(lab.getExaminations())
                        .build()
        ));
        return labsDTO;
    }
}
