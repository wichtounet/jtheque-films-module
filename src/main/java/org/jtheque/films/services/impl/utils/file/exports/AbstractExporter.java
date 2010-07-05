package org.jtheque.films.services.impl.utils.file.exports;

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
