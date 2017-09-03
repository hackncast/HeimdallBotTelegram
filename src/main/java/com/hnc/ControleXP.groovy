package com.hnc

import com.hnc.bd.Usuario
import com.hnc.bd.UsuarioBD

/**
 * Created by samuel on 11/07/17.
 */
class ControleXP {

    UsuarioBD usuarioBD;

    static ControleXP myself;

    private HashMap<Integer, Usuario> usuarioXPs = new HashMap<>();

    private ControleXP(){
        usuarioBD = new UsuarioBD();
        try {
            List<Usuario> lista = usuarioBD.lista();
            for( Usuario u : lista) {
                usuarioXPs.put(u.id, u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Usuario getUsuario(Integer id) {
        Usuario usuarioXP = null;
        if (usuarioXPs.containsKey(id)) {
            usuarioXP = usuarioXPs.get(id);
        }

        return usuarioXP;
    }

    public Usuario add(Integer id, String nome, Long valor, Integer sorteio) {
        Usuario usuarioXP;
        if (usuarioXPs.containsKey(id)) {
            usuarioXP = usuarioXPs.get(id);
            if (valor + usuarioXP.getValorXp() > 0) {
                usuarioXP.setValorXp(valor + usuarioXP.getValorXp());
            } else {
                usuarioXP.setValorXp(0);
            }
            if(sorteio != null){
                usuarioXP.setSorteio(sorteio);
            }
            usuarioBD.alterar(usuarioXP);
        } else {
            usuarioXP = new Usuario();
            usuarioXP.setId(id);
            usuarioXP.setNome(nome);
            usuarioXP.setValorXp(valor);
            if(sorteio != null){
                usuarioXP.setSorteio(sorteio);
            }
            usuarioXPs.put(id, usuarioXP);
            usuarioBD.incluir(usuarioXP);
        }

        return usuarioXP;
    }

    HashMap<Integer, Usuario> getUsuarioXPs() {
        return usuarioXPs
    }

    public List<Usuario> lista(String where){
        return usuarioBD.lista(where);
    }

    static ControleXP getInstance(){

        if(myself == null){
            myself = new ControleXP();
        }

        return myself;
    }
}
