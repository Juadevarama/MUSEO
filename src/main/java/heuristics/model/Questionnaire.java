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
@Table(name = "questionnaires")
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class Questionnaire {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne(optional = true) // TODO: Cambiar a false cuando estén los usuarios
	@JoinColumn(name = "administratorID")
	private Administrator administrator; 

	@Column(name = "product")
	private String product;

	@Column(name = "description")
	private String description;

	@Column(name = "closed")
	@NotNull
	private Boolean closed;

	@Column(name = "filled")
	@NotNull
	private Boolean filled;
}
