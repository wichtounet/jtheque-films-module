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

import org.jtheque.films.view.impl.models.able.IFilmographyModel;

/**
 * A model for the filmography view.
 *
 * @author Baptiste Wicht
 */
public final class FilmographyModel implements IFilmographyModel {
    private Object filmo;
    private boolean builded;

    @Override
    public void setFilmo(Object filmo) {
        this.filmo = filmo;
    }

    @Override
    public Object getFilmo() {
        return filmo;
    }

    @Override
    public void setBuilded() {
        builded = true;
    }

    @Override
    public boolean isBuilded() {
        return builded;
    }
}
