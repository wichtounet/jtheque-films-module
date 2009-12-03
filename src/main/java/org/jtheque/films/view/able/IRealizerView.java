package org.jtheque.films.view.able;

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

import org.jtheque.core.managers.view.able.components.TabComponent;
import org.jtheque.films.view.impl.fb.IPersonFormBean;
import org.jtheque.primary.view.able.PrincipalDataView;
import org.jtheque.primary.view.impl.listeners.CurrentObjectListener;

/**
 * A realizer view specification.
 *
 * @author Baptiste Wicht
 */
public interface IRealizerView extends PrincipalDataView, CurrentObjectListener, TabComponent {
    /**
     * Fill a <code>RealizerFormBean</code> with the infos in the interface.
     *
     * @return The filled <code>RealizerFormBean</code>.
     */
    IPersonFormBean fillPersonFormBean();
}
