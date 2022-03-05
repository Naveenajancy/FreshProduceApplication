package com.freshproduceapp.dao;

import com.freshproduceapp.model.CustomerReceipt;
import com.freshproduceapp.model.TrackProduct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class TrackProductDao {

    private JdbcTemplate jdbcTemplate;

    public TrackProductDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createProductTrack(TrackProduct trackProduct){
        String sqlQuery = "INSERT INTO track_product (customer_uuid, freshproduce_id, quantity, price_per_pound, quantity_price) " +
                "VALUES (?, ?, ?, ?, ? ) ;";
        //todo change into queryForObject
        jdbcTemplate.update(sqlQuery,
                trackProduct.getCustomerUUID(),
                trackProduct.getFreshProduceId(),
                trackProduct.getQuantity(),
                trackProduct.getPricePerPound(),
                trackProduct.getQuantityPrice());
        //return id
    }
}
