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

import org.jtheque.core.managers.view.able.IWindowView;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.impl.utils.cover.Format;

import java.awt.Image;

/**
 * A cover view specification.
 *
 * @author Baptiste Wicht
 */
public interface ICoverView extends IWindowView {
    /**
     * Display the image.
     *
     * @param image The image to display.
     */
    void display(Image image);

    /**
     * Return the selected film.
     *
     * @return The film to select.
     */
    Film getSelectedFilm();

    /**
     * Return the selected format.
     *
     * @return The selected format.
     */
    Format getSelectedFormat();
}