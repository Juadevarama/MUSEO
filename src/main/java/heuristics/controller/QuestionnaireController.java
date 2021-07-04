package heuristics.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

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

import heuristics.model.DevelopmentPhase;
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
import heuristics.service.DevelopmentPhaseService;
import heuristics.service.FinalHeuristicService;
import heuristics.service.GameAspectService;
import heuristics.service.HeuristicPlatformService;
import heuristics.service.HeuristicQuestionnaireService;
import heuristics.service.KeywordService;
import heuristics.service.NielsenHeuristicService;
import heuristics.service.PlatformService;
import heuristics.service.PurposeService;
import heuristics.service.QuestionnaireService;
import heuristics.service.UsabilityAspectService;
import heuristics.service.UserService;
import heuristics.service.UserServiceImpl;
import javassist.expr.NewArray;

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

    @Autowired
    public QuestionnaireController(QuestionnaireService questionnaireService, PlatformService platformService, 
    PurposeService purposeService, DevelopmentPhaseService developmentPhaseService, GameAspectService gameAspectService,
    KeywordService keywordService, NielsenHeuristicService nielsenHeuristicService, UsabilityAspectService usabilityAspectService,
    HeuristicQuestionnaireService heuristicQuestionnaireService, FinalHeuristicService finalHeuristicService, UserServiceImpl userService){
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
    }

    // Questionnaire List 

    @GetMapping("/questionnaireList")
    public String initQuestionnaireListForm(Model model){

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUsername(userDetails.getUsername());

        model.addAttribute("questionnaireList", questionnaireService.findQuestionnairesByUserID(user.getId()));

        return "questionnaireList";
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
    BindingResult result, Model model){

        // Actualizamos el nombre y la descripción

        questionnaireService.saveQuestionnaire(questionnaire);

        // Actualizamos los objetos HeuristicQuestionnaire

        heuristicQuestionnaireService.updateHQ(choosenFH, questionnaire);
            
        return "redirect:/questionnaireList?update";
    }
    
}
