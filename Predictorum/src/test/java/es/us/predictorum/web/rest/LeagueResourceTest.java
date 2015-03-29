package es.us.predictorum.web.rest;

import es.us.predictorum.Application;
import es.us.predictorum.domain.League;
import es.us.predictorum.repository.LeagueRepository;

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
 * Test class for the LeagueResource REST controller.
 *
 * @see LeagueResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LeagueResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_SEASON = "SAMPLE_TEXT";
    private static final String UPDATED_SEASON = "UPDATED_TEXT";

    @Inject
    private LeagueRepository leagueRepository;

    private MockMvc restLeagueMockMvc;

    private League league;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LeagueResource leagueResource = new LeagueResource();
        ReflectionTestUtils.setField(leagueResource, "leagueRepository", leagueRepository);
        this.restLeagueMockMvc = MockMvcBuilders.standaloneSetup(leagueResource).build();
    }

    @Before
    public void initTest() {
        league = new League();
        league.setName(DEFAULT_NAME);
        league.setSeason(DEFAULT_SEASON);
    }

    @Test
    @Transactional
    public void createLeague() throws Exception {
        // Validate the database is empty
        assertThat(leagueRepository.findAll()).hasSize(0);

        // Create the League
        restLeagueMockMvc.perform(post("/api/leagues")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(league)))
                .andExpect(status().isCreated());

        // Validate the League in the database
        List<League> leagues = leagueRepository.findAll();
        assertThat(leagues).hasSize(1);
        League testLeague = leagues.iterator().next();
        assertThat(testLeague.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLeague.getSeason()).isEqualTo(DEFAULT_SEASON);
    }

    @Test
    @Transactional
    public void getAllLeagues() throws Exception {
        // Initialize the database
        leagueRepository.saveAndFlush(league);

        // Get all the leagues
        restLeagueMockMvc.perform(get("/api/leagues"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(league.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].season").value(DEFAULT_SEASON.toString()));
    }

    @Test
    @Transactional
    public void getLeague() throws Exception {
        // Initialize the database
        leagueRepository.saveAndFlush(league);

        // Get the league
        restLeagueMockMvc.perform(get("/api/leagues/{id}", league.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(league.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.season").value(DEFAULT_SEASON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLeague() throws Exception {
        // Get the league
        restLeagueMockMvc.perform(get("/api/leagues/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeague() throws Exception {
        // Initialize the database
        leagueRepository.saveAndFlush(league);

        // Update the league
        league.setName(UPDATED_NAME);
        league.setSeason(UPDATED_SEASON);
        restLeagueMockMvc.perform(put("/api/leagues")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(league)))
                .andExpect(status().isOk());

        // Validate the League in the database
        List<League> leagues = leagueRepository.findAll();
        assertThat(leagues).hasSize(1);
        League testLeague = leagues.iterator().next();
        assertThat(testLeague.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLeague.getSeason()).isEqualTo(UPDATED_SEASON);
    }

    @Test
    @Transactional
    public void deleteLeague() throws Exception {
        // Initialize the database
        leagueRepository.saveAndFlush(league);

        // Get the league
        restLeagueMockMvc.perform(delete("/api/leagues/{id}", league.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<League> leagues = leagueRepository.findAll();
        assertThat(leagues).hasSize(0);
    }
}
