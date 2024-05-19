package com.csncl.nutritracker.tools;

import com.google.common.annotations.VisibleForTesting;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 * Utility class that reads SQL query from specific files which allows version control over SQL
 * queries being used in this project.
 */
@Service
public class SqlFileReader {
  private static final String DEFAULT_SQL_ROOT_DIR = "src/main/java/com/csncl/nutritracker/sql/";
  private static final Logger logger = Logger.getLogger(SqlFileReader.class.getName());

  private final String sqlRootDir;

  public SqlFileReader() {
    sqlRootDir = DEFAULT_SQL_ROOT_DIR;
  }

  @VisibleForTesting
  SqlFileReader(String sqlRootDir) {
    this.sqlRootDir = sqlRootDir;
  }

  /** Reads sql query from a file to be executed. */
  public Optional<String> getSqlQuery(String queryName) {
    Path filePath = Paths.get(sqlRootDir, queryName + ".sql");

    try {
      return Optional.of(Files.readString(filePath).trim());
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Error reading file: {0}", filePath.toString());
      e.printStackTrace(System.err);
    }

    return Optional.empty();
  }
}
