package com.hnc.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "PERGUNTAS")
public class PerguntasTB implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	Integer id;

	@Column(name = "DS_PERGUNTA")
	String dsPergunta;

	@Column(name = "DS_RESPOSTA")
	String dsResposta;

	@Column(name = "NR_PERCENTUAL")
	Integer nrPercentual;

}
