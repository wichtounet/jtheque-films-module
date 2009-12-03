package org.jtheque.films.controllers.able;

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

import org.jtheque.films.view.able.IRealizerView;
import org.jtheque.films.view.impl.models.able.IRealizersModel;
import org.jtheque.primary.controller.able.IPrincipalController;
import org.jtheque.primary.od.able.Person;

import javax.swing.event.TreeSelectionListener;

/**
 * A realizer controller specification.
 *
 * @author Baptiste Wicht
 */
public interface IRealizerController extends IPrincipalController<Person>, TreeSelectionListener {
    /**
     * Save the current realizer.
     */
    void save();

    /**
     * Edit the current realizer.
     */
    void manualEdit();

    /**
     * Create new realizer.
     */
    void createRealizer();

    /**
     * Delete the current realizer.
     */
    void deleteCurrentRealizer();

    /**
     * Cancel the current state.
     */
    void cancel();

    @Override
    IRealizersModel getViewModel();

    @Override
    IRealizerView getView();
}
