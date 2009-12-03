package org.jtheque.films.view.impl.actions.cover;

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

import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.services.able.ICoverService;
import org.jtheque.films.view.able.ICoverView;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * An action to validate the saga view.
 *
 * @author Baptiste Wicht
 */
public final class AcUpdateCover extends JThequeAction {
    private static final long serialVersionUID = -6791055361978541369L;

    @Resource
    private ICoverService service;

    @Resource
    private ICoverView coverView;

    /**
     * Construct a AcValidateSagaView.
     */
    public AcUpdateCover() {
        super("cover.view.actions.update");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        coverView.display(service.getReportImage(coverView.getSelectedFilm(), coverView.getSelectedFormat()));
    }
}