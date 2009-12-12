package org.jtheque.films.view.impl.actions.auto.choice;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
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