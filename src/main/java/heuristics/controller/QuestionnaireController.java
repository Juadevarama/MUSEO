package heuristics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import heuristics.model.DevelopmentPhase;
import heuristics.model.GameAspect;
import heuristics.model.Keyword;
import heuristics.model.NielsenHeuristic;
import heuristics.model.Platform;
import heuristics.model.Purpose;
import heuristics.model.Questionnaire;
import heuristics.model.UsabilityAspect;
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

@Controller
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
    private final HeuristicPlatformService heuristicPlatformService;

    @Autowired
    public QuestionnaireController(QuestionnaireService questionnaireService, PlatformService platformService, 
    PurposeService purposeService, DevelopmentPhaseService developmentPhaseService, GameAspectService gameAspectService,
    KeywordService keywordService, NielsenHeuristicService nielsenHeuristicService, UsabilityAspectService usabilityAspectService,
    HeuristicQuestionnaireService heuristicQuestionnaireService, HeuristicPlatformService heuristicPlatformService,
    FinalHeuristicService finalHeuristicService){
        this.questionnaireService = questionnaireService;
        this.platformService = platformService;
        this.purposeService = purposeService;
        this.developmentPhaseService = developmentPhaseService;
        this.gameAspectService = gameAspectService;
        this.keywordService = keywordService;
        this.nielsenHeuristicService = nielsenHeuristicService;
        this.usabilityAspectService = usabilityAspectService;
        this.heuristicQuestionnaireService = heuristicQuestionnaireService;
        this.heuristicPlatformService = heuristicPlatformService;
        this.finalHeuristicService = finalHeuristicService;
    }

    // TODO: Cambiar estos dos metodos a generate, no create. El create será cuando se elijan las FinalHeuristic. 

    // Generate Questionnaire
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

        System.out.println("Hola");

        System.out.println(heuristicQuestionnaireService.findHeuristicQuestionnaireByQuestionnaireId(1));  

        return "createQuestionnaire";
    } 

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

        // Creamos los Objetos HeuristicQuestionnaire

        questionnaireService.generateHeuristics(questionnaire,choosenPlatforms, 
        choosenPurposes, choosenDevelopmentPhases, choosenGameAspects, choosenKeywords, 
        choosenNielsenHeuristics, choosenUsabilityAspects); 

        // Ponemos los Boolean 

        System.out.println(questionnaire);

        // Sacamos las finalHeuristics (Aún no se puede porque no se ha guardado el cuestionario)

        System.out.println(heuristicQuestionnaireService.findHeuristicQuestionnaireByQuestionnaireId(questionnaire.getId()));

        // La ID todavía no se ha generado aquí, antes habrá que hacer el guardarlo en la BBDD.

        
        model.addAttribute("choosenPlatforms", choosenPlatforms);
        model.addAttribute("choosenPurposes", choosenPurposes);
        model.addAttribute("choosenDevelopmentPhases", choosenDevelopmentPhases);
        model.addAttribute("choosenGameAspects", choosenGameAspects);
        model.addAttribute("choosenKeywords", choosenKeywords);
        model.addAttribute("choosenNielsenHeuristics", choosenNielsenHeuristics);
        model.addAttribute("choosenUsabilityAspects", choosenUsabilityAspects);
        return "testchoosen";
    } 
    
}
