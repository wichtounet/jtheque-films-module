package org.jtheque.films.view.impl.models;

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

import org.jtheque.films.view.impl.models.able.IAutoImportFilm;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A model for the auto import view.
 *
 * @author Baptiste Wicht
 */
public final class AutoImportModel implements IAutoImportFilm {
    /* Variables */
    private Collection<String> titles;

    @Override
    public Collection<String> getTitles() {
        if (titles == null) {
            titles = new ArrayList<String>(8);
        }

        return titles;
    }

    @Override
    public void setTitles(Collection<String> titles) {
        this.titles = new ArrayList<String>(titles);
    }
}
