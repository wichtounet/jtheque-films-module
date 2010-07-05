package org.jtheque.films.controllers.impl;

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
