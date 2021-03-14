package heuristics.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "heuristicsplatforms")
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class HeuristicPlatform {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "num")
	private Integer heuristicRawId;

	@OneToOne
	@JoinColumn(name = "finalheuristics_id")
	private FinalHeuristic finalHeuristic;

	@OneToOne
	@JoinColumn(name = "platforms_num")
	private Platform platform;
}