package com.csncl.nutritracker.testhelpers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.commons.lang3.tuple.Triple;

/** TestHelpers utilities that help with unit testing. */
public class TestHelpers {

  /**
   * Mock a simple database with specified columns. One caveat with this is that we can only have
   * one value for one column with its type.
   *
   * @param columnMapping List of columns with their types and the singular value assigned to them.
   */
  public static Connection mockSimpleGetOnlyDbConnection(
      ImmutableList<Triple<String, String, String>> columnMapping) throws Exception {
    Connection mockDbConnection = mock(Connection.class);
    Statement mockStatement = mock(Statement.class);
    ResultSet mockResultSet = mock(ResultSet.class);

    for (Triple<String, String, String> columnMappingItem : columnMapping) {
      // Triplets will have the following values:
      String columnName = columnMappingItem.getLeft();
      String columnType = columnMappingItem.getMiddle();
      String columnValue = columnMappingItem.getRight();
      switch (columnType.toLowerCase()) {
        case "int":
          when(mockResultSet.getInt(columnName)).thenReturn(Integer.parseInt(columnValue));
          break;
        case "double":
          when(mockResultSet.getDouble(columnName)).thenReturn(Double.parseDouble(columnValue));
          break;
        case "string":
          when(mockResultSet.getString(columnName)).thenReturn(columnValue);
          break;
        default:
          throw new IllegalArgumentException("Invalid column type: " + columnType);
      }
    }
    // Can only limit to 1 rows for now.
    when(mockResultSet.next()).thenReturn(true, false);
    when(mockStatement.executeQuery(any())).thenReturn(mockResultSet);
    when(mockDbConnection.createStatement()).thenReturn(mockStatement);

    return mockDbConnection;
  }
}
