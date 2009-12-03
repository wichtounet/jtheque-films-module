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
import org.jtheque.primary.view.impl.models.tree.TreeElement;
import org.jtheque.utils.DesktopMail;
import org.jtheque.utils.DesktopUtils;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.io.CopyException;
import org.jtheque.utils.io.FileUtils;
import org.jtheque.utils.io.SimpleFilter;
import org.jtheque.utils.print.PrintUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Collection;

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

    /**
     * Init the view.
     */
    @PostConstruct
    public void init() {
        setState(getViewState());
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
    public void valueChanged(TreeSelectionEvent event) {
        TreePath current = ((JTree) event.getSource()).getSelectionPath();

        if (current != null && current.getLastPathComponent() instanceof TreeElement) {
            Film film = (Film) current.getLastPathComponent();

            if (film != null) {
                view(film);
            }
        }
    }

    @Override
    public void save() {
        ControllerState newState = getState().save(filmView.fillFilmFormBean());

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public IFilmsModel getViewModel() {
        return (IFilmsModel) filmView.getModel();
    }

    @Override
    public void view(Film film) {
        ControllerState newState = getState().view(film);

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public void manualEdit() {
        ControllerState newState = getState().manualEdit();

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public void createFilm() {
        ControllerState newState = getState().create();

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public void deleteCurrentFilm() {
        ControllerState newState = getState().delete();

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public void cancel() {
        ControllerState newState = getState().cancel();

        if (newState != null) {
            setAndApplyState(newState);
        }
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

    @Override
    public Collection<Film> getDisplayList() {
        return getViewModel().getDisplayList();
    }
}