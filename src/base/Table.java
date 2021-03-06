/*
 * Copyright (C) 2015 abo0ody
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package base;

import base.utils.NoSchemaException;
import base.utils.ReadException;
import base.utils.ValuesException;
import java.sql.SQLException;
import java.util.ArrayList;
import static base.utils.StringUtils.breakString;
import base.utils.NoSchemaException;

/**
 * Class responsible for handling tables
 * @author abo0ody
 */
public class Table extends TableOperations {
    TableSchema tableSchema = null;
    String tableName = null;
    ArrayList<ArrayList<String>> rowData = null;
    /**
     * Constructs a table object and reads it's contents from the database if it exists
     * @param liteConn
     * @throws SQLException 
     */
    public Table(LiteConnection liteConn, String TableName) throws SQLException
    {
        //Note: liteConn is not supposed to be stored in this class because all the
        // operations will be handled by the parent class (TableOperations)
        super(liteConn);
        this.tableName = TableName;
        //this.tableSchema = new TableSchema(liteConn, this.tableName);
        
    }
    
    /**
     * Creates Table object and writes it into database
     * @param liteConn Database connection
     * @param TableName Name of the table
     * @param ts Table schema
     * @throws SQLException 
     */
    public Table(LiteConnection liteConn, String TableName, TableSchema ts) throws SQLException
    {
        super(liteConn);
        this.tableName = TableName;
        this.tableSchema = ts;
        //super.writeTable(TableName, ts);
    }
    
//    /**
//     * Dummy constructor
//     */
//    public Table() {
//        super();
//    }
    
    /**
     * Writes the table to the database.
     * @throws SQLException 
     * @throws base.utils.NoSchemaException 
     */
    public void writeTable() throws SQLException, NoSchemaException 
    {
        if(this.tableSchema != null) { 
            super.writeTable(this.tableName, this.tableSchema);
        } else throw new NoSchemaException("Attempt to write a table to database without specifying it's schema"); 
    }
    
    /**
     * Inserts a new row to the table
     * @param Values String specifying the values to be stored separated by ';' example "1; user; pass;"
     * @throws SQLException
     * @throws ValuesException 
     */
    public void insertRow(String Values) throws SQLException, ValuesException
    {
        ArrayList<String> vals = breakString(Values, ";");
        
        super.insertRow(this, vals);
    }
    
    
    /**
     * Inserts a new row to the table
     * @param Values ArrayList of strings specifying the values to be stored example: [0] = 1, [1] = user, [2] = pass
     * @throws SQLException
     * @throws ValuesException 
     */
    public void insertRow(ArrayList<String> Values) throws SQLException, ValuesException
    {
        super.insertRow(this, Values);
    }
    
    /**
     * Sets the data for the rows stored in the table object.
     * @param rowData 
     */
    public void setRowData(ArrayList<ArrayList<String>> rowData) 
    {
        this.rowData = new ArrayList<>(rowData);
    }
    
    /**
     * Gets the row data stored in the table object.
     * @return the data of the rows stored in the table object
     */
    public ArrayList<ArrayList<String>> getRowData()
    {
        return this.rowData;
    }
    
    /**
     * 
     * @return The name of the table
     */
    public String getTableName() {
        return this.tableName;
    }
    
    /**
     * Sets the name of the table
     * @param tableName The new name of the table
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    /**
     * 
     * @return The schema of the table 
     */
    public TableSchema getTableSchema() 
    {
        return this.tableSchema;
    }
    
    /**
     * Sets the schema of the table
     * @param tableSchema 
     */
    public void setTableSchema(TableSchema tableSchema)
    {
        this.tableSchema = tableSchema;
    }
    
    /**
     * Reads all the rows from the database to the memory
     * Warning: might be memory intensive, only use with small tables
     * Note: The data will not be used with normal table functionality. If you want to use it, use getRowData.
     */
    public void populateAllRowData() throws SQLException, ReadException
    {
        setRowData(super.readAllRowData(this));
    }
    
    /**
     * Returns the data stored in one row of the table.
     * @param rowIndex The index of the row.
     * @return ArrayList of strings with every element being a columns value.
     * @throws SQLException
     * @throws ReadException 
     */
    public ArrayList<String> readRow(int rowIndex) throws SQLException, ReadException
    {
        return super.readRow(this, rowIndex);
    }
    
//    /**
//     * Reads the values of multiple columns. to read the values of only one column use #readColumnValues
//     * @param columnNames The names of the columns
//     * @return A double ArrayList of strings with every element in the first ArrayList being a column, and the elements of the second one being a row's value.
//     * @throws ReadException
//     * @throws SQLException 
//     */
//    public ArrayList<ArrayList<String>> readMultipleColumnsValues(ArrayList<String> columnNames) throws ReadException, SQLException
//    {
//        return super.readMultipleColumnsValues(this, columnNames);
//    }
    
    /**
     * Reads the values stored in a single column.
     * @param columnName The name of the column.
     * @return An ArrayList with every element being a row's value.
     * @throws SQLException
     * @throws ReadException 
     */
    public ArrayList<String> readColumnValues(String columnName) throws SQLException, ReadException
    {
        return super.readColumnValues(this, columnName);
    }
    
    public void deleteTable() throws SQLException
    {
        super.deleteTable(this);
    }
}
