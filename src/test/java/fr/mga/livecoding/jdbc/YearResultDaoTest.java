package fr.mga.livecoding.jdbc;

import fr.mga.livecoding.ConnectionManager;
import fr.mga.livecoding.YearResult;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class YearResultDaoTest {

    private YearResultDao yearResultDao;

    @Before
    public void setUp() throws Exception {
        // Given
        Connection connection = ConnectionManager.getConnection();
        ConnectionManager.init(connection);
        this.yearResultDao = new YearResultDao(connection);
    }

    @Test
    public void should_retrieve_year_result() {
        // When
        List<YearResult> yearResults = yearResultDao.findYearResultBy("IT");

        // Then
        assertThat(yearResults).isNotEmpty();
        assertThat(yearResults).hasSize(12);
        assertThat(yearResults).extracting("creationDate")
                .containsExactly(LocalDate.of(2016, Month.JANUARY, 1),
                        LocalDate.of(2016, Month.FEBRUARY, 1),
                        LocalDate.of(2016, Month.MARCH, 1),
                        LocalDate.of(2016, Month.APRIL, 1),
                        LocalDate.of(2016, Month.MAY, 1),
                        LocalDate.of(2016, Month.JUNE, 1),
                        LocalDate.of(2016, Month.JULY, 1),
                        LocalDate.of(2016, Month.AUGUST, 1),
                        LocalDate.of(2016, Month.SEPTEMBER, 1),
                        LocalDate.of(2016, Month.OCTOBER, 1),
                        LocalDate.of(2016, Month.NOVEMBER, 1),
                        LocalDate.of(2016, Month.DECEMBER, 1));
    }
}
