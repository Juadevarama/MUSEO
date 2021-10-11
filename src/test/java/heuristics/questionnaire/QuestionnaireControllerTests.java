package heuristics.questionnaire;

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
import heuristics.controller.QuestionnaireController;
import heuristics.model.Questionnaire;
import heuristics.model.User;
import heuristics.service.AnswerService;
import heuristics.service.DevelopmentPhaseService;
import heuristics.service.FinalHeuristicService;
import heuristics.service.GameAspectService;
import heuristics.service.HeuristicQuestionnaireService;
import heuristics.service.HeuristicUserService;
import heuristics.service.KeywordService;
import heuristics.service.NielsenHeuristicService;
import heuristics.service.PlatformService;
import heuristics.service.PurposeService;
import heuristics.service.QuestionnaireService;
import heuristics.service.UsabilityAspectService;
import heuristics.service.UserServiceImpl;

@WebMvcTest(controllers = QuestionnaireController.class, 
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
classes = WebSecurityConfigurer.class), 
excludeAutoConfiguration = WebSecurityConfig.class)
public class QuestionnaireControllerTests {

    private static final Logger logger = LoggerFactory.getLogger(QuestionnaireControllerTests.class);
    
    private static final int TEST_QUESTIONNAIRE_ID = 10;
    private static final int TEST_ADMIN_ID = 10;

    @MockBean
    private QuestionnaireService questionnaireService;

    @MockBean
    private PlatformService platformService;
    
    @MockBean
    private PurposeService purposeService;
    
    @MockBean
    private DevelopmentPhaseService developmentPhaseService;
    
    @MockBean
    private GameAspectService gameAspectService;
    
    @MockBean
    private KeywordService keywordService;
    
    @MockBean
    private NielsenHeuristicService nielsenHeuristicService;
    
    @MockBean
    private UsabilityAspectService usabilityAspectService;
    
    @MockBean
    private FinalHeuristicService finalHeuristicService;
    
    @MockBean
    private HeuristicQuestionnaireService heuristicQuestionnaireService;
    
    @MockBean
    private UserServiceImpl userService;
    
    @MockBean
    private AnswerService answerService;
    
    @MockBean
    private HeuristicUserService heuristicUserService;

    @Autowired
    private MockMvc mockMvc;

    private User john;
    private Questionnaire questionnaireTest;
    
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

        questionnaireTest = new Questionnaire();
        questionnaireTest.setId(TEST_QUESTIONNAIRE_ID);
        questionnaireTest.setProduct("Hades");
        questionnaireTest.setClosed(false);
        given(this.questionnaireService.findQuestionnaireByID(TEST_QUESTIONNAIRE_ID)).willReturn(questionnaireTest);

    }

    // Test de listado de cuestionarios para administradores

	@WithMockUser(value = "spring")
	@Test	
	void TestInitQuestionnaireListFormAdmin() throws Exception {

		String response = mockMvc.perform(get("/questionnaireList"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("user"))
            .andExpect(model().attributeExists("questionnaireList"))
            .andExpect(view().name("questionnaireList"))
            .andReturn().getResponse().getContentAsString();

        logger.info("response: " + response);
	}

    // Test de creación de formulario (GET)

    @WithMockUser(value = "spring")
	@Test	
	void TestInitCreateQuestionnaireForm() throws Exception {
		String response = mockMvc.perform(get("/createQuestionnaire"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("questionnaire"))
            .andExpect(model().attributeExists("allPlatforms"))
            .andExpect(model().attributeExists("allPurposes"))
            .andExpect(model().attributeExists("allDevelopmentPhases"))
            .andExpect(model().attributeExists("allGameAspects"))
            .andExpect(model().attributeExists("allKeywords"))
            .andExpect(model().attributeExists("allNielsenHeuristics"))
            .andExpect(model().attributeExists("allUsabilityAspects"))
            .andExpect(view().name("createQuestionnaire"))
            .andReturn().getResponse().getContentAsString();

        logger.info("response: " + response);
	}

    // Test de creación de formulario (POST)

/*     @WithMockUser(value = "spring")
	@Test	
	void TestProcessCreateQuestionnaireForm() throws Exception {

        String response = mockMvc.perform(post("/createQuestionnaire")
            .with(csrf())
            .param("product", questionnaireTest.getProduct())
            .param("closed", questionnaireTest.getClosed().toString()))
            //.andExpect(model().attributeHasNoErrors("questionnaire"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/questionnaireList?create"))
            .andReturn().getResponse().getContentAsString();

        logger.info("response: " + response);
	} 
 */
    // Test de actualización de cuestionario (GET)

/*     @WithMockUser(value = "spring")
	@Test	
	void TestInitUpdateQuestionnaireForm() throws Exception {
		mockMvc.perform(get("/updateQuestionnaire", TEST_QUESTIONNAIRE_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("questionnaire"))
				.andExpect(model().attributeExists("fHSelected"))
                .andExpect(model().attributeExists("fHAutomatic"))
                .andExpect(model().attributeExists("fHRemainder")) 
                .andExpect(view().name("updateQuestionnaire"));
	} */

    // Test de actualización de cuestionario (POST)

/*     @WithMockUser(value = "spring")
    @Test	
    void TestProcessUpdateQuestionnaireForm() throws Exception {

        // Parámetros requeridos

        String action = "Save";

        HeuristicUser hU = new HeuristicUser();
        hU.setId(1000);
        hU.setUserID(john.getId());
        hU.setQuestionnaireID(questionnaireTest.getId());
        hU.setOwner(Boolean.TRUE);

        mockMvc.perform(post("/updateQuestionnaire", action))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/questionnaireList?update"));
    } */






}


