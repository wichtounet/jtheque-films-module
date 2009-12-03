package org.jtheque.films.view.impl.actions.jtheque;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.utils.Constants;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * Action to print the list.
 *
 * @author Baptiste Wicht
 */
public final class AcPrintList extends JThequeAction {
    private static final long serialVersionUID = 858318520274595532L;

    @Resource
    private IFilmsService filmsService;

    /**
     * Construct a new AcPrintList.
     */
    public AcPrintList() {
        super("generic.view.actions.print");

        setIcon(Managers.getManager(IResourceManager.class).getIcon(Constants.IMAGE_BASE_NAME, "print", ImageType.PNG));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        filmsService.printListOfFilms();
    }
}