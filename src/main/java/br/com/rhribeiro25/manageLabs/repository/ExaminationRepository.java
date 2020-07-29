package br.com.rhribeiro25.manageLabs.repository;

import br.com.rhribeiro25.manageLabs.model.ExaminationModel;
import br.com.rhribeiro25.manageLabs.model.enums.ExaminationStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Set;

/**
 * @author Renan Ribeiro
 * @date 15/07/2020.
 */
@Repository
public interface ExaminationRepository extends JpaRepository<ExaminationModel, Long> {


    public ExaminationModel findByIdAndStatus(Long id, ExaminationStatusEnum status);

    public Set<ExaminationModel> findByStatus(ExaminationStatusEnum status);

    public Set<ExaminationModel> findByName(String name);

    public Set<ExaminationModel> findByCreatedAtBetween(Date from, Date to);

}
