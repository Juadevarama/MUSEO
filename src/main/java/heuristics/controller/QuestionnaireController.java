package heuristics.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import heuristics.model.Platform;
import heuristics.model.QuestionnaireAux;
import heuristics.service.PlatformService;
import heuristics.service.QuestionnaireService;

@Controller
public class QuestionnaireController {

    private final QuestionnaireService questionnaireService;
    private final PlatformService platformService;

    @Autowired
    public QuestionnaireController(QuestionnaireService questionnaireService, PlatformService platformService){
        this.questionnaireService = questionnaireService;
        this.platformService = platformService;
    }

    // Create Questionnaire
    @GetMapping("/createQuestionnaire")
    public String initCreateQuestionnaireForm(Model model){
        
        model.addAttribute("questionnaireAux", new QuestionnaireAux());
        model.addAttribute("allPlatforms", platformService.findAllPlatform());

        System.out.println(platformService.findAllPlatform());
        //model.addAttribute("listQuestionnaires", questionnaireService.findAllQuestionnaire());
        return "createQuestionnaire";
    } 

    @PostMapping("/createQuestionnaire")
    public String processCreateQuestionnaireForm(@ModelAttribute("questionnaireAux") QuestionnaireAux questionnaireAux,
    @RequestParam(value = "choosenPlatforms" , required = false) List<Platform> choosenPlatforms, BindingResult bindingResult, Model model){
        
        System.out.println("Estas son las plataformas escogidas " + choosenPlatforms);
        System.out.println(questionnaireAux.getPlatforms());
        model.addAttribute("choosenPlatforms", choosenPlatforms);
        return "testchoosen";
    } 
    
}
