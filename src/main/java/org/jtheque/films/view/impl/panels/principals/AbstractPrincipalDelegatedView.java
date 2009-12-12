package org.jtheque.films.view.impl.panels.principals;

import org.jtheque.core.managers.view.able.components.IModel;
import org.jtheque.core.managers.view.able.components.TabComponent;
import org.jtheque.films.view.impl.AbstractDelegatedView;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.view.able.PrincipalDataView;
import org.jtheque.primary.view.able.ToolbarView;
import org.jtheque.primary.view.impl.listeners.CurrentObjectListener;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;

import javax.swing.JComponent;

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

public abstract class AbstractPrincipalDelegatedView extends AbstractDelegatedView implements PrincipalDataView, CurrentObjectListener, TabComponent {
    private AbstractPrincipalDataPanel<? extends IModel> view;

    private final int position;
    private final String titleKey;

    protected AbstractPrincipalDelegatedView(int position, String titleKey) {
        super();
        
        this.position = position;
        this.titleKey = titleKey;
    }

    @Override
    public Integer getPosition() {
        return position;
    }

    @Override
    public String getTitleKey() {
        return titleKey;
    }
    
    @Override
    public JComponent getComponent() {
        return view; 
    }

    @Override
    public void select(Data data) {
        view.select(data);
    }

    @Override
    public void sort(String sort) {
        view.sort(sort);
    }

    @Override
    public void resort() {
        view.resort();
    }

    @Override
    public void selectFirst() {
        view.selectFirst();
    }

    @Override
    public ToolbarView getToolbarView() {
        return view.getToolbarView();
    }
    
    @Override
    public IModel getModel() {
        return view.getModel();
    }

    @Override
    public void clear() {
        view.clear();
    }
    
    @Override
    public void objectChanged(ObjectChangedEvent event) {
        view.setCurrent(event.getObject());
    }

    @Override
    public AbstractPrincipalDataPanel<? extends IModel> getImplementationView() {
        return view;
    }

    public void setView(AbstractPrincipalDataPanel<? extends IModel> view) {
        this.view = view;
    }
}