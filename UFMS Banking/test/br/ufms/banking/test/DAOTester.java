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
package br.ufms.banking.test;

import br.ufms.kruger.util.persistence.Bean;
import br.ufms.kruger.util.persistence.ReadOnlyDAO;
import br.ufms.kruger.util.persistence.ReadWriteDAO;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author kleberkruger
 * @param <B>
 * @param <T>
 */
public abstract class DAOTester<B extends Bean<T>, T extends Serializable> {

    private final ReadOnlyDAO<B, T> readOnlyDAO;
    private final ReadWriteDAO<B, T> readWriteDAO;
    private String msgError = "";

    public DAOTester(ReadOnlyDAO<B, T> dao) {
        this.readOnlyDAO = dao;
        this.readWriteDAO = (dao instanceof ReadWriteDAO) ? (ReadWriteDAO<B, T>) dao : null;
    }

    public void start() {
        if (readWriteDAO == null) {
            readOnlyRoutine();
        } else {
            readWriteRoutine();
        }
    }

    private void readOnlyRoutine() {
        try {
            Collection<B> list = testGetAll();
            if (list.size() > 0) {
                for (B bean : list) {
                    testGet(bean.getID());
                }
            } else {
                System.out.println("Elements not found.");
            }

        } catch (SQLException ex) {
            System.err.println(msgError + ex.getMessage());
        }
    }

    private void readWriteRoutine() {
        try {
            B bean = testInsert(createBean());
            bean = testGet(bean.getID());
            if (bean != null) {
                updateBean(bean);
                testUpdate(bean);
                bean = testGet(bean.getID());
                testDelete(bean.getID());
            }
            testGetAll();

        } catch (SQLException ex) {
            System.err.println(msgError + ex.getMessage());
        }
    }

    private B testInsert(B bean) throws SQLException {
        print("Testing insert()...");
        msgError = "insert() Error: ";

        // insert dependencies if exists...
        List<Serializable> dependencies = new ArrayList<>();
        insertDependencyList(dependencies);

        readWriteDAO.insert(bean, dependencies.toArray(new Serializable[dependencies.size()]));
        return bean;
    }

    private B testUpdate(B bean) throws SQLException {
        print("Testing update()...");
        msgError = "update() Error: ";
        readWriteDAO.update(bean);
        return bean;
    }

    private void testDelete(T id) throws SQLException {
        print("Testing delete()...");
        msgError = "delete() Error: ";

        readWriteDAO.delete(id);
    }

    private B testGet(T id) throws SQLException {
        print("Testing get()...");
        msgError = "get() Error: ";

        B bean = readOnlyDAO.get(id);
        if (bean != null) {
            printBean(bean);
        } else {
            System.err.println("Element not found.");
        }
        return bean;
    }

    private Collection<B> testGetAll() throws SQLException {
        print("Testing getAll()...");
        msgError = "getAll() Error: ";

        Collection<B> list = readOnlyDAO.getAll();
        list.stream().forEach((b) -> {
            printBean(b);
        });
        return list;
    }

    protected abstract void printBean(B bean);

    protected abstract B createBean();

    protected abstract void updateBean(B bean);

    protected void insertDependencyList(List<Serializable> dependencies) {
        //
    }

    protected void print(String msg) {
        StringBuilder str = new StringBuilder();
        str.append("------------------------------------------------------------\n");
        str.append(msg).append("\n");
        str.append("------------------------------------------------------------\n");
        System.out.print(str);
    }

}
