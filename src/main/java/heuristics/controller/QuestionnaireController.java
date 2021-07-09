package heuristics.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.lowagie.text.DocumentException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.qos.logback.core.net.SyslogOutputStream;
import heuristics.model.Answer;
import heuristics.model.DevelopmentPhase;
import heuristics.model.ExportToPDF;
import heuristics.model.FinalHeuristic;
import heuristics.model.GameAspect;
import heuristics.model.HeuristicQuestionnaire;
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
import heuristics.service.KeywordService;
import heuristics.service.NielsenHeuristicService;
import heuristics.service.PlatformService;
import heuristics.service.PurposeService;
import heuristics.service.QuestionnaireService;
import heuristics.service.UsabilityAspectService;
import heuristics.service.UserServiceImpl;

@Controller
@Transactional(readOnly = false)
public class QuestionnaireController {

    private final QuestionnaireService questionnaireService;
    private final PlatformService platformService;
    private final PurposeService purposeService;
    private final DevelopmentPhaseService developmentPhaseService;
    private final GameAspectService gameAspectService;
    private final KeywordService keywordService;
    private final NielsenHeuristicService nielsenHeuristicService;
    private final UsabilityAspectService usabilityAspectService;

    private final FinalHeuristicService finalHeuristicService;
    private final HeuristicQuestionnaireService heuristicQuestionnaireService;
    private final UserServiceImpl userService;
    private final AnswerService answerService;

    @Autowired
    public QuestionnaireController(QuestionnaireService questionnaireService, PlatformService platformService, 
    PurposeService purposeService, DevelopmentPhaseService developmentPhaseService, GameAspectService gameAspectService,
    KeywordService keywordService, NielsenHeuristicService nielsenHeuristicService, UsabilityAspectService usabilityAspectService,
    HeuristicQuestionnaireService heuristicQuestionnaireService, FinalHeuristicService finalHeuristicService, 
    UserServiceImpl userService, AnswerService answerService){
        this.questionnaireService = questionnaireService;
        this.platformService = platformService;
        this.purposeService = purposeService;
        this.developmentPhaseService = developmentPhaseService;
        this.gameAspectService = gameAspectService;
        this.keywordService = keywordService;
        this.nielsenHeuristicService = nielsenHeuristicService;
        this.usabilityAspectService = usabilityAspectService;
        this.heuristicQuestionnaireService = heuristicQuestionnaireService;
        this.finalHeuristicService = finalHeuristicService;
        this.userService = userService;
        this.answerService = answerService;
    }

    // Questionnaire List 

    @GetMapping("/questionnaireList")
    public String initQuestionnaireListForm(Model model){

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUsername(userDetails.getUsername());
        model.addAttribute("user", user);

        if(user.getRole().equals("Administrator")){
            model.addAttribute("questionnaireList", questionnaireService.findQuestionnairesByUserID(user.getId()));
            return "questionnaireList";
        }

        if(user.getRole().equals("Critic")){
            model.addAttribute("questionnaireList", questionnaireService.findQuestionnairesByCritic(user));
        }

        return "questionnaireList";
    }

        // Show Questionnaire

        @GetMapping("/showQuestionnaire")
        public String initShowQuestionnaireForm(Model model, 
        @RequestParam(value = "questionnaireId" , required = true) Integer questionnaireId){
    
            // Sacamos el cuestionario con el que estamos trabajando.
    
            Questionnaire questionnaire = questionnaireService.findQuestionnaireByID(questionnaireId);
    
            // Sacamos todos los objetos HeuristicQuestionnaire relacionados al cuestionario.  
    
            List<HeuristicQuestionnaire> hQList = heuristicQuestionnaireService.findHeuristicQuestionnaireByQuestionnaireId(questionnaireId);
    
            // Ahora sacamos las FH que el usuario ha dejado guardadas y las automáticas no guardadas
    
            List<FinalHeuristic> fHSelected = finalHeuristicService.findSelectedFH(hQList);
            List<FinalHeuristic> fHAutomatic = finalHeuristicService.findAutomaticFH(hQList);
    
            // Por último, ponemos el resto de las FH.
    
            List<FinalHeuristic> fHRemainder = finalHeuristicService.findRemainderFH(hQList, fHSelected, fHAutomatic);
    
            model.addAttribute("questionnaire", questionnaire);
            model.addAttribute("fHSelected", fHSelected);
            model.addAttribute("fHAutomatic", fHAutomatic);
            model.addAttribute("fHRemainder", fHRemainder);
            
            return "showQuestionnaire";
        } 

    // Create Questionnaire (GET)

    @GetMapping("/createQuestionnaire")
    public String initCreateQuestionnaireForm(Model model){
        
        Questionnaire questionnaire = new Questionnaire();
        model.addAttribute("questionnaire", questionnaire);
        model.addAttribute("allPlatforms", platformService.findAllPlatform());
        model.addAttribute("allPurposes", purposeService.findAllPurpose());
        model.addAttribute("allDevelopmentPhases", developmentPhaseService.findAllDevelopmentphase());
        model.addAttribute("allGameAspects", gameAspectService.findAllGameAspect());
        model.addAttribute("allKeywords", keywordService.findAllKeyword());
        model.addAttribute("allNielsenHeuristics", nielsenHeuristicService.findAllNielsenHeuristic());
        model.addAttribute("allUsabilityAspects", usabilityAspectService.findAllUsabilityAspect());

        return "createQuestionnaire";
    } 

    // Create Questionnaire (POST)

    @PostMapping("/createQuestionnaire")
    public String processCreateQuestionnaireForm(@ModelAttribute("questionnaire") Questionnaire questionnaire,
    @RequestParam(value = "choosenPlatforms" , required = false) List<Platform> choosenPlatforms, 
    @RequestParam(value = "choosenPurposes" , required = false) List<Purpose> choosenPurposes,
    @RequestParam(value = "choosenDevelopmentPhases" , required = false) List<DevelopmentPhase> choosenDevelopmentPhases,
    @RequestParam(value = "choosenGameAspects" , required = false) List<GameAspect> choosenGameAspects,
    @RequestParam(value = "choosenKeywords" , required = false) List<Keyword> choosenKeywords,
    @RequestParam(value = "choosenNielsenHeuristics" , required = false) List<NielsenHeuristic> choosenNielsenHeuristics,
    @RequestParam(value = "choosenUsabilityAspects" , required = false) List<UsabilityAspect> choosenUsabilityAspects, 
    BindingResult result, Model model){

        // Guardamos el cuestionario

        questionnaireService.saveQuestionnaire(questionnaire);

        // Creamos los Objetos HeuristicQuestionnaire a partir de las listas escogidas

        questionnaireService.generateHeuristics(questionnaire, choosenPlatforms, 
        choosenPurposes, choosenDevelopmentPhases, choosenGameAspects, choosenKeywords, 
        choosenNielsenHeuristics, choosenUsabilityAspects); 

        /* Primero cogemos todos los objetos HeuristicQuestionnaire generados, y luego vamos filtrando 
        las FinalHeuristic de estos, para que no haya repetidas, y las metemos en la lista que usaremos */

        List<FinalHeuristic> finalHeuristicList = finalHeuristicService.findFHByCuestionnaire(
            heuristicQuestionnaireService.findHeuristicQuestionnaireByQuestionnaireId(questionnaire.getId()));

        model.addAttribute("questionnaire", questionnaire);
        model.addAttribute("allFinalHeuristic", finalHeuristicList);
        return "redirect:/questionnaireList?create";
    }
    
    // Update Questionnaire (GET)

    @GetMapping("/updateQuestionnaire")
    public String initUpdateQuestionnaireForm(Model model, 
    @RequestParam(value = "questionnaireId" , required = true) Integer questionnaireId){

        // Sacamos el cuestionario con el que estamos trabajando.

        Questionnaire questionnaire = questionnaireService.findQuestionnaireByID(questionnaireId);

        // Sacamos todos los objetos HeuristicQuestionnaire relacionados al cuestionario.  

        List<HeuristicQuestionnaire> hQList = heuristicQuestionnaireService.findHeuristicQuestionnaireByQuestionnaireId(questionnaireId);

        // Ahora sacamos las FH que el usuario ha dejado guardadas y las automáticas no guardadas

        List<FinalHeuristic> fHSelected = finalHeuristicService.findSelectedFH(hQList);
        List<FinalHeuristic> fHAutomatic = finalHeuristicService.findAutomaticFH(hQList);

        // Por último, ponemos el resto de las FH.

        List<FinalHeuristic> fHRemainder = finalHeuristicService.findRemainderFH(hQList, fHSelected, fHAutomatic);

        model.addAttribute("questionnaire", questionnaire);
        model.addAttribute("fHSelected", fHSelected);
        model.addAttribute("fHAutomatic", fHAutomatic);
        model.addAttribute("fHRemainder", fHRemainder);
        
        return "updateQuestionnaire";
    } 

    // Update Questionnaire (POST)

    @PostMapping("/updateQuestionnaire")
    public String processUpdateQuestionnaireForm(@Valid Questionnaire questionnaire,
    @RequestParam(value = "choosenFH" , required = false) List<FinalHeuristic> choosenFH, 
    @RequestParam(value="action", required=true) String action, BindingResult result, Model model){

        // Actualizamos el nombre y la descripción

        if(action.equals("Complete")){
            questionnaire.setClosed(true);
        }

        questionnaireService.saveQuestionnaire(questionnaire);

        // Actualizamos los objetos HeuristicQuestionnaire

        heuristicQuestionnaireService.updateHQ(choosenFH, questionnaire);
            
        return "redirect:/questionnaireList?update";
    }

    // Export to PDF (GET)
    
    @GetMapping("/exportToPDF")
    public void initExportQuestionnaireForm(Model model, HttpServletResponse response, 
    @RequestParam(value = "questionnaireId" , required = true) Integer questionnaireId) throws DocumentException, IOException{

        // Decimos que es un documento PDF

        response.setContentType("application/pdf");

        // Establecemos el Header
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=questionnaire.pdf";
        
        response.setHeader(headerKey, headerValue);

        // Cogemos la lista de Heurísticas seleccionadas del cuestionario

        List<HeuristicQuestionnaire> hQList = heuristicQuestionnaireService.findHeuristicQuestionnaireByQuestionnaireId(questionnaireId);

        List<FinalHeuristic> fHList = finalHeuristicService.findSelectedFH(hQList);

        ExportToPDF exporter = new ExportToPDF(fHList);
        exporter.export(response);
    } 

    // Delivery Management (GET)

    @GetMapping("/deliveryManagement")
    public String initDeliveryManagementForm(Model model, 
    @RequestParam(value = "questionnaireId" , required = true) Integer questionnaireId){

        // Sacamos el cuestionario con el que estamos trabajando.

        Questionnaire questionnaire = questionnaireService.findQuestionnaireByID(questionnaireId);

        // Sacamos la lista de usuarios a los que se les ha enviado el cuestionario

        // Del cuestionario sacamos todas las HeuristicsQuestionnaires

        List<HeuristicQuestionnaire> hQList = heuristicQuestionnaireService.findHeuristicQuestionnaireByQuestionnaireId(questionnaireId);
        List<HeuristicQuestionnaire> selectedHQ = heuristicQuestionnaireService.filterSelectedHQ(hQList);        

        // De cada heuristicQuestionnaires sacamos todas las Answers

        // ¿Cojo todas las answer que tengan 1 de los hQ de la lista, y saco el user mientras no esté?
        
        List<User> recipientList = new ArrayList<>();

        for (HeuristicQuestionnaire hQ : selectedHQ) {
            
            List<Answer> answerList = answerService.findAllAnswer().stream().
            filter(a -> a.getHeuristicQuestionnaireID().equals(hQ.getId())).collect(Collectors.toList());

            for (Answer ans : answerList) {
                if(!(recipientList.contains(userService.findUserById(ans.getUserID())))){
                    recipientList.add(userService.findUserById(ans.getUserID()));
                }
            }
        }

        model.addAttribute("questionnaire", questionnaire);
        model.addAttribute("recipientList", recipientList);
        
        return "deliveryManagement";
    } 

    // Delivery Management (POST)

    @PostMapping("/deliveryManagement")
    public String processDeliveryManagementForm(@RequestParam(value = "recipient" , required = true) String recipient, 
    @RequestParam(value="action", required=true) String action, 
    @RequestParam(value="questionnaireId", required=true) Integer questionnaireId, Model model){

        // Actualizamos los usuarios activos para el ratio de cobertura

        if(action.equals("Save")){
            
        }

        // Añadimos un nuevo usuario a la lista

        if(action.equals("Add")){

            // Primero vamos a ver si el username introducido es válido
            List<User> users = userService.findAllUser();
            if(!(users.contains(userService.findUserByUsername(recipient)))){
   
                return "redirect:/deliveryManagement?questionnaireId=" + questionnaireId;
            }

            User user = userService.findUserByUsername(recipient);

            // Aquí tenemos que crear todos los objetos de Answer, uno por hQ para ese cuestionario
            // Sacamos todos los objetos hQ a partir del cuestionario

            List<HeuristicQuestionnaire> hQList = heuristicQuestionnaireService.findHeuristicQuestionnaireByQuestionnaireId(questionnaireId);
            List<HeuristicQuestionnaire> selectedHQ = heuristicQuestionnaireService.filterSelectedHQ(hQList);

            // Ahora, para cada objeto hQ creamos un objeto Answer

            answerService.generate(selectedHQ, user);

            model.addAttribute("questionnaireId", questionnaireId);
            return "redirect:/deliveryManagement?questionnaireId=" + questionnaireId;
        }

        return "redirect:/deliveryManagement?add";
    }
}
