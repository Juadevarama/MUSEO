package heuristics.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "administrators")
@Data
public class Administrator extends User {



}
