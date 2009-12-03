package org.jtheque.films.view.impl.models.table;

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
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.managers.persistence.able.DataListener;
import org.jtheque.core.managers.persistence.able.Entity;
import org.jtheque.films.persistence.dao.able.IDaoFilms;
import org.jtheque.primary.od.able.Lending;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.services.able.ILendingsService;
import org.jtheque.utils.bean.IntDate;
import org.jtheque.utils.collections.CollectionUtils;

import javax.annotation.Resource;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * A table model to display lendings of film.
 *
 * @author Baptiste Wicht
 */
public final class LendingsTableModel extends AbstractTableModel implements TableModelListener, DataListener {
    private static final long serialVersionUID = 1L;

    /**
     * The columns of the table.
     *
     * @author Baptiste Wicht
     */
    private interface Columns {
        int ID = 0;
        int FILM = 1;
        int BORROWER = 2;
        int DATE = 3;
    }

    private String[] headers;

    private List<Lending> lendings;

    @Resource
    private ILendingsService lendingsService;

    @Resource
    private IDaoFilms daoFilms;

    /**
     * Construct a new <code>LendingsTableModel</code>.
     */
    public LendingsTableModel() {
        super();

        addTableModelListener(this);

        Managers.getManager(IBeansManager.class).inject(this);

        lendingsService.addDataListener(this);

        lendings = CollectionUtils.copyOf(lendingsService.getLendings());
    }

    /**
     * Set the headers of the table.
     *
     * @param headers The headers of the table.
     */
    public void setHeader(String[] headers) {
        this.headers = headers.clone();

        fireTableStructureChanged();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public int getRowCount() {
        return lendingsService.getLendings().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value;

        switch (columnIndex) {
            case Columns.ID:
                value = lendings.get(rowIndex).getId();
                break;
            case Columns.FILM:
                value = daoFilms.getFilm(lendings.get(rowIndex).getTheOther());
                break;
            case Columns.BORROWER:
                value = lendings.get(rowIndex).getThePerson();
                break;
            case Columns.DATE:
                value = lendings.get(rowIndex).getDate();
                break;
            default:
                value = "";
                break;
        }

        return value;
    }

    @Override
    public void setValueAt(Object value, int column, int row) {
        if (value != null) {
            Lending lending = lendingsService.getLending(row);

            switch (column) {
                case Columns.FILM:
                    lending.setTheOther(((Entity) value).getId());
                    break;
                case Columns.BORROWER:
                    lending.setThePerson((Person) value);
                    break;
                case Columns.DATE:
                    lending.setDate(new IntDate((Integer) value));
                    break;
                default:
                    Managers.getManager(ILoggingManager.class).getLogger(getClass()).debug("Anormal entry in switch clause. ");
                    break;
            }

            fireTableCellUpdated(column, row);
        }
    }

    @Override
    public String getColumnName(int column) {
        return headers[column];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public void tableChanged(TableModelEvent event) {
        if (event.getType() == TableModelEvent.UPDATE &&
                event.getColumn() != -1 &&
                event.getFirstRow() == event.getLastRow()) {
            lendingsService.save(lendingsService.getLending(event.getFirstRow()));
        }
    }

    @Override
    public void dataChanged() {
        lendings = CollectionUtils.copyOf(lendingsService.getLendings());
        fireTableDataChanged();
    }
}