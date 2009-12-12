package org.jtheque.films.view.impl.panels.principals;

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

import org.jdesktop.swingx.JXHyperlink;
import org.jdesktop.swingx.JXTitledPanel;
import org.jdesktop.swingx.border.DropShadowBorder;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.persistence.able.DataContainer;
import org.jtheque.core.utils.ui.Borders;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.core.utils.ui.constraints.ConstraintManager;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.utils.Constants;
import org.jtheque.films.view.able.IActorView;
import org.jtheque.films.view.impl.actions.actor.NewCountryAction;
import org.jtheque.films.view.impl.actions.sort.AcSortActor;
import org.jtheque.films.view.impl.fb.IPersonFormBean;
import org.jtheque.films.view.impl.fb.PersonFormBean;
import org.jtheque.films.view.impl.models.ActorsModel;
import org.jtheque.films.view.impl.models.able.IActorsModel;
import org.jtheque.films.view.impl.toolbars.JPanelActorToolBar;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.od.able.Country;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.view.impl.components.panels.JThequeTitledPanel;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;
import org.jtheque.primary.view.impl.models.DataContainerCachedComboBoxModel;
import org.jtheque.primary.view.impl.models.NotesComboBoxModel;
import org.jtheque.primary.view.impl.renderers.NoteComboRenderer;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.PostConstruct;
import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.TreeSelectionListener;
import java.awt.Font;
import java.util.Collection;

/**
 * View to add/edit an actor.
 *
 * @author Baptiste Wicht
 */
public final class ActorView extends AbstractPrincipalDelegatedView implements IActorView {
    /**
     * Construct a new ActorView.
     */
    public ActorView() {
        super(2, "actor.data.title");
    }
    
    @Override
    public IPersonFormBean fillActorFormBean() {
        IPersonFormBean fb = new PersonFormBean();

        getImplementationView().fillFormBean(fb);

        return fb;
    }

    @Override
    public void objectChanged(ObjectChangedEvent event) {
        getImplementationView().setCurrent(event.getObject());
    }

    @Override
    public IActorsModel getModel() {
        return (IActorsModel) super.getModel();
    }

    @PostConstruct
    public void init(){
        buildInEDT();
        
        getModel().addCurrentObjectListener(this);
    }
    
    @Override
    protected void buildDelegatedView() {
        ActorViewImpl impl = new ActorViewImpl();
        setView(impl);
        impl.build();
    }

    private static final class ActorViewImpl extends AbstractPrincipalDataPanel<IActorsModel> {
        private static final int FIELD_COLUMNS = 20;
        
        private final Action newCountryAction;
        
        private JXTitledPanel actorPanel;
    
        private JTextField fieldFirstName;
        private JTextField fieldName;
        private DataContainerCachedComboBoxModel<Country> modelCountries;
        private NotesComboBoxModel modelNote;
        private JComboBox comboCountries;
        private JComboBox comboNote;

        private ActorViewImpl() {
            super(IActorService.DATA_TYPE);
            
            setModel(new ActorsModel());
            
            newCountryAction = new NewCountryAction();
        }

        /**
         * Build the view.
         */
        private void build() {
            getModel().addDisplayListListener(this);

            PanelBuilder builder = new PanelBuilder(this);

            buildPanelList(builder);
            buildPanelTri(builder);
            buildPanelActor(builder);

            setBorder(Borders.DIALOG_BORDER);

            getTree().addTreeSelectionListener(Managers.getManager(IBeansManager.class).<TreeSelectionListener>getBean("actorController"));

            getModel().addDisplayListListener(this);

            selectFirst();
        }

        /**
         * Build the internal panel actor.
         *
         * @param parent The builder of the frame.
         */
        private void buildPanelActor(PanelBuilder parent) {
            actorPanel = new JThequeTitledPanel("actor.view.panel.actor.title");
            actorPanel.setBorder(new DropShadowBorder());
            actorPanel.setTitleFont(actorPanel.getTitleFont().deriveFont(Font.BOLD));

            PanelBuilder builder = new PanelBuilder();

            JPanelActorToolBar toolBar = new JPanelActorToolBar();
            
            setToolBar(toolBar);
            
            builder.add(toolBar, builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, 0, 1));

            addFirstNameField(builder);
            addNameField(builder);
            addCountryField(builder);
            addNoteField(builder);

            actorPanel.setContentContainer(builder.getPanel());

            parent.add(actorPanel, parent.gbcSet(1, 0, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 1, 2, 1 - Constants.AN_HALF, 1.0));
        }

        /**
         * Add the field for first name.
         *
         * @param builder The builder.
         */
        private void addFirstNameField(PanelBuilder builder) {
            builder.addI18nLabel(Constants.Properties.Person.FIRST_NAME, builder.gbcSet(0, 1));

            fieldFirstName = builder.add(new JTextField(FIELD_COLUMNS), builder.gbcSet(1, 1, GridBagUtils.NONE, 0, 1));
            ConstraintManager.configure(fieldFirstName, Constants.Properties.Person.FIRST_NAME);
            fieldFirstName.setEnabled(false);
        }

        /**
         * Add the field for name.
         *
         * @param builder The builder.
         */
        private void addNameField(PanelBuilder builder) {
            builder.addI18nLabel(Constants.Properties.Person.NAME, builder.gbcSet(0, 2));

            fieldName = builder.add(new JTextField(FIELD_COLUMNS), builder.gbcSet(1, 2, GridBagUtils.NONE, 0, 1));
            ConstraintManager.configure(fieldName, Constants.Properties.Person.NAME);
            fieldName.setEnabled(false);
        }

        /**
         * Add the field for country.
         *
         * @param builder The builder.
         */
        private void addCountryField(PanelBuilder builder) {
            builder.addI18nLabel(Constants.Properties.Person.COUNTRY, builder.gbcSet(0, 3));

            modelCountries = new DataContainerCachedComboBoxModel<Country>(Managers.getManager(IBeansManager.class).<DataContainer<Country>>getBean("countriesService"));

            comboCountries = builder.addComboBox(modelCountries, builder.gbcSet(1, 3));
            comboCountries.setEnabled(false);

            newCountryAction.setEnabled(false);

            builder.addButton(newCountryAction, builder.gbcSet(2, 3, GridBagUtils.NONE, 0, 1));
        }

        /**
         * Add the field for note.
         *
         * @param builder The builder.
         */
        private void addNoteField(PanelBuilder builder) {
            builder.addI18nLabel(Constants.Properties.Person.NOTE, builder.gbcSet(0, 4));

            modelNote = new NotesComboBoxModel();

            comboNote = builder.addComboBox(modelNote, builder.gbcSet(1, 4, GridBagUtils.NONE, GridBagUtils.ABOVE_BASELINE_LEADING, 0, 0, 1.0, 1.0));
            comboNote.setEnabled(false);
            comboNote.setRenderer(new NoteComboRenderer());
        }

        /**
         * Build the internal panel sort.
         *
         * @param parent The builder of the frame.
         */
        private static void buildPanelTri(PanelBuilder parent) {
            JXTitledPanel panelTri = new JThequeTitledPanel("actor.view.panel.sort.title");
            panelTri.setBorder(new DropShadowBorder());
            panelTri.setTitleFont(panelTri.getTitleFont().deriveFont(Font.BOLD));

            PanelBuilder builder = new PanelBuilder();

            JXHyperlink linkTriPays = builder.add(new JXHyperlink(new AcSortActor("actor.view.actions.sort.country", "Countries")),
                    builder.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));
            linkTriPays.setClickedColor(linkTriPays.getUnclickedColor());

            JXHyperlink linkTriNote = builder.add(new JXHyperlink(new AcSortActor("actor.view.actions.sort.note", "Notes")),
                    builder.gbcSet(0, 1, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));
            linkTriNote.setClickedColor(linkTriNote.getUnclickedColor());

            panelTri.setContentContainer(builder.getPanel());

            parent.add(panelTri, parent.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START, Constants.AN_HALF, 0.0));
        }

        /**
         * Build the internal panel list.
         *
         * @param parent The builder of the frame.
         */
        private void buildPanelList(PanelBuilder parent) {
            JXTitledPanel panelList = new JThequeTitledPanel("actor.view.panel.list.title");
            panelList.setBorder(new DropShadowBorder());
            panelList.setTitleFont(panelList.getTitleFont().deriveFont(Font.BOLD));

            PanelBuilder builder = new PanelBuilder();

            setTreeModel(getSorter().createInitialModel(IActorService.DATA_TYPE));

            initTree();

            builder.addScrolled(getTree(), builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 0, 0, 1.0, 1.0));

            panelList.setContentContainer(builder.getPanel());

            parent.add(panelList, parent.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, Constants.AN_HALF, 1.0));
        }

        @Override
        public void fillFormBean(FormBean formBean) {
            IPersonFormBean fb = (IPersonFormBean)formBean;

            fb.setName(fieldName.getText());
            fb.setFirstName(fieldFirstName.getText());
            fb.setNote(modelNote.getSelectedNote());
            fb.setCountry(modelCountries.getSelectedData());
        }

        @Override
        public void setCurrent(Object object) {
            Person current = (Person) object;
            
            assert current.getType().equals(IActorService.PERSON_TYPE) : "The person must of type Actor";

            actorPanel.setTitle(current.getDisplayableText());
    
            fieldFirstName.setText(current.getFirstName());
            fieldName.setText(current.getName());
            modelCountries.setSelectedItem(current.getTheCountry());
            modelNote.setSelectedItem(current.getNote());
        }

        @Override
        protected void validate(Collection<JThequeError> errors) {
            ConstraintManager.validate(Constants.Properties.Person.NAME, fieldName.getText(), errors);
            ConstraintManager.validate(Constants.Properties.Person.FIRST_NAME, fieldFirstName.getText(), errors);
            ConstraintManager.validate(Constants.Properties.Person.COUNTRY, modelCountries.getSelectedData(), errors);
        }

        @Override
        public void setEnabled(boolean enabled) {
            fieldFirstName.setEnabled(enabled);
            fieldName.setEnabled(enabled);
            comboCountries.setEnabled(enabled);
            comboNote.setEnabled(enabled);
            newCountryAction.setEnabled(enabled);
        }
    }
}