package heuristics.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "heuristicsraw")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeuristicRaw {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "num")
	private Integer id; 

    @Column(name = "heuristicRAW")
	@NotEmpty
	private String heuristicRawString;

    @Column(name = "author")
	@NotEmpty
	private String author;

    @Column(name = "classificationByAuthor")
	private String classificationByAuthor;

    @Column(name = "compiledFrom")
	private String compiledFrom;

    @Column(name = "year")
	@NotEmpty
	private String year;

}
