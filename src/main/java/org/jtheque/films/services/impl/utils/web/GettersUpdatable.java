package org.jtheque.films.services.impl.utils.web;

import org.jtheque.core.managers.update.AbstractUpdatable;
import org.jtheque.core.managers.update.Updatable;
import org.jtheque.utils.bean.Version;

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