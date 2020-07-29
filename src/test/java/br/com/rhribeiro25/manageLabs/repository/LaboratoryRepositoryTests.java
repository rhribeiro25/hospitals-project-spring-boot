package br.com.rhribeiro25.manageLabs.repository;

import br.com.rhribeiro25.manageLabs.model.LaboratoryModel;
import br.com.rhribeiro25.manageLabs.model.enums.LaboratoryStatusEnum;
import br.com.rhribeiro25.manageLabs.utils.Formatting;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Renan Ribeiro
 * @date 15/07/2020.
 */

@RunWith(MockitoJUnitRunner.class)
public class LaboratoryRepositoryTests {

    @Mock
    private LaboratoryRepository laboratoryRepository;

    private List<LaboratoryModel> labs;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.labs = new ArrayList<>();
        this.labs.add(new LaboratoryModel(1L, Formatting.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-01 00:00:23.003"), "Laboratory Name test 1", "Laboratory Address test 1", LaboratoryStatusEnum.ACTIVE, null));
        this.labs.add(new LaboratoryModel(2L, Formatting.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-02 00:00:23.003"), "Laboratory Name test 2", "Laboratory Address test 2", LaboratoryStatusEnum.ACTIVE, null));
        this.labs.add(new LaboratoryModel(3L, Formatting.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-03 00:00:23.003"), "Laboratory Name test 3", "Laboratory Address test 3", LaboratoryStatusEnum.ACTIVE, null));
    }

    @Test
    public void findByIdSuccess() {
        when(laboratoryRepository.findById(1L)).thenReturn(Optional.ofNullable(this.labs.get(0)));
        LaboratoryModel lab = laboratoryRepository.findById(1L).orElse(null);
        assertEquals(Formatting.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-01 00:00:23.003"), lab.getCreatedAt());
        assertEquals("Laboratory Name test 1", lab.getName());
        assertEquals("Laboratory Address test 1", lab.getAddress());
    }

    @Test
    public void saveSuccess() {
        LaboratoryModel lab = this.labs.get(0);
        laboratoryRepository.save(lab);
        verify(laboratoryRepository, times(1)).save(lab);
    }
}



















