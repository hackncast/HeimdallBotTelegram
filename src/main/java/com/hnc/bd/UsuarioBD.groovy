package com.hnc.bd

import java.sql.Statement
import java.sql.PreparedStatement
import java.sql.ResultSet

/**
 * Created by samuel on 18/07/17.
 */
class UsuarioBD {

    SQLLite sqlLite;

    public UsuarioBD() {
        criarTabela();
    }

    void criarTabela() {
        sqlLite = new SQLLite();
        try {
            Statement statement = sqlLite.connection.createStatement();
            statement.executeUpdate("create table if not exists USUARIO (id integer, nome string, valor_xp integer, kudo integer, sorteio integer, PRIMARY KEY(id));");
        } finally {
            sqlLite.close();
        }
    }

    void incluir(Usuario usuario) {
        sqlLite = new SQLLite();
        try {
            PreparedStatement statement = sqlLite.connection.prepareStatement("INSERT INTO USUARIO (ID, NOME, VALOR_XP, KUDO,SORTEIO) VALUES (?,?,?,?,?);");
            statement.setInt(1, usuario.id);
            statement.setString(2, usuario.nome);
            statement.setLong(3, usuario.valorXp ? usuario.valorXp : 0l);
            statement.setLong(4, usuario.kudo ? usuario.kudo : 0l);
            statement.setInt(5, usuario.sorteio ? usuario.sorteio : 0);
            statement.executeUpdate();
        } finally {
            sqlLite.close();
        }
    }

    void alterar(Usuario usuario) {
        sqlLite = new SQLLite();
        try {
            PreparedStatement statement = sqlLite.connection.prepareStatement("UPDATE USUARIO set ID = ?, NOME = ?, VALOR_XP = ?, KUDO = ?, SORTEIO = ? where id = ?;");
            statement.setInt(1, usuario.id);
            statement.setString(2, usuario.nome);
            statement.setLong(3, usuario.valorXp ? usuario.valorXp : 0l);
            statement.setLong(4, usuario.kudo ? usuario.kudo : 0l);
            statement.setInt(5, usuario.sorteio ? usuario.sorteio : 0);
            statement.setInt(6, usuario.id);
            statement.executeUpdate();
        } finally {
            sqlLite.close();
        }
    }

    List<Usuario> lista() {
        List<Usuario> lista = new ArrayList<>();
        sqlLite = new SQLLite();
        try {
            PreparedStatement statement = sqlLite.connection.prepareStatement("select ID, NOME, VALOR_XP, KUDO, SORTEIO from USUARIO;");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("ID"));
                u.setNome(rs.getString("NOME"));
                u.setValorXp(rs.getLong("VALOR_XP"));
                u.setKudo(rs.getLong("KUDO"));
                u.setSorteio(rs.getInt("SORTEIO"))
                lista.add(u);
            }
        } finally {
            sqlLite.close();
        }
        return lista;
    }

    List<Usuario> lista(String where) {
        List<Usuario> lista = new ArrayList<>();
        sqlLite = new SQLLite();
        try {
            PreparedStatement statement = sqlLite.connection.prepareStatement("select ID, NOME, VALOR_XP, KUDO, SORTEIO from USUARIO " + where);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("ID"));
                u.setNome(rs.getString("NOME"));
                u.setValorXp(rs.getLong("VALOR_XP"));
                u.setKudo(rs.getLong("KUDO"));
                u.setSorteio(rs.getInt("SORTEIO"))
                lista.add(u);
            }
        } finally {
            sqlLite.close();
        }
        return lista;
    }
}
