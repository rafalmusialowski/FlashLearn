package pl.wsb.flashlearn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import pl.wsb.flashlearn.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FlashLearnApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void unauthenticatedRedirectToLogin() throws Exception {
        mockMvc.perform(get("/any_url"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void unauthenticatedRecursiveRedirectToLogin() throws Exception {
        mockMvc.perform(get("/any_url/recursive"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void successfullRegisterFeedback() throws Exception {
        String testUser = "testUser";
        mockMvc.perform(post("/register")
                        .param("username", testUser)
                        .param("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("message", "Registration completed with success!"));
        userRepository.deleteUserByUsername(testUser);
    }

    @Test
    void redirectAuthenticatedToFlashcardsDashboard() throws Exception {
        mockMvc.perform(get("/flashcards")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user("user").roles("USER")))
                .andExpect(status().isOk());
    }

    @Test
    void redirectLogout() throws Exception {
        mockMvc.perform(post("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?logout"));
    }
}
