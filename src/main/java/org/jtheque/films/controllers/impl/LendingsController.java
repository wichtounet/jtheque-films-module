package org.jtheque.films.controllers.impl;

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
import org.jtheque.core.managers.undo.IUndoRedoManager;
import org.jtheque.core.managers.view.able.IView;
import org.jtheque.core.managers.view.able.controller.AbstractController;
import org.jtheque.films.controllers.able.ILendingsController;
import org.jtheque.films.view.able.ILendingsView;
import org.jtheque.primary.controller.impl.undo.GenericDataDeletedEdit;
import org.jtheque.primary.od.able.Lending;
import org.jtheque.primary.services.able.ILendingsService;

import javax.annotation.Resource;

/**
 * A lendings controller implementation.
 *
 * @author Baptiste Wicht
 */
public final class LendingsController extends AbstractController implements ILendingsController {
    @Resource
    private ILendingsService lendingsService;

    @Resource
    private ILendingsView lendingsView;

    @Override
    public IView getView() {
        return lendingsView;
    }

    @Override
    public void deleteLending(int id) {
        Lending lending = lendingsService.getLending(id);

        boolean deleted = lendingsService.delete(lending);

        if (deleted) {
            Managers.getManager(IUndoRedoManager.class).addEdit(new GenericDataDeletedEdit<Lending>("lendingsService", lending));
        }
    }
}