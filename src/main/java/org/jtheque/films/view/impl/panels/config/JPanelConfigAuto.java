package org.jtheque.films.view.impl.panels.config;

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
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.view.impl.components.config.ConfigTabComponent;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.core.utils.ui.ValidationUtils;
import org.jtheque.films.IFilmsModule;
import org.jtheque.films.services.impl.utils.config.Configuration;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.Collection;

/**
 * A config panel to configure the automatic add of film.
 *
 * @author Baptiste Wicht
 */
public final class JPanelConfigAuto extends JPanel implements ConfigTabComponent {
    private static final long serialVersionUID = 6787412105243753090L;

    private JTextField fieldNumberOfActors;

    /**
     * Construct a new JPanelConfigAuto. 
     */
    public JPanelConfigAuto() {
        super();

        build();
        cancel();
    }

    @Override
    public String getTitle() {
        return Managers.getManager(ILanguageManager.class).getMessage("config.view.tab.auto");
    }

    /**
     * Build the view.
     */
    private void build() {
        PanelBuilder builder = new PanelBuilder(this);

        builder.addI18nLabel("config.auto.actors",
                builder.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.ABOVE_BASELINE_LEADING));

        fieldNumberOfActors = builder.add(new JTextField(4),
                builder.gbcSet(1, 0, GridBagUtils.NONE, GridBagUtils.ABOVE_BASELINE_LEADING, 0, 0, 1.0, 1.0));
    }

    @Override
    public void apply() {
        getConfig().setNumberOfActors(Integer.parseInt(fieldNumberOfActors.getText()));
    }

    @Override
    public void cancel() {
        fieldNumberOfActors.setText(Integer.toString(getConfig().getNumberOfActors()));
    }

    @Override
    public void validate(Collection<JThequeError> errors) {
        ValidationUtils.rejectIfNotNumerical(fieldNumberOfActors.getText(), "config.auto.actors", errors);
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    /**
     * Return the configuration of the films module.
     *
     * @return The configuration of the films module.
     */
    private static Configuration getConfig() {
        return Managers.getManager(IBeansManager.class).<IFilmsModule>getBean("filmsModule").getConfiguration();
    }
}