package com.lesfurets.db.dao;

import com.lefurets.db.tables.YearResult;
import com.lesfurets.db.YearReport;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.lefurets.db.tables.YearResult.YEAR_RESULT;
import static java.util.stream.Collectors.toList;

public class YearResultDao {
    private Connection connection;

    public YearResultDao(Connection connection) {
        this.connection = connection;
    }

    public List<YearReport> findYearResultBy(String departement) {
        return DSL.using(connection)
                .select(YEAR_RESULT.DEPARTEMENT,
                        YEAR_RESULT.MANAGER,
                        YEAR_RESULT.NETPROFIT,
                        YEAR_RESULT.OPERATINGEXPENSE,
                        YEAR_RESULT.TURNOVER,
                        YEAR_RESULT.CREATION_DATE)
                .from(YEAR_RESULT)
                .where(YEAR_RESULT.DEPARTEMENT.eq(departement))
                .orderBy(YEAR_RESULT.CREATION_DATE.asc())
                .stream()
                .map(r -> new YearReport(r.get(YEAR_RESULT.DEPARTEMENT),
                        r.get(YEAR_RESULT.MANAGER),
                        r.get(YEAR_RESULT.NETPROFIT),
                        r.get(YEAR_RESULT.OPERATINGEXPENSE),
                        r.get(YEAR_RESULT.TURNOVER),
                        r.get(YEAR_RESULT.CREATION_DATE, LocalDate.class)))
                .collect(toList());
    }
}
