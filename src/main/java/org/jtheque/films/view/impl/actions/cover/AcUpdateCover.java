package org.jtheque.films.view.impl.actions.cover;

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
import org.jtheque.films.services.able.ICoverService;
import org.jtheque.films.view.able.ICoverView;

import java.awt.event.ActionEvent;

/**
 * An action to validate the saga view.
 *
 * @author Baptiste Wicht
 */
public final class AcUpdateCover extends JThequeAction {
    /**
     * Construct a AcValidateSagaView.
     */
    public AcUpdateCover() {
        super("cover.view.actions.update");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ICoverService coverService = Managers.getManager(IBeansManager.class).getBean("coverService");
        ICoverView coverView = Managers.getManager(IBeansManager.class).getBean("coverView");

        coverView.display(coverService.getReportImage(coverView.getSelectedFilm(), coverView.getSelectedFormat()));
    }
}
