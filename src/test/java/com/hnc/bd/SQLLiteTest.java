package com.hnc.bd;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by samuel on 18/07/17.
 */
public class SQLLiteTest {

    private UsuarioBD usuarioBD;


    @Before
    public void init() {
        usuarioBD = new UsuarioBD();
    }

    @Test
    public void incluir(){
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("JOAO");
        usuario.setValorXp(11l);
        usuario.setKudo(11l);
//        usuarioBD.incluir(usuario);
    }

}