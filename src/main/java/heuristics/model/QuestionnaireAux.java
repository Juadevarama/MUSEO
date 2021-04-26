package heuristics.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// @Table(name = "questionnaires") En teoría no debería hacerle falta, ya que tampoco queremos guardarlo en la BBDD
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionnaireAux {

	private List<Platform> platforms;
}
