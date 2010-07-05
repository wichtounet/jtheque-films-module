package org.jtheque.films.view.impl.actions.search;

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
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.controllers.able.ISearchController;

import java.awt.event.ActionEvent;

/**
 * Action to validate the search view.
 *
 * @author Baptiste Wicht
 */
public final class AcValidateSearchView extends JThequeAction {
    /**
     * Construct a new AcValidateSearchView.
     */
    public AcValidateSearchView() {
        super("generic.view.actions.search");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ISearchController searchController = Managers.getManager(IBeansManager.class).getBean("searchController");

        searchController.search(searchController.getView().buildSearcher());
    }
}
