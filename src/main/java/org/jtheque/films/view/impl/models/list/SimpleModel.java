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

import javax.swing.DefaultListModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A simple list model.
 *
 * @author Baptiste Wicht
 */
public final class SimpleModel<T> extends DefaultListModel {
    private static final long serialVersionUID = 627034111249354845L;

    private final List<T> objects;

    /**
     * Construct a new SimpleModel.
     */
    public SimpleModel() {
        super();

        objects = new ArrayList<T>(10);
    }

    @Override
    public Object getElementAt(int index) {
        return objects.get(index);
    }

    @Override
    public Object get(int index) {
        return objects.get(index);
    }

    @Override
    public int getSize() {
        return objects.size();
    }

    @Override
    public Object remove(int index) {
        T actor = objects.remove(index);
        fireIntervalRemoved(this, index, index);
        return actor;
    }

    @Override
    public void addElement(Object obj) {
        objects.add((T) obj);
        fireIntervalAdded(this, getSize(), getSize());
    }

    @Override
    public void removeAllElements() {
        objects.clear();
        fireContentsChanged(this, 0, getSize());
    }

    @Override
    public boolean removeElement(Object obj) {
        T t = (T) obj;

        int index = objects.indexOf(t);
        boolean remove = objects.remove(t);
        fireIntervalRemoved(this, index, index);
        return remove;
    }

    /**
     * Return the objects.
     *
     * @return A List containing all the objects of the model.
     */
    public Collection<T> getObjects() {
        return objects;
    }
}