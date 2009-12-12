package org.jtheque.films.controllers.impl.undo;

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