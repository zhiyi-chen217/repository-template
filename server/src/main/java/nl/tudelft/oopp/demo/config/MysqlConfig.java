package nl.tudelft.oopp.demo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class is for setting up the MySQL database and use Connection pooling
 * to reduce the effort of continuously creating and closing connections.
 */
@Configuration
public class MysqlConfig {

    private static HikariConfig config = new HikariConfig();


    /**
     * This method creates the datasource.
     * @return
     */
    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        config.setJdbcUrl("jdbc:mysql://oopp53.cicks9cesfm3.us-east-1.rds.amazonaws.com:3306/oopp53");
        config.setUsername("admin");
        config.setPassword("TK74yTRLz1IwMdpSos2r");
        //         config.setJdbcUrl("jdbc:mysql://localhost:3306/reserve");
        //         config.setUsername("user");
        //         config.setPassword("password");
        HikariDataSource ds = new HikariDataSource(config);
        return ds;
    }

}
