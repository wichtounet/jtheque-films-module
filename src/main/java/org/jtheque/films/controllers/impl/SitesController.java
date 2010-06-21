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

import org.jtheque.core.managers.log.IJThequeLogger;
import org.jtheque.core.managers.log.Logger;
import org.jtheque.core.managers.view.able.controller.AbstractController;
import org.jtheque.films.controllers.able.ISitesController;
import org.jtheque.films.utils.FileUtils;
import org.jtheque.films.view.able.ISitesView;

import javax.annotation.Resource;
import javax.swing.event.ListSelectionEvent;

import java.io.IOException;

/**
 * A sites controller implementation.
 *
 * @author Baptiste Wicht
 */
public final class SitesController extends AbstractController implements ISitesController {
    @Resource
    private ISitesView sitesView;

    @Logger
    private IJThequeLogger logger;

    @Override
    public ISitesView getView() {
        return sitesView;
    }

    @Override
    public void valueChanged(ListSelectionEvent event) {
        try {
            sitesView.getSitesTextComponent().setPage(FileUtils.getLocalisedPage(
                    sitesView.getSelectedSite()));
        } catch (IOException e1) {
            sitesView.getSitesTextComponent().setText("");
            logger.error(e1);
        }
    }
}