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
import org.jdesktop.swingx.JXImagePanel;
import org.jdesktop.swingx.JXTitledPanel;
import org.jdesktop.swingx.border.DropShadowBorder;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.language.TabTitleUpdater;
import org.jtheque.core.managers.persistence.able.DataContainer;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.core.utils.ui.constraints.ConstraintManager;
import org.jtheque.films.controllers.able.IFilmController;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.films.utils.Constants;
import org.jtheque.films.utils.Constants.Properties.Film;
import org.jtheque.films.view.able.IFilmView;
import org.jtheque.films.view.able.IInfosActorsView;
import org.jtheque.films.view.able.IInfosFilmView;
import org.jtheque.films.view.able.IInfosKindsView;
import org.jtheque.films.view.able.IInfosOthersView;
import org.jtheque.films.view.able.IInfosPersoView;
import org.jtheque.films.view.impl.actions.sort.AcSortFilm;
import org.jtheque.films.view.impl.fb.FilmFormBean;
import org.jtheque.films.view.impl.fb.IFilmFormBean;
import org.jtheque.films.view.impl.models.FilmsModel;
import org.jtheque.films.view.impl.models.able.IFilmsModel;
import org.jtheque.films.view.impl.toolbars.JPanelFilmToolBar;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.od.able.Type;
import org.jtheque.primary.services.able.IKindsService;
import org.jtheque.primary.services.able.ITypesService;
import org.jtheque.primary.view.impl.actions.type.AcNewType;
import org.jtheque.primary.view.impl.components.panels.JThequeTitledPanel;
import org.jtheque.primary.view.impl.models.DataContainerCachedComboBoxModel;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
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
public final class FilmView extends AbstractPrincipalDelegatedView implements IFilmView {
    public FilmView() {
        super(1, "film.data.title");
    }

    @Override
    public IFilmFormBean fillFilmFormBean() {
        IFilmFormBean fb = new FilmFormBean();

        getImplementationView().fillFormBean(fb);

        return fb;
    }

    @Override
    public void setImageOfPanel(BufferedImage image) {
        ((FilmViewImpl)getImplementationView()).setImage(image);
    }

    @Override
    public IFilmsModel getModel() {
        return (IFilmsModel) super.getModel();
    }
    
    @PostConstruct
    public void init(){
        buildInEDT();
    }
    
    @Override
    protected void buildDelegatedView() {
        FilmViewImpl impl = new FilmViewImpl();
        setView(impl);
        impl.build();
        
        getModel().addCurrentObjectListener(this);
    }

    private static final class FilmViewImpl extends AbstractPrincipalDataPanel<IFilmsModel> {
        private final IInfosKindsView panelKinds;
        private final IInfosActorsView panelActors;
        private final IInfosPersoView panelPerso;
        private final IInfosFilmView panelInfos;
        private final IInfosOthersView panelOthers;
        
        private JXTitledPanel panelFilm;
    
        private JTextField fieldTitle;
        private DataContainerCachedComboBoxModel<Type> modelType;
        private JComboBox comboType;
        private JButton buttonNewType;
        private JXImagePanel panelImage;

        private FilmViewImpl() {
            super(IFilmsService.DATA_TYPE);
            
            setModel(new FilmsModel());
            
            panelKinds = Managers.getManager(IBeansManager.class).getBean("filmKindsView");
            panelActors = Managers.getManager(IBeansManager.class).getBean("filmActorsView");
            panelPerso = Managers.getManager(IBeansManager.class).getBean("filmPersoView");
            panelInfos = Managers.getManager(IBeansManager.class).getBean("filmInfosView");
            panelOthers = Managers.getManager(IBeansManager.class).getBean("filmOthersView");
        }

        /**
         * Build the view.
         */
        private void build() {
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
            IFilmController filmController = Managers.getManager(IBeansManager.class).getBean("filmController");
            
            panelImage.addMouseListener(filmController);
            
            getTree().addTreeSelectionListener(filmController);
            
            getModel().addDisplayListListener(this);
            
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
    
            JPanelFilmToolBar toolBar = new JPanelFilmToolBar();
            
            setToolBar(toolBar);
            
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
    
            parent.add(panelFilm, parent.gbcSet(1, 0, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 1, 2, 1 - Constants.A_QUARTER, 1.0));
        }
    
        /**
         * Add the field for the type.
         *
         * @param builder The builder of the view.
         */
        private void addTypeField(PanelBuilder builder) {
            builder.addI18nLabel(Film.TYPE, builder.gbcSet(0, 2));
    
            modelType = new DataContainerCachedComboBoxModel<Type>(Managers.getManager(IBeansManager.class).<DataContainer<Type>>getBean("typesServices"));
    
            comboType = builder.addComboBox(modelType, builder.gbcSet(1, 2));
            comboType.setEnabled(false);
    
            buttonNewType = builder.addButton(new AcNewType(), builder.gbcSet(2, 2));
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
        private static void buildPanelTri(PanelBuilder parent) {
            JXTitledPanel panelTri = new JThequeTitledPanel("film.view.panel.sort.title");
            panelTri.setBorder(new DropShadowBorder());
            panelTri.setTitleFont(panelTri.getTitleFont().deriveFont(Font.BOLD));
    
            PanelBuilder builder = new PanelBuilder();
            
            JXHyperlink linkTriGenre = builder.add(new JXHyperlink(new AcSortFilm("film.view.actions.sort.kind", IKindsService.DATA_TYPE)),
                    builder.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));
            linkTriGenre.setClickedColor(linkTriGenre.getUnclickedColor());
    
            JXHyperlink linkTriType = builder.add(new JXHyperlink(new AcSortFilm("film.view.actions.sort.type", ITypesService.DATA_TYPE)),
                    builder.gbcSet(0, 1, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));
            linkTriType.setClickedColor(linkTriType.getUnclickedColor());
    
            JXHyperlink linkTriRealizer = builder.add(new JXHyperlink(new AcSortFilm("film.view.actions.sort.realizer", IRealizersService.DATA_TYPE)),
                    builder.gbcSet(0, 2, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));
            linkTriRealizer.setClickedColor(linkTriRealizer.getUnclickedColor());
    
            JXHyperlink linkTriYear = builder.add(new JXHyperlink(new AcSortFilm("film.view.actions.sort.year", "Year")),
                    builder.gbcSet(0, 3, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));
            linkTriYear.setClickedColor(linkTriYear.getUnclickedColor());
    
            panelTri.setContentContainer(builder.getPanel());
    
            parent.add(panelTri, parent.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START, Constants.A_QUARTER, 0.0));
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
    
            setTreeModel(getSorter().createInitialModel(IFilmsService.DATA_TYPE));
    
            initTree();
    
            builder.addScrolled(getTree(), builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 0, 0, 1.0, 1.0));
    
            panelList.setContentContainer(builder.getPanel());
    
            parent.add(panelList, parent.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, Constants.A_QUARTER, 1.0));
        }
    
        @Override
        public void setCurrent(Object object) {
            org.jtheque.films.persistence.od.able.Film film = (org.jtheque.films.persistence.od.able.Film)object;
            
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
        public void fillFormBean(FormBean formBean) {
            IFilmFormBean fb = (IFilmFormBean)formBean;
            
            fb.setTitle(fieldTitle.getText());
            fb.setType(modelType.getSelectedData());
    
            panelActors.fillFilm(fb);
            panelPerso.fillFilm(fb);
            panelInfos.fillFilm(fb);
            panelOthers.fillFilm(fb);
            panelKinds.fillFilm(fb);
        }

        public void setImage(Image image) {
            panelImage.setImage(image);
        }

        @Override
        public void validate(Collection<JThequeError> errors) {
            ConstraintManager.validate(Film.TITLE, fieldTitle.getText(), errors);
            ConstraintManager.validate(Film.TYPE, modelType, errors);
    
            panelKinds.validate(errors);
            panelActors.validate(errors);
            panelPerso.validate(errors);
            panelInfos.validate(errors);
            panelOthers.validate(errors);
        }
    }
}