package com.freshproduceapp.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class FreshProduceInventoryDao {
    private final JdbcTemplate jdbcTemplate;

    public FreshProduceInventoryDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int updateFreshProduce(Integer freshProduceId, Double restockFreshProduce){
        String sqlQuery = "UPDATE public.fresh_produce SET available_lbs = ? WHERE freshproduce_id = ? ;";
        return jdbcTemplate.update(sqlQuery,restockFreshProduce, freshProduceId );
    }

}
