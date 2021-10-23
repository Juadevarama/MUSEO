package heuristics.questionnaire;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

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
import heuristics.model.Answer;
import heuristics.model.DevelopmentPhase;
import heuristics.model.FinalHeuristic;
import heuristics.model.GameAspect;
import heuristics.model.HeuristicQuestionnaire;
import heuristics.model.HeuristicUser;
import heuristics.model.Keyword;
import heuristics.model.NielsenHeuristic;
import heuristics.model.Platform;
import heuristics.model.Purpose;
import heuristics.model.Questionnaire;
import heuristics.model.UsabilityAspect;
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
    private static final int TEST_EVALUATOR_ID = 11;
    private static final int TEST_HEURISTIC__USER_ID = 1000;
    private static final int TEST_HEURISTIC__USER_2_ID = 1001;
    private static final int TEST_FINAL_HEURISTIC_ID = 5000;

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
    private User jane;
    private Questionnaire questionnaireTest;
    private FinalHeuristic finalHeuristicTest;
    
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

        jane = new User();
        jane.setId(TEST_EVALUATOR_ID);
        jane.setUsername("IamJane");
        jane.setPassword("DoeJane20");
        jane.setRole("Evaluator");
        jane.setName("Jane");
        jane.setSurnames("Doe");
        jane.setEmail("doeJane@gmail.com");

        questionnaireTest = new Questionnaire();
        questionnaireTest.setId(TEST_QUESTIONNAIRE_ID);
        questionnaireTest.setProduct("Hades");
        questionnaireTest.setClosed(false);
        given(this.questionnaireService.findQuestionnaireByID(TEST_QUESTIONNAIRE_ID)).willReturn(questionnaireTest);

        HeuristicUser hU = new HeuristicUser();
        hU.setId(TEST_HEURISTIC__USER_ID);
        hU.setUserID(TEST_ADMIN_ID);
        hU.setQuestionnaireID(TEST_QUESTIONNAIRE_ID);
        hU.setOwner(Boolean.TRUE);

        given(this.heuristicUserService.findHeuristicUserByIDs(TEST_ADMIN_ID, TEST_QUESTIONNAIRE_ID)).willReturn(hU);

        List<HeuristicUser> hUList = new ArrayList<HeuristicUser>();
        hUList.add(hU);
        given(this.heuristicUserService.findHeuristicUsersByUserId(TEST_ADMIN_ID)).willReturn(hUList);

        HeuristicUser hU2 = new HeuristicUser();
        hU2.setId(TEST_HEURISTIC__USER_2_ID);
        hU2.setUserID(TEST_EVALUATOR_ID);
        hU2.setQuestionnaireID(TEST_QUESTIONNAIRE_ID);
        hU2.setOwner(Boolean.FALSE);

        given(this.heuristicUserService.findHeuristicUserByIDs(TEST_EVALUATOR_ID, TEST_QUESTIONNAIRE_ID)).willReturn(hU2);

        List<HeuristicUser> hU2List = new ArrayList<HeuristicUser>();
        hU2List.add(hU2);
        given(this.heuristicUserService.findHeuristicUsersByUserId(TEST_EVALUATOR_ID)).willReturn(hU2List);
        
        List<HeuristicUser> hUQList = new ArrayList<HeuristicUser>();
        hUQList.add(hU);
        hUQList.add(hU2);
        given(this.heuristicUserService.findHeuristicUserByquestionnaireID(TEST_QUESTIONNAIRE_ID)).willReturn(hUQList);

        finalHeuristicTest = new FinalHeuristic();
        finalHeuristicTest.setId(TEST_FINAL_HEURISTIC_ID);
        finalHeuristicTest.setHeuristicString("Esta es una heurística de prueba");
        
        given(finalHeuristicService.findFinalHeuristicById(TEST_FINAL_HEURISTIC_ID)).willReturn(finalHeuristicTest);

    }

    // Test de listado de cuestionarios para administradores

	@WithMockUser(value = "spring")
	@Test	
	void TestInitQuestionnaireListFormAdministrator() throws Exception {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        given(userService.findUserByUsername(userDetails.getUsername())).willReturn(john);

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

    /* @WithMockUser(value = "spring")
	@Test	
	void TestProcessCreateQuestionnaireForm() throws Exception {

/*         HeuristicQuestionnaire hQ = new HeuristicQuestionnaire();
        hQ.setId(3000);
        hQ.setQuestionnaireID(questionnaireTest.getId());
        hQ.setFinalHeuristicID(TEST_FINAL_HEURISTIC_ID);
        hQ.setAutomatic(Boolean.TRUE);
        hQ.setSelected(Boolean.FALSE);

        List<HeuristicQuestionnaire> HQList = new ArrayList<HeuristicQuestionnaire>();
        HQList.add(hQ);

        List<FinalHeuristic> FHList = new ArrayList<FinalHeuristic>();
        FHList.add(finalHeuristicService.findFinalHeuristicById(TEST_FINAL_HEURISTIC_ID));

        List<Platform> choosenPlatforms = new ArrayList<Platform>();
        List<Purpose> choosenPurposes = new ArrayList<Purpose>();
        List<DevelopmentPhase> choosenDevelopmentPhases = new ArrayList<DevelopmentPhase>();
        List<GameAspect> choosenGameAspects = new ArrayList<GameAspect>();
        List<Keyword> choosenKeywords = new ArrayList<Keyword>();
        List<NielsenHeuristic> choosenNielsenHeuristics = new ArrayList<NielsenHeuristic>();
        List<UsabilityAspect> choosenUsabilityAspects = new ArrayList<UsabilityAspect>(); 



        given(finalHeuristicService.findFHByQuestionnaire(
            heuristicQuestionnaireService.findHeuristicQuestionnaireByQuestionnaireId(questionnaireTest.getId())))
            .willReturn(FHList);

        String response = mockMvc.perform(post("/createQuestionnaire")
            .with(csrf())
            .param("product", "Hades")
            .param("description", "Good game")
            .param("closed", "FALSE")
            .param("finalHeuristic", finalHeuristicTest.toString())
            .flashAttr("allFinalHeuristic", new ArrayList<FinalHeuristic>())
            .flashAttr("questionnaire", new Questionnaire()))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/questionnaireList?create"))
            .andReturn().getResponse().getContentAsString();

        logger.info("response: " + response);
	}   */

    // Test de actualización de cuestionario para administradores (GET)

    @WithMockUser(value = "spring")
	@Test	
	void TestInitUpdateQuestionnaireFormAdministrator() throws Exception {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        given(userService.findUserByUsername(userDetails.getUsername())).willReturn(john);
		
        String response = mockMvc.perform(get("/updateQuestionnaire")
            .param("questionnaireId", questionnaireTest.getId().toString()))    // Esto es lo único que parece funcionar con los RequestParam
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("user"))
            .andExpect(model().attributeExists("questionnaire"))
			.andExpect(model().attributeExists("fHSelected"))
            .andExpect(model().attributeExists("fHAutomatic"))
            .andExpect(model().attributeExists("fHRemainder")) 
            .andExpect(view().name("updateQuestionnaire"))
            .andReturn().getResponse().getContentAsString();

        logger.info("response: " + response);
	} 

    // Test de actualización de cuestionario para administradores (POST)

/*     @WithMockUser(value = "spring")
    @Test	
    void TestProcessUpdateQuestionnaireFormAdministrator() throws Exception {

        // Parámetros requeridos

        String action = "Save"; // Con esta acción no cerramos el formulario

        String response = mockMvc.perform(post("/updateQuestionnaire", action)
                .param("questionnaire", questionnaireTest.toString()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/questionnaireList?update"))
                .andReturn().getResponse().getContentAsString();

        logger.info("response: " + response);
    }  */

    // Test de Mostrar Cuestionario sin rellenar para administradores

    @WithMockUser(value = "spring")
    @Test 
    void TestInitShowQuestionnaireFormAdministrator() throws Exception {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        given(userService.findUserByUsername(userDetails.getUsername())).willReturn(john);
        
        String response = mockMvc.perform(get("/showQuestionnaire")
            .param("questionnaireId", questionnaireTest.getId().toString()))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("fHSelected"))
            .andExpect(model().attributeExists("fHAutomatic"))
            .andExpect(model().attributeExists("fHRemainder"))
            .andExpect(model().attributeExists("ansForm")) 
            .andExpect(model().attributeExists("questionnaire"))
            .andExpect(view().name("showQuestionnaire"))
            .andReturn().getResponse().getContentAsString();

        logger.info("response: " + response);
    }

   // Test de Exportación a PDF

   @WithMockUser(value = "spring")
   @Test
   void TestInitExportQuestionnaireForm() throws Exception {
       
       String response = mockMvc.perform(get("/exportToPDF")
           .param("questionnaireId", questionnaireTest.getId().toString()))
           .andExpect(status().isOk())
           .andReturn().getResponse().getContentAsString();

       logger.info("response: " + response);
   }

    // Test de gestión de envíos (GET)

    @WithMockUser(value = "spring")
    @Test 
    void TestInitDeliveryManagementForm() throws Exception {

        List<HeuristicUser> hUList = new ArrayList<HeuristicUser>();
        given(heuristicUserService.findHeuristicUserByquestionnaireID(TEST_QUESTIONNAIRE_ID)).willReturn(hUList);

        String response = mockMvc.perform(get("/deliveryManagement")
            .param("questionnaireId", questionnaireTest.getId().toString()))
            .andExpect(status().isOk())
            //.andExpect(model().attributeExists("questionnaire"))
            //.andExpect(model().attributeExists("auxMap"))
            .andExpect(view().name("deliveryManagement"))
            .andReturn().getResponse().getContentAsString();

        logger.info("response: " + response);
    }

    // Test de gestión de envíos (POST)  

/*     @WithMockUser(value = "string") // Da 403
    @Test 
    void TestProcessDeliveryManagementForm() throws Exception {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("A ver:" + userDetails);

        List<User> recipientList = new ArrayList<User>();
        
        String response = mockMvc.perform(post("/deliveryManagement")
            .param("recipient", jane.toString())
            .param("action", "Add")
            .param("recipientList", recipientList.toString())
            .param("questionnaireId", questionnaireTest.getId().toString()))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("questionnaire"))
            .andExpect(view().name("redirect:/deliveryManagement?questionnaireId=" + TEST_QUESTIONNAIRE_ID))
            .andReturn().getResponse().getContentAsString();

        logger.info("response: " + response);
    } */

    // Test de listado de cuestionarios para evaluadores

	@WithMockUser(value = "spring")
	@Test	
	void TestInitQuestionnaireListFormEvaluator() throws Exception {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        given(userService.findUserByUsername(userDetails.getUsername())).willReturn(jane);

		String response = mockMvc.perform(get("/questionnaireList"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("user"))
            .andExpect(model().attributeExists("auxMap"))
            .andExpect(view().name("questionnaireList"))
            .andReturn().getResponse().getContentAsString();

        logger.info("response: " + response);
	}

    // Test de Mostrar Cuestionario sin rellenar para evaluadores

    @WithMockUser(value = "spring")
    @Test 
    void TestInitShowQuestionnaireFormEvaluator() throws Exception {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        given(userService.findUserByUsername(userDetails.getUsername())).willReturn(jane);
        
        String response = mockMvc.perform(get("/showQuestionnaire")
            .param("questionnaireId", questionnaireTest.getId().toString()))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("ansForm")) 
            .andExpect(model().attributeExists("questionnaire"))
            .andExpect(view().name("showQuestionnaire"))
            .andReturn().getResponse().getContentAsString();

        logger.info("response: " + response);
    }

        // Test de actualización de cuestionario para evaluadores (GET)

        @WithMockUser(value = "spring")
        @Test	
        void TestInitUpdateQuestionnaireFormEvaluator() throws Exception {
    
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            given(userService.findUserByUsername(userDetails.getUsername())).willReturn(jane);
            
            String response = mockMvc.perform(get("/updateQuestionnaire")
                .param("questionnaireId", questionnaireTest.getId().toString()))    
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("answerList"))
                .andExpect(model().attributeExists("questionnaire"))
                .andExpect(model().attributeExists("auxMap"))
                .andExpect(view().name("updateQuestionnaire"))
                .andReturn().getResponse().getContentAsString();
    
            logger.info("response: " + response);
        } 

/*     // Test de actualización de cuestionario para evaluadores (POST)

    @WithMockUser(value = "spring")
    @Test	
    void TestProcessUpdateQuestionnaireFormEvaluator() throws Exception {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        given(userService.findUserByUsername(userDetails.getUsername())).willReturn(jane);

        // Parámetros requeridos

        String action = "Save"; // Con esta acción no cerramos el formulario
        List<Answer> answerList = new ArrayList<Answer>();
        List<String> answerAux = new ArrayList<String>();


        String response = mockMvc.perform(post("/updateQuestionnaire")
                .param("questionnaire", questionnaireTest.toString())
                .param("answerList", answerList.toString())
                .param("answerAux", answerAux.toString())
                .param("action", action))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("/questionnaireList?update"))
                .andReturn().getResponse().getContentAsString();

        logger.info("response: " + response);
    }   */

    // Test de Mostrar Cuestionario relleno

    @WithMockUser(value = "spring")
    @Test 
    void TestInitShowFilledQuestionnaireForm() throws Exception {

        given(userService.findUserById(TEST_EVALUATOR_ID)).willReturn(jane);
        
        String response = mockMvc.perform(get("/showFilledQuestionnaire")
            .param("questionnaireId", questionnaireTest.getId().toString())
            .param("userId", jane.getId().toString())
            .param("ansForm", Boolean.TRUE.toString()))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("user"))
            .andExpect(model().attributeExists("auxMap")) 
            .andExpect(model().attributeExists("ansForm"))
            .andExpect(model().attributeExists("questionnaire"))
            .andExpect(view().name("showQuestionnaire"))
            .andReturn().getResponse().getContentAsString();

        logger.info("response: " + response);
    }
}


