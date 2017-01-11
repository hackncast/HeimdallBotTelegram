package com.hnc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by samuel on 10/01/17.
 */
public class SorteioTest {

    Sorteio sorteio;


    @Before
    public void init(){
        this.sorteio = Sorteio.getSorteio();
    }


    @Test
    public void getMensagemRandom() {
        sorteio.addPessoa("MANUEL");
        sorteio.addPessoa("JOAO");
        sorteio.addPessoa("CARLO");
        sorteio.addPessoa("MATEU");
        sorteio.addPessoa("MARCO");

        System.out.println(sorteio.getMensagemRandom());
        System.out.println(sorteio.getLista());
    }

}