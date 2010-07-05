package org.jtheque.films.view.impl.actions.film;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
