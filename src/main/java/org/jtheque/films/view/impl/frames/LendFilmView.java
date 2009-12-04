package org.jtheque.films.view.impl.frames;

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

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.view.able.ILendFilmView;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.services.able.IBorrowersService;
import org.jtheque.primary.view.impl.models.DataContainerCachedComboBoxModel;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.Action;
import java.awt.Container;
import java.awt.Frame;
import java.util.Collection;

/**
 * The JFrame for lend a film.
 *
 * @author Baptiste Wicht
 */
public final class LendFilmView extends SwingDialogView implements ILendFilmView {
    private static final long serialVersionUID = 2370792054850689428L;

    private DataContainerCachedComboBoxModel<Film> modelFilms;
    private DataContainerCachedComboBoxModel<Person> modelBorrowers;

    @Resource
    private IBorrowersService borrowersService;

    @Resource
    private IFilmsService filmsService;

    private Action validateAction;
    private Action closeAction;
    private Action newBorrowerAction;

    /**
     * Construct a new <code>JFrameLendFilm</code> and initialize the frame.
     *
     * @param parent The parent frame.
     */
    public LendFilmView(Frame parent) {
        super(parent);
    }

    /**
     * Build the view.
     */
    @PostConstruct
    private void build() {
        setContentPane(buildContentPane());
        setTitleKey("lend.view.title");
        pack();

        setLocationRelativeTo(getOwner());
    }

    /**
     * Build the content pane of the frame.
     *
     * @return The content pane.
     */
    private Container buildContentPane() {
        PanelBuilder builder = new PanelBuilder();

        builder.addI18nLabel("lend.view.film", builder.gbcSet(0, 0));

        modelFilms = new DataContainerCachedComboBoxModel<Film>(filmsService);

        builder.addComboBox(modelFilms, builder.gbcSet(1, 0, GridBagUtils.HORIZONTAL, 2, 1));

        builder.addI18nLabel("lend.view.borrower", builder.gbcSet(0, 1));

        modelBorrowers = new DataContainerCachedComboBoxModel<Person>(borrowersService);

        builder.addComboBox(modelBorrowers, builder.gbcSet(1, 1));

        builder.addButton(newBorrowerAction, builder.gbcSet(2, 1));

        builder.addButtonBar(builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL, 3, 1), validateAction, closeAction);

        return builder.getPanel();
    }

    @Override
    public Film getSelectedFilm() {
        return modelFilms.getSelectedData();
    }

    @Override
    public Person getSelectedBorrower() {
        return modelBorrowers.getSelectedData();
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
    }

    /**
     * Set the action to create a new borrower.
     *
     * @param newBorrowerAction The action to create a new borrower.
     */
    public void setNewBorrowerAction(Action newBorrowerAction) {
        this.newBorrowerAction = newBorrowerAction;
    }

    /**
     * Set the action to close the view.
     *
     * @param closeAction The action to close the view.
     */
    public void setCloseAction(Action closeAction) {
        this.closeAction = closeAction;
    }

    /**
     * Set the action to validate the view.
     *
     * @param validateAction The action to validate the view.
     */
    public void setValidateAction(Action validateAction) {
        this.validateAction = validateAction;
    }
}