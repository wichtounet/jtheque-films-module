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
import org.jdesktop.swingx.JXImagePanel;
import org.jdesktop.swingx.JXTitledPanel;
import org.jdesktop.swingx.JXTree;
import org.jdesktop.swingx.border.DropShadowBorder;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.language.TabTitleUpdater;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.core.utils.ui.constraints.ConstraintManager;
import org.jtheque.films.controllers.able.IFilmController;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.utils.Constants;
import org.jtheque.films.utils.Constants.Properties.Film;
import org.jtheque.films.view.able.IFilmView;
import org.jtheque.films.view.able.IInfosActorsView;
import org.jtheque.films.view.able.IInfosFilmView;
import org.jtheque.films.view.able.IInfosKindsView;
import org.jtheque.films.view.able.IInfosOthersView;
import org.jtheque.films.view.able.IInfosPersoView;
import org.jtheque.films.view.impl.fb.FilmFormBean;
import org.jtheque.films.view.impl.fb.IFilmFormBean;
import org.jtheque.films.view.impl.models.able.IFilmsModel;
import org.jtheque.films.view.impl.toolbars.JPanelFilmToolBar;
import org.jtheque.primary.od.able.Type;
import org.jtheque.primary.services.able.ITypesService;
import org.jtheque.primary.view.able.ToolbarView;
import org.jtheque.primary.view.impl.components.panels.JThequeTitledPanel;
import org.jtheque.primary.view.impl.components.panels.PrincipalDataPanel;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;
import org.jtheque.primary.view.impl.models.DataContainerCachedComboBoxModel;
import org.jtheque.primary.view.impl.renderers.JThequeTreeCellRenderer;
import org.jtheque.primary.view.impl.sort.SortManager;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * View to create/edit films.
 *
 * @author Baptiste Wicht
 */
public final class FilmView extends PrincipalDataPanel<IFilmsModel> implements IFilmView {
    private static final long serialVersionUID = -461914454879477388L;

    private JXTitledPanel panelFilm;

    private JTextField fieldTitle;
    private DataContainerCachedComboBoxModel<Type> modelType;
    private JComboBox comboType;
    private JButton buttonNewType;
    private JXImagePanel panelImage;

    private IInfosKindsView panelKinds;
    private IInfosActorsView panelActors;
    private IInfosPersoView panelPerso;
    private IInfosFilmView panelInfos;
    private IInfosOthersView panelOthers;

    private JPanelFilmToolBar toolBar;

    private JXTree treeFilms;

    private final SortManager sorter = new SortManager();

    private Action sortByKindAction;
    private Action sortByTypeAction;
    private Action sortByRealizerAction;
    private Action sortByYearAction;
    private Action newTypeAction;

    @Resource
    private ITypesService typesService;

    @Resource
    private IFilmController filmController;
    private static final double A_QUARTER = 0.25;

    /**
     * Build the view.
     */
    @PostConstruct
    private void build() {
        getModel().addDisplayListListener(this);
        getModel().addViewStateListener(this);

        PanelBuilder builder = new PanelBuilder(this);

        buildPanelList(builder);
        buildPanelTri(builder);
        buildPanelFilm(builder);

        addListeners();
    }

    /**
     * Add listeners to and from the view.
     */
    private void addListeners() {
        panelImage.addMouseListener(filmController);
        treeFilms.addTreeSelectionListener(filmController);

        getModel().addViewStateListener(this);
        getModel().addDisplayListListener(this);

        getModel().addCurrentObjectListener(this);
        getModel().addCurrentObjectListener(panelKinds);
        getModel().addCurrentObjectListener(panelInfos);
        getModel().addCurrentObjectListener(panelActors);
        getModel().addCurrentObjectListener(panelPerso);
        getModel().addCurrentObjectListener(panelOthers);
    }

    /**
     * Build the internal panel film.
     *
     * @param parent The parent builder.
     */
    private void buildPanelFilm(PanelBuilder parent) {
        panelFilm = new JThequeTitledPanel("film.view.panel.film.title");
        panelFilm.setBorder(new DropShadowBorder());
        panelFilm.setTitleFont(panelFilm.getTitleFont().deriveFont(Font.BOLD));

        PanelBuilder builder = new PanelBuilder();

        builder.add(toolBar, builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, 4, 1));

        builder.addI18nLabel(Film.TITLE, builder.gbcSet(0, 1));

        fieldTitle = builder.add(new JTextField(20), builder.gbcSet(1, 1, GridBagUtils.NONE, 0, 1));
        ConstraintManager.configure(fieldTitle, Film.TITLE);
        fieldTitle.setEnabled(false);

        addTypeField(builder);

        panelImage = new JXImagePanel();
        panelImage.setBackground(Color.white);
        builder.add(panelImage, builder.gbcSet(3, 1, GridBagUtils.BOTH, 1, 2));

        addTabbedPane(builder);

        panelFilm.setContentContainer(builder.getPanel());

        parent.add(panelFilm, parent.gbcSet(1, 0, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 1, 2, 1 - A_QUARTER, 1.0));
    }

    /**
     * Add the field for the type.
     *
     * @param builder The builder of the view.
     */
    private void addTypeField(PanelBuilder builder) {
        builder.addI18nLabel(Film.TYPE, builder.gbcSet(0, 2));

        modelType = new DataContainerCachedComboBoxModel<Type>(typesService);

        comboType = builder.addComboBox(modelType, builder.gbcSet(1, 2));
        comboType.setEnabled(false);

        buttonNewType = builder.addButton(newTypeAction, builder.gbcSet(2, 2));
        buttonNewType.setEnabled(false);
    }

    /**
     * Add the tabbed pane to the view.
     *
     * @param builder The builder of the view.
     */
    private void addTabbedPane(PanelBuilder builder) {
        Insets oldInsets = UIManager.getInsets("TabbedPane.contentBorderInsets");
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));

        JTabbedPane tabInfos = new JTabbedPane();

        UIManager.put("TabbedPane.contentBorderInsets", oldInsets);

        Map<JComponent, String> components = new HashMap<JComponent, String>(3);

        components.put(panelInfos.getImpl(), "film.view.panel.general.title");
        tabInfos.addTab(getMessage("film.view.panel.general.title"), panelInfos.getImpl());

        components.put(panelKinds.getImpl(), "film.view.panel.kinds.title");
        tabInfos.addTab(getMessage("film.view.panel.kinds.title"), panelKinds.getImpl());

        components.put(panelActors.getImpl(), "film.view.panel.actors.title");
        tabInfos.addTab(getMessage("film.view.panel.actors.title"), panelActors.getImpl());

        components.put(panelOthers.getImpl(), "film.view.panel.others.title");
        tabInfos.addTab(getMessage("film.view.panel.others.title"), panelOthers.getImpl());

        components.put(panelPerso.getImpl(), "film.view.panel.personal.title");
        tabInfos.addTab(getMessage("film.view.panel.personal.title"), panelPerso.getImpl());

        builder.add(tabInfos, builder.gbcSet(0, 3, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 0, 0, 1.0, 1.0));

        Managers.getManager(ILanguageManager.class).addInternationalizable(new TabTitleUpdater(tabInfos, components));
    }

    /**
     * Build the internal panel sort.
     *
     * @param parent The parent builder.
     */
    private void buildPanelTri(PanelBuilder parent) {
        JXTitledPanel panelTri = new JThequeTitledPanel("film.view.panel.sort.title");
        panelTri.setBorder(new DropShadowBorder());
        panelTri.setTitleFont(panelTri.getTitleFont().deriveFont(Font.BOLD));

        PanelBuilder builder = new PanelBuilder();

        JXHyperlink linkTriGenre = builder.add(new JXHyperlink(sortByKindAction),
                builder.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));
        linkTriGenre.setClickedColor(linkTriGenre.getUnclickedColor());

        JXHyperlink linkTriType = builder.add(new JXHyperlink(sortByTypeAction),
                builder.gbcSet(0, 1, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));
        linkTriType.setClickedColor(linkTriType.getUnclickedColor());

        JXHyperlink linkTriRealizer = builder.add(new JXHyperlink(sortByRealizerAction),
                builder.gbcSet(0, 2, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));
        linkTriRealizer.setClickedColor(linkTriRealizer.getUnclickedColor());

        JXHyperlink linkTriYear = builder.add(new JXHyperlink(sortByYearAction),
                builder.gbcSet(0, 3, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));
        linkTriYear.setClickedColor(linkTriYear.getUnclickedColor());

        panelTri.setContentContainer(builder.getPanel());

        parent.add(panelTri, parent.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START, A_QUARTER, 0.0));
    }

    /**
     * Build the internal panel list.
     *
     * @param parent The parent builder.
     */
    private void buildPanelList(PanelBuilder parent) {
        JXTitledPanel panelList = new JThequeTitledPanel("film.view.panel.list.title");
        panelList.setBorder(new DropShadowBorder());
        panelList.setTitleFont(panelList.getTitleFont().deriveFont(Font.BOLD));

        PanelBuilder builder = new PanelBuilder();

        setTreeModel(sorter.createInitialModel(IFilmsService.DATA_TYPE));

        treeFilms = new JXTree(getTreeModel());
        treeFilms.setCellRenderer(new JThequeTreeCellRenderer());
        treeFilms.putClientProperty("JTree.lineStyle", "None");

        builder.addScrolled(treeFilms, builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 0, 0, 1.0, 1.0));

        panelList.setContentContainer(builder.getPanel());

        parent.add(panelList, parent.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, A_QUARTER, 1.0));
    }

    @Override
    public void objectChanged(ObjectChangedEvent event) {
        setCurrentFilm((org.jtheque.films.persistence.od.able.Film) event.getObject());
    }

    /**
     * Set the currently displayed film.
     *
     * @param film The new current film
     */
    private void setCurrentFilm(org.jtheque.films.persistence.od.able.Film film) {
        panelFilm.setTitle(film.getDisplayableText());

        fieldTitle.setText(film.getTitle());

        if (film.getTheType() != null) {
            modelType.setSelectedItem(film.getTheType());
        }

        if (film.getImage() == null || film.getImage().length() == 0) {
            panelImage.setImage(null);
        } else {
            panelImage.setImage(Managers.getManager(IResourceManager.class).getImage(
                    film.getImage(),
                    ImageType.JPG,
                    Constants.MINIATURE_WIDTH));
        }
    }

    @Override
    public void stateChanged() {
        setEnabled(getModel().isEnabled());
    }

    @Override
    public void setEnabled(boolean enabled) {
        fieldTitle.setEnabled(enabled);
        buttonNewType.setEnabled(enabled);
        comboType.setEnabled(enabled);

        panelKinds.setEnabled(enabled);
        panelActors.setEnabled(enabled);
        panelPerso.setEnabled(enabled);
        panelInfos.setEnabled(enabled);
        panelOthers.setEnabled(enabled);
    }

    @Override
    public boolean isEnabled() {
        return getModel().isEnabled();
    }

    @Override
    public IFilmFormBean fillFilmFormBean() {
        IFilmFormBean fb = new FilmFormBean();

        fb.setTitle(fieldTitle.getText());
        fb.setType(modelType.getSelectedData());

        panelActors.fillFilm(fb);
        panelPerso.fillFilm(fb);
        panelInfos.fillFilm(fb);
        panelOthers.fillFilm(fb);
        panelKinds.fillFilm(fb);

        return fb;
    }

    @Override
    public ToolbarView getToolbarView() {
        return toolBar;
    }

    @Override
    public void setImageOfPanel(BufferedImage image) {
        panelImage.setImage(image);
    }

    @Override
    public IInfosActorsView getPanelActors() {
        return panelActors;
    }

    @Override
    public IInfosKindsView getPanelKinds() {
        return panelKinds;
    }

    @Override
    public String getDataType() {
        return IFilmsService.DATA_TYPE;
    }

    @Override
    protected JXTree getTree() {
        return treeFilms;
    }

    @Override
    public JComponent getImpl() {
        return this;
    }

    @Override
    public Integer getPosition() {
        return 1;
    }

    @Override
    public String getTitleKey() {
        return "film.data.title";
    }

    @Override
    public void clear() {
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
        ConstraintManager.validate(Film.TITLE, fieldTitle.getText(), errors);
        ConstraintManager.validate(Film.TYPE, modelType, errors);

        panelKinds.validate(errors);
        panelActors.validate(errors);
        panelPerso.validate(errors);
        panelInfos.validate(errors);
        panelOthers.validate(errors);
    }

    /**
     * Set the action to sort by kind.
     *
     * @param sortByKindAction The action to sort by kind.
     */
    public void setSortByKindAction(Action sortByKindAction) {
        this.sortByKindAction = sortByKindAction;
    }

    /**
     * Set the action to sort by type.
     *
     * @param sortByTypeAction The action to sort by type.
     */
    public void setSortByTypeAction(Action sortByTypeAction) {
        this.sortByTypeAction = sortByTypeAction;
    }

    /**
     * Set the action to sort by realizer.
     *
     * @param sortByRealizerAction The action to sort by realizer.
     */
    public void setSortByRealizerAction(Action sortByRealizerAction) {
        this.sortByRealizerAction = sortByRealizerAction;
    }

    /**
     * Set the action to sort by year.
     *
     * @param sortByYearAction The action to sort by year.
     */
    public void setSortByYearAction(Action sortByYearAction) {
        this.sortByYearAction = sortByYearAction;
    }

    /**
     * Set the action to create a new type.
     *
     * @param newTypeAction The action to create a new type.
     */
    public void setNewTypeAction(Action newTypeAction) {
        this.newTypeAction = newTypeAction;
    }

    /**
     * Set the panel of the actors.
     *
     * @param panelActors The panel of actors.
     */
    public void setPanelActors(IInfosActorsView panelActors) {
        this.panelActors = panelActors;
    }

    /**
     * Set the panel of the general informations.
     *
     * @param panelInfos The panel of general informations.
     */
    public void setPanelInfos(IInfosFilmView panelInfos) {
        this.panelInfos = panelInfos;
    }

    /**
     * Set the panel of the kinds.
     *
     * @param panelKinds The panel of kinds.
     */
    public void setPanelKinds(IInfosKindsView panelKinds) {
        this.panelKinds = panelKinds;
    }

    /**
     * Set the panel of the personal informations.
     *
     * @param panelPerso The panel of the personal informations.
     */
    public void setPanelPerso(IInfosPersoView panelPerso) {
        this.panelPerso = panelPerso;
    }

    /**
     * Set the panel of the others informations.
     *
     * @param panelOthers The panel of others informations.
     */
    public void setPanelOthers(IInfosOthersView panelOthers) {
        this.panelOthers = panelOthers;
    }

    /**
     * Set the tool bar of the view.
     *
     * @param toolBar The tool bar of the view.
     */
    public void setToolBar(JPanelFilmToolBar toolBar) {
        this.toolBar = toolBar;
    }
}