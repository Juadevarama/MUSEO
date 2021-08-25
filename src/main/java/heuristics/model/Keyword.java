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
@Table(name = "keywords")
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class Keyword {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num")
	private Integer id; 
	
    @Column(name = "keyword")
	@NotEmpty
	private String keywordString;
    
}
