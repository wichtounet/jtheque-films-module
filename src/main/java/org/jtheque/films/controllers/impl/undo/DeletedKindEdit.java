package org.jtheque.films.controllers.impl.undo;

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
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.primary.od.able.Kind;
import org.jtheque.primary.services.able.IKindsService;

import javax.annotation.Resource;
import javax.swing.undo.AbstractUndoableEdit;

/**
 * An edit for a deletion of kind.
 *
 * @author Baptiste Wicht
 */
public final class DeletedKindEdit extends AbstractUndoableEdit {
    private final Kind kind;

    @Resource
    private IKindsService kindsService;

    /**
     * Construct a new DeletedKindEdit.
     *
     * @param kind The deleted kind.
     */
    public DeletedKindEdit(Kind kind) {
        super();

        Managers.getManager(IBeansManager.class).inject(this);

        this.kind = kind;
    }

    @Override
    public void undo() {
        super.undo();

        kindsService.create(kind);
    }

    @Override
    public void redo() {
        super.redo();

        kindsService.delete(kind);
    }

    @Override
    public String getPresentationName() {
        return Managers.getManager(ILanguageManager.class).getMessage("undo.edits.delete");
    }
}
