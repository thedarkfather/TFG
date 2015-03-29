package es.us.predictorum.web.rest;

import com.codahale.metrics.annotation.Timed;
import es.us.predictorum.domain.Actor;
import es.us.predictorum.repository.ActorRepository;
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
 * REST controller for managing Actor.
 */
@RestController
@RequestMapping("/api")
public class ActorResource {

    private final Logger log = LoggerFactory.getLogger(ActorResource.class);

    @Inject
    private ActorRepository actorRepository;

    /**
     * POST  /actors -> Create a new actor.
     */
    @RequestMapping(value = "/actors",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Actor actor) throws URISyntaxException {
        log.debug("REST request to save Actor : {}", actor);
        if (actor.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new actor cannot already have an ID").build();
        }
        actorRepository.save(actor);
        return ResponseEntity.created(new URI("/api/actors/" + actor.getId())).build();
    }

    /**
     * PUT  /actors -> Updates an existing actor.
     */
    @RequestMapping(value = "/actors",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Actor actor) throws URISyntaxException {
        log.debug("REST request to update Actor : {}", actor);
        if (actor.getId() == null) {
            return create(actor);
        }
        actorRepository.save(actor);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /actors -> get all the actors.
     */
    @RequestMapping(value = "/actors",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Actor>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Actor> page = actorRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/actors", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /actors/:id -> get the "id" actor.
     */
    @RequestMapping(value = "/actors/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Actor> get(@PathVariable Long id) {
        log.debug("REST request to get Actor : {}", id);
        return Optional.ofNullable(actorRepository.findOne(id))
            .map(actor -> new ResponseEntity<>(
                actor,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /actors/:id -> delete the "id" actor.
     */
    @RequestMapping(value = "/actors/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Actor : {}", id);
        actorRepository.delete(id);
    }
}
