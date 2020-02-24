package nl.tudelft.oopp.demo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySQLConfig {

    private static HikariConfig config = new HikariConfig();


    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        config.setJdbcUrl( "jdbc:mysql://projects-db.ewi.tudelft.nl/projects_OOPP53" );
        config.setUsername( "pu_OOPP53" );
        config.setPassword( "iQvefOwHCEDp" );
        HikariDataSource ds = new HikariDataSource( config );
        return ds;
    }
}
