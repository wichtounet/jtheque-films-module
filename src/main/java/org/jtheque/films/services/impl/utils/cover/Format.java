package org.jtheque.films.services.impl.utils.cover;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;

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

/**
 * A format of cover.
 *
 * @author Baptiste Wicht
 */
public final class Format {
    private final String key;
    private final String form;

    /**
     * Construct a new Format.
     *
     * @param key  The internationalization of the name of the format.
     * @param form The name of the file of the form.
     */
    public Format(String key, String form) {
        super();

        this.key = key;
        this.form = form;
    }

    /**
     * Return the name's internationalization key.
     *
     * @return the name's internationalization key.
     */
    public String getKey() {
        return key;
    }

    /**
     * Return the form's file's name.
     *
     * @return the form's file's name.
     */
    public String getForm() {
        return form;
    }

    @Override
    public String toString() {
        return Managers.getManager(ILanguageManager.class).getMessage(key);
    }
}
