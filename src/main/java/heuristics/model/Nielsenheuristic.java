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
@Table(name = "nielsenheuristics")
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class NielsenHeuristic {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num")
	private Integer id; 

    @Column(name = "nielsenHeuristic")
	@NotEmpty
	private String nielsenHeuristicString;

	@Column(name = "number")
	@NotEmpty
	private Integer numbInteger; //¿Sobra? ¿Está repetida?
    
	
}
