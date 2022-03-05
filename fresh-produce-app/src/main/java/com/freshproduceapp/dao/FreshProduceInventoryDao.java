package com.freshproduceapp.dao;

import com.freshproduceapp.model.FreshProduce;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class FreshProduceInventoryDao {
    private final JdbcTemplate jdbcTemplate;

    public FreshProduceInventoryDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<FreshProduce> retrieveAllFreshProduceInventoryItems() {
        String query = "SELECT * FROM fresh_produce order by freshproduce_id";
        return jdbcTemplate.query(query, new FreshProduceRowMapper());
    }

    public FreshProduce selectItemById(Integer freshProduceId){
        String sqlQuery = "SELECT * FROM fresh_produce WHERE freshproduce_id = ? ;";
        return jdbcTemplate.query(sqlQuery, new Object[] {freshProduceId}, new FreshProduceRowMapper()).get(0);
    }

    public int updateItem(Integer freshProduceId, Double remainingPounds){
        String sqlQuery = "UPDATE fresh_produce SET available_lbs = ? WHERE freshproduce_id = ? ;";
        return jdbcTemplate.update(sqlQuery,remainingPounds, freshProduceId );
    }




    private static class FreshProduceRowMapper implements RowMapper<FreshProduce>{

        @Override
        public FreshProduce mapRow(ResultSet rs, int rowNum) throws SQLException {
            FreshProduce freshProduce = new FreshProduce();
            freshProduce.setFreshProduceId(rs.getInt("freshproduce_id"));
            freshProduce.setFreshProduceName(rs.getString("freshproduce_name"));
            freshProduce.setPricePerPound(rs.getBigDecimal("price_per_lb"));
            freshProduce.setAvailablePounds(rs.getDouble("available_lbs"));
            return freshProduce;

        }
    }

}
