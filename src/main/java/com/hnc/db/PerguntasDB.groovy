package com.hnc.db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class PerguntasDB {

	private EntityManager entityManager;

	public PerguntasDB() {
		try {
			EntityManagerFactory factory = Persistence.createEntityManagerFactory "hjpa"
			entityManager = factory.createEntityManager()
		} catch( Exception e ) {
			e.printStackTrace()
		}
	}

	public void criarTabela() throws ClassNotFoundException, IOException, SQLException {
	}

	public void incluir( PerguntasTB perguntasTB ) throws ClassNotFoundException, IOException, SQLException {
		entityManager.transaction.begin()
		entityManager.persist perguntasTB
		entityManager.transaction.commit()
	}

	public void excluir( PerguntasTB perguntasTB ) throws ClassNotFoundException, IOException, SQLException {
		if( perguntasTB == null || perguntasTB.getId() == null ) {
			return;
		}

		entityManager.transaction.begin()
		entityManager.remove perguntasTB
		entityManager.transaction.commit()
	}

	public PerguntasTB busca( PerguntasTB perguntasTB ) throws ClassNotFoundException, IOException, SQLException {
		return entityManager.find( PerguntasTB.class, perguntasTB.getId() )
	}

	public List<PerguntasTB> listar( PerguntasTB perguntasTB ) throws ClassNotFoundException, IOException, SQLException {
		if( perguntasTB == null ) {
			return null
		}
		CriteriaBuilder cb = entityManager.criteriaBuilder
		CriteriaQuery<PerguntasTB> criteria = cb.createQuery( PerguntasTB.class )

		Root<PerguntasTB> pergunta = criteria.from PerguntasTB.class
		
		criteria.orderBy( cb.asc( pergunta.get( "id" ) ) )


		TypedQuery<PerguntasTB> query = entityManager.createQuery criteria
		return query.getResultList()
	}

	public List<PerguntasTB> listar( String sql ) throws ClassNotFoundException, IOException, SQLException {
		Query query = entityManager.createNativeQuery sql, ArrayList.class
		return query.getResultList()
	}
}
