package org.jtheque.films.view.impl.models.list;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
