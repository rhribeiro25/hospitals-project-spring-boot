package br.com.rhribeiro25.manageLabs.controller;//package br.com.rhribeiro25.manageLAbs.controller;

import java.util.*;

import br.com.rhribeiro25.manageLabs.model.LaboratoryModel;
import br.com.rhribeiro25.manageLabs.model.enums.LaboratoryStatusEnum;
import br.com.rhribeiro25.manageLabs.repository.LaboratoryRepository;
import br.com.rhribeiro25.manageLabs.utils.Formatting;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Renan Ribeiro
 * @date 15/07/2020.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LaboratoryControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @MockBean
    private LaboratoryRepository laboratoryRepository;

    private Set<LaboratoryModel> labs;
    private LaboratoryModel labModel;
    private Date from, to;
    private HttpEntity request;

    @TestConfiguration
    static class SpringBootTestConfig {
        @Bean
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder().basicAuthentication("user", "manageLabs@2020");
        }
    }

    @Before
    public void setup() {
        labs = new HashSet<>();
        labs.add(new LaboratoryModel(1L, Formatting.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-01 00:00:23.003"), "Laboratory Name test 1", "Laboratory Address test 1", LaboratoryStatusEnum.ACTIVE, null));
        labs.add(new LaboratoryModel(2L, Formatting.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-02 00:00:23.003"), "Laboratory Name test 2", "Laboratory Address test 2", LaboratoryStatusEnum.ACTIVE, null));
        labs.add(new LaboratoryModel(3L, Formatting.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-03 00:00:23.003"), "Laboratory Name test 3", "Laboratory Address test 3", LaboratoryStatusEnum.ACTIVE, null));
        labs.add(new LaboratoryModel(4L, Formatting.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-04 00:00:23.003"), "Laboratory Name test 4", "Laboratory Address test 4", LaboratoryStatusEnum.ACTIVE, null));
        labs.add(new LaboratoryModel(5L, Formatting.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-05 00:00:23.003"), "Laboratory Name test 5", "Laboratory Address test 5", LaboratoryStatusEnum.ACTIVE, null));
        labs.add(new LaboratoryModel(6L, Formatting.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-06 00:00:23.003"), "Laboratory Name test 6", "Laboratory Address test 6", LaboratoryStatusEnum.ACTIVE, null));
        labs.add(new LaboratoryModel(7L, Formatting.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-07 00:00:23.003"), "Laboratory Name test 7", "Laboratory Address test 7", LaboratoryStatusEnum.ACTIVE, null));
        labs.add(new LaboratoryModel(8L, Formatting.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-08 00:00:23.003"), "Laboratory Name test 8", "Laboratory Address test 8", LaboratoryStatusEnum.ACTIVE, null));
        labs.add(new LaboratoryModel(9L, Formatting.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-09 00:00:23.003"), "Laboratory Name test 9", "Laboratory Address test 9", LaboratoryStatusEnum.ACTIVE, null));
        labs.add(new LaboratoryModel(10L, Formatting.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-10 00:00:23.003"), "Laboratory Name test 10", "Laboratory Address test 10", LaboratoryStatusEnum.ACTIVE, null));
        labModel = new LaboratoryModel(11L, Formatting.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-11 00:00:23.003"), "Laboratory Name test 11", "Laboratory Address test 11", LaboratoryStatusEnum.ACTIVE, null);
        from = Formatting.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-01 00:00:00.000");
        to = Formatting.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-31-12 00:00:00.000");
        request = new HttpEntity<>(labModel);
    }

    /**
     * FindById
     */
    @Test
    public void findByIdHttpStatus200() {
        BDDMockito.when(laboratoryRepository.findById(1L)).thenReturn(Optional.of(labModel));
        ResponseEntity<LaboratoryModel> response = restTemplate.getForEntity("/manage-labs/laboratories/find-by-id/1", LaboratoryModel.class);
        Assertions.assertThat(response.getBody().getAddress()).isEqualTo(labModel.getAddress());
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void findByIdHttpStatus401() {
        restTemplate = restTemplate.withBasicAuth("test", "test");
        BDDMockito.when(laboratoryRepository.findById(1L)).thenReturn(Optional.of(labModel));
        ResponseEntity<String> response = restTemplate.getForEntity("/manage-labs/laboratories/find-by-id/11", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void findByIdHttpStatus404() {
        BDDMockito.when(laboratoryRepository.findById(1L)).thenReturn(Optional.of(labModel));
        ResponseEntity<String> response = restTemplate.getForEntity("/manage-labs/laboratories/find-by-id/2", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void findByIdHttpStatus400() {
        BDDMockito.when(laboratoryRepository.findById(1L)).thenReturn(Optional.of(labModel));
        ResponseEntity<String> response = restTemplate.getForEntity("/manage-labs/laboratories/find-by-id/test", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void findByIdHttpStatus405() {
        BDDMockito.when(laboratoryRepository.findById(1L)).thenReturn(Optional.of(labModel));
        ResponseEntity<String> response = restTemplate.postForEntity("/manage-labs/laboratories/find-by-id/11", labModel, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(405);
    }

    /**
     * FindByBetween
     */
    @Test
    public void findByCreatedAtBetweenHttpStatus200() {
        BDDMockito.when(laboratoryRepository.findByCreatedAtBetween(from, to)).thenReturn(labs);
        ResponseEntity<List> response = restTemplate.getForEntity(
                "/manage-labs/laboratories/find-by-createdat-between/2020-01-01 00:00:00.000/2020-31-12 00:00:00.000", List.class);
        Assertions.assertThat(response.getBody().size()).isEqualTo(10);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void findByCreatedAtBetweenHttpStatus401() {
        restTemplate = restTemplate.withBasicAuth("test", "test");
        BDDMockito.when(laboratoryRepository.findByCreatedAtBetween(from, to)).thenReturn(labs);
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/manage-labs/laboratories/find-by-createdat-between/2020-01-01 00:00:00.000/2020-31-12 00:00:00.000", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void findByCreatedAtBetweenHttpStatus404() {
        BDDMockito.when(laboratoryRepository.findByCreatedAtBetween(from, to)).thenReturn(labs);
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/manage-labs/laboratories/find-by-createat-between/2020-01-01 00:00:00.000/2020-31-12 00:00:00.000", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void findByCreatedAtBetweenHttpStatus405() {
        BDDMockito.when(laboratoryRepository.findByCreatedAtBetween(from, to)).thenReturn(labs);
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/manage-labs/laboratories/find-by-createdat-between/2020-01-01 00:00:00.000/2020-31-12 00:00:00.000", labs, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(405);
    }

    /**
     * Create
     */
    @Test
    public void createHttpStatus201() {
        restTemplate = restTemplate.withBasicAuth("admin", "manageLabs@2020");
        BDDMockito.when(laboratoryRepository.save(labModel)).thenReturn(labModel);
        ResponseEntity<LaboratoryModel> response = restTemplate.postForEntity("/manage-labs/laboratories/create", request, LaboratoryModel.class);
        Assertions.assertThat(response.getBody().getAddress()).isEqualTo(labModel.getAddress());
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    public void createHttpStatus403() {
        BDDMockito.when(laboratoryRepository.save(labModel)).thenReturn(labModel);
        ResponseEntity<String> response = restTemplate.postForEntity("/manage-labs/laboratories/create", request, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void createHttpStatus404() {
        restTemplate = restTemplate.withBasicAuth("admin", "manageLabs@2020");
        BDDMockito.when(laboratoryRepository.save(labModel)).thenReturn(labModel);
        ResponseEntity<String> response = restTemplate.postForEntity("/lab/create", request, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void createHttpStatus405() {
        restTemplate = restTemplate.withBasicAuth("admin", "manageLabs@2020");
        BDDMockito.when(laboratoryRepository.save(labModel)).thenReturn(labModel);
        ResponseEntity<String> response = restTemplate.getForEntity("/manage-labs/laboratories/create", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(405);
    }

    @Test
    public void createHttpStatus415() {
        BDDMockito.when(laboratoryRepository.save(labModel)).thenReturn(labModel);
        ResponseEntity<String> response = restTemplate.postForEntity("/manage-labs/laboratories/create", "null", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(415);
    }

    /**
     * Update
     */

    @Test
    public void updateHttpStatus400() {
        BDDMockito.when(laboratoryRepository.save(labModel)).thenReturn(labModel);
        BDDMockito.when(laboratoryRepository.existsById(11L)).thenReturn(true);
        ResponseEntity<LaboratoryModel> exchange = restTemplate.exchange("/manage-labs/laboratories/update", HttpMethod.PUT, null, LaboratoryModel.class);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void updateHttpStatus405() {
        restTemplate = restTemplate.withBasicAuth("admin", "manageLabs@2020");
        BDDMockito.when(laboratoryRepository.save(labModel)).thenReturn(labModel);
        BDDMockito.when(laboratoryRepository.existsById(11L)).thenReturn(true);
        ResponseEntity<LaboratoryModel> exchange = restTemplate.exchange("/manage-labs/laboratories/update", HttpMethod.POST, request,
                LaboratoryModel.class);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(405);
    }

    /**
     * Delete
     */

    @Test
    public void deleteHttpStatus401() {
        restTemplate = restTemplate.withBasicAuth("test", "manageLabs@2020");
        BDDMockito.doNothing().when(laboratoryRepository).deleteById(11L);
        BDDMockito.when(laboratoryRepository.existsById(11L)).thenReturn(true);
        ResponseEntity<String> exchange = restTemplate.exchange("/manage-labs/laboratories/delete/11", HttpMethod.DELETE, null,
                String.class);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void deleteHttpStatus403() {
        BDDMockito.doNothing().when(laboratoryRepository).deleteById(11L);
        BDDMockito.when(laboratoryRepository.existsById(11L)).thenReturn(true);
        ResponseEntity<String> exchange = restTemplate.exchange("/manage-labs/laboratories/delete/11", HttpMethod.DELETE, null,
                String.class);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void deleteHttpStatus404() {
        restTemplate = restTemplate.withBasicAuth("admin", "manageLabs@2020");
        BDDMockito.doNothing().when(laboratoryRepository).deleteById(11L);
        BDDMockito.when(laboratoryRepository.existsById(11L)).thenReturn(false);
        ResponseEntity<String> exchange = restTemplate.exchange("/lab/delete/11", HttpMethod.DELETE, null,
                String.class);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void deleteHttpStatus405() {
        restTemplate = restTemplate.withBasicAuth("admin", "manageLabs@2020");
        BDDMockito.doNothing().when(laboratoryRepository).deleteById(11L);
        BDDMockito.when(laboratoryRepository.existsById(11L)).thenReturn(true);
        ResponseEntity<String> exchange = restTemplate.exchange("/manage-labs/laboratories/delete/11", HttpMethod.GET, null, String.class);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(405);
    }


}
