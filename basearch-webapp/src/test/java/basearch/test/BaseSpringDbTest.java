package basearch.test;

import javax.sql.DataSource;

import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import basearch.dao.AuthDao;
import basearch.dao.MetadataDao;
import basearch.dao.UserDao;
import basearch.dao.impl.AuthDaoImpl;
import basearch.dao.impl.MetadataDaoImpl;
import basearch.dao.impl.UserDaoImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public abstract class BaseSpringDbTest {

	@Configuration
	static class Config {

		@Bean
		public DataSource dataSource() {
			return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.HSQL)
				.addScript("classpath:test-db-ddl.sql")
				.addScript("classpath:test-db-initial-data.sql")
				.build();
		}

		@Bean
		public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
			LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
			emf.setDataSource(dataSource());
			return emf;
		}

		@Bean
		public PlatformTransactionManager transactionManager() {
			JpaTransactionManager tm = new JpaTransactionManager();
			tm.setEntityManagerFactory(entityManagerFactory().getObject());
			return tm;
		}

		@Bean
		public MetadataDao metadataDao() {
			return new MetadataDaoImpl();
		}

		@Bean
		public AuthDao authDao() {
			return new AuthDaoImpl();
		}

		@Bean
		public UserDao userDao() {
			return new UserDaoImpl();
		}

	}

}