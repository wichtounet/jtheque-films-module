package org.jtheque.films.view.impl.models.list;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.jtheque.core.managers.persistence.able.DataContainer;
import org.jtheque.core.managers.persistence.able.DataListener;
import org.jtheque.core.managers.persistence.able.Entity;

import javax.swing.DefaultListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A list model for actors.
 *
 * @author Baptiste Wicht
 */
public final class DataCachedContainerListModel<T extends Entity> extends DefaultListModel implements DataListener {
    private static final long serialVersionUID = 627034111249354845L;

    private List<T> datas;

    private final DataContainer<T> dao;

    /**
     * Construct a new ActorsListModel.
     *
     * @param dao The dao to manage the objects from.
     */
    public DataCachedContainerListModel(DataContainer<T> dao) {
        super();

        this.dao = dao;

        dao.addDataListener(this);
        reload();
    }

    @Override
    public Object getElementAt(int index) {
        return datas.get(index);
    }

    @Override
    public Object get(int index) {
        return datas.get(index);
    }

    @Override
    public int getSize() {
        return datas.size();
    }

    @Override
    public Object remove(int i) {
        T data = datas.remove(i);
        fireIntervalRemoved(this, i, i);
        return data;
    }

    @Override
    public void addElement(Object obj) {
        datas.add((T) obj);
        fireIntervalAdded(this, getSize(), getSize());
    }

    @Override
    public void removeAllElements() {
        datas.clear();
        fireContentsChanged(this, 0, getSize());
    }

    @Override
    public boolean removeElement(Object obj) {
        T t = (T) obj;

        int index = datas.indexOf(t);
        if (index > -1) {
            boolean remove = datas.remove(t);
            fireIntervalRemoved(this, index, index);
            return remove;
        }

        return false;
    }

    @Override
    public void dataChanged() {
        reload();
    }

    /**
     * Reload the model.
     */
    public void reload() {
        datas = new ArrayList<T>(dao.getDatas());
        fireContentsChanged(this, 0, getSize());
    }
}