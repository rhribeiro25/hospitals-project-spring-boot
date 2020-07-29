package br.com.rhribeiro25.manageLabs.model;

import br.com.rhribeiro25.manageLabs.model.enums.LaboratoryStatusEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "laboratory")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LaboratoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    private Date createdAt;

    private String name;

    private String address;

    private LaboratoryStatusEnum status;

    @EqualsAndHashCode.Exclude
    @ApiModelProperty(value = "Laboratórios Associados ao Exame", hidden = true)
    @JsonBackReference("laboratories")
    @ManyToMany(mappedBy="laboratories", fetch = FetchType.LAZY)
    private Set<ExaminationModel> examinations;

    public LaboratoryModel(String name, String address, Set<ExaminationModel> examinations) {
        this.name = name;
        this.address = address;
        this.examinations = examinations;
    }

    public LaboratoryModel(Long id, String name, String address, LaboratoryStatusEnum status, Set<ExaminationModel> examinations) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.status = status;
        this.examinations = examinations;
    }

}
