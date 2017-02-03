package edu.cpp.cs499.p2.web.rest;

import edu.cpp.cs499.p2.Cs499P2App;

import edu.cpp.cs499.p2.domain.Classes;
import edu.cpp.cs499.p2.repository.ClassesRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClassesResource REST controller.
 *
 * @see ClassesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cs499P2App.class)
public class ClassesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LECTURER = "AAAAAAAAAA";
    private static final String UPDATED_LECTURER = "BBBBBBBBBB";

    private static final String DEFAULT_MAJOR = "AAAAAAAAAA";
    private static final String UPDATED_MAJOR = "BBBBBBBBBB";

    @Inject
    private ClassesRepository classesRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restClassesMockMvc;

    private Classes classes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClassesResource classesResource = new ClassesResource();
        ReflectionTestUtils.setField(classesResource, "classesRepository", classesRepository);
        this.restClassesMockMvc = MockMvcBuilders.standaloneSetup(classesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classes createEntity(EntityManager em) {
        Classes classes = new Classes()
                .name(DEFAULT_NAME)
                .lecturer(DEFAULT_LECTURER)
                .major(DEFAULT_MAJOR);
        return classes;
    }

    @Before
    public void initTest() {
        classes = createEntity(em);
    }

    @Test
    @Transactional
    public void createClasses() throws Exception {
        int databaseSizeBeforeCreate = classesRepository.findAll().size();

        // Create the Classes

        restClassesMockMvc.perform(post("/api/classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classes)))
            .andExpect(status().isCreated());

        // Validate the Classes in the database
        List<Classes> classesList = classesRepository.findAll();
        assertThat(classesList).hasSize(databaseSizeBeforeCreate + 1);
        Classes testClasses = classesList.get(classesList.size() - 1);
        assertThat(testClasses.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClasses.getLecturer()).isEqualTo(DEFAULT_LECTURER);
        assertThat(testClasses.getMajor()).isEqualTo(DEFAULT_MAJOR);
    }

    @Test
    @Transactional
    public void createClassesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classesRepository.findAll().size();

        // Create the Classes with an existing ID
        Classes existingClasses = new Classes();
        existingClasses.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassesMockMvc.perform(post("/api/classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingClasses)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Classes> classesList = classesRepository.findAll();
        assertThat(classesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClasses() throws Exception {
        // Initialize the database
        classesRepository.saveAndFlush(classes);

        // Get all the classesList
        restClassesMockMvc.perform(get("/api/classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].lecturer").value(hasItem(DEFAULT_LECTURER.toString())))
            .andExpect(jsonPath("$.[*].major").value(hasItem(DEFAULT_MAJOR.toString())));
    }

    @Test
    @Transactional
    public void getClasses() throws Exception {
        // Initialize the database
        classesRepository.saveAndFlush(classes);

        // Get the classes
        restClassesMockMvc.perform(get("/api/classes/{id}", classes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.lecturer").value(DEFAULT_LECTURER.toString()))
            .andExpect(jsonPath("$.major").value(DEFAULT_MAJOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClasses() throws Exception {
        // Get the classes
        restClassesMockMvc.perform(get("/api/classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClasses() throws Exception {
        // Initialize the database
        classesRepository.saveAndFlush(classes);
        int databaseSizeBeforeUpdate = classesRepository.findAll().size();

        // Update the classes
        Classes updatedClasses = classesRepository.findOne(classes.getId());
        updatedClasses
                .name(UPDATED_NAME)
                .lecturer(UPDATED_LECTURER)
                .major(UPDATED_MAJOR);

        restClassesMockMvc.perform(put("/api/classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClasses)))
            .andExpect(status().isOk());

        // Validate the Classes in the database
        List<Classes> classesList = classesRepository.findAll();
        assertThat(classesList).hasSize(databaseSizeBeforeUpdate);
        Classes testClasses = classesList.get(classesList.size() - 1);
        assertThat(testClasses.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClasses.getLecturer()).isEqualTo(UPDATED_LECTURER);
        assertThat(testClasses.getMajor()).isEqualTo(UPDATED_MAJOR);
    }

    @Test
    @Transactional
    public void updateNonExistingClasses() throws Exception {
        int databaseSizeBeforeUpdate = classesRepository.findAll().size();

        // Create the Classes

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClassesMockMvc.perform(put("/api/classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classes)))
            .andExpect(status().isCreated());

        // Validate the Classes in the database
        List<Classes> classesList = classesRepository.findAll();
        assertThat(classesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClasses() throws Exception {
        // Initialize the database
        classesRepository.saveAndFlush(classes);
        int databaseSizeBeforeDelete = classesRepository.findAll().size();

        // Get the classes
        restClassesMockMvc.perform(delete("/api/classes/{id}", classes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Classes> classesList = classesRepository.findAll();
        assertThat(classesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
