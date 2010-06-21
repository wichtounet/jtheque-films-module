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
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.core.utils.ui.builders.PanelBuilder;
import org.jtheque.films.services.impl.utils.Filmography;
import org.jtheque.films.view.able.IFilmographyView;
import org.jtheque.films.view.impl.models.able.IFilmographyModel;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JTextPane;
import javax.swing.text.html.HTMLEditorKit;

import java.awt.Container;
import java.awt.Frame;
import java.util.Collection;

/**
 * A frame to display a filmography of an actor.
 *
 * @author Baptiste Wicht
 */
public final class FilmographyView extends SwingDialogView implements IFilmographyView {
    private static final long serialVersionUID = 7168128065378572964L;

    private JTextPane textFilmo;

    /**
     * Construct a new <code>JFrameFilmography</code>.
     *
     * @param parent The parent frame.
     */
    public FilmographyView(Frame parent) {
        super(parent);

        setLocationRelativeTo(getOwner());
    }

    @Override
    public void sendMessage(String message, Object value) {
        if ("filmo".equals(message)) {
            getModel().setFilmo(value);
            updateFilmo(value);
        }
    }

    /**
     * Update the filmography.
     *
     * @param value The filmography object.
     */
    private void updateFilmo(Object value) {
        Filmography filmo = (Filmography) value;

        setTitle(getMessage("filmography.view.title") + ' ' + filmo.getActor());

        if (getModel().isBuilded()) {
            updateFilmo(filmo);
        } else {
            setContentPane(buildContentPane(filmo));

            pack();

            getModel().setBuilded();
        }
    }

    /**
     * Return the content pane initialized.
     *
     * @param filmo The filmography we want to display.
     *
     * @return The content pane.
     */
    private Container buildContentPane(Filmography filmo) {
        PanelBuilder builder = new JThequePanelBuilder();

        textFilmo = new JTextPane();
        textFilmo.setContentType("text/html");
        textFilmo.setEditable(false);
        textFilmo.setEditorKit(new HTMLEditorKit());

        updateFilmo(filmo);

        builder.addScrolled(textFilmo, builder.gbcSet(0, 0, GridBagUtils.BOTH));

        builder.addButtonBar(builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL), new CloseViewAction("generic.view.actions.close", this));

        return builder.getPanel();
    }

    /**
     * Update the content of the text pane with a new filmography.
     *
     * @param filmo The filmography.
     */
    private void updateFilmo(Filmography filmo) {
        StringBuilder builder = new StringBuilder("<html><body>");

        builder.append("<h2>").append(getMessage("filmography.view.header.actor")).append(' ').append(filmo.getActor()).append("</h2>");
        builder.append("<h3>").append(getMessage("filmography.view.header.films")).append("</h3>");
        builder.append("<ul>");

        for (String film : filmo.getFilms()) {
            builder.append("<li>").append(film).append("</li>");
        }

        builder.append("</ul>");
        builder.append("</body></html>");

        textFilmo.setText(builder.toString());
    }

    @Override
    public IFilmographyModel getModel() {
        return (IFilmographyModel) super.getModel();
    }

    @Override
    public void refreshText() {
        super.refreshText();

        if (getModel() != null) {
            updateFilmo(getModel().getFilmo());
        }
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
    }
}