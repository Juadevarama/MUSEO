package heuristics.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "finalheuristics")
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class FinalHeuristic {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "num")
	private HeuristicRaw heuristicRaw; 

    @Column(name = "heuristic")
	@NotEmpty
	private String heuristicString;

 	@Override
	public String toString(){
		return "FinalHeuristic(heuristicString=" + getHeuristicString() + ")";
	} 
}
