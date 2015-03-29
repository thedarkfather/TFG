package es.us.predictorum.web.rest;

import es.us.predictorum.Application;
import es.us.predictorum.domain.Actor;
import es.us.predictorum.repository.ActorRepository;

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
 * Test class for the ActorResource REST controller.
 *
 * @see ActorResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ActorResourceTest {


    private static final Integer DEFAULT_SR_POINTS = 0;
    private static final Integer UPDATED_SR_POINTS = 1;

    private static final Integer DEFAULT_DR_POINTS = 0;
    private static final Integer UPDATED_DR_POINTS = 1;

    private static final Integer DEFAULT_S_HRPOINTS = 0;
    private static final Integer UPDATED_S_HRPOINTS = 1;

    private static final Integer DEFAULT_D_HRPOINTS = 0;
    private static final Integer UPDATED_D_HRPOINTS = 1;

    private static final Integer DEFAULT_H_GPOINTS = 0;
    private static final Integer UPDATED_H_GPOINTS = 1;

    private static final Integer DEFAULT_A_GPOINTS = 0;
    private static final Integer UPDATED_A_GPOINTS = 1;

    private static final Integer DEFAULT_H_HGPOINTS = 0;
    private static final Integer UPDATED_H_HGPOINTS = 1;

    private static final Integer DEFAULT_H_AGPOINTS = 0;
    private static final Integer UPDATED_H_AGPOINTS = 1;

    private static final Integer DEFAULT_M_T25_POINTS = 0;
    private static final Integer UPDATED_M_T25_POINTS = 1;

    @Inject
    private ActorRepository actorRepository;

    private MockMvc restActorMockMvc;

    private Actor actor;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ActorResource actorResource = new ActorResource();
        ReflectionTestUtils.setField(actorResource, "actorRepository", actorRepository);
        this.restActorMockMvc = MockMvcBuilders.standaloneSetup(actorResource).build();
    }

    @Before
    public void initTest() {
        actor = new Actor();
        actor.setSrPoints(DEFAULT_SR_POINTS);
        actor.setDrPoints(DEFAULT_DR_POINTS);
        actor.setSHRPoints(DEFAULT_S_HRPOINTS);
        actor.setDHRPoints(DEFAULT_D_HRPOINTS);
        actor.setHGPoints(DEFAULT_H_GPOINTS);
        actor.setAGPoints(DEFAULT_A_GPOINTS);
        actor.setHHGPoints(DEFAULT_H_HGPOINTS);
        actor.setHAGPoints(DEFAULT_H_AGPOINTS);
        actor.setMT25Points(DEFAULT_M_T25_POINTS);
    }

    @Test
    @Transactional
    public void createActor() throws Exception {
        // Validate the database is empty
        assertThat(actorRepository.findAll()).hasSize(0);

        // Create the Actor
        restActorMockMvc.perform(post("/api/actors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(actor)))
                .andExpect(status().isCreated());

        // Validate the Actor in the database
        List<Actor> actors = actorRepository.findAll();
        assertThat(actors).hasSize(1);
        Actor testActor = actors.iterator().next();
        assertThat(testActor.getSrPoints()).isEqualTo(DEFAULT_SR_POINTS);
        assertThat(testActor.getDrPoints()).isEqualTo(DEFAULT_DR_POINTS);
        assertThat(testActor.getSHRPoints()).isEqualTo(DEFAULT_S_HRPOINTS);
        assertThat(testActor.getDHRPoints()).isEqualTo(DEFAULT_D_HRPOINTS);
        assertThat(testActor.getHGPoints()).isEqualTo(DEFAULT_H_GPOINTS);
        assertThat(testActor.getAGPoints()).isEqualTo(DEFAULT_A_GPOINTS);
        assertThat(testActor.getHHGPoints()).isEqualTo(DEFAULT_H_HGPOINTS);
        assertThat(testActor.getHAGPoints()).isEqualTo(DEFAULT_H_AGPOINTS);
        assertThat(testActor.getMT25Points()).isEqualTo(DEFAULT_M_T25_POINTS);
    }

    @Test
    @Transactional
    public void getAllActors() throws Exception {
        // Initialize the database
        actorRepository.saveAndFlush(actor);

        // Get all the actors
        restActorMockMvc.perform(get("/api/actors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(actor.getId().intValue()))
                .andExpect(jsonPath("$.[0].srPoints").value(DEFAULT_SR_POINTS))
                .andExpect(jsonPath("$.[0].drPoints").value(DEFAULT_DR_POINTS))
                .andExpect(jsonPath("$.[0].sHRPoints").value(DEFAULT_S_HRPOINTS))
                .andExpect(jsonPath("$.[0].dHRPoints").value(DEFAULT_D_HRPOINTS))
                .andExpect(jsonPath("$.[0].hGPoints").value(DEFAULT_H_GPOINTS))
                .andExpect(jsonPath("$.[0].aGPoints").value(DEFAULT_A_GPOINTS))
                .andExpect(jsonPath("$.[0].hHGPoints").value(DEFAULT_H_HGPOINTS))
                .andExpect(jsonPath("$.[0].hAGPoints").value(DEFAULT_H_AGPOINTS))
                .andExpect(jsonPath("$.[0].mT25Points").value(DEFAULT_M_T25_POINTS));
    }

    @Test
    @Transactional
    public void getActor() throws Exception {
        // Initialize the database
        actorRepository.saveAndFlush(actor);

        // Get the actor
        restActorMockMvc.perform(get("/api/actors/{id}", actor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(actor.getId().intValue()))
            .andExpect(jsonPath("$.srPoints").value(DEFAULT_SR_POINTS))
            .andExpect(jsonPath("$.drPoints").value(DEFAULT_DR_POINTS))
            .andExpect(jsonPath("$.sHRPoints").value(DEFAULT_S_HRPOINTS))
            .andExpect(jsonPath("$.dHRPoints").value(DEFAULT_D_HRPOINTS))
            .andExpect(jsonPath("$.hGPoints").value(DEFAULT_H_GPOINTS))
            .andExpect(jsonPath("$.aGPoints").value(DEFAULT_A_GPOINTS))
            .andExpect(jsonPath("$.hHGPoints").value(DEFAULT_H_HGPOINTS))
            .andExpect(jsonPath("$.hAGPoints").value(DEFAULT_H_AGPOINTS))
            .andExpect(jsonPath("$.mT25Points").value(DEFAULT_M_T25_POINTS));
    }

    @Test
    @Transactional
    public void getNonExistingActor() throws Exception {
        // Get the actor
        restActorMockMvc.perform(get("/api/actors/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActor() throws Exception {
        // Initialize the database
        actorRepository.saveAndFlush(actor);

        // Update the actor
        actor.setSrPoints(UPDATED_SR_POINTS);
        actor.setDrPoints(UPDATED_DR_POINTS);
        actor.setSHRPoints(UPDATED_S_HRPOINTS);
        actor.setDHRPoints(UPDATED_D_HRPOINTS);
        actor.setHGPoints(UPDATED_H_GPOINTS);
        actor.setAGPoints(UPDATED_A_GPOINTS);
        actor.setHHGPoints(UPDATED_H_HGPOINTS);
        actor.setHAGPoints(UPDATED_H_AGPOINTS);
        actor.setMT25Points(UPDATED_M_T25_POINTS);
        restActorMockMvc.perform(put("/api/actors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(actor)))
                .andExpect(status().isOk());

        // Validate the Actor in the database
        List<Actor> actors = actorRepository.findAll();
        assertThat(actors).hasSize(1);
        Actor testActor = actors.iterator().next();
        assertThat(testActor.getSrPoints()).isEqualTo(UPDATED_SR_POINTS);
        assertThat(testActor.getDrPoints()).isEqualTo(UPDATED_DR_POINTS);
        assertThat(testActor.getSHRPoints()).isEqualTo(UPDATED_S_HRPOINTS);
        assertThat(testActor.getDHRPoints()).isEqualTo(UPDATED_D_HRPOINTS);
        assertThat(testActor.getHGPoints()).isEqualTo(UPDATED_H_GPOINTS);
        assertThat(testActor.getAGPoints()).isEqualTo(UPDATED_A_GPOINTS);
        assertThat(testActor.getHHGPoints()).isEqualTo(UPDATED_H_HGPOINTS);
        assertThat(testActor.getHAGPoints()).isEqualTo(UPDATED_H_AGPOINTS);
        assertThat(testActor.getMT25Points()).isEqualTo(UPDATED_M_T25_POINTS);
    }

    @Test
    @Transactional
    public void deleteActor() throws Exception {
        // Initialize the database
        actorRepository.saveAndFlush(actor);

        // Get the actor
        restActorMockMvc.perform(delete("/api/actors/{id}", actor.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Actor> actors = actorRepository.findAll();
        assertThat(actors).hasSize(0);
    }
}
