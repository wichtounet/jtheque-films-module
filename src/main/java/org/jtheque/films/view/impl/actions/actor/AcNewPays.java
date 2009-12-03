package org.jtheque.films.view.impl.actions.actor;

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

import org.jtheque.primary.view.impl.actions.country.AbstractAcNewCountry;

/**
 * Action to create a new pays from the actor view.
 *
 * @author Baptiste Wicht
 */
public final class AcNewPays extends AbstractAcNewCountry {
    private static final long serialVersionUID = -7066992938277331080L;

    /**
     * Construct a new AcNewPays.
     */
    public AcNewPays() {
        super("generic.view.actions.new");
    }
}