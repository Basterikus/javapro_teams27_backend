package org.javaproteam27.socialnetwork.controller;

import org.javaproteam27.socialnetwork.model.dto.request.LoginRq;
import org.javaproteam27.socialnetwork.model.dto.response.PersonRs;
import org.javaproteam27.socialnetwork.model.dto.response.ResponseRs;
import org.javaproteam27.socialnetwork.service.LoginService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
//@TestPropertySource("/application-test.yml")
@WithUserDetails("test@mail.ru")
@ActiveProfiles("test")
@Transactional
@Sql(value = {"classpath:sql/person/insert-person.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class PostsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostsController postsController;

    @Autowired
    private LoginService loginService;

    private final String postUrl = "/api/v1/post/1";

    @Test
    public void deletePost() throws Exception {

        this.mockMvc.perform(delete(postUrl))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void updatePost() {
    }

    @Test
    void getPost() throws Exception {

        this.mockMvc.perform(get(postUrl))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void findPost() {
    }
}