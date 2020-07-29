package br.com.rhribeiro25.manageLabs.model;

import br.com.rhribeiro25.manageLabs.model.enums.ExaminationStatusEnum;
import br.com.rhribeiro25.manageLabs.model.enums.ExaminationTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @author Renan Ribeiro
 * @date 15/07/2020.
 */

@Entity
@Table(name = "examination")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    private Date createdAt;

    private String name;

    private ExaminationTypeEnum type;

    private ExaminationStatusEnum status;

    @EqualsAndHashCode.Exclude
    @ApiModelProperty(value = "Exames Associados ao Laboratório", hidden = true)
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "lab_has_exams",
            joinColumns = {@JoinColumn(name = "exam_id")},
            inverseJoinColumns = {@JoinColumn(name = "lab_id")})
    private Set<LaboratoryModel> laboratories;

}
