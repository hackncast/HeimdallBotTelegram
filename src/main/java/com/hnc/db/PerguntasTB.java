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
	private Integer id;

	@Column(name = "DS_PERGUNTA")
	private String dsPergunta;

	@Column(name = "DS_RESPOSTA")
	private String dsResposta;

	@Column(name = "NR_PERCENTUAL")
	private Integer nrPercentual;

	public Integer getId() {
		return id;
	}

	public void setId( Integer id ) {
		this.id = id;
	}

	public String getDsPergunta() {
		return dsPergunta;
	}

	public void setDsPergunta( String dsPergunta ) {
		this.dsPergunta = dsPergunta;
	}

	public String getDsResposta() {
		return dsResposta;
	}

	public void setDsResposta( String dsResposta ) {
		this.dsResposta = dsResposta;
	}

	
	public Integer getNrPercentual() {
		return nrPercentual;
	}
	
	
	public void setNrPercentual( Integer nrPercentual ) {
		this.nrPercentual = nrPercentual;
	}

}
