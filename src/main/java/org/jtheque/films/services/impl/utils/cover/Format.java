package org.jtheque.films.services.impl.utils.cover;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;

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
