package org.jtheque.films.view.impl.actions.lendings;

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

import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.primary.controller.able.IChoiceController;

import javax.annotation.Resource;

import java.awt.event.ActionEvent;

/**
 * Action to return a film.
 *
 * @author Baptiste Wicht
 */
public final class ReturnFilmAction extends JThequeAction {
    @Resource
    private IChoiceController choiceController;

    /**
     * Construct a new ReturnFilmAction.
     */
    public ReturnFilmAction() {
        super("jtheque.actions.return");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        choiceController.setAction("return");
        choiceController.setContent(IFilmsService.DATA_TYPE);
        choiceController.displayView();
    }
}
