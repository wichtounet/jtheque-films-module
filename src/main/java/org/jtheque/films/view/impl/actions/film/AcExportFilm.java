package org.jtheque.films.view.impl.actions.film;

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
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.controllers.able.IExportController;
import org.jtheque.films.controllers.able.IFilmController;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.io.SimpleFilter;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * Action to export a film.
 *
 * @author Baptiste Wicht
 */
public final class AcExportFilm extends JThequeAction {
    private final SimpleFilter jTFFilter = new SimpleFilter("Files JTF(*.jtf)", ".jtf");

    @Resource
    private IFilmController filmController;

    @Resource
    private IExportController exportController;

    /**
     * Construct a new AcExportFilm.
     */
    public AcExportFilm() {
        super("film.view.actions.export");
        
        Managers.getManager(IBeansManager.class).inject(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionevent) {
        String filePath = Managers.getManager(IViewManager.class).chooseFile(jTFFilter);

        if (StringUtils.isNotEmpty(filePath)) {
            exportController.exportFilm(filePath, filmController.getViewModel().getCurrentFilm());
        }
    }
}