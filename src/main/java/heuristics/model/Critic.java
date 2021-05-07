package heuristics.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "critics")
@Data
public class Critic extends User {



}
