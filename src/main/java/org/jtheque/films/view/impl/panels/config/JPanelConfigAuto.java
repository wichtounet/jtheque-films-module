package org.jtheque.films.view.impl.panels.config;

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
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.view.impl.components.config.ConfigTabComponent;
import org.jtheque.core.utils.ui.ValidationUtils;
import org.jtheque.core.utils.ui.builders.I18nPanelBuilder;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
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
        I18nPanelBuilder builder = new JThequePanelBuilder(this);

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
