package com.hnc;

import java.util.ArrayList;

/**
 * Created by samuel on 10/01/17.
 */
public class Sorteio {

    private static Sorteio sorteio;

    private ArrayList<String> listaDePessoas;

    private Sorteio() {
        listaDePessoas = new ArrayList<>();
        
    }

    public String getMensagemRandom() {
        int sorte = (int) (Math.random() * (listaDePessoas.size() - 1));
        return listaDePessoas.get(sorte);
    }

    public void addPessoa(String nome) {
        if (!listaDePessoas.contains(nome)) {
            listaDePessoas.add(nome);
        }
    }

    public String getLista() {
        StringBuffer sb = new StringBuffer();
        sb.append("Pessoas que estão no sorteio:");
        for (String nome : listaDePessoas) {
            sb.append("\n");
            sb.append("@");
            sb.append(nome);
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
