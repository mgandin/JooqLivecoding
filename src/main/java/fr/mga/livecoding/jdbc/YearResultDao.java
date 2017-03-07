package fr.mga.livecoding.jdbc;

import fr.mga.livecoding.YearResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class YearResultDao {

    private static String SELECT_YEAR_RESULT = "select departement,manager,netprofit,operatingexpense,turnover,creation_date "
            + "from year_result where departement=? order by creation_date asc";

    private final Connection connection;

    public YearResultDao(Connection connection) {
        this.connection = connection;
    }

    public List<YearResult> findYearResultBy(String departementToFind) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_YEAR_RESULT)) {
            List<YearResult> results = new ArrayList<>();
            preparedStatement.setString(1, departementToFind);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String departement = resultSet.getString("departement");
                String manager = resultSet.getString("manager");
                Double netProfit = resultSet.getDouble("netprofit");
                Double operatingExpense = resultSet.getDouble("operatingexpense");
                Double turnover = resultSet.getDouble("turnover");
                Date date = resultSet.getDate("creation_date");
                LocalDate localDate = Instant.ofEpochMilli(date.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                results.add(new YearResult(departement, manager, netProfit, operatingExpense, turnover, localDate));
            }
            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
