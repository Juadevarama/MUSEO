package heuristics.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "heuristicsquestionnaires")
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class HeuristicQuestionnaire {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "finalheuristicID")
	private FinalHeuristic finalHeuristic;

	@ManyToOne(optional = false)
	@JoinColumn(name = "questionnaireID")
	private Questionnaire questionnaire;

	@NotNull
	private Boolean selected;

	@NotNull
	private Boolean automatic;
}
