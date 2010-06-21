package org.jtheque.films;

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
import org.jtheque.core.managers.collection.ICollectionsService;
import org.jtheque.core.managers.error.IErrorManager;
import org.jtheque.core.managers.error.InternationalizedError;
import org.jtheque.core.managers.feature.Feature;
import org.jtheque.core.managers.feature.Feature.FeatureType;
import org.jtheque.core.managers.feature.IFeatureManager;
import org.jtheque.core.managers.feature.IFeatureManager.CoreFeature;
import org.jtheque.core.managers.file.IFileManager;
import org.jtheque.core.managers.file.able.FileType;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.managers.module.annotations.Module;
import org.jtheque.core.managers.module.annotations.Plug;
import org.jtheque.core.managers.module.annotations.PrePlug;
import org.jtheque.core.managers.module.annotations.UnPlug;
import org.jtheque.core.managers.module.beans.CollectionBasedModule;
import org.jtheque.core.managers.schema.ISchemaManager;
import org.jtheque.core.managers.schema.Schema;
import org.jtheque.core.managers.state.IStateManager;
import org.jtheque.core.managers.state.StateException;
import org.jtheque.core.managers.update.IUpdateManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.components.TabComponent;
import org.jtheque.core.managers.view.impl.components.config.ConfigTabComponent;
import org.jtheque.core.utils.ui.constraints.ConstraintManager;
import org.jtheque.core.utils.ui.constraints.MaxLengthConstraint;
import org.jtheque.core.utils.ui.constraints.NotNullConstraint;
import org.jtheque.films.persistence.FilmsSchema;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.films.services.impl.utils.closures.LendingCheckClosure;
import org.jtheque.films.services.impl.utils.config.ConfigManager;
import org.jtheque.films.services.impl.utils.config.Configuration;
import org.jtheque.films.services.impl.utils.file.backup.JTDBackupWriter;
import org.jtheque.films.services.impl.utils.file.backup.XMLBackupWriter;
import org.jtheque.films.services.impl.utils.file.restore.DBV3BackupReader;
import org.jtheque.films.services.impl.utils.file.restore.DBV4BackupReader;
import org.jtheque.films.services.impl.utils.file.restore.JTDBackupReader;
import org.jtheque.films.services.impl.utils.file.restore.XMLBackupReader;
import org.jtheque.films.services.impl.utils.web.GettersUpdatable;
import org.jtheque.films.utils.Constants;
import org.jtheque.films.utils.Constants.Properties.Film;
import org.jtheque.films.utils.Constants.Properties.Kind;
import org.jtheque.films.utils.Constants.Properties.Person;
import org.jtheque.films.utils.Constants.Properties.Publication;
import org.jtheque.films.utils.Constants.Properties.Saga;
import org.jtheque.films.utils.Constants.Properties.Type;
import org.jtheque.primary.PrimaryUtils;
import org.jtheque.primary.services.able.ILendingsService;
import org.jtheque.primary.utils.DataTypeManager;
import org.jtheque.primary.view.impl.choice.ChoiceAction;
import org.jtheque.primary.view.impl.choice.ChoiceActionFactory;
import org.jtheque.primary.view.impl.sort.Sorter;
import org.jtheque.primary.view.impl.sort.SorterFactory;
import org.jtheque.utils.collections.ArrayUtils;
import org.jtheque.utils.collections.CollectionUtils;

/**
 * A JTheque Module for managing films.
 *
 * @author Baptiste Wicht
 */
@Module(id = "jtheque-films-module", i18n = "classpath:org/jtheque/films/i18n/films", version = "1.4.2", core = "2.0.2",
        jarFile = "jtheque-films-module-1.4.2.jar", updateURL = "http://jtheque.developpez.com/public/versions/FilmsModule.versions")
public final class FilmsModule implements CollectionBasedModule, IFilmsModule {
    private ConfigManager config;

    private final ConfigTabComponent[] configTabComponents;
    private final TabComponent[] tabComponents;
    private final Sorter[] sorters;
    private final ChoiceAction[] choiceActions;

    //Features
    private Feature sitesFeature;
    private Feature refreshFeature;
    private Feature printFeature;
    private Feature exportFeature;
    private Feature importFolderFeature;
    private Feature importFeature;
    private Feature searchFeature;
    private Feature filmsFeature;
    private Feature actorsFeature;
    private Feature realizersFeature;
    private Feature lendingsFeature;
    private Feature othersFeature;
    private Feature publicationFeature;
    private Feature videoFeature;
    private Feature coverFeature;

    //Writers/Readers
    private JTDBackupWriter jtdWriter;
    private XMLBackupWriter xmlWriter;
    private XMLBackupReader xmlReader;
    private JTDBackupReader jtdReader;
    private DBV3BackupReader dbv3Reader;
    private DBV4BackupReader dbv4Reader;

    private Schema schema;

    /**
     * Construct a new FilmsModule.
     *
     * @param choiceActions       The choice actions of the module.
     * @param tabComponents       The tab components of the module.
     * @param sorters             The sorters of the module.
     * @param configTabComponents The config tab components.
     */
    public FilmsModule(ChoiceAction[] choiceActions, TabComponent[] tabComponents, Sorter[] sorters, ConfigTabComponent[] configTabComponents) {
        super();

        this.choiceActions = ArrayUtils.copyOf(choiceActions);
        this.tabComponents = ArrayUtils.copyOf(tabComponents);
        this.sorters = ArrayUtils.copyOf(sorters);
        this.configTabComponents = ArrayUtils.copyOf(configTabComponents);
    }

    /**
     * Pre plug the module.
     */
    @PrePlug
    public void prePlug() {
        Managers.getManager(IUpdateManager.class).registerUpdatable(GettersUpdatable.getInstance());

        PrimaryUtils.setPrimaryImpl("Films");
        PrimaryUtils.prePlug();

        schema = new FilmsSchema();

        Managers.getManager(ISchemaManager.class).registerSchema(schema);
    }

    /**
     * Plug the module.
     */
    @Plug
    public void plug() {
        loadConfiguration();

        PrimaryUtils.plug();

        DataTypeManager.bindDataTypeToKey(IFilmsService.DATA_TYPE, "film.data.title");
        DataTypeManager.bindDataTypeToKey(IActorService.DATA_TYPE, "actor.data.title");
        DataTypeManager.bindDataTypeToKey(IRealizersService.DATA_TYPE, "realizer.data.title");

        for (Sorter sorter : sorters) {
            SorterFactory.getInstance().addSorter(sorter);
        }

        addFeatures();

        for (ConfigTabComponent component : configTabComponents) {
            Managers.getManager(IViewManager.class).addConfigTabComponent(component);
        }
    }

    /**
     * Load the configuration.
     */
    private void loadConfiguration() {
        config = Managers.getManager(IStateManager.class).getState(ConfigManager.class);

        if (config == null) {
            try {
                config = Managers.getManager(IStateManager.class).createState(ConfigManager.class);
            } catch (StateException e) {
                Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
                config = new ConfigManager();
                Managers.getManager(IErrorManager.class).addError(new InternationalizedError("error.loading.configuration"));
            }

            config.setDefaults();
        }
    }

    /**
     * Add the features.
     */
    private void addFeatures() {
        IFeatureManager manager = Managers.getManager(IFeatureManager.class);

        addHelpFeatures(manager);
        addFileFeatures(manager);
        addEmptyFeatures(manager);
        addLendingsFeature(manager);
        addOthersFeature(manager);

        videoFeature = manager.addSubFeature(manager.getFeature(CoreFeature.ADVANCED), "openVideoViewAction", FeatureType.SEPARATED_ACTION, 500);
    }

    /**
     * Add the help features.
     *
     * @param manager The feature manager.
     */
    private void addHelpFeatures(IFeatureManager manager) {
        sitesFeature = manager.addSubFeature(manager.getFeature(CoreFeature.HELP), "openSitesViewAction", FeatureType.ACTION, 2);
    }

    /**
     * Add the lendings features.
     *
     * @param manager The feature manager.
     */
    private void addLendingsFeature(IFeatureManager manager) {
        lendingsFeature = manager.createFeature(400, FeatureType.PACK, "menu.jtheque.lendings");

        manager.addSubFeature(lendingsFeature, "openLendingsViewAction", FeatureType.ACTION, 1, Constants.IMAGE_BASE_NAME, "lendings");
        manager.addSubFeature(lendingsFeature, "lendFilmAction", FeatureType.ACTION, 2);
        manager.addSubFeature(lendingsFeature, "returnFilmAction", FeatureType.ACTION, 3);

        manager.addFeature(lendingsFeature);
    }

    /**
     * Add the realizers features.
     *
     * @param manager The feature manager.
     */
    private void addEmptyFeatures(IFeatureManager manager) {
        manager.addFeature(realizersFeature = manager.createFeature(300, FeatureType.PACK, "menu.jtheque.realizers"));
        manager.addFeature(actorsFeature = manager.createFeature(200, FeatureType.PACK, "menu.jtheque.actors"));
        manager.addFeature(filmsFeature = manager.createFeature(100, FeatureType.PACK, "menu.jtheque.films"));
    }

    /**
     * Add the file features.
     *
     * @param manager The feature manager.
     */
    private void addFileFeatures(IFeatureManager manager) {
        refreshFeature = manager.addSubFeature(manager.getFeature(CoreFeature.FILE), "refreshListAction", FeatureType.ACTION, 100);

        printFeature = manager.addSubFeature(manager.getFeature(CoreFeature.FILE), "printListAction", FeatureType.ACTION, 101);

        publicationFeature = manager.addSubFeature(manager.getFeature(CoreFeature.FILE), "openPublicationViewAction", FeatureType.ACTION, 102);

        addExportFeatures(manager);
        addImportFeatures(manager);

        coverFeature = manager.addSubFeature(manager.getFeature(CoreFeature.FILE), "openCoverViewAction", FeatureType.SEPARATED_ACTION, 105);

        importFolderFeature = manager.addSubFeature(manager.getFeature(CoreFeature.FILE), "openAutoImportViewAction", FeatureType.ACTION, 106);

        searchFeature = manager.createFeature(100, FeatureType.ACTIONS, "menu.jtheque.edit.find");

        addSubFeatures(manager, searchFeature, "searchFilmAction", "searchActorAction", "searchRealizerAction");

        manager.getFeature(CoreFeature.EDIT).addSubFeature(searchFeature);
    }

    /**
     * Add the export feature.
     *
     * @param manager The feature manager.
     */
    private void addExportFeatures(IFeatureManager manager) {
        exportFeature = manager.createFeature(103, FeatureType.SEPARATED_ACTIONS, "menu.jtheque.file.export");
        exportFeature.setBaseName(Constants.IMAGE_BASE_NAME);
        exportFeature.setIcon("export");

        addSubFeatures(manager, exportFeature, "exportToExcelAction", "exportToXMLAction", "exportToHTMLAction",
                "exportToPDFAction", "exportToTXTAction", "exportToJTFEAction", "exportToCSVAction", "exportToRTFAction");

        manager.getFeature(CoreFeature.FILE).addSubFeature(exportFeature);
    }

    /**
     * Add the import feature.
     *
     * @param manager The feature manager.
     */
    private void addImportFeatures(IFeatureManager manager) {
        importFeature = manager.createFeature(104, FeatureType.ACTIONS, "menu.jtheque.file.import");
        importFeature.setBaseName(Constants.IMAGE_BASE_NAME);
        importFeature.setIcon("import");

        addSubFeatures(manager, importFeature, "importFromXMLAction", "importFromJTFAction", "importFromJTFEAction");

        manager.getFeature(CoreFeature.FILE).addSubFeature(importFeature);
    }

    /**
     * Add the others feature.
     *
     * @param manager The feature manager.
     */
    private void addOthersFeature(IFeatureManager manager) {
        othersFeature = manager.createFeature(500, FeatureType.PACK, "menu.jtheque.others");

        addNewFeature(manager);
        addDeleteFeature(manager);
        addEditFeature(manager);

        manager.addFeature(othersFeature);
    }

    /**
     * Add the new feature.
     *
     * @param manager The feature manager.
     */
    private void addNewFeature(IFeatureManager manager) {
        Feature newFeature = manager.createFeature(1, FeatureType.ACTIONS, "menu.jtheque.others.new");
        newFeature.setBaseName(Constants.IMAGE_BASE_NAME);
        newFeature.setIcon("add_others");

        addSubFeatures(manager, newFeature, "newKindAction", "newTypeAction", "newLanguageMenuAction",
                "newBorrowerMenuAction", "newCountryMenuAction", "newSagaAction");

        othersFeature.addSubFeature(newFeature);
    }

    /**
     * Add the delete feature.
     *
     * @param manager The feature manager.
     */
    private void addDeleteFeature(IFeatureManager manager) {
        Feature deleteFeature = manager.createFeature(2, FeatureType.ACTIONS, "menu.jtheque.others.delete");
        deleteFeature.setBaseName(Constants.IMAGE_BASE_NAME);
        deleteFeature.setIcon("delete_others");

        addSubFeatures(manager, deleteFeature, "deleteKindMenuAction", "deleteTypeAction", "deleteLanguageMenuAction",
                "deleteCountryMenuAction", "deleteBorrowerMenuAction", "deleteSagaMenuAction");

        othersFeature.addSubFeature(deleteFeature);
    }

    /**
     * Add the edit feature.
     *
     * @param manager The feature manager.
     */
    private void addEditFeature(IFeatureManager manager) {
        Feature editFeature = manager.createFeature(3, FeatureType.ACTIONS, "menu.jtheque.others.modify");
        editFeature.setBaseName(Constants.IMAGE_BASE_NAME);
        editFeature.setIcon("edit_others");

        addSubFeatures(manager, editFeature, "editKindMenuAction", "editTypeAction", "editLanguageMenuAction",
                "editBorrowerMenuAction", "editCountryMenuAction", "editSagaMenuAction");

        othersFeature.addSubFeature(editFeature);
    }

    private static void addSubFeatures(IFeatureManager manager, Feature parent, String... actions) {
        for (int i = 0; i < actions.length; i++) {
            manager.addSubFeature(parent, actions[i], FeatureType.ACTION, i + 1);
        }
    }

    @Override
    public boolean chooseCollection(String collection, String password, boolean create) {
        ICollectionsService collectionsService = Managers.getManager(IBeansManager.class).getBean("collectionsService");

        if (create) {
            collectionsService.createCollectionAndUse(collection, password);
        } else if (!collectionsService.login(collection, password)) {
            return false;
        }

        return true;
    }

    @Override
    public void plugCollection() {
        for (ChoiceAction action : choiceActions) {
            ChoiceActionFactory.addChoiceAction(action);
        }

        for (TabComponent component : tabComponents) {
            Managers.getManager(IViewManager.class).addTabComponent(component);
        }

        configureBackupAndRestore();

        configureViewConstraints();

        if (getConfiguration().mustControlLendingsOnStartup()) {
            verifyLendings();
        }
    }

    /**
     * Verify lendings.
     */
    private static void verifyLendings() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                CollectionUtils.forAllDo(
                        Managers.getManager(IBeansManager.class).<ILendingsService>getBean("lendingsService").getLendings(),
                        new LendingCheckClosure());
            }
        }).start();
    }

    /**
     * Configure the backup and restore.
     */
    private void configureBackupAndRestore() {
        jtdWriter = new JTDBackupWriter();
        xmlWriter = new XMLBackupWriter();

        xmlReader = new XMLBackupReader();
        jtdReader = new JTDBackupReader();
        dbv3Reader = new DBV3BackupReader();
        dbv4Reader = new DBV4BackupReader();

        Managers.getManager(IFileManager.class).registerBackupWriter(FileType.JTD, jtdWriter);
        Managers.getManager(IFileManager.class).registerBackupWriter(FileType.XML, xmlWriter);

        Managers.getManager(IFileManager.class).registerBackupReader(FileType.XML, xmlReader);
        Managers.getManager(IFileManager.class).registerBackupReader(FileType.JTD, jtdReader);
        Managers.getManager(IFileManager.class).registerBackupReader(FileType.V3, dbv3Reader);
        Managers.getManager(IFileManager.class).registerBackupReader(FileType.V4, dbv4Reader);
    }

    /**
     * Configure the view constraints validation.
     */
    private static void configureViewConstraints() {
        ConstraintManager.addConstraint(Person.NAME, new MaxLengthConstraint(Person.NAME_MAX_LENGTH, Person.NAME, false, false));
        ConstraintManager.addConstraint(Person.FIRST_NAME, new MaxLengthConstraint(Person.FIRST_NAME_MAX_LENGTH, Person.FIRST_NAME, false, false));

        ConstraintManager.addConstraint(Film.TITLE, new MaxLengthConstraint(Film.TITLE_MAX_LENGTH, Film.TITLE, false, false));
        ConstraintManager.addConstraint(Film.YEAR, new MaxLengthConstraint(Film.YEAR_MAX_LENGTH, Film.YEAR, false, true));
        ConstraintManager.addConstraint(Film.DURATION, new MaxLengthConstraint(Film.DURATION_MAX_LENGTH, Film.DURATION, false, true));
        ConstraintManager.addConstraint(Film.RESUME, new MaxLengthConstraint(Film.RESUME_MAX_LENGTH, Film.RESUME, false, false));
        ConstraintManager.addConstraint(Film.COMMENT, new MaxLengthConstraint(Film.COMMENT_MAX_LENGTH, Film.COMMENT, false, false));
        ConstraintManager.addConstraint(Film.IMAGE, new MaxLengthConstraint(Film.IMAGE_MAX_LENGTH, Film.IMAGE, false, false));
        ConstraintManager.addConstraint(Film.FILE_PATH, new MaxLengthConstraint(Film.FILE_PATH_MAX_LENGTH, Film.FILE_PATH, false, false));
        ConstraintManager.addConstraint(Film.KIND, new NotNullConstraint(Film.KIND));
        ConstraintManager.addConstraint(Film.TYPE, new NotNullConstraint(Film.TYPE));

        ConstraintManager.addConstraint("saga.name", new MaxLengthConstraint(Saga.NAME_MAX_LENGTH, Saga.NAME, false, false));
        ConstraintManager.addConstraint("kind.name", new MaxLengthConstraint(Kind.NAME_MAX_LENGTH, Kind.NAME, false, false));
        ConstraintManager.addConstraint("type.name", new MaxLengthConstraint(Type.NAME_MAX_LENGTH, Type.NAME, false, false));

        ConstraintManager.addConstraint(Publication.HOST, new MaxLengthConstraint(Publication.HOST_MAX_LENGTH, Publication.HOST, false, false));
        ConstraintManager.addConstraint(Publication.USER, new MaxLengthConstraint(Publication.USER_MAX_LENGTH, Publication.USER, true, false));
        ConstraintManager.addConstraint(Publication.PASSWORD, new MaxLengthConstraint(Publication.PASSWORD_MAX_LENGTH, Publication.PASSWORD, true, false));
        ConstraintManager.addConstraint(Publication.PATH, new MaxLengthConstraint(Publication.PATH_MAX_LENGTH, Publication.PATH, false, false));
        ConstraintManager.addConstraint(Publication.PORT, new MaxLengthConstraint(Publication.PORT_MAX_LENGTH, Publication.PORT, false, true));
    }

    /**
     * Un plug the module.
     */
    @UnPlug
    public void unplug() {
        SorterFactory.getInstance().removeSorters(sorters);

        PrimaryUtils.unplug();

        for (ConfigTabComponent component : configTabComponents) {
            Managers.getManager(IViewManager.class).removeConfigTabComponent(component);
        }

        for (TabComponent component : tabComponents) {
            Managers.getManager(IViewManager.class).removeTabComponent(component);
        }

        ChoiceActionFactory.removeChoiceActions(choiceActions);

        removeFeatures();

        unregisterBackupObjects();

        DataTypeManager.unbindDataType(IFilmsService.DATA_TYPE);
        DataTypeManager.unbindDataType(IActorService.DATA_TYPE);
        DataTypeManager.unbindDataType(IRealizersService.DATA_TYPE);

        Managers.getManager(ISchemaManager.class).unregisterSchema(schema);
    }

    /**
     * Unregister the backup objects.
     */
    private void unregisterBackupObjects() {
        Managers.getManager(IFileManager.class).unregisterBackupWriter(FileType.JTD, jtdWriter);
        Managers.getManager(IFileManager.class).unregisterBackupWriter(FileType.XML, xmlWriter);

        Managers.getManager(IFileManager.class).unregisterBackupReader(FileType.XML, xmlReader);
        Managers.getManager(IFileManager.class).unregisterBackupReader(FileType.JTD, jtdReader);
        Managers.getManager(IFileManager.class).unregisterBackupReader(FileType.V3, dbv3Reader);
        Managers.getManager(IFileManager.class).unregisterBackupReader(FileType.V4, dbv4Reader);
    }

    /**
     * Remove the features.
     */
    private void removeFeatures() {
        Managers.getManager(IFeatureManager.class).getFeature(CoreFeature.HELP).removeSubFeature(sitesFeature);
        Managers.getManager(IFeatureManager.class).getFeature(CoreFeature.FILE).removeSubFeature(refreshFeature);
        Managers.getManager(IFeatureManager.class).getFeature(CoreFeature.FILE).removeSubFeature(printFeature);
        Managers.getManager(IFeatureManager.class).getFeature(CoreFeature.FILE).removeSubFeature(importFeature);
        Managers.getManager(IFeatureManager.class).getFeature(CoreFeature.FILE).removeSubFeature(exportFeature);
        Managers.getManager(IFeatureManager.class).getFeature(CoreFeature.FILE).removeSubFeature(importFolderFeature);
        Managers.getManager(IFeatureManager.class).getFeature(CoreFeature.EDIT).removeSubFeature(searchFeature);
        Managers.getManager(IFeatureManager.class).getFeature(CoreFeature.EDIT).removeSubFeature(publicationFeature);
        Managers.getManager(IFeatureManager.class).getFeature(CoreFeature.EDIT).removeSubFeature(coverFeature);
        Managers.getManager(IFeatureManager.class).getFeature(CoreFeature.ADVANCED).removeSubFeature(videoFeature);
        Managers.getManager(IFeatureManager.class).removeFeature(realizersFeature);
        Managers.getManager(IFeatureManager.class).removeFeature(actorsFeature);
        Managers.getManager(IFeatureManager.class).removeFeature(filmsFeature);
        Managers.getManager(IFeatureManager.class).removeFeature(othersFeature);
        Managers.getManager(IFeatureManager.class).removeFeature(lendingsFeature);
    }

    @Override
    public Configuration getConfiguration() {
        return config;
    }

    @Override
    public Feature getFilmsFeature() {
        return filmsFeature;
    }

    @Override
    public Feature getActorsFeature() {
        return actorsFeature;
    }

    @Override
    public Feature getRealizersFeature() {
        return realizersFeature;
    }
}