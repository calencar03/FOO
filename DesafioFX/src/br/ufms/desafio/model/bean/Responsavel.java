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
package br.ufms.desafio.model.bean;

import java.util.List;

/**
 *
 * @author Kleber Kruger
 */
public class Responsavel extends Jogador {

    private String cpf;
    private List<Aluno> dependentes;
    private List<Aluno> dependentesAtivos;

    /**
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * @return the dependentes
     */
    public List<Aluno> getDependentes() {
        return dependentes;
    }

    /**
     * @param dependentes the dependentes to set
     */
    public void setDependentes(List<Aluno> dependentes) {
        this.dependentes = dependentes;
    }

    /**
     * @return the dependentesAtivos
     */
    public List<Aluno> getDependentesAtivos() {
        return dependentesAtivos;
    }

    /**
     * @param dependentesAtivos the dependentesAtivos to set
     */
    public void setDependentesAtivos(List<Aluno> dependentesAtivos) {
        this.dependentesAtivos = dependentesAtivos;
    }

}
