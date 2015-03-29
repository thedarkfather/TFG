package es.us.predictorum.web.rest;

import com.codahale.metrics.annotation.Timed;
import es.us.predictorum.domain.Prediction;
import es.us.predictorum.repository.PredictionRepository;
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
 * REST controller for managing Prediction.
 */
@RestController
@RequestMapping("/api")
public class PredictionResource {

    private final Logger log = LoggerFactory.getLogger(PredictionResource.class);

    @Inject
    private PredictionRepository predictionRepository;

    /**
     * POST  /predictions -> Create a new prediction.
     */
    @RequestMapping(value = "/predictions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Prediction prediction) throws URISyntaxException {
        log.debug("REST request to save Prediction : {}", prediction);
        if (prediction.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new prediction cannot already have an ID").build();
        }
        predictionRepository.save(prediction);
        return ResponseEntity.created(new URI("/api/predictions/" + prediction.getId())).build();
    }

    /**
     * PUT  /predictions -> Updates an existing prediction.
     */
    @RequestMapping(value = "/predictions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Prediction prediction) throws URISyntaxException {
        log.debug("REST request to update Prediction : {}", prediction);
        if (prediction.getId() == null) {
            return create(prediction);
        }
        predictionRepository.save(prediction);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /predictions -> get all the predictions.
     */
    @RequestMapping(value = "/predictions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Prediction>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Prediction> page = predictionRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/predictions", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /predictions/:id -> get the "id" prediction.
     */
    @RequestMapping(value = "/predictions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Prediction> get(@PathVariable Long id) {
        log.debug("REST request to get Prediction : {}", id);
        return Optional.ofNullable(predictionRepository.findOne(id))
            .map(prediction -> new ResponseEntity<>(
                prediction,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /predictions/:id -> delete the "id" prediction.
     */
    @RequestMapping(value = "/predictions/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Prediction : {}", id);
        predictionRepository.delete(id);
    }
}
