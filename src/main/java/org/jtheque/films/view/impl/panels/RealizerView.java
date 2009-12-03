package org.jtheque.films.view.impl.panels;

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
import org.jdesktop.swingx.JXTree;
import org.jdesktop.swingx.border.DropShadowBorder;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.utils.ui.Borders;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.core.utils.ui.constraints.ConstraintManager;
import org.jtheque.films.controllers.able.IRealizerController;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.films.utils.Constants;
import org.jtheque.films.view.able.IRealizerView;
import org.jtheque.films.view.impl.fb.IPersonFormBean;
import org.jtheque.films.view.impl.fb.PersonFormBean;
import org.jtheque.films.view.impl.models.able.IRealizersModel;
import org.jtheque.films.view.impl.toolbars.JPanelRealizerToolBar;
import org.jtheque.primary.od.able.Country;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.services.able.ICountriesService;
import org.jtheque.primary.view.able.ToolbarView;
import org.jtheque.primary.view.impl.components.panels.JThequeTitledPanel;
import org.jtheque.primary.view.impl.components.panels.PrincipalDataPanel;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;
import org.jtheque.primary.view.impl.models.DataContainerCachedComboBoxModel;
import org.jtheque.primary.view.impl.models.NotesComboBoxModel;
import org.jtheque.primary.view.impl.renderers.JThequeTreeCellRenderer;
import org.jtheque.primary.view.impl.renderers.NoteComboRenderer;
import org.jtheque.primary.view.impl.sort.SortManager;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import java.awt.Font;
import java.util.Collection;

/**
 * User interface to edit/add realizer.
 *
 * @author Baptiste Wicht
 */
public final class RealizerView extends PrincipalDataPanel<IRealizersModel> implements IRealizerView {
    private static final long serialVersionUID = -4304886748651187432L;

    private JXTitledPanel panelRealizer;

    private JTextField fieldFirstName;
    private JTextField fieldName;
    private DataContainerCachedComboBoxModel<Country> modelCountries;
    private NotesComboBoxModel modelNote;
    private JComboBox comboCountries;
    private JComboBox comboNote;
    private final JPanelRealizerToolBar toolBar;

    private JXTree treeRealizers;

    /* Instances */
    private final SortManager sorter = new SortManager();

    @Resource
    private ICountriesService countriesService;

    @Resource
    private IRealizerController realizerController;

    private final Action newCountryAction;
    private final Action sortByNoteAction;
    private final Action sortByCountryAction;
    private static final int FIELD_COLUMNS = 20;
    private static final double A_QUARTER = 0.25;

    /**
     * Construct a new ActorView.
     *
     * @param newCountryAction    The action to create a new country.
     * @param sortByNoteAction    The action to sort by note.
     * @param sortByCountryAction The action to sort by country.
     * @param toolBar             The tool bar.
     */
    public RealizerView(Action newCountryAction, Action sortByNoteAction, Action sortByCountryAction, JPanelRealizerToolBar toolBar) {
        super();

        this.newCountryAction = newCountryAction;
        this.sortByNoteAction = sortByNoteAction;
        this.sortByCountryAction = sortByCountryAction;
        this.toolBar = toolBar;
    }

    /**
     * Build the view.
     */
    @PostConstruct
    private void build() {
        getModel().addDisplayListListener(this);

        PanelBuilder builder = new PanelBuilder(this);

        buildPanelList(builder);
        buildPanelTri(builder);
        buildPanelRealizer(builder);

        setBorder(Borders.DIALOG_BORDER);

        addListeners();

        selectFirst();
    }

    /**
     * Add listeners to and from the view.
     */
    private void addListeners() {
        treeRealizers.addTreeSelectionListener(realizerController);

        getModel().addCurrentObjectListener(this);
    }

    /**
     * Build the internal panel realizer.
     *
     * @param parent The builder of the frame.
     */
    private void buildPanelRealizer(PanelBuilder parent) {
        panelRealizer = new JThequeTitledPanel("realizer.view.panel.realizer.title");
        panelRealizer.setBorder(new DropShadowBorder());
        panelRealizer.setTitleFont(panelRealizer.getTitleFont().deriveFont(Font.BOLD));

        PanelBuilder builder = new PanelBuilder();

        builder.add(toolBar, builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, 0, 1));

        addNamesFields(builder);
        addCountryField(builder);
        addNoteField(builder);

        panelRealizer.setContentContainer(builder.getPanel());

        parent.add(panelRealizer, parent.gbcSet(1, 0, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 1, 2, 1 - A_QUARTER, 1.0));
    }

    /**
     * Add the field for the name and the first name of the realizer.
     *
     * @param builder The builder of the view.
     */
    private void addNamesFields(PanelBuilder builder) {
        builder.addI18nLabel(Constants.Properties.Person.FIRST_NAME, builder.gbcSet(0, 1));

        fieldFirstName = builder.add(new JTextField(FIELD_COLUMNS), builder.gbcSet(1, 1, GridBagUtils.NONE, 0, 1));
        ConstraintManager.configure(fieldFirstName, Constants.Properties.Person.FIRST_NAME);
        fieldFirstName.setEnabled(false);

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

        modelCountries = new DataContainerCachedComboBoxModel<Country>(countriesService);

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
    private void buildPanelTri(PanelBuilder parent) {
        JXTitledPanel panelTri = new JThequeTitledPanel("realizer.view.panel.sort.title");
        panelTri.setBorder(new DropShadowBorder());
        panelTri.setTitleFont(panelTri.getTitleFont().deriveFont(Font.BOLD));

        PanelBuilder builder = new PanelBuilder();

        JXHyperlink linkTriPays = builder.add(new JXHyperlink(sortByCountryAction),
                builder.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));
        linkTriPays.setClickedColor(linkTriPays.getUnclickedColor());

        JXHyperlink linkTriNote = builder.add(new JXHyperlink(sortByNoteAction),
                builder.gbcSet(0, 1, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));
        linkTriNote.setClickedColor(linkTriNote.getUnclickedColor());

        panelTri.setContentContainer(builder.getPanel());

        parent.add(panelTri, parent.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START, A_QUARTER, 0.0));
    }

    /**
     * Build the internal panel list.
     *
     * @param parent The builder of the frame.
     */
    private void buildPanelList(PanelBuilder parent) {
        JXTitledPanel panelList = new JThequeTitledPanel("realizer.view.panel.list.title");
        panelList.setBorder(new DropShadowBorder());
        panelList.setTitleFont(panelList.getTitleFont().deriveFont(Font.BOLD));

        PanelBuilder builder = new PanelBuilder();

        setTreeModel(sorter.createInitialModel(IRealizersService.DATA_TYPE));

        treeRealizers = new JXTree(getTreeModel());
        treeRealizers.setCellRenderer(new JThequeTreeCellRenderer());
        treeRealizers.putClientProperty("JTree.lineStyle", "None");

        builder.addScrolled(treeRealizers, builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 0, 0, 1.0, 1.0));

        panelList.setContentContainer(builder.getPanel());

        parent.add(panelList, parent.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, A_QUARTER, 1.0));
    }

    /**
     * Set the current realizer.
     *
     * @param current The current realizer
     */
    private void setCurrentRealizer(Person current) {
        assert current.getType().equals(IRealizersService.PERSON_TYPE) : "The person must of type Actor";

        panelRealizer.setTitle(current.getDisplayableText());

        fieldFirstName.setText(current.getFirstName());
        fieldName.setText(current.getName());
        comboCountries.setSelectedItem(current.getTheCountry().getName());
        comboNote.setSelectedItem(current.getNote());
    }

    @Override
    public IPersonFormBean fillPersonFormBean() {
        IPersonFormBean fb = new PersonFormBean();

        fb.setName(fieldName.getText());
        fb.setFirstName(fieldFirstName.getText());
        fb.setNote(modelNote.getSelectedNote());
        fb.setCountry(modelCountries.getSelectedData());

        return fb;
    }

    @Override
    public void objectChanged(ObjectChangedEvent event) {
        setCurrentRealizer((Person) event.getObject());
    }

    @Override
    public void setEnabled(boolean enabled) {
        fieldFirstName.setEnabled(enabled);
        fieldName.setEnabled(enabled);
        comboCountries.setEnabled(enabled);
        comboNote.setEnabled(enabled);
        newCountryAction.setEnabled(enabled);
    }

    @Override
    public ToolbarView getToolbarView() {
        return toolBar;
    }

    @Override
    public String getDataType() {
        return IRealizersService.DATA_TYPE;
    }

    @Override
    protected JXTree getTree() {
        return treeRealizers;
    }

    @Override
    public JComponent getImpl() {
        return this;
    }

    @Override
    public Integer getPosition() {
        return 3;
    }

    @Override
    public String getTitleKey() {
        return "realizer.data.title";
    }

    @Override
    public void clear() {
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
        ConstraintManager.validate(Constants.Properties.Person.NAME, fieldName.getText(), errors);
        ConstraintManager.validate(Constants.Properties.Person.FIRST_NAME, fieldFirstName.getText(), errors);
        ConstraintManager.validate(Constants.Properties.Person.COUNTRY, modelCountries.getSelectedData(), errors);
    }

    @Override
    public JComponent getComponent() {
        return this;
    }
}