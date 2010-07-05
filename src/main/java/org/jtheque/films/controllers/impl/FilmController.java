package org.jtheque.films.controllers.impl;

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
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.films.controllers.able.IFilmController;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.utils.Constants;
import org.jtheque.films.view.able.IFilmView;
import org.jtheque.films.view.impl.models.able.IFilmsModel;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.impl.PrincipalController;
import org.jtheque.utils.DesktopMail;
import org.jtheque.utils.DesktopUtils;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.io.CopyException;
import org.jtheque.utils.io.FileUtils;
import org.jtheque.utils.io.SimpleFilter;
import org.jtheque.utils.print.PrintUtils;

import javax.annotation.Resource;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * A film controller implementation.
 *
 * @author Baptiste Wicht
 */
public final class FilmController extends PrincipalController<Film> implements IFilmController {
    @Resource
    private IFilmsService filmService;

    @Resource
    private IFilmView filmView;

    private final SimpleFilter imagesFilter = new SimpleFilter("Images(*.jpg,*.png)", ".jpg,.png,.gif");

    public FilmController(ControllerState viewState, ControllerState modifyState,
                          ControllerState newObjectState, ControllerState autoAddState) {
        super(viewState, modifyState, newObjectState, autoAddState);
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if (event.getClickCount() == 2 && filmView.isEnabled()) {
            String image = Managers.getManager(IViewManager.class).chooseFile(imagesFilter);

            if (StringUtils.isNotEmpty(image)) {
                String extension = image.substring(image.lastIndexOf('.'));

                String destination = Constants.Files.MINIATURE_FOLDER + '/' +
                        getViewModel().getCurrentFilm().getTitle() + extension;

                try {
                    FileUtils.copy(image, destination);
                } catch (CopyException e1) {
                    Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e1);
                }

                BufferedImage bufferedImage = Managers.getManager(IResourceManager.class).getImage(image, Constants.MINIATURE_WIDTH);

                filmView.setImageOfPanel(bufferedImage);

                filmView.refresh();
            }
        }
    }

    @Override
    public IFilmsModel getViewModel() {
        return (IFilmsModel) filmView.getModel();
    }

    @Override
    public void save() {
        save(filmView.fillFilmFormBean());
    }

    @Override
    public void sendCurrentFilmByMail() {
        DesktopMail mail = new DesktopMail();
        mail.setSubject("Fiche du film : " + getViewModel().getCurrentFilm().getTitle());
        mail.setBody(filmService.generateEmail(getViewModel().getCurrentFilm()));

        DesktopUtils.mail(mail);
    }

    @Override
    public void printCurrentFilm() {
        PrintUtils.printString(filmService.generateFilmDescriptionForPrinting(getViewModel().getCurrentFilm()));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //Nothing to be done
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Nothing to be done
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Nothing to be done
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Nothing to be done
    }

    @Override
    public IFilmView getView() {
        return filmView;
    }

    @Override
    public String getDataType() {
        return IFilmsService.DATA_TYPE;
    }
}
