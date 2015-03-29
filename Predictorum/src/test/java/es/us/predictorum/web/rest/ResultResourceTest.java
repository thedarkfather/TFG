package es.us.predictorum.web.rest;

import es.us.predictorum.Application;
import es.us.predictorum.domain.Result;
import es.us.predictorum.repository.ResultRepository;

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
 * Test class for the ResultResource REST controller.
 *
 * @see ResultResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ResultResourceTest {


    private static final Integer DEFAULT_HOME_GOALS = 0;
    private static final Integer UPDATED_HOME_GOALS = 1;

    private static final Integer DEFAULT_AWAY_GOALS = 0;
    private static final Integer UPDATED_AWAY_GOALS = 1;

    private static final Integer DEFAULT_HALF_HOME_GOALS = 0;
    private static final Integer UPDATED_HALF_HOME_GOALS = 1;

    private static final Integer DEFAULT_HALF_AWAY_GOALS = 0;
    private static final Integer UPDATED_HALF_AWAY_GOALS = 1;

    @Inject
    private ResultRepository resultRepository;

    private MockMvc restResultMockMvc;

    private Result result;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ResultResource resultResource = new ResultResource();
        ReflectionTestUtils.setField(resultResource, "resultRepository", resultRepository);
        this.restResultMockMvc = MockMvcBuilders.standaloneSetup(resultResource).build();
    }

    @Before
    public void initTest() {
        result = new Result();
        result.setHomeGoals(DEFAULT_HOME_GOALS);
        result.setAwayGoals(DEFAULT_AWAY_GOALS);
        result.setHalfHomeGoals(DEFAULT_HALF_HOME_GOALS);
        result.setHalfAwayGoals(DEFAULT_HALF_AWAY_GOALS);
    }

    @Test
    @Transactional
    public void createResult() throws Exception {
        // Validate the database is empty
        assertThat(resultRepository.findAll()).hasSize(0);

        // Create the Result
        restResultMockMvc.perform(post("/api/results")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(result)))
                .andExpect(status().isCreated());

        // Validate the Result in the database
        List<Result> results = resultRepository.findAll();
        assertThat(results).hasSize(1);
        Result testResult = results.iterator().next();
        assertThat(testResult.getHomeGoals()).isEqualTo(DEFAULT_HOME_GOALS);
        assertThat(testResult.getAwayGoals()).isEqualTo(DEFAULT_AWAY_GOALS);
        assertThat(testResult.getHalfHomeGoals()).isEqualTo(DEFAULT_HALF_HOME_GOALS);
        assertThat(testResult.getHalfAwayGoals()).isEqualTo(DEFAULT_HALF_AWAY_GOALS);
    }

    @Test
    @Transactional
    public void getAllResults() throws Exception {
        // Initialize the database
        resultRepository.saveAndFlush(result);

        // Get all the results
        restResultMockMvc.perform(get("/api/results"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(result.getId().intValue()))
                .andExpect(jsonPath("$.[0].homeGoals").value(DEFAULT_HOME_GOALS))
                .andExpect(jsonPath("$.[0].awayGoals").value(DEFAULT_AWAY_GOALS))
                .andExpect(jsonPath("$.[0].halfHomeGoals").value(DEFAULT_HALF_HOME_GOALS))
                .andExpect(jsonPath("$.[0].halfAwayGoals").value(DEFAULT_HALF_AWAY_GOALS));
    }

    @Test
    @Transactional
    public void getResult() throws Exception {
        // Initialize the database
        resultRepository.saveAndFlush(result);

        // Get the result
        restResultMockMvc.perform(get("/api/results/{id}", result.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(result.getId().intValue()))
            .andExpect(jsonPath("$.homeGoals").value(DEFAULT_HOME_GOALS))
            .andExpect(jsonPath("$.awayGoals").value(DEFAULT_AWAY_GOALS))
            .andExpect(jsonPath("$.halfHomeGoals").value(DEFAULT_HALF_HOME_GOALS))
            .andExpect(jsonPath("$.halfAwayGoals").value(DEFAULT_HALF_AWAY_GOALS));
    }

    @Test
    @Transactional
    public void getNonExistingResult() throws Exception {
        // Get the result
        restResultMockMvc.perform(get("/api/results/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResult() throws Exception {
        // Initialize the database
        resultRepository.saveAndFlush(result);

        // Update the result
        result.setHomeGoals(UPDATED_HOME_GOALS);
        result.setAwayGoals(UPDATED_AWAY_GOALS);
        result.setHalfHomeGoals(UPDATED_HALF_HOME_GOALS);
        result.setHalfAwayGoals(UPDATED_HALF_AWAY_GOALS);
        restResultMockMvc.perform(put("/api/results")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(result)))
                .andExpect(status().isOk());

        // Validate the Result in the database
        List<Result> results = resultRepository.findAll();
        assertThat(results).hasSize(1);
        Result testResult = results.iterator().next();
        assertThat(testResult.getHomeGoals()).isEqualTo(UPDATED_HOME_GOALS);
        assertThat(testResult.getAwayGoals()).isEqualTo(UPDATED_AWAY_GOALS);
        assertThat(testResult.getHalfHomeGoals()).isEqualTo(UPDATED_HALF_HOME_GOALS);
        assertThat(testResult.getHalfAwayGoals()).isEqualTo(UPDATED_HALF_AWAY_GOALS);
    }

    @Test
    @Transactional
    public void deleteResult() throws Exception {
        // Initialize the database
        resultRepository.saveAndFlush(result);

        // Get the result
        restResultMockMvc.perform(delete("/api/results/{id}", result.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Result> results = resultRepository.findAll();
        assertThat(results).hasSize(0);
    }
}
