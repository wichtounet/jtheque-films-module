package org.jtheque.films.view.impl.actions.jtheque;

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
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.utils.Constants;

import javax.annotation.Resource;

import java.awt.event.ActionEvent;

/**
 * Action to print the list.
 *
 * @author Baptiste Wicht
 */
public final class AcPrintList extends JThequeAction {
    private static final long serialVersionUID = 858318520274595532L;

    @Resource
    private IFilmsService filmsService;

    /**
     * Construct a new AcPrintList.
     */
    public AcPrintList() {
        super("generic.view.actions.print");

        setIcon(Managers.getManager(IResourceManager.class).getIcon(Constants.IMAGE_BASE_NAME, "print", ImageType.PNG));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        filmsService.printListOfFilms();
    }
}
