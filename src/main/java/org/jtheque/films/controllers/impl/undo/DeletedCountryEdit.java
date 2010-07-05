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
import org.jtheque.primary.od.able.Country;
import org.jtheque.primary.services.able.ICountriesService;

import javax.annotation.Resource;
import javax.swing.undo.AbstractUndoableEdit;

/**
 * An edit for a deletion of country.
 *
 * @author Baptiste Wicht
 */
public final class DeletedCountryEdit extends AbstractUndoableEdit {
    private final Country country;

    @Resource
    private ICountriesService countriesService;

    /**
     * Construct a new DeletedCountryEdit.
     *
     * @param kind The deleted country.
     */
    public DeletedCountryEdit(Country kind) {
        super();

        Managers.getManager(IBeansManager.class).inject(this);

        country = kind;
    }

    @Override
    public void undo() {
        super.undo();

        countriesService.create(country);
    }

    @Override
    public void redo() {
        super.redo();

        countriesService.delete(country);
    }

    @Override
    public String getPresentationName() {
        return Managers.getManager(ILanguageManager.class).getMessage("undo.edits.delete");
    }
}
