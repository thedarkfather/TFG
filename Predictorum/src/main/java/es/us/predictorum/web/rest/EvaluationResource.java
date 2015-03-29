package es.us.predictorum.web.rest;

import com.codahale.metrics.annotation.Timed;
import es.us.predictorum.domain.Evaluation;
import es.us.predictorum.repository.EvaluationRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Evaluation.
 */
@RestController
@RequestMapping("/api")
public class EvaluationResource {

    private final Logger log = LoggerFactory.getLogger(EvaluationResource.class);

    @Inject
    private EvaluationRepository evaluationRepository;

    /**
     * POST  /evaluations -> Create a new evaluation.
     */
    @RequestMapping(value = "/evaluations",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Evaluation evaluation) throws URISyntaxException {
        log.debug("REST request to save Evaluation : {}", evaluation);
        if (evaluation.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new evaluation cannot already have an ID").build();
        }
        evaluationRepository.save(evaluation);
        return ResponseEntity.created(new URI("/api/evaluations/" + evaluation.getId())).build();
    }

    /**
     * PUT  /evaluations -> Updates an existing evaluation.
     */
    @RequestMapping(value = "/evaluations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Evaluation evaluation) throws URISyntaxException {
        log.debug("REST request to update Evaluation : {}", evaluation);
        if (evaluation.getId() == null) {
            return create(evaluation);
        }
        evaluationRepository.save(evaluation);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /evaluations -> get all the evaluations.
     */
    @RequestMapping(value = "/evaluations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Evaluation>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Evaluation> page = evaluationRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/evaluations", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /evaluations/:id -> get the "id" evaluation.
     */
    @RequestMapping(value = "/evaluations/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Evaluation> get(@PathVariable Long id) {
        log.debug("REST request to get Evaluation : {}", id);
        return Optional.ofNullable(evaluationRepository.findOne(id))
            .map(evaluation -> new ResponseEntity<>(
                evaluation,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /evaluations/:id -> delete the "id" evaluation.
     */
    @RequestMapping(value = "/evaluations/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Evaluation : {}", id);
        evaluationRepository.delete(id);
    }
}
