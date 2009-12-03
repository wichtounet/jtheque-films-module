package org.jtheque.films.services.impl.utils.file.exports;

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

import org.jtheque.primary.od.able.Data;

import java.util.Collection;

/**
 * An abstract exporter.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractExporter<T extends Data> implements Exporter<T> {
    private Collection<T> datas;

    @Override
    public void setDatas(Collection<T> datas) {
        this.datas = datas;
    }

    /**
     * Return all the datas of the exporter.
     *
     * @return A Collection containing all the datas of the exporter.
     */
    protected final Collection<T> getDatas() {
        return datas;
    }
}