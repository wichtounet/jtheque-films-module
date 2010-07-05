package org.jtheque.films.view.impl.panels.search;

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
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.utils.ui.builders.I18nPanelBuilder;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.films.services.impl.utils.search.DataSearcher;
import org.jtheque.films.services.impl.utils.search.Searcher;
import org.jtheque.films.services.impl.utils.search.filters.CountryFilter;
import org.jtheque.films.services.impl.utils.search.filters.NoteFilter;
import org.jtheque.films.utils.Constants.Properties.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.primary.services.able.ISimpleDataService;
import org.jtheque.primary.view.impl.models.DataContainerCachedComboBoxModel;
import org.jtheque.primary.view.impl.models.NotesComboBoxModel;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.JCheckBox;

/**
 * A search panel for realizers.
 *
 * @author Baptiste Wicht
 */
public final class JPanelRealizerSearch extends JPanelSearch {
    private static final long serialVersionUID = -3486881325683585131L;

    /* Graphics components */
    private JCheckBox boxNote;
    private JCheckBox boxCountry;

    private NotesComboBoxModel modelNotes;
    private DataContainerCachedComboBoxModel<SimpleData> modelCountries;

    @Resource
    private ISimpleDataService countriesService;

    /**
     * Build the panel.
     */
    @PostConstruct
    private void build() {
        I18nPanelBuilder builder = new JThequePanelBuilder(this);

        boxNote = builder.add(new JCheckBox(), builder.gbcSet(0, 0));

        builder.addI18nLabel(Person.NOTE, builder.gbcSet(1, 0));

        modelNotes = new NotesComboBoxModel(daoNotes);

        builder.addComboBox(modelNotes, builder.gbcSet(2, 0));

        boxCountry = builder.add(new JCheckBox(), builder.gbcSet(0, 1));

        builder.addI18nLabel(Person.COUNTRY, builder.gbcSet(1, 1));

        modelCountries = new DataContainerCachedComboBoxModel<SimpleData>(countriesService);

        builder.addComboBox(modelCountries, builder.gbcSet(2, 1));
    }

    @Override
    public Searcher<org.jtheque.primary.od.able.Person> getSearcher() {
        Searcher<org.jtheque.primary.od.able.Person> search = new DataSearcher<org.jtheque.primary.od.able.Person>();

        if (boxCountry.isSelected()) {
            search.addFilter(new CountryFilter<org.jtheque.primary.od.able.Person>(modelCountries.getSelectedData()));
        }

        if (boxNote.isSelected()) {
            search.addFilter(new NoteFilter<org.jtheque.primary.od.able.Person>(modelNotes.getSelectedNote()));
        }

        return search;
    }

    @Override
    public String getTitle() {
        return Managers.getManager(ILanguageManager.class).getMessage("search.realizer.title");
    }
}
