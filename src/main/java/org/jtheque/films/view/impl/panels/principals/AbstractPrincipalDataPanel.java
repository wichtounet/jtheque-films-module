package org.jtheque.films.view.impl.panels.principals;

import org.jdesktop.swingx.JXTree;
import org.jtheque.core.managers.view.able.components.IModel;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.view.able.ToolbarView;
import org.jtheque.primary.view.impl.components.panels.PrincipalDataPanel;
import org.jtheque.primary.view.impl.renderers.JThequeTreeCellRenderer;
import org.jtheque.primary.view.impl.sort.SortManager;

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

public abstract class AbstractPrincipalDataPanel<M extends IModel> extends PrincipalDataPanel<M> {
    private ToolbarView toolBar;
    private JXTree tree;

    private final String dataType;

    private static final SortManager SORTER = new SortManager();
    
    public AbstractPrincipalDataPanel(String dataType) {
        super();
        
        this.dataType = dataType;
    }

    @Override
    public final Object getImpl() {
        return this;
    }

    @Override
    public final ToolbarView getToolbarView() {
        return toolBar;
    }

    @Override
    public void clear() {
        //Nothing to do by default
    }
    
    @Override
    protected final JXTree getTree() {
        return tree;
    }

    @Override
    public final String getDataType() {
        return dataType;
    }
    
    final void setToolBar(ToolbarView toolBar) {
        this.toolBar = toolBar;
    }

    final void initTree(){
        tree = new JXTree(getTreeModel());
        tree.setCellRenderer(new JThequeTreeCellRenderer());
        tree.putClientProperty("JTree.lineStyle", "None");
    }

    static SortManager getSorter() {
        return SORTER;
    }

    public abstract void fillFormBean(FormBean fb);

    public abstract void setCurrent(Object object);
}