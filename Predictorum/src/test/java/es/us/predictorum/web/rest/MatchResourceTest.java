package es.us.predictorum.web.rest;

import es.us.predictorum.Application;
import es.us.predictorum.domain.Match;
import es.us.predictorum.repository.MatchRepository;

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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MatchResource REST controller.
 *
 * @see MatchResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MatchResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final DateTime DEFAULT_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_STR = dateTimeFormatter.print(DEFAULT_DATE);

    @Inject
    private MatchRepository matchRepository;

    private MockMvc restMatchMockMvc;

    private Match match;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MatchResource matchResource = new MatchResource();
        ReflectionTestUtils.setField(matchResource, "matchRepository", matchRepository);
        this.restMatchMockMvc = MockMvcBuilders.standaloneSetup(matchResource).build();
    }

    @Before
    public void initTest() {
        match = new Match();
        match.setDate(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createMatch() throws Exception {
        // Validate the database is empty
        assertThat(matchRepository.findAll()).hasSize(0);

        // Create the Match
        restMatchMockMvc.perform(post("/api/matchs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(match)))
                .andExpect(status().isCreated());

        // Validate the Match in the database
        List<Match> matchs = matchRepository.findAll();
        assertThat(matchs).hasSize(1);
        Match testMatch = matchs.iterator().next();
        assertThat(testMatch.getDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void getAllMatchs() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchs
        restMatchMockMvc.perform(get("/api/matchs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(match.getId().intValue()))
                .andExpect(jsonPath("$.[0].date").value(DEFAULT_DATE_STR));
    }

    @Test
    @Transactional
    public void getMatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get the match
        restMatchMockMvc.perform(get("/api/matchs/{id}", match.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(match.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingMatch() throws Exception {
        // Get the match
        restMatchMockMvc.perform(get("/api/matchs/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Update the match
        match.setDate(UPDATED_DATE);
        restMatchMockMvc.perform(put("/api/matchs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(match)))
                .andExpect(status().isOk());

        // Validate the Match in the database
        List<Match> matchs = matchRepository.findAll();
        assertThat(matchs).hasSize(1);
        Match testMatch = matchs.iterator().next();
        assertThat(testMatch.getDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void deleteMatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get the match
        restMatchMockMvc.perform(delete("/api/matchs/{id}", match.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Match> matchs = matchRepository.findAll();
        assertThat(matchs).hasSize(0);
    }
}
