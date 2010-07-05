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
import org.jtheque.primary.od.able.Language;
import org.jtheque.primary.services.able.ILanguagesService;

import javax.annotation.Resource;
import javax.swing.undo.AbstractUndoableEdit;

/**
 * An edit for a deletion of language.
 *
 * @author Baptiste Wicht
 */
public final class DeletedLanguageEdit extends AbstractUndoableEdit {
    private final Language language;

    @Resource
    private ILanguagesService languagesService;

    /**
     * Construct a new DeletedLanguageEdit.
     *
     * @param lending The deleted language.
     */
    public DeletedLanguageEdit(Language lending) {
        super();

        Managers.getManager(IBeansManager.class).inject(this);

        language = lending;
    }

    @Override
    public void undo() {
        super.undo();

        languagesService.create(language);
    }

    @Override
    public void redo() {
        super.redo();

        languagesService.delete(language);
    }

    @Override
    public String getPresentationName() {
        return Managers.getManager(ILanguageManager.class).getMessage("undo.edits.delete");
    }
}
