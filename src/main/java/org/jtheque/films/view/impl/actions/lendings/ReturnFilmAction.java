package org.jtheque.films.view.impl.actions.lendings;

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
    private static final long serialVersionUID = 2033212310981426905L;

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