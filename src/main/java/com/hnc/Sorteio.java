package com.hnc;

import com.hnc.bd.*;

import java.util.List;

/**
 * Created by samuel on 10/01/17.
 */
public class Sorteio {

    private static Sorteio sorteio;

    private ControleXP controleXP;

    private Sorteio() {
        controleXP = ControleXP.getInstance();
    }

    public String getMensagemRandom() {
        List<Usuario> usuarios = controleXP.lista("where sorteio = 1");
        int sorte = (int) (Math.random() * (usuarios.size() - 1));
        return usuarios.get(sorte).getNome();
    }

    public void addPessoa(Integer id, String nome) {
        controleXP.add(id, nome, 0l, 1);
    }

    public String getLista() {
        StringBuffer sb = new StringBuffer();
        sb.append("Pessoas que estão no sorteio:");
        List<Usuario> usuarios = controleXP.lista("where sorteio = 1");

        for (Usuario usuario : usuarios) {
            sb.append("\n");
            sb.append("@");
            sb.append(usuario.getNome());
        }
        return sb.toString();
    }

    public static Sorteio getSorteio() {
        if (sorteio == null) {
            sorteio = new Sorteio();
        }
        return sorteio;
    }

}
