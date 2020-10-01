package tech.interview.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tech.interview.model.UserLDAPModel;
import tech.interview.service.UserLDAPService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserLDAPController.class)
class UserLDAPControllerMockIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserLDAPService userLDAPService;

    @Test
    public void findOne() throws Exception {
        // Mock User data
        UserLDAPModel user = new UserLDAPModel();
        user.setUid("user");
        user.setSn("foo");
        user.setCn("User Foo");

        when(userLDAPService.ldapUserFindOne(anyString())).thenReturn(user);

        // Create a Mock GET HTTP request to verify the UserLDAPController Response.
        mvc.perform(MockMvcRequestBuilders.get("/users/any"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uid").value("user"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sn").value("foo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cn").value("User Foo"));
    }

    @Test
    public void createUser() throws Exception {
        // Mock User data
        UserLDAPModel user = new UserLDAPModel();
        user.setUid("user");
        user.setSn("foo");
        user.setCn("User Foo");

        when(userLDAPService.create(any(UserLDAPModel.class))).thenReturn(user);

        // Create a Mock POST HTTP request to verify the UserLDAPController Response.
        mvc.perform(MockMvcRequestBuilders.post("/users")
                .content(new ObjectMapper().writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uid").value("user"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sn").value("foo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cn").value("User Foo"));
    }
}