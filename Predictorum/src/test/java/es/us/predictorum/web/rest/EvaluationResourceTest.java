package es.us.predictorum.web.rest;

import es.us.predictorum.Application;
import es.us.predictorum.domain.Evaluation;
import es.us.predictorum.repository.EvaluationRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EvaluationResource REST controller.
 *
 * @see EvaluationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EvaluationResourceTest {


    private static final Boolean DEFAULT_TYPE = false;
    private static final Boolean UPDATED_TYPE = true;

    @Inject
    private EvaluationRepository evaluationRepository;

    private MockMvc restEvaluationMockMvc;

    private Evaluation evaluation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EvaluationResource evaluationResource = new EvaluationResource();
        ReflectionTestUtils.setField(evaluationResource, "evaluationRepository", evaluationRepository);
        this.restEvaluationMockMvc = MockMvcBuilders.standaloneSetup(evaluationResource).build();
    }

    @Before
    public void initTest() {
        evaluation = new Evaluation();
        evaluation.setType(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createEvaluation() throws Exception {
        // Validate the database is empty
        assertThat(evaluationRepository.findAll()).hasSize(0);

        // Create the Evaluation
        restEvaluationMockMvc.perform(post("/api/evaluations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(evaluation)))
                .andExpect(status().isCreated());

        // Validate the Evaluation in the database
        List<Evaluation> evaluations = evaluationRepository.findAll();
        assertThat(evaluations).hasSize(1);
        Evaluation testEvaluation = evaluations.iterator().next();
        assertThat(testEvaluation.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void getAllEvaluations() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get all the evaluations
        restEvaluationMockMvc.perform(get("/api/evaluations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(evaluation.getId().intValue()))
                .andExpect(jsonPath("$.[0].type").value(DEFAULT_TYPE.booleanValue()));
    }

    @Test
    @Transactional
    public void getEvaluation() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get the evaluation
        restEvaluationMockMvc.perform(get("/api/evaluations/{id}", evaluation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(evaluation.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEvaluation() throws Exception {
        // Get the evaluation
        restEvaluationMockMvc.perform(get("/api/evaluations/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvaluation() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Update the evaluation
        evaluation.setType(UPDATED_TYPE);
        restEvaluationMockMvc.perform(put("/api/evaluations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(evaluation)))
                .andExpect(status().isOk());

        // Validate the Evaluation in the database
        List<Evaluation> evaluations = evaluationRepository.findAll();
        assertThat(evaluations).hasSize(1);
        Evaluation testEvaluation = evaluations.iterator().next();
        assertThat(testEvaluation.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void deleteEvaluation() throws Exception {
        // Initialize the database
        evaluationRepository.saveAndFlush(evaluation);

        // Get the evaluation
        restEvaluationMockMvc.perform(delete("/api/evaluations/{id}", evaluation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Evaluation> evaluations = evaluationRepository.findAll();
        assertThat(evaluations).hasSize(0);
    }
}
