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

import org.jdesktop.swingx.JXImagePanel;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.ICoverService;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.impl.utils.cover.Format;
import org.jtheque.films.view.able.ICoverView;
import org.jtheque.films.view.impl.actions.CloseViewAction;
import org.jtheque.films.view.impl.actions.cover.AcExportCover;
import org.jtheque.films.view.impl.actions.cover.AcPrintCover;
import org.jtheque.films.view.impl.actions.cover.AcUpdateCover;
import org.jtheque.films.view.impl.models.combo.CoverFormatComboBoxModel;
import org.jtheque.primary.view.impl.models.DataContainerCachedComboBoxModel;
import org.jtheque.utils.ui.GridBagUtils;

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
     * @param frame        The parent frame.
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
        PanelBuilder builder = new PanelBuilder();

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
        viewer.setImage(coverService.getReportImage(modelFilms.getSelectedData(), modelFormats.getSelectedFormat()));

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
        return modelFormats.getSelectedFormat();
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
    }
}