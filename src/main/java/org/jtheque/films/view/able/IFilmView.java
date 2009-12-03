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
import org.jtheque.films.view.impl.fb.IFilmFormBean;
import org.jtheque.primary.view.able.PrincipalDataView;
import org.jtheque.primary.view.able.ToolbarView;
import org.jtheque.primary.view.impl.listeners.CurrentObjectListener;
import org.jtheque.primary.view.impl.listeners.ViewStateListener;

import java.awt.image.BufferedImage;

/**
 * @author Baptiste Wicht
 */
public interface IFilmView extends CurrentObjectListener, TabComponent, ViewStateListener, PrincipalDataView {
    /**
     * Fill a <code>FilmFormBean</code> with the infos in the interface.
     *
     * @return The filled <code>FilmFormBean</code>.
     */
    IFilmFormBean fillFilmFormBean();

    /**
     * Set the image of the miniature's panel.
     *
     * @param image The image of the film to display.
     */
    void setImageOfPanel(BufferedImage image);

    /**
     * Return the actors panels.
     *
     * @return The actors panel.
     */
    IInfosActorsView getPanelActors();

    /**
     * Return the kinds panels.
     *
     * @return The kinds panel.
     */
    IInfosKindsView getPanelKinds();

    @Override
    ToolbarView getToolbarView();
}
