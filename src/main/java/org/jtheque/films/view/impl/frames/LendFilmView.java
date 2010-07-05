package org.jtheque.films.view.impl.frames;

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
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.builders.I18nPanelBuilder;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.view.able.ILendFilmView;
import org.jtheque.films.view.impl.actions.lend.AcValidateLendView;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.services.able.IPersonService;
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
    private IPersonService borrowersService;

    @Resource
    private IFilmsService filmsService;

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
        I18nPanelBuilder builder = new JThequePanelBuilder();

        builder.addI18nLabel("lend.view.film", builder.gbcSet(0, 0));

        modelFilms = new DataContainerCachedComboBoxModel<Film>(filmsService);

        builder.addComboBox(modelFilms, builder.gbcSet(1, 0, GridBagUtils.HORIZONTAL, 2, 1));

        builder.addI18nLabel("lend.view.borrower", builder.gbcSet(0, 1));

        modelBorrowers = new DataContainerCachedComboBoxModel<Person>(borrowersService);

        builder.addComboBox(modelBorrowers, builder.gbcSet(1, 1));

        builder.addButton(Managers.getManager(IBeansManager.class).<Action>getBean("newBorrowerAction"), builder.gbcSet(2, 1));

        builder.addButtonBar(builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL, 3, 1),
                new AcValidateLendView(), new CloseViewAction("generic.view.actions.cancel", this));

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
}
