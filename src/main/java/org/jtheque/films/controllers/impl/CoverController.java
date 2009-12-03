package org.jtheque.films.controllers.impl;

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

import org.jtheque.core.managers.view.able.controller.AbstractController;
import org.jtheque.films.controllers.able.ICoverController;
import org.jtheque.films.view.able.ICoverView;

import javax.annotation.Resource;

/**
 * A cover controller implementation.
 *
 * @author Baptiste Wicht
 */
public final class CoverController extends AbstractController implements ICoverController {
    @Resource
    private ICoverView coverView;

    @Override
    public ICoverView getView() {
        return coverView;
    }
}