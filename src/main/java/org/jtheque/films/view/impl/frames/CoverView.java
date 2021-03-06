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

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.builders.I18nPanelBuilder;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.ICoverService;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.impl.utils.cover.Format;
import org.jtheque.films.view.able.ICoverView;
import org.jtheque.films.view.impl.actions.cover.AcExportCover;
import org.jtheque.films.view.impl.actions.cover.AcPrintCover;
import org.jtheque.films.view.impl.actions.cover.AcUpdateCover;
import org.jtheque.films.view.impl.models.combo.CoverFormatComboBoxModel;
import org.jtheque.primary.view.impl.models.DataContainerCachedComboBoxModel;
import org.jtheque.utils.ui.GridBagUtils;

import org.jdesktop.swingx.JXImagePanel;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Image;
import java.util.Collection;

/**
 * A cover view implementation.
 *
 * @author Baptiste Wicht
 */
public final class CoverView extends SwingDialogView implements ICoverView {
    @Resource
    private IFilmsService filmsService;

    @Resource
    private ICoverService coverService;

    private DataContainerCachedComboBoxModel<Film> modelFilms;
    private CoverFormatComboBoxModel modelFormats;
    private JXImagePanel viewer;

    /**
     * Construct a new CoverView.
     *
     * @param frame The parent frame.
     */
    public CoverView(Frame frame) {
        super(frame);
    }

    /**
     * Build the view.
     */
    @PostConstruct
    private void build() {
        setTitleKey("cover.view.title");
        setContentPane(buildContentPane());
        pack();

        setLocationRelativeTo(getOwner());
    }

    /**
     * Build the content pane.
     *
     * @return The builded content pane.
     */
    private Container buildContentPane() {
        I18nPanelBuilder builder = new JThequePanelBuilder();

        builder.addI18nLabel("cover.view.film", builder.gbcSet(0, 0));

        modelFilms = new DataContainerCachedComboBoxModel<Film>(filmsService);
        modelFilms.selectFirst();

        builder.addComboBox(modelFilms, builder.gbcSet(1, 0));

        builder.addI18nLabel("cover.view.format", builder.gbcSet(0, 1));

        modelFormats = new CoverFormatComboBoxModel();
        modelFormats.selectFirst();

        builder.addComboBox(modelFormats, builder.gbcSet(1, 1));

        viewer = new JXImagePanel();
        viewer.setBackground(Color.white);
        viewer.setImage(coverService.getReportImage(modelFilms.getSelectedData(), modelFormats.getSelectedItem()));

        builder.add(viewer, builder.gbcSet(0, 2, GridBagUtils.BOTH, 2, 1));

        builder.addButtonBar(builder.gbcSet(0, 3, GridBagUtils.HORIZONTAL, 2, 1),
                new AcUpdateCover(), new AcPrintCover(), new AcExportCover(), new CloseViewAction("generic.view.actions.cancel", this));

        return builder.getPanel();
    }

    @Override
    public void display(Image image) {
        viewer.setImage(image);
    }

    @Override
    public Film getSelectedFilm() {
        return modelFilms.getSelectedData();
    }

    @Override
    public Format getSelectedFormat() {
        return modelFormats.getSelectedItem();
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
    }
}
