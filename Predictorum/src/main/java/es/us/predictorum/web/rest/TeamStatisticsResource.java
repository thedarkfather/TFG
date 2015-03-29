package es.us.predictorum.web.rest;

import com.codahale.metrics.annotation.Timed;
import es.us.predictorum.domain.TeamStatistics;
import es.us.predictorum.repository.TeamStatisticsRepository;
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
 * REST controller for managing TeamStatistics.
 */
@RestController
@RequestMapping("/api")
public class TeamStatisticsResource {

    private final Logger log = LoggerFactory.getLogger(TeamStatisticsResource.class);

    @Inject
    private TeamStatisticsRepository teamStatisticsRepository;

    /**
     * POST  /teamStatisticss -> Create a new teamStatistics.
     */
    @RequestMapping(value = "/teamStatisticss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody TeamStatistics teamStatistics) throws URISyntaxException {
        log.debug("REST request to save TeamStatistics : {}", teamStatistics);
        if (teamStatistics.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new teamStatistics cannot already have an ID").build();
        }
        teamStatisticsRepository.save(teamStatistics);
        return ResponseEntity.created(new URI("/api/teamStatisticss/" + teamStatistics.getId())).build();
    }

    /**
     * PUT  /teamStatisticss -> Updates an existing teamStatistics.
     */
    @RequestMapping(value = "/teamStatisticss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody TeamStatistics teamStatistics) throws URISyntaxException {
        log.debug("REST request to update TeamStatistics : {}", teamStatistics);
        if (teamStatistics.getId() == null) {
            return create(teamStatistics);
        }
        teamStatisticsRepository.save(teamStatistics);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /teamStatisticss -> get all the teamStatisticss.
     */
    @RequestMapping(value = "/teamStatisticss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TeamStatistics>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<TeamStatistics> page = teamStatisticsRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teamStatisticss", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /teamStatisticss/:id -> get the "id" teamStatistics.
     */
    @RequestMapping(value = "/teamStatisticss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TeamStatistics> get(@PathVariable Long id) {
        log.debug("REST request to get TeamStatistics : {}", id);
        return Optional.ofNullable(teamStatisticsRepository.findOne(id))
            .map(teamStatistics -> new ResponseEntity<>(
                teamStatistics,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /teamStatisticss/:id -> delete the "id" teamStatistics.
     */
    @RequestMapping(value = "/teamStatisticss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete TeamStatistics : {}", id);
        teamStatisticsRepository.delete(id);
    }
}
