package es.us.predictorum.web.rest;

import es.us.predictorum.Application;
import es.us.predictorum.domain.Comment;
import es.us.predictorum.repository.CommentRepository;

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
 * Test class for the CommentResource REST controller.
 *
 * @see CommentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CommentResourceTest {

    private static final String DEFAULT_TEXT = "SAMPLE_TEXT";
    private static final String UPDATED_TEXT = "UPDATED_TEXT";

    private static final Integer DEFAULT_POS_POINTS = 0;
    private static final Integer UPDATED_POS_POINTS = 1;

    private static final Integer DEFAULT_NEG_POINTS = 0;
    private static final Integer UPDATED_NEG_POINTS = 1;

    @Inject
    private CommentRepository commentRepository;

    private MockMvc restCommentMockMvc;

    private Comment comment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CommentResource commentResource = new CommentResource();
        ReflectionTestUtils.setField(commentResource, "commentRepository", commentRepository);
        this.restCommentMockMvc = MockMvcBuilders.standaloneSetup(commentResource).build();
    }

    @Before
    public void initTest() {
        comment = new Comment();
        comment.setText(DEFAULT_TEXT);
        comment.setPosPoints(DEFAULT_POS_POINTS);
        comment.setNegPoints(DEFAULT_NEG_POINTS);
    }

    @Test
    @Transactional
    public void createComment() throws Exception {
        // Validate the database is empty
        assertThat(commentRepository.findAll()).hasSize(0);

        // Create the Comment
        restCommentMockMvc.perform(post("/api/comments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(comment)))
                .andExpect(status().isCreated());

        // Validate the Comment in the database
        List<Comment> comments = commentRepository.findAll();
        assertThat(comments).hasSize(1);
        Comment testComment = comments.iterator().next();
        assertThat(testComment.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testComment.getPosPoints()).isEqualTo(DEFAULT_POS_POINTS);
        assertThat(testComment.getNegPoints()).isEqualTo(DEFAULT_NEG_POINTS);
    }

    @Test
    @Transactional
    public void getAllComments() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the comments
        restCommentMockMvc.perform(get("/api/comments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(comment.getId().intValue()))
                .andExpect(jsonPath("$.[0].text").value(DEFAULT_TEXT.toString()))
                .andExpect(jsonPath("$.[0].posPoints").value(DEFAULT_POS_POINTS))
                .andExpect(jsonPath("$.[0].negPoints").value(DEFAULT_NEG_POINTS));
    }

    @Test
    @Transactional
    public void getComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get the comment
        restCommentMockMvc.perform(get("/api/comments/{id}", comment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(comment.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.posPoints").value(DEFAULT_POS_POINTS))
            .andExpect(jsonPath("$.negPoints").value(DEFAULT_NEG_POINTS));
    }

    @Test
    @Transactional
    public void getNonExistingComment() throws Exception {
        // Get the comment
        restCommentMockMvc.perform(get("/api/comments/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Update the comment
        comment.setText(UPDATED_TEXT);
        comment.setPosPoints(UPDATED_POS_POINTS);
        comment.setNegPoints(UPDATED_NEG_POINTS);
        restCommentMockMvc.perform(put("/api/comments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(comment)))
                .andExpect(status().isOk());

        // Validate the Comment in the database
        List<Comment> comments = commentRepository.findAll();
        assertThat(comments).hasSize(1);
        Comment testComment = comments.iterator().next();
        assertThat(testComment.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testComment.getPosPoints()).isEqualTo(UPDATED_POS_POINTS);
        assertThat(testComment.getNegPoints()).isEqualTo(UPDATED_NEG_POINTS);
    }

    @Test
    @Transactional
    public void deleteComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get the comment
        restCommentMockMvc.perform(delete("/api/comments/{id}", comment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Comment> comments = commentRepository.findAll();
        assertThat(comments).hasSize(0);
    }
}
