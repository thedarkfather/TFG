package es.us.predictorum.web.rest;

import es.us.predictorum.Application;
import es.us.predictorum.domain.Prediction;
import es.us.predictorum.repository.PredictionRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PredictionResource REST controller.
 *
 * @see PredictionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PredictionResourceTest {

    private static final String DEFAULT_SIMPLE_RESULT = "SAMPLE_TEXT";
    private static final String UPDATED_SIMPLE_RESULT = "UPDATED_TEXT";

    private static final BigDecimal DEFAULT_P_SIMPLE_RESULT = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_P_SIMPLE_RESULT = BigDecimal.ONE;
    private static final String DEFAULT_DOUBLE_RESULT = "SAMPLE_TEXT";
    private static final String UPDATED_DOUBLE_RESULT = "UPDATED_TEXT";

    private static final BigDecimal DEFAULT_P_DOUBLE_RESULT = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_P_DOUBLE_RESULT = BigDecimal.ONE;
    private static final String DEFAULT_SIMPLE_HALF_RESULT = "SAMPLE_TEXT";
    private static final String UPDATED_SIMPLE_HALF_RESULT = "UPDATED_TEXT";

    private static final BigDecimal DEFAULT_P_SIMPLE_HALF_RESULT = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_P_SIMPLE_HALF_RESULT = BigDecimal.ONE;
    private static final String DEFAULT_DOUBLE_HALF_RESULT = "SAMPLE_TEXT";
    private static final String UPDATED_DOUBLE_HALF_RESULT = "UPDATED_TEXT";

    private static final BigDecimal DEFAULT_P_DOUBLE_HALF_RESULT = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_P_DOUBLE_HALF_RESULT = BigDecimal.ONE;

    private static final Integer DEFAULT_HOME_GOALS = 0;
    private static final Integer UPDATED_HOME_GOALS = 1;

    private static final BigDecimal DEFAULT_P_HOME_GOALS = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_P_HOME_GOALS = BigDecimal.ONE;

    private static final Integer DEFAULT_AWAY_GOALS = 0;
    private static final Integer UPDATED_AWAY_GOALS = 1;

    private static final Integer DEFAULT_HALF_HOME_GOALS = 0;
    private static final Integer UPDATED_HALF_HOME_GOALS = 1;

    private static final BigDecimal DEFAULT_P_AWAY_GOALS = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_P_AWAY_GOALS = BigDecimal.ONE;

    private static final BigDecimal DEFAULT_P_HALF_HOME_GOALS = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_P_HALF_HOME_GOALS = BigDecimal.ONE;

    private static final Integer DEFAULT_HALF_AWAY_GOALS = 0;
    private static final Integer UPDATED_HALF_AWAY_GOALS = 1;

    private static final BigDecimal DEFAULT_P_HALF_AWAY_GOALS = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_P_HALF_AWAY_GOALS = BigDecimal.ONE;

    private static final Boolean DEFAULT_MORE_THAN25 = false;
    private static final Boolean UPDATED_MORE_THAN25 = true;

    private static final BigDecimal DEFAULT_P_MORE_THAN25 = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_P_MORE_THAN25 = BigDecimal.ONE;

    @Inject
    private PredictionRepository predictionRepository;

    private MockMvc restPredictionMockMvc;

    private Prediction prediction;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PredictionResource predictionResource = new PredictionResource();
        ReflectionTestUtils.setField(predictionResource, "predictionRepository", predictionRepository);
        this.restPredictionMockMvc = MockMvcBuilders.standaloneSetup(predictionResource).build();
    }

    @Before
    public void initTest() {
        prediction = new Prediction();
        prediction.setSimpleResult(DEFAULT_SIMPLE_RESULT);
        prediction.setPSimpleResult(DEFAULT_P_SIMPLE_RESULT);
        prediction.setDoubleResult(DEFAULT_DOUBLE_RESULT);
        prediction.setPDoubleResult(DEFAULT_P_DOUBLE_RESULT);
        prediction.setSimpleHalfResult(DEFAULT_SIMPLE_HALF_RESULT);
        prediction.setPSimpleHalfResult(DEFAULT_P_SIMPLE_HALF_RESULT);
        prediction.setDoubleHalfResult(DEFAULT_DOUBLE_HALF_RESULT);
        prediction.setPDoubleHalfResult(DEFAULT_P_DOUBLE_HALF_RESULT);
        prediction.setHomeGoals(DEFAULT_HOME_GOALS);
        prediction.setPHomeGoals(DEFAULT_P_HOME_GOALS);
        prediction.setAwayGoals(DEFAULT_AWAY_GOALS);
        prediction.setHalfHomeGoals(DEFAULT_HALF_HOME_GOALS);
        prediction.setPAwayGoals(DEFAULT_P_AWAY_GOALS);
        prediction.setPHalfHomeGoals(DEFAULT_P_HALF_HOME_GOALS);
        prediction.setHalfAwayGoals(DEFAULT_HALF_AWAY_GOALS);
        prediction.setPHalfAwayGoals(DEFAULT_P_HALF_AWAY_GOALS);
        prediction.setMoreThan25(DEFAULT_MORE_THAN25);
        prediction.setPMoreThan25(DEFAULT_P_MORE_THAN25);
    }

    @Test
    @Transactional
    public void createPrediction() throws Exception {
        // Validate the database is empty
        assertThat(predictionRepository.findAll()).hasSize(0);

        // Create the Prediction
        restPredictionMockMvc.perform(post("/api/predictions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(prediction)))
                .andExpect(status().isCreated());

        // Validate the Prediction in the database
        List<Prediction> predictions = predictionRepository.findAll();
        assertThat(predictions).hasSize(1);
        Prediction testPrediction = predictions.iterator().next();
        assertThat(testPrediction.getSimpleResult()).isEqualTo(DEFAULT_SIMPLE_RESULT);
        assertThat(testPrediction.getPSimpleResult()).isEqualTo(DEFAULT_P_SIMPLE_RESULT);
        assertThat(testPrediction.getDoubleResult()).isEqualTo(DEFAULT_DOUBLE_RESULT);
        assertThat(testPrediction.getPDoubleResult()).isEqualTo(DEFAULT_P_DOUBLE_RESULT);
        assertThat(testPrediction.getSimpleHalfResult()).isEqualTo(DEFAULT_SIMPLE_HALF_RESULT);
        assertThat(testPrediction.getPSimpleHalfResult()).isEqualTo(DEFAULT_P_SIMPLE_HALF_RESULT);
        assertThat(testPrediction.getDoubleHalfResult()).isEqualTo(DEFAULT_DOUBLE_HALF_RESULT);
        assertThat(testPrediction.getPDoubleHalfResult()).isEqualTo(DEFAULT_P_DOUBLE_HALF_RESULT);
        assertThat(testPrediction.getHomeGoals()).isEqualTo(DEFAULT_HOME_GOALS);
        assertThat(testPrediction.getPHomeGoals()).isEqualTo(DEFAULT_P_HOME_GOALS);
        assertThat(testPrediction.getAwayGoals()).isEqualTo(DEFAULT_AWAY_GOALS);
        assertThat(testPrediction.getHalfHomeGoals()).isEqualTo(DEFAULT_HALF_HOME_GOALS);
        assertThat(testPrediction.getPAwayGoals()).isEqualTo(DEFAULT_P_AWAY_GOALS);
        assertThat(testPrediction.getPHalfHomeGoals()).isEqualTo(DEFAULT_P_HALF_HOME_GOALS);
        assertThat(testPrediction.getHalfAwayGoals()).isEqualTo(DEFAULT_HALF_AWAY_GOALS);
        assertThat(testPrediction.getPHalfAwayGoals()).isEqualTo(DEFAULT_P_HALF_AWAY_GOALS);
        assertThat(testPrediction.getMoreThan25()).isEqualTo(DEFAULT_MORE_THAN25);
        assertThat(testPrediction.getPMoreThan25()).isEqualTo(DEFAULT_P_MORE_THAN25);
    }

    @Test
    @Transactional
    public void getAllPredictions() throws Exception {
        // Initialize the database
        predictionRepository.saveAndFlush(prediction);

        // Get all the predictions
        restPredictionMockMvc.perform(get("/api/predictions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(prediction.getId().intValue()))
                .andExpect(jsonPath("$.[0].simpleResult").value(DEFAULT_SIMPLE_RESULT.toString()))
                .andExpect(jsonPath("$.[0].pSimpleResult").value(DEFAULT_P_SIMPLE_RESULT.intValue()))
                .andExpect(jsonPath("$.[0].doubleResult").value(DEFAULT_DOUBLE_RESULT.toString()))
                .andExpect(jsonPath("$.[0].pDoubleResult").value(DEFAULT_P_DOUBLE_RESULT.intValue()))
                .andExpect(jsonPath("$.[0].simpleHalfResult").value(DEFAULT_SIMPLE_HALF_RESULT.toString()))
                .andExpect(jsonPath("$.[0].pSimpleHalfResult").value(DEFAULT_P_SIMPLE_HALF_RESULT.intValue()))
                .andExpect(jsonPath("$.[0].doubleHalfResult").value(DEFAULT_DOUBLE_HALF_RESULT.toString()))
                .andExpect(jsonPath("$.[0].pDoubleHalfResult").value(DEFAULT_P_DOUBLE_HALF_RESULT.intValue()))
                .andExpect(jsonPath("$.[0].homeGoals").value(DEFAULT_HOME_GOALS))
                .andExpect(jsonPath("$.[0].pHomeGoals").value(DEFAULT_P_HOME_GOALS.intValue()))
                .andExpect(jsonPath("$.[0].awayGoals").value(DEFAULT_AWAY_GOALS))
                .andExpect(jsonPath("$.[0].halfHomeGoals").value(DEFAULT_HALF_HOME_GOALS))
                .andExpect(jsonPath("$.[0].pAwayGoals").value(DEFAULT_P_AWAY_GOALS.intValue()))
                .andExpect(jsonPath("$.[0].pHalfHomeGoals").value(DEFAULT_P_HALF_HOME_GOALS.intValue()))
                .andExpect(jsonPath("$.[0].halfAwayGoals").value(DEFAULT_HALF_AWAY_GOALS))
                .andExpect(jsonPath("$.[0].pHalfAwayGoals").value(DEFAULT_P_HALF_AWAY_GOALS.intValue()))
                .andExpect(jsonPath("$.[0].moreThan25").value(DEFAULT_MORE_THAN25.booleanValue()))
                .andExpect(jsonPath("$.[0].pMoreThan25").value(DEFAULT_P_MORE_THAN25.intValue()));
    }

    @Test
    @Transactional
    public void getPrediction() throws Exception {
        // Initialize the database
        predictionRepository.saveAndFlush(prediction);

        // Get the prediction
        restPredictionMockMvc.perform(get("/api/predictions/{id}", prediction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(prediction.getId().intValue()))
            .andExpect(jsonPath("$.simpleResult").value(DEFAULT_SIMPLE_RESULT.toString()))
            .andExpect(jsonPath("$.pSimpleResult").value(DEFAULT_P_SIMPLE_RESULT.intValue()))
            .andExpect(jsonPath("$.doubleResult").value(DEFAULT_DOUBLE_RESULT.toString()))
            .andExpect(jsonPath("$.pDoubleResult").value(DEFAULT_P_DOUBLE_RESULT.intValue()))
            .andExpect(jsonPath("$.simpleHalfResult").value(DEFAULT_SIMPLE_HALF_RESULT.toString()))
            .andExpect(jsonPath("$.pSimpleHalfResult").value(DEFAULT_P_SIMPLE_HALF_RESULT.intValue()))
            .andExpect(jsonPath("$.doubleHalfResult").value(DEFAULT_DOUBLE_HALF_RESULT.toString()))
            .andExpect(jsonPath("$.pDoubleHalfResult").value(DEFAULT_P_DOUBLE_HALF_RESULT.intValue()))
            .andExpect(jsonPath("$.homeGoals").value(DEFAULT_HOME_GOALS))
            .andExpect(jsonPath("$.pHomeGoals").value(DEFAULT_P_HOME_GOALS.intValue()))
            .andExpect(jsonPath("$.awayGoals").value(DEFAULT_AWAY_GOALS))
            .andExpect(jsonPath("$.halfHomeGoals").value(DEFAULT_HALF_HOME_GOALS))
            .andExpect(jsonPath("$.pAwayGoals").value(DEFAULT_P_AWAY_GOALS.intValue()))
            .andExpect(jsonPath("$.pHalfHomeGoals").value(DEFAULT_P_HALF_HOME_GOALS.intValue()))
            .andExpect(jsonPath("$.halfAwayGoals").value(DEFAULT_HALF_AWAY_GOALS))
            .andExpect(jsonPath("$.pHalfAwayGoals").value(DEFAULT_P_HALF_AWAY_GOALS.intValue()))
            .andExpect(jsonPath("$.moreThan25").value(DEFAULT_MORE_THAN25.booleanValue()))
            .andExpect(jsonPath("$.pMoreThan25").value(DEFAULT_P_MORE_THAN25.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPrediction() throws Exception {
        // Get the prediction
        restPredictionMockMvc.perform(get("/api/predictions/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrediction() throws Exception {
        // Initialize the database
        predictionRepository.saveAndFlush(prediction);

        // Update the prediction
        prediction.setSimpleResult(UPDATED_SIMPLE_RESULT);
        prediction.setPSimpleResult(UPDATED_P_SIMPLE_RESULT);
        prediction.setDoubleResult(UPDATED_DOUBLE_RESULT);
        prediction.setPDoubleResult(UPDATED_P_DOUBLE_RESULT);
        prediction.setSimpleHalfResult(UPDATED_SIMPLE_HALF_RESULT);
        prediction.setPSimpleHalfResult(UPDATED_P_SIMPLE_HALF_RESULT);
        prediction.setDoubleHalfResult(UPDATED_DOUBLE_HALF_RESULT);
        prediction.setPDoubleHalfResult(UPDATED_P_DOUBLE_HALF_RESULT);
        prediction.setHomeGoals(UPDATED_HOME_GOALS);
        prediction.setPHomeGoals(UPDATED_P_HOME_GOALS);
        prediction.setAwayGoals(UPDATED_AWAY_GOALS);
        prediction.setHalfHomeGoals(UPDATED_HALF_HOME_GOALS);
        prediction.setPAwayGoals(UPDATED_P_AWAY_GOALS);
        prediction.setPHalfHomeGoals(UPDATED_P_HALF_HOME_GOALS);
        prediction.setHalfAwayGoals(UPDATED_HALF_AWAY_GOALS);
        prediction.setPHalfAwayGoals(UPDATED_P_HALF_AWAY_GOALS);
        prediction.setMoreThan25(UPDATED_MORE_THAN25);
        prediction.setPMoreThan25(UPDATED_P_MORE_THAN25);
        restPredictionMockMvc.perform(put("/api/predictions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(prediction)))
                .andExpect(status().isOk());

        // Validate the Prediction in the database
        List<Prediction> predictions = predictionRepository.findAll();
        assertThat(predictions).hasSize(1);
        Prediction testPrediction = predictions.iterator().next();
        assertThat(testPrediction.getSimpleResult()).isEqualTo(UPDATED_SIMPLE_RESULT);
        assertThat(testPrediction.getPSimpleResult()).isEqualTo(UPDATED_P_SIMPLE_RESULT);
        assertThat(testPrediction.getDoubleResult()).isEqualTo(UPDATED_DOUBLE_RESULT);
        assertThat(testPrediction.getPDoubleResult()).isEqualTo(UPDATED_P_DOUBLE_RESULT);
        assertThat(testPrediction.getSimpleHalfResult()).isEqualTo(UPDATED_SIMPLE_HALF_RESULT);
        assertThat(testPrediction.getPSimpleHalfResult()).isEqualTo(UPDATED_P_SIMPLE_HALF_RESULT);
        assertThat(testPrediction.getDoubleHalfResult()).isEqualTo(UPDATED_DOUBLE_HALF_RESULT);
        assertThat(testPrediction.getPDoubleHalfResult()).isEqualTo(UPDATED_P_DOUBLE_HALF_RESULT);
        assertThat(testPrediction.getHomeGoals()).isEqualTo(UPDATED_HOME_GOALS);
        assertThat(testPrediction.getPHomeGoals()).isEqualTo(UPDATED_P_HOME_GOALS);
        assertThat(testPrediction.getAwayGoals()).isEqualTo(UPDATED_AWAY_GOALS);
        assertThat(testPrediction.getHalfHomeGoals()).isEqualTo(UPDATED_HALF_HOME_GOALS);
        assertThat(testPrediction.getPAwayGoals()).isEqualTo(UPDATED_P_AWAY_GOALS);
        assertThat(testPrediction.getPHalfHomeGoals()).isEqualTo(UPDATED_P_HALF_HOME_GOALS);
        assertThat(testPrediction.getHalfAwayGoals()).isEqualTo(UPDATED_HALF_AWAY_GOALS);
        assertThat(testPrediction.getPHalfAwayGoals()).isEqualTo(UPDATED_P_HALF_AWAY_GOALS);
        assertThat(testPrediction.getMoreThan25()).isEqualTo(UPDATED_MORE_THAN25);
        assertThat(testPrediction.getPMoreThan25()).isEqualTo(UPDATED_P_MORE_THAN25);
    }

    @Test
    @Transactional
    public void deletePrediction() throws Exception {
        // Initialize the database
        predictionRepository.saveAndFlush(prediction);

        // Get the prediction
        restPredictionMockMvc.perform(delete("/api/predictions/{id}", prediction.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Prediction> predictions = predictionRepository.findAll();
        assertThat(predictions).hasSize(0);
    }
}
