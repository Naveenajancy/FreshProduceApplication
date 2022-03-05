package com.freshproduceapp.dao;

import com.freshproduceapp.model.CustomerReceipt;
import com.freshproduceapp.model.TrackOrder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TrackOrderDao {
    //This class executes SQL queries or updates, initiating iteration over ResultSets and catching JDBC exceptions and translating them to the generic,
    // more informative exception hierarchy defined in the org.springframework.dao package.
    private JdbcTemplate jdbcTemplate;

    public TrackOrderDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createTrackOrder(CustomerReceipt trackOrder){
        String sqlQuery = "INSERT INTO track_order(customer_uuid, totalCost, datePurchased) " +
                "VALUES (?, ?, ?);";
        jdbcTemplate.update(sqlQuery,
                trackOrder.getUuid(),
                trackOrder.getTotalCost(),
                trackOrder.getDatePurchased());
        //todo return id
    }


}
