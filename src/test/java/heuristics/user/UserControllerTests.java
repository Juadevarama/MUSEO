package heuristics.user;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import heuristics.configuration.WebSecurityConfig;
import heuristics.controller.UserController;
import heuristics.model.User;
import heuristics.service.UserServiceImpl;

@WebMvcTest(controllers = UserController.class, 
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
classes = WebSecurityConfigurer.class), 
excludeAutoConfiguration = WebSecurityConfig.class)
public class UserControllerTests {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerTests.class);
    
    private static final int TEST_ADMIN_ID = 10;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private MockMvc mockMvc;

    private User john;
    
    @BeforeEach
    void setup(){

        john = new User();
        john.setId(TEST_ADMIN_ID);
        john.setRole("Administrator");
        john.setUsername("IamJohn");
        john.setPassword("DoeJohn10");
        john.setName("John");
        john.setSurnames("Doe");
        john.setEmail("doeJohn@gmail.com");
        given(this.userService.findUserById(TEST_ADMIN_ID)).willReturn(john);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        given(userService.findUserByUsername(userDetails.getUsername())).willReturn(john);
    }

    // Test registro de usuario (GET)

    @WithMockUser(value = "spring")
    @Test   
    void testInitCreationForm() throws Exception {
        
        String response = mockMvc.perform(get("/register"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("user"))
            .andExpect(view().name("register"))
            .andReturn().getResponse().getContentAsString();

        logger.info("response: " + response);
    }  

    // Test registro de usuario (POST)

    @WithMockUser(value = "spring")
    @Test   
    void testProcessCreationForm() throws Exception {

        String response = mockMvc.perform(post("/register")
            .with(csrf())
            .param("username", "Joe")
            .param("password", "Joe123")
            .param("name", "Joe")
            .param("surnames", "Harrison")
            .param("role", "Administrator")
            .param("email", "harrisonJoe@gmail.com"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/register?success"))
            .andReturn().getResponse().getContentAsString();

        logger.info("response: " + response);
    } 

    // Test Show de usuario

    @WithMockUser(value = "spring")
    @Test   
    void testInitShowUserForm() throws Exception {
        
        String response = mockMvc.perform(get("/showProfile"))
            .andExpect(model().attributeExists("user"))
            .andExpect(status().isOk())
            .andExpect(view().name("showProfile"))
            .andReturn().getResponse().getContentAsString();

        logger.info("response: " + response);
    } 

    // Test de Update Perfil (GET)

    @WithMockUser(value = "spring")
    @Test   
    void testInitUpdateUserForm() throws Exception {
        
        String response = mockMvc.perform(get("/updateProfile"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("user"))
            .andExpect(model().attribute("user", hasProperty("role", is("Administrator"))))
            .andExpect(model().attribute("user", hasProperty("username", is("IamJohn"))))
            .andExpect(model().attribute("user", hasProperty("name", is("John"))))
            .andExpect(model().attribute("user", hasProperty("surnames", is("Doe"))))
            .andExpect(model().attribute("user", hasProperty("email", is("doeJohn@gmail.com"))))
            .andExpect(view().name("updateProfile")).andReturn()
            .getResponse().getContentAsString();

        logger.info("response: " + response);
    } 

    // Test de Update Perfil (POST)

    @WithMockUser(value = "spring")
    @Test   
    void testProcessUpdateUserForm() throws Exception {
        
        String response = mockMvc.perform(post("/updateProfile")
            .with(csrf())
            .param("username", "IamJohn")
            .param("password", "Joe123")
            .param("role", "Administrator")
            .param("name", "John")
            .param("surnames", "Doe")
            .param("email", "doeJohn@gmail.com"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/showProfile?success"))
            .andReturn().getResponse().getContentAsString();

        logger.info("response: " + response);
    } 

    // Test de Login de Usuario 

    @WithMockUser(value = "spring")
    @Test   
    void testInitLoginUserForm() throws Exception {
        
        String response = mockMvc.perform(get("/login"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        logger.info("response: " + response);
    }

    // Test de Logout de Usuario 

    @WithMockUser(value = "spring")
    @Test   
    void testInitLogoutUserForm() throws Exception {
        
        String response = mockMvc.perform(get("/login?logout=true"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        logger.info("response: " + response);
    }
}


