package org.jtheque.films.view.impl.actions.realizer;

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
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.controllers.able.IRealizerController;
import org.jtheque.films.controllers.able.ISearchController;
import org.jtheque.films.utils.Constants;

import java.awt.event.ActionEvent;

/**
 * An action to search realizers.
 *
 * @author Baptiste Wicht
 */
public final class AcSearchRealizer extends JThequeAction {
    /**
     * Construct a new AcSearchRealizer.
     */
    public AcSearchRealizer() {
        super("jtheque.actions.search.realizer");

        setIcon(Managers.getManager(IResourceManager.class).getIcon(Constants.IMAGE_BASE_NAME, "search", ImageType.PNG));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ISearchController searchController = Managers.getManager(IBeansManager.class).getBean("filmSearchController");

        searchController.setResearchController(Managers.getManager(IBeansManager.class).<IRealizerController>getBean("realizerController"));
        searchController.displayView();
    }
}
