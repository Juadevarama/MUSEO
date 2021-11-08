package heuristics.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import heuristics.model.Answer;
import heuristics.model.DevelopmentPhase;
import heuristics.model.ExportToPDF;
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
    private final HeuristicUserService heuristicUserService;

    @Autowired
    public QuestionnaireController(QuestionnaireService questionnaireService, PlatformService platformService, 
    PurposeService purposeService, DevelopmentPhaseService developmentPhaseService, GameAspectService gameAspectService,
    KeywordService keywordService, NielsenHeuristicService nielsenHeuristicService, UsabilityAspectService usabilityAspectService,
    HeuristicQuestionnaireService heuristicQuestionnaireService, FinalHeuristicService finalHeuristicService, 
    UserServiceImpl userService, AnswerService answerService, HeuristicUserService heuristicUserService){
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
        this.heuristicUserService = heuristicUserService;
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

        if(user.getRole().equals("Evaluator")){

            List<Questionnaire> questionnaireList = questionnaireService.findQuestionnairesByEvaluator(user);
            List<HeuristicUser> hUList = new ArrayList<>();

            for (Questionnaire q : questionnaireList){
                
                hUList.add(heuristicUserService.findHeuristicUserByIDs(user.getId(), q.getId()));
            }

            Map<HeuristicUser, Questionnaire> auxMap = new HashMap<>();
            for (HeuristicUser hU : hUList){
    
                auxMap.put(hU, questionnaireService.findQuestionnaireByID(hU.getQuestionnaireID()));
            }

            model.addAttribute("auxMap", auxMap);
        }

        return "questionnaireList";
    }

    // Show Questionnaire

    @GetMapping("/showQuestionnaire")
    public String initShowQuestionnaireForm(Model model, 
    @RequestParam(value = "questionnaireId" , required = true) Integer questionnaireId){

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUsername(userDetails.getUsername());
        model.addAttribute("user", user);

        // Creamos un Booleano para diferenciar las vistas de show del formulario y el formulario de respuestas.

        Boolean ansForm = Boolean.FALSE;

        // Sacamos el cuestionario con el que estamos trabajando.

        Questionnaire questionnaire = questionnaireService.findQuestionnaireByID(questionnaireId);

        // Sacamos todos los objetos HeuristicQuestionnaire relacionados al cuestionario.  

        List<HeuristicQuestionnaire> hQList = heuristicQuestionnaireService.findHeuristicQuestionnaireByQuestionnaireId(questionnaireId);

        // Ahora sacamos las FH que el usuario ha dejado guardadas y las automáticas no guardadas

        List<FinalHeuristic> fHSelected = finalHeuristicService.findSelectedFH(hQList);
        List<FinalHeuristic> fHAutomatic = finalHeuristicService.findAutomaticFH(hQList);

        // Por último, ponemos el resto de las FH.

        List<FinalHeuristic> fHRemainder = finalHeuristicService.findRemainderFH(hQList, fHSelected, fHAutomatic);

        model.addAttribute("fHSelected", fHSelected);
        model.addAttribute("fHAutomatic", fHAutomatic);
        model.addAttribute("fHRemainder", fHRemainder);
        
        model.addAttribute("ansForm", ansForm);
        model.addAttribute("questionnaire", questionnaire);
        
        return "showQuestionnaire";
    } 

    // Show Filled Questionnaire

    @GetMapping("/showFilledQuestionnaire")
    public String initShowFilledQuestionnaireForm(Model model, 
    @RequestParam(value = "questionnaireId" , required = true) Integer questionnaireId,
    @RequestParam(value = "userId" , required = true) Integer userId,
    @RequestParam(value = "ansForm" , required = true) Boolean ansForm){

        User user = userService.findUserById(userId);
        model.addAttribute("user", user);

        // Sacamos el cuestionario con el que estamos trabajando.

        Questionnaire questionnaire = questionnaireService.findQuestionnaireByID(questionnaireId);

        // Sacamos todos los objetos HeuristicQuestionnaire relacionados al cuestionario.  

        List<HeuristicQuestionnaire> hQList = heuristicQuestionnaireService.findHeuristicQuestionnaireByQuestionnaireId(questionnaireId);

        // Sacamos la lista de respuestas

        List<Answer> answerList = new ArrayList<>();

        for (HeuristicQuestionnaire hQ : hQList){
            List<Answer> answers = answerService.findAnswerByheuristicQuestionnaireID(hQ.getId());
            for (Answer answer : answers) {
                if(answer.getUserID().equals(user.getId())){
                    answerList.add(answer);
                }
            }
        }

        // Mapeamos las respuestas con sus fH

        Map<FinalHeuristic, Answer> auxMap = new HashMap<>();
        for (Answer ans : answerList){
            
            FinalHeuristic fH = finalHeuristicService.findFinalHeuristicById(heuristicQuestionnaireService.
            findHeuristicQuestionnaireById(ans.getHeuristicQuestionnaireID()).getFinalHeuristicID());

            auxMap.put(fH, ans);
        }

        model.addAttribute("auxMap", auxMap);
        model.addAttribute("ansForm", ansForm);
        model.addAttribute("questionnaire", questionnaire);
        
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
    public String processCreateQuestionnaireForm(@Valid Questionnaire questionnaire,
    @RequestParam(value = "choosenPlatforms" , required = false) List<Platform> choosenPlatforms, 
    @RequestParam(value = "choosenPurposes" , required = false) List<Purpose> choosenPurposes,
    @RequestParam(value = "choosenDevelopmentPhases" , required = false) List<DevelopmentPhase> choosenDevelopmentPhases,
    @RequestParam(value = "choosenGameAspects" , required = false) List<GameAspect> choosenGameAspects,
    @RequestParam(value = "choosenKeywords" , required = false) List<Keyword> choosenKeywords,
    @RequestParam(value = "choosenNielsenHeuristics" , required = false) List<NielsenHeuristic> choosenNielsenHeuristics,
    @RequestParam(value = "choosenUsabilityAspects" , required = false) List<UsabilityAspect> choosenUsabilityAspects, 
    Model model){

        // Guardamos el cuestionario

        questionnaireService.saveQuestionnaire(questionnaire);

        // Creamos el objeto HeuristicUser que vincula el usuario con el cuestionario

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUsername(userDetails.getUsername());
        heuristicUserService.generate(user.getId(), questionnaire.getId());

        // Creamos los Objetos HeuristicQuestionnaire a partir de las listas escogidas

        questionnaireService.generateHeuristics(questionnaire, choosenPlatforms, 
        choosenPurposes, choosenDevelopmentPhases, choosenGameAspects, choosenKeywords, 
        choosenNielsenHeuristics, choosenUsabilityAspects); 

        /* Primero cogemos todos los objetos HeuristicQuestionnaire generados, y luego vamos filtrando 
        las FinalHeuristic de estos, para que no haya repetidas, y las metemos en la lista que usaremos */

        List<FinalHeuristic> finalHeuristicList = finalHeuristicService.findFHByQuestionnaire(
            heuristicQuestionnaireService.findHeuristicQuestionnaireByQuestionnaireId(questionnaire.getId()));

        model.addAttribute("questionnaire", questionnaire);
        model.addAttribute("allFinalHeuristic", finalHeuristicList);
        return "redirect:/questionnaireList?create";
    }
    
    // Update Questionnaire (GET)

    @GetMapping("/updateQuestionnaire")
    public String initUpdateQuestionnaireForm(Model model, 
    @RequestParam(value = "questionnaireId" , required = true) Integer questionnaireId){

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUsername(userDetails.getUsername());
        model.addAttribute("user", user);

        if(user.getRole().equals("Administrator")){

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

        if(user.getRole().equals("Evaluator")){

            // Sacamos el cuestionario con el que estamos trabajando.

            Questionnaire questionnaire = questionnaireService.findQuestionnaireByID(questionnaireId);

            // Sacamos todos los objetos HeuristicQuestionnaire relacionados al cuestionario.  

            List<HeuristicQuestionnaire> hQList = heuristicQuestionnaireService.findHeuristicQuestionnaireByQuestionnaireId(questionnaireId);

            // Sacamos la lista de respuestas

            List<Answer> answerList = new ArrayList<>();

            for (HeuristicQuestionnaire hQ : hQList){
                List<Answer> answers = answerService.findAnswerByheuristicQuestionnaireID(hQ.getId());
                for (Answer answer : answers) {
                    if(answer.getUserID().equals(user.getId())){
                        answerList.add(answer);
                    }
                }
            }

            // Mapeamos las respuestas con sus fH

            Map<FinalHeuristic, Answer> auxMap = new HashMap<>();
            for (Answer ans : answerList){
                
                FinalHeuristic fH = finalHeuristicService.findFinalHeuristicById(heuristicQuestionnaireService.
                findHeuristicQuestionnaireById(ans.getHeuristicQuestionnaireID()).getFinalHeuristicID());

                auxMap.put(fH, ans);
            }

            model.addAttribute("answerList", answerList);
            model.addAttribute("questionnaire", questionnaire);
            model.addAttribute("auxMap", auxMap);

            return "updateQuestionnaire";
        }
        
        return "updateQuestionnaire";
    } 

    // Update Questionnaire (POST)

    @PostMapping("/updateQuestionnaire")
    public String processUpdateQuestionnaireForm(@Valid Questionnaire questionnaire,
    @RequestParam(value = "choosenFH" , required = false) List<FinalHeuristic> choosenFH, // Esto para los administradore
    @RequestParam(value = "answerList" , required = false) List<Answer> answerList, // Esto para los evaluadores
    @RequestParam(value = "answerAux" , required = false) List<String> answerAux, // Esto para los evaluadores
    @RequestParam(value="action", required= true) String action, BindingResult result, Model model){

        // Cogemos el usuario

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUsername(userDetails.getUsername());
        model.addAttribute("user", user);

        if(user.getRole().equals("Administrator")){

            if(choosenFH == null){
                return "redirect:/updateQuestionnaire?questionnaireId=" + questionnaire.getId() + "&question";
            }

            if(action.equals("Complete")){   
                questionnaire.setClosed(true);
            }

            // Actualizamos los objetos HeuristicQuestionnaire

            heuristicQuestionnaireService.updateHQ(choosenFH, questionnaire);
        }

        if(user.getRole().equals("Evaluator")){

            // Actualizamos la lista de respuestas

            Integer i = 0;

            for (Answer answer: answerList) {
                
                answer.setAnsString(answerAux.get(i));
                i++;
            }

            
            if(action.equals("Complete")){

                /*  Primero hay que revisar que todas las respuestas estén respondidas.
                    Sacamos la lista de respuestas. Recorremos la lista y 
                    vemos que están todas respondidas.  */

                if(answerList.stream().anyMatch(a -> a.getAnsString() == null || a.getAnsString().equals(""))){

                    model.addAttribute("questionnaire", questionnaire);

                    return "redirect:/updateQuestionnaire?questionnaireId=" + questionnaire.getId() + "&answer";
                }

                /*  Si esto no se cumple, cogemos el objeto HeuristicUser que vincula
                    al cuestionario y al evaluador y ponemos el campo filled a true.    */

                HeuristicUser hU = heuristicUserService.findHeuristicUserByIDs(user.getId(), questionnaire.getId());
                hU.setFilled(Boolean.TRUE);
            } 
        }

        questionnaireService.saveQuestionnaire(questionnaire);
            
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

        /*  Sacamos la lista de usuarios a los que se les ha enviado el cuestionario.
            Para sacar a los usuarios evaluadores, cogemos la lista de usuarios
            que tienen un heuristicUser con el cuestionario, sin el owner */
        
        List<HeuristicUser> hUList = heuristicUserService.findHeuristicUserByquestionnaireID(questionnaireId);

        // Filtramos la lista para quitar el administrador.

        List<HeuristicUser> filteredList = hUList.stream()
            .filter(hU -> hU.getOwner().equals(Boolean.FALSE))
            .collect(Collectors.toList());

        // Cada hU lo tenemos que mapear con su ratio de cobertura.

        Map<HeuristicUser, User> auxMap = new HashMap<>();

        for (HeuristicUser hU : filteredList) {

            // Sacamos el usuario

            User user = userService.findUserById(hU.getUserID());

            // Vemos si el formulario está respondido, para sacar el Ratio

            if(hU.getFilled().equals(Boolean.TRUE)){

                // Sacamos todas las respuestas de cada usuario

                List<Answer> answerList = answerService.findAnswersByUserID(user.getId());

				// Filtramos las respuestas a las del cuestionario en cuestión

				List<Answer> filteredAnswerList = answerList.stream().
					filter(a -> heuristicQuestionnaireService.findHeuristicQuestionnaireById(
					a.getHeuristicQuestionnaireID()).getQuestionnaireID().equals(questionnaireId)).
					collect(Collectors.toList());

                // Y vamos a sacar el porcentaje de las respuestas que es "Yes"

                Integer yes = 0;
                for (Answer a : filteredAnswerList) {

                    if(a.getAnsString().equals("Yes")){
                        yes++;
                    }
                }

                // Calculamos el ratio de cobertura.

                hU.setCoverageRatio((yes*100/filteredAnswerList.size())); 
            }

            // Y añadimos la pareja usuario/ratio al mapa. 

            auxMap.put(hU, user);
        }

        model.addAttribute("questionnaire", questionnaire);
        model.addAttribute("auxMap", auxMap);
        
        return "deliveryManagement";
    } 

    // Delivery Management (POST)

    @PostMapping("/deliveryManagement")
    public String processDeliveryManagementForm(@RequestParam(value = "evaluator" , required = true) String evaluator, 
    @RequestParam(value="questionnaireId", required=true) Integer questionnaireId, Model model){

        // Añadimos un nuevo usuario a la lista

		// Primero vamos a ver si el username introducido es válido

		List<User> users = userService.findAllUser();
		User user = userService.findUserByUsername(evaluator);

		// Vemos si existe, y si tiene el rol de evaluador.

		if(!(users.contains(user)) || !(user.getRole().equals("Evaluator"))){

			return "redirect:/deliveryManagement?questionnaireId=" + questionnaireId + "&valid";
		}

		// También vemos si ya está en la lista. 

        	/*  Sacamos la lista de usuarios a los que se les ha enviado el cuestionario.
				Para sacar a los usuarios evaluadores, cogemos la lista de usuarios
				que tienen un heuristicUser con el cuestionario, sin el owner */
        
			List<HeuristicUser> hUList = heuristicUserService.findHeuristicUserByquestionnaireID(questionnaireId);

			// Filtramos la lista para quitar el administrador.
	
			List<HeuristicUser> filteredList = hUList.stream()
				.filter(hU -> hU.getOwner().equals(Boolean.FALSE))
				.collect(Collectors.toList());
	
			// Cada hU lo tenemos que mapear con su ratio de cobertura. Creamos una lista de usuarios
	
			List<User> evaluatorList = new ArrayList<User>();

			for (HeuristicUser hU : filteredList) {

				// Sacamos el usuario
	
				User auxUser = userService.findUserById(hU.getUserID());
	
				// Lo metemos en la lista de usuarios
	
				evaluatorList.add(auxUser);
			}

			if(evaluatorList.contains(user)){
				return "redirect:/deliveryManagement?questionnaireId=" + questionnaireId + "&exists";
			}

		// Una vez checkeadas estas dos cosas, lo añadimos.

		/*  Lo primero que hacemos es generar un objeto heuristicUser que vincule
			el usuario con el cuestionario. */

		heuristicUserService.generate(user.getId(), questionnaireId);

		/*  Creamos todos los objetos de Answer, uno por hQ para ese cuestionario.
			Sacamos todos los objetos hQ a partir del cuestionario  */

		List<HeuristicQuestionnaire> hQList = heuristicQuestionnaireService.findHeuristicQuestionnaireByQuestionnaireId(questionnaireId);
		List<HeuristicQuestionnaire> selectedHQ = heuristicQuestionnaireService.filterSelectedHQ(hQList);

		// Ahora, para cada objeto hQ creamos un objeto Answer

		answerService.generate(selectedHQ, user);

		model.addAttribute("questionnaireId", questionnaireId);

		return "redirect:/deliveryManagement?questionnaireId=" + questionnaireId + "&add";
    }
}
