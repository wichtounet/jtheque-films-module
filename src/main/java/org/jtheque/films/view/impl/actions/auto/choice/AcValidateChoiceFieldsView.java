package org.jtheque.films.view.impl.actions.auto.choice;

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
import org.jtheque.films.controllers.able.IAutoAddController;
import org.jtheque.films.services.impl.utils.EditArguments;
import org.jtheque.films.view.able.IChoiceFieldsView;

import javax.annotation.Resource;

import java.awt.event.ActionEvent;

/**
 * Action to validate the choice fields view.
 *
 * @author Baptiste Wicht
 */
public final class AcValidateChoiceFieldsView extends JThequeAction {
    @Resource
    private IAutoAddController autoAddController;

    @Resource
    private IChoiceFieldsView choiceFieldsView;

    /**
     * Construct a new AcValidateChoiceFieldsView.
     */
    public AcValidateChoiceFieldsView() {
        super("generic.view.actions.validate");

        Managers.getManager(IBeansManager.class).inject(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        EditArguments args = new EditArguments();

        args.setEditActors(choiceFieldsView.isBoxActorsSelected());
        args.setEditDuration(choiceFieldsView.isBoxDurationSelected());
        args.setEditImage(choiceFieldsView.isBoxImageSelected());
        args.setEditKind(choiceFieldsView.isBoxKindSelected());
        args.setEditRealizer(choiceFieldsView.isBoxRealizerSelected());
        args.setEditYear(choiceFieldsView.isBoxYearSelected());
        args.isEditResume(choiceFieldsView.isBoxResumeSelected());

        autoAddController.setFields(args);
        autoAddController.displayView();

        choiceFieldsView.closeDown();
    }
}
