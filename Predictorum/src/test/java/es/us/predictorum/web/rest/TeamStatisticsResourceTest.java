package es.us.predictorum.web.rest;

import es.us.predictorum.Application;
import es.us.predictorum.domain.TeamStatistics;
import es.us.predictorum.repository.TeamStatisticsRepository;

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
 * Test class for the TeamStatisticsResource REST controller.
 *
 * @see TeamStatisticsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TeamStatisticsResourceTest {

    private static final String DEFAULT_STREAK = "SAMPLE_TEXT";
    private static final String UPDATED_STREAK = "UPDATED_TEXT";

    private static final Integer DEFAULT_WON_MATCH_PERCENTAGE = 0;
    private static final Integer UPDATED_WON_MATCH_PERCENTAGE = 1;

    private static final Integer DEFAULT_LOST_MATCH_PERCENTAGE = 0;
    private static final Integer UPDATED_LOST_MATCH_PERCENTAGE = 1;

    private static final Integer DEFAULT_DRAWN_MATCH_PERCENTAGE = 0;
    private static final Integer UPDATED_DRAWN_MATCH_PERCENTAGE = 1;

    private static final Integer DEFAULT_MORE_THAN25_PERCENTAGE = 0;
    private static final Integer UPDATED_MORE_THAN25_PERCENTAGE = 1;

    private static final Integer DEFAULT_WON_HALF_MATCH_PERCENTAGE = 0;
    private static final Integer UPDATED_WON_HALF_MATCH_PERCENTAGE = 1;

    private static final Integer DEFAULT_LOST_HALF_MATCH_PERCENTAGE = 0;
    private static final Integer UPDATED_LOST_HALF_MATCH_PERCENTAGE = 1;

    private static final Integer DEFAULT_DRAW_HALF_MATCH_PERCENTAGE = 0;
    private static final Integer UPDATED_DRAW_HALF_MATCH_PERCENTAGE = 1;

    private static final Integer DEFAULT_HOME_WON_MATCHES = 0;
    private static final Integer UPDATED_HOME_WON_MATCHES = 1;

    private static final Integer DEFAULT_AWAY_WON_MATCHES = 0;
    private static final Integer UPDATED_AWAY_WON_MATCHES = 1;

    private static final Integer DEFAULT_LEAGUE_POSITION = 0;
    private static final Integer UPDATED_LEAGUE_POSITION = 1;

    private static final Integer DEFAULT_LEAGUE_POINTS = 0;
    private static final Integer UPDATED_LEAGUE_POINTS = 1;

    @Inject
    private TeamStatisticsRepository teamStatisticsRepository;

    private MockMvc restTeamStatisticsMockMvc;

    private TeamStatistics teamStatistics;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TeamStatisticsResource teamStatisticsResource = new TeamStatisticsResource();
        ReflectionTestUtils.setField(teamStatisticsResource, "teamStatisticsRepository", teamStatisticsRepository);
        this.restTeamStatisticsMockMvc = MockMvcBuilders.standaloneSetup(teamStatisticsResource).build();
    }

    @Before
    public void initTest() {
        teamStatistics = new TeamStatistics();
        teamStatistics.setStreak(DEFAULT_STREAK);
        teamStatistics.setWonMatchPercentage(DEFAULT_WON_MATCH_PERCENTAGE);
        teamStatistics.setLostMatchPercentage(DEFAULT_LOST_MATCH_PERCENTAGE);
        teamStatistics.setDrawnMatchPercentage(DEFAULT_DRAWN_MATCH_PERCENTAGE);
        teamStatistics.setMoreThan25Percentage(DEFAULT_MORE_THAN25_PERCENTAGE);
        teamStatistics.setWonHalfMatchPercentage(DEFAULT_WON_HALF_MATCH_PERCENTAGE);
        teamStatistics.setLostHalfMatchPercentage(DEFAULT_LOST_HALF_MATCH_PERCENTAGE);
        teamStatistics.setDrawHalfMatchPercentage(DEFAULT_DRAW_HALF_MATCH_PERCENTAGE);
        teamStatistics.setHomeWonMatches(DEFAULT_HOME_WON_MATCHES);
        teamStatistics.setAwayWonMatches(DEFAULT_AWAY_WON_MATCHES);
        teamStatistics.setLeaguePosition(DEFAULT_LEAGUE_POSITION);
        teamStatistics.setLeaguePoints(DEFAULT_LEAGUE_POINTS);
    }

    @Test
    @Transactional
    public void createTeamStatistics() throws Exception {
        // Validate the database is empty
        assertThat(teamStatisticsRepository.findAll()).hasSize(0);

        // Create the TeamStatistics
        restTeamStatisticsMockMvc.perform(post("/api/teamStatisticss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(teamStatistics)))
                .andExpect(status().isCreated());

        // Validate the TeamStatistics in the database
        List<TeamStatistics> teamStatisticss = teamStatisticsRepository.findAll();
        assertThat(teamStatisticss).hasSize(1);
        TeamStatistics testTeamStatistics = teamStatisticss.iterator().next();
        assertThat(testTeamStatistics.getStreak()).isEqualTo(DEFAULT_STREAK);
        assertThat(testTeamStatistics.getWonMatchPercentage()).isEqualTo(DEFAULT_WON_MATCH_PERCENTAGE);
        assertThat(testTeamStatistics.getLostMatchPercentage()).isEqualTo(DEFAULT_LOST_MATCH_PERCENTAGE);
        assertThat(testTeamStatistics.getDrawnMatchPercentage()).isEqualTo(DEFAULT_DRAWN_MATCH_PERCENTAGE);
        assertThat(testTeamStatistics.getMoreThan25Percentage()).isEqualTo(DEFAULT_MORE_THAN25_PERCENTAGE);
        assertThat(testTeamStatistics.getWonHalfMatchPercentage()).isEqualTo(DEFAULT_WON_HALF_MATCH_PERCENTAGE);
        assertThat(testTeamStatistics.getLostHalfMatchPercentage()).isEqualTo(DEFAULT_LOST_HALF_MATCH_PERCENTAGE);
        assertThat(testTeamStatistics.getDrawHalfMatchPercentage()).isEqualTo(DEFAULT_DRAW_HALF_MATCH_PERCENTAGE);
        assertThat(testTeamStatistics.getHomeWonMatches()).isEqualTo(DEFAULT_HOME_WON_MATCHES);
        assertThat(testTeamStatistics.getAwayWonMatches()).isEqualTo(DEFAULT_AWAY_WON_MATCHES);
        assertThat(testTeamStatistics.getLeaguePosition()).isEqualTo(DEFAULT_LEAGUE_POSITION);
        assertThat(testTeamStatistics.getLeaguePoints()).isEqualTo(DEFAULT_LEAGUE_POINTS);
    }

    @Test
    @Transactional
    public void getAllTeamStatisticss() throws Exception {
        // Initialize the database
        teamStatisticsRepository.saveAndFlush(teamStatistics);

        // Get all the teamStatisticss
        restTeamStatisticsMockMvc.perform(get("/api/teamStatisticss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(teamStatistics.getId().intValue()))
                .andExpect(jsonPath("$.[0].streak").value(DEFAULT_STREAK.toString()))
                .andExpect(jsonPath("$.[0].wonMatchPercentage").value(DEFAULT_WON_MATCH_PERCENTAGE))
                .andExpect(jsonPath("$.[0].lostMatchPercentage").value(DEFAULT_LOST_MATCH_PERCENTAGE))
                .andExpect(jsonPath("$.[0].drawnMatchPercentage").value(DEFAULT_DRAWN_MATCH_PERCENTAGE))
                .andExpect(jsonPath("$.[0].moreThan25Percentage").value(DEFAULT_MORE_THAN25_PERCENTAGE))
                .andExpect(jsonPath("$.[0].wonHalfMatchPercentage").value(DEFAULT_WON_HALF_MATCH_PERCENTAGE))
                .andExpect(jsonPath("$.[0].lostHalfMatchPercentage").value(DEFAULT_LOST_HALF_MATCH_PERCENTAGE))
                .andExpect(jsonPath("$.[0].drawHalfMatchPercentage").value(DEFAULT_DRAW_HALF_MATCH_PERCENTAGE))
                .andExpect(jsonPath("$.[0].homeWonMatches").value(DEFAULT_HOME_WON_MATCHES))
                .andExpect(jsonPath("$.[0].awayWonMatches").value(DEFAULT_AWAY_WON_MATCHES))
                .andExpect(jsonPath("$.[0].leaguePosition").value(DEFAULT_LEAGUE_POSITION))
                .andExpect(jsonPath("$.[0].leaguePoints").value(DEFAULT_LEAGUE_POINTS));
    }

    @Test
    @Transactional
    public void getTeamStatistics() throws Exception {
        // Initialize the database
        teamStatisticsRepository.saveAndFlush(teamStatistics);

        // Get the teamStatistics
        restTeamStatisticsMockMvc.perform(get("/api/teamStatisticss/{id}", teamStatistics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(teamStatistics.getId().intValue()))
            .andExpect(jsonPath("$.streak").value(DEFAULT_STREAK.toString()))
            .andExpect(jsonPath("$.wonMatchPercentage").value(DEFAULT_WON_MATCH_PERCENTAGE))
            .andExpect(jsonPath("$.lostMatchPercentage").value(DEFAULT_LOST_MATCH_PERCENTAGE))
            .andExpect(jsonPath("$.drawnMatchPercentage").value(DEFAULT_DRAWN_MATCH_PERCENTAGE))
            .andExpect(jsonPath("$.moreThan25Percentage").value(DEFAULT_MORE_THAN25_PERCENTAGE))
            .andExpect(jsonPath("$.wonHalfMatchPercentage").value(DEFAULT_WON_HALF_MATCH_PERCENTAGE))
            .andExpect(jsonPath("$.lostHalfMatchPercentage").value(DEFAULT_LOST_HALF_MATCH_PERCENTAGE))
            .andExpect(jsonPath("$.drawHalfMatchPercentage").value(DEFAULT_DRAW_HALF_MATCH_PERCENTAGE))
            .andExpect(jsonPath("$.homeWonMatches").value(DEFAULT_HOME_WON_MATCHES))
            .andExpect(jsonPath("$.awayWonMatches").value(DEFAULT_AWAY_WON_MATCHES))
            .andExpect(jsonPath("$.leaguePosition").value(DEFAULT_LEAGUE_POSITION))
            .andExpect(jsonPath("$.leaguePoints").value(DEFAULT_LEAGUE_POINTS));
    }

    @Test
    @Transactional
    public void getNonExistingTeamStatistics() throws Exception {
        // Get the teamStatistics
        restTeamStatisticsMockMvc.perform(get("/api/teamStatisticss/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeamStatistics() throws Exception {
        // Initialize the database
        teamStatisticsRepository.saveAndFlush(teamStatistics);

        // Update the teamStatistics
        teamStatistics.setStreak(UPDATED_STREAK);
        teamStatistics.setWonMatchPercentage(UPDATED_WON_MATCH_PERCENTAGE);
        teamStatistics.setLostMatchPercentage(UPDATED_LOST_MATCH_PERCENTAGE);
        teamStatistics.setDrawnMatchPercentage(UPDATED_DRAWN_MATCH_PERCENTAGE);
        teamStatistics.setMoreThan25Percentage(UPDATED_MORE_THAN25_PERCENTAGE);
        teamStatistics.setWonHalfMatchPercentage(UPDATED_WON_HALF_MATCH_PERCENTAGE);
        teamStatistics.setLostHalfMatchPercentage(UPDATED_LOST_HALF_MATCH_PERCENTAGE);
        teamStatistics.setDrawHalfMatchPercentage(UPDATED_DRAW_HALF_MATCH_PERCENTAGE);
        teamStatistics.setHomeWonMatches(UPDATED_HOME_WON_MATCHES);
        teamStatistics.setAwayWonMatches(UPDATED_AWAY_WON_MATCHES);
        teamStatistics.setLeaguePosition(UPDATED_LEAGUE_POSITION);
        teamStatistics.setLeaguePoints(UPDATED_LEAGUE_POINTS);
        restTeamStatisticsMockMvc.perform(put("/api/teamStatisticss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(teamStatistics)))
                .andExpect(status().isOk());

        // Validate the TeamStatistics in the database
        List<TeamStatistics> teamStatisticss = teamStatisticsRepository.findAll();
        assertThat(teamStatisticss).hasSize(1);
        TeamStatistics testTeamStatistics = teamStatisticss.iterator().next();
        assertThat(testTeamStatistics.getStreak()).isEqualTo(UPDATED_STREAK);
        assertThat(testTeamStatistics.getWonMatchPercentage()).isEqualTo(UPDATED_WON_MATCH_PERCENTAGE);
        assertThat(testTeamStatistics.getLostMatchPercentage()).isEqualTo(UPDATED_LOST_MATCH_PERCENTAGE);
        assertThat(testTeamStatistics.getDrawnMatchPercentage()).isEqualTo(UPDATED_DRAWN_MATCH_PERCENTAGE);
        assertThat(testTeamStatistics.getMoreThan25Percentage()).isEqualTo(UPDATED_MORE_THAN25_PERCENTAGE);
        assertThat(testTeamStatistics.getWonHalfMatchPercentage()).isEqualTo(UPDATED_WON_HALF_MATCH_PERCENTAGE);
        assertThat(testTeamStatistics.getLostHalfMatchPercentage()).isEqualTo(UPDATED_LOST_HALF_MATCH_PERCENTAGE);
        assertThat(testTeamStatistics.getDrawHalfMatchPercentage()).isEqualTo(UPDATED_DRAW_HALF_MATCH_PERCENTAGE);
        assertThat(testTeamStatistics.getHomeWonMatches()).isEqualTo(UPDATED_HOME_WON_MATCHES);
        assertThat(testTeamStatistics.getAwayWonMatches()).isEqualTo(UPDATED_AWAY_WON_MATCHES);
        assertThat(testTeamStatistics.getLeaguePosition()).isEqualTo(UPDATED_LEAGUE_POSITION);
        assertThat(testTeamStatistics.getLeaguePoints()).isEqualTo(UPDATED_LEAGUE_POINTS);
    }

    @Test
    @Transactional
    public void deleteTeamStatistics() throws Exception {
        // Initialize the database
        teamStatisticsRepository.saveAndFlush(teamStatistics);

        // Get the teamStatistics
        restTeamStatisticsMockMvc.perform(delete("/api/teamStatisticss/{id}", teamStatistics.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TeamStatistics> teamStatisticss = teamStatisticsRepository.findAll();
        assertThat(teamStatisticss).hasSize(0);
    }
}
