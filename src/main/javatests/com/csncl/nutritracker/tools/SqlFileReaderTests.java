package com.csncl.nutritracker.tools;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SqlFileReaderTests {
  private static final String TEST_SQL_ROOT_DIR = "src/main/javatests/com/csncl/nutritracker/sql/";

  private static SqlFileReader fileReader;

  @BeforeAll
  public static void setUp() {
    fileReader = new SqlFileReader(TEST_SQL_ROOT_DIR);
  }

  @Test
  public void getSqlQuery_readFileSuccessfully() {
    Optional<String> actual = fileReader.getSqlQuery("TestSql");

    assertThat(actual).isNotEmpty();
    assertThat(actual.get()).isEqualTo("SELECT * FROM NonExistingTable");
  }

  @Test
  public void getSqlQuery_fileNotFound_returnsEmpty() {
    Optional<String> actual = fileReader.getSqlQuery("NonExistentFile");

    assertThat(actual).isEmpty();
  }
}
