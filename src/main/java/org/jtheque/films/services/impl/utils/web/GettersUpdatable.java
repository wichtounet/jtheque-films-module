package org.jtheque.films.services.impl.utils.web;

import org.jtheque.core.managers.update.AbstractUpdatable;
import org.jtheque.core.managers.update.Updatable;
import org.jtheque.utils.bean.Version;

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
 * An updatable to keep up to date the web getters.
 *
 * @author Baptiste Wicht
 */
public final class GettersUpdatable extends AbstractUpdatable {
    private static final Updatable INSTANCE = new GettersUpdatable();

    /**
     * Construct a new GettersUpdatable.
     */
    private GettersUpdatable() {
        super("WebGetters", "updatables.getters");
    }

    @Override
    public Version getDefaultVersion() {
        return new Version("1.0");
    }

    @Override
    public String getVersionsFileURL() {
        return "http://jtheque.developpez.com/public/versions/getters.versions";
    }

    /**
     * Return the unique instance of the class.
     *
     * @return The unique instance of GettersUpdatable.
     */
    public static Updatable getInstance() {
        return INSTANCE;
    }
}
