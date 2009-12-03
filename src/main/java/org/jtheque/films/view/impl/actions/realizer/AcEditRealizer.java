package org.jtheque.films.view.impl.actions.realizer;

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
import org.jtheque.films.controllers.able.IRealizerController;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * An action to edit a realizer.
 *
 * @author Baptiste Wicht
 */
public final class AcEditRealizer extends JThequeAction {
    private static final long serialVersionUID = -8874148111701214800L;

    @Resource
    private IRealizerController realizerController;

    /**
     * Construct a new AcEditRealizer.
     */
    public AcEditRealizer() {
        super("generic.view.actions.edit");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        realizerController.manualEdit();
    }
}