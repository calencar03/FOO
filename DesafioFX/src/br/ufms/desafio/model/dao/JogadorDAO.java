/*
 * Copyright (C) 2016 kleberkruger
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.ufms.desafio.model.dao;

import br.ufms.desafio.model.bean.Jogador;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author kleberkruger
 * @param <B>
 */
public abstract class JogadorDAO<B extends Jogador> extends UsuarioDAO<B> {

    public JogadorDAO(Class<B> clazz) {
        super(clazz);
    }

    private void insertJogador(Connection conn, B bean) throws SQLException {
        final String sql = "INSERT INTO desafio.professor (codigo, data_nascimento, deficiencias) "
                + "VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, bean.getCodigo());
            ps.setDate(2, Date.valueOf(bean.getNascimento()));
            ps.setArray(3, conn.createArrayOf("VARCHAR", bean.getDeficiencias().toArray()));
            ps.executeUpdate();
        }
    }

    private void updateJogador(Connection conn, B bean) throws SQLException {
        final String sql = "UPDATE desafio.escola SET (data_nascimento = ?, deficiencias = ?) "
                + "WHERE codigo = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(bean.getNascimento()));
            ps.setArray(2, conn.createArrayOf("VARCHAR", bean.getDeficiencias().toArray()));
            ps.setLong(3, bean.getCodigo());
            ps.executeUpdate();
        }
    }

    protected void saveJogador(Connection conn, B bean) throws SQLException {
        if (bean.getCodigo() == null) {
            insertJogador(conn, bean);
        } else {
            updateJogador(conn, bean);
        }
    }

    @Override
    protected void insert(Connection conn, B bean, Serializable... dependencies) throws SQLException {
        super.insert(conn, bean, dependencies);
        insertJogador(conn, bean);
    }

    @Override
    protected void update(Connection conn, B bean) throws SQLException {
        super.update(conn, bean);
        updateJogador(conn, bean);
    }

    @Override
    protected B populateBean(B jogador, Connection conn, ResultSet rs) throws SQLException {
        super.populateBean(jogador, conn, rs);
        jogador.setNascimento(rs.getDate("data_nascimento").toLocalDate());
//        jogador.setDeficiencias(rs.getString("deficiencias").split(","));

        return jogador;
    }

}
