package org.jtheque.films.view.impl.actions.sort;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.view.impl.actions.JThequeSimpleAction;
import org.jtheque.films.view.able.IFilmView;

import java.awt.event.ActionEvent;

/**
 * Action to sort the films.
 *
 * @author Baptiste Wicht
 */
public final class AcSortFilm extends JThequeSimpleAction {
    private final String sortType;

    /**
     * Construct a new AcSortFilm.
     *
     * @param key      The internationalization key.
     * @param sortType The type of sort.
     */
    public AcSortFilm(String key, String sortType) {
        super();

        setTextKey(key);

        this.sortType = sortType;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Managers.getManager(IBeansManager.class).<IFilmView>getBean("filmView").sort(sortType);
    }
}
