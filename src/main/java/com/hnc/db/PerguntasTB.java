package com.hnc.db;

import java.io.Serializable;

public class PerguntasTB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String dsPergunta;

	private String dsResposta;

	private Integer nrPercetual;

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

	public Integer getNrPercetual() {
		return nrPercetual;
	}

	public void setNrPercetual( Integer nrPercetual ) {
		this.nrPercetual = nrPercetual;
	}

}
