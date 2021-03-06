/*
 * Copyright (C) 2017 Kleber Kruger
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
package br.ufms.banking.model.domain;

import br.ufms.banking.model.enumerate.TipoTransacao;

/**
 *
 * @author Kleber Kruger
 */
public class Deposito extends Transacao {

    private static final long serialVersionUID = 1L;

    private final String favorecido;
    private final String depositante;

    /**
     *
     * @param favorecido
     * @param valor
     * @param depositante
     */
    protected Deposito(String favorecido, double valor, String depositante) {
        super(valor, null);
        this.favorecido = favorecido;
        this.depositante = depositante;
    }

    /**
     * @return the favorecido
     */
    public final String getFavorecido() {
        return favorecido;
    }

    /**
     * @return the depositante
     */
    public final String getDepositante() {
        return depositante;
    }

    @Override
    public final TipoTransacao getTipoTransacao() {
        return TipoTransacao.DEPOSITO;
    }

}
