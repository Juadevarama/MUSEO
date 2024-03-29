package heuristics.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

	@Column(name = "finalheuristicID")
	@NotNull
	private Integer finalHeuristicID;

	@Column(name = "questionnaireID")
	@NotNull
	private Integer questionnaireID;

	@Column(name = "selected")
	@NotNull
	private Boolean  selected;

	@Column(name = "automatic")
	@NotNull
	private Boolean automatic;
}
