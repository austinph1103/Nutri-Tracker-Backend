package com.csncl.nutritracker.testhelpers;

import org.apache.commons.lang3.tuple.Triple;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * TestHelpers utilities that help with unit testing.
 */
//public class TestHelpers {

/**
 * Mock a simple database with specified columns. One caveat with this is that we can only have
 * one value for one column with its type.
 *
 * @param columnMapping List of columns with their types and the singular value assigned to them.
 */
//  public static Connection mockSimpleGetOnlyDbConnection(
//      ImmutableList<Triple<String, String, String>> columnMapping) throws Exception {
//    Connection mockDbConnection = mock(Connection.class);
//    Statement mockStatement = mock(Statement.class);
//    ResultSet mockResultSet = mock(ResultSet.class);
//
//    for (Triple<String, String, String> columnMappingItem : columnMapping) {
//      // Triplets will have the following values:
//      String columnName = columnMappingItem.getLeft();
//      String columnType = columnMappingItem.getMiddle();
//      String columnValue = columnMappingItem.getRight();
//      switch (columnType.toLowerCase()) {
//        case "int":
//          when(mockResultSet.getInt(columnName)).thenReturn(Integer.parseInt(columnValue));
//          break;
//        case "double":
//          when(mockResultSet.getDouble(columnName)).thenReturn(Double.parseDouble(columnValue));
//          break;
//        case "string":
//          when(mockResultSet.getString(columnName)).thenReturn(columnValue);
//          break;
//        default:
//          throw new IllegalArgumentException("Invalid column type: " + columnType);
//      }
//    }
//    // Can only limit to 1 rows for now.
//    when(mockResultSet.next()).thenReturn(true, false);
//    when(mockStatement.executeQuery(any())).thenReturn(mockResultSet);
//    when(mockDbConnection.createStatement()).thenReturn(mockStatement);
//
//    return mockDbConnection;
//  }
//}
public class TestHelpers {
    public static Connection mockSimpleGetOnlyDbConnection(List<Triple<String, String, String>> columnMapping) {
        ResultSet mockResultSet = mock(ResultSet.class);
        try {
            for (Triple<String, String, String> column : columnMapping) {
                switch (column.getMiddle()) {
                    case "string":
                        when(mockResultSet.getString(column.getLeft())).thenReturn(column.getRight());
                        break;
                    case "int":
                        when(mockResultSet.getInt(column.getLeft())).thenReturn(Integer.parseInt(column.getRight()));
                        break;
                    case "double":
                        when(mockResultSet.getDouble(column.getLeft())).thenReturn(Double.parseDouble(column.getRight()));
                        break;
                }
            }
            when(mockResultSet.next()).thenReturn(true).thenReturn(false);
            Statement mockStatement = mock(Statement.class);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
            Connection mockConnection = mock(Connection.class);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            return mockConnection;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to mock database connection", e);
        }
    }
}


