package heuristics.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "heuristicskeywords")
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class HeuristicKeyword {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "num")
	private Integer id;
	
	@Column(name = "heuristicID")
	@NotEmpty
	private Integer finalHeuristicID;

	@Column(name = "keywordID")
	@NotEmpty
	private Integer keywordID;
}
