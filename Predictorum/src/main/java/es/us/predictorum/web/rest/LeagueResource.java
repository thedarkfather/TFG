package es.us.predictorum.web.rest;

import com.codahale.metrics.annotation.Timed;
import es.us.predictorum.domain.League;
import es.us.predictorum.repository.LeagueRepository;
import es.us.predictorum.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing League.
 */
@RestController
@RequestMapping("/api")
public class LeagueResource {

    private final Logger log = LoggerFactory.getLogger(LeagueResource.class);

    @Inject
    private LeagueRepository leagueRepository;

    /**
     * POST  /leagues -> Create a new league.
     */
    @RequestMapping(value = "/leagues",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody League league) throws URISyntaxException {
        log.debug("REST request to save League : {}", league);
        if (league.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new league cannot already have an ID").build();
        }
        leagueRepository.save(league);
        return ResponseEntity.created(new URI("/api/leagues/" + league.getId())).build();
    }

    /**
     * PUT  /leagues -> Updates an existing league.
     */
    @RequestMapping(value = "/leagues",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody League league) throws URISyntaxException {
        log.debug("REST request to update League : {}", league);
        if (league.getId() == null) {
            return create(league);
        }
        leagueRepository.save(league);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /leagues -> get all the leagues.
     */
    @RequestMapping(value = "/leagues",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<League>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<League> page = leagueRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/leagues", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /leagues/:id -> get the "id" league.
     */
    @RequestMapping(value = "/leagues/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<League> get(@PathVariable Long id) {
        log.debug("REST request to get League : {}", id);
        return Optional.ofNullable(leagueRepository.findOne(id))
            .map(league -> new ResponseEntity<>(
                league,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /leagues/:id -> delete the "id" league.
     */
    @RequestMapping(value = "/leagues/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete League : {}", id);
        leagueRepository.delete(id);
    }
}
