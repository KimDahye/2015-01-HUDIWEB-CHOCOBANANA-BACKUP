package ubuntudo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

import support.QueryCollection;
import ubuntudo.model.UserEntity;

@Repository("userDao")
public class UserDao extends QueryCollection {
	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void initialize() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		DatabasePopulatorUtils.execute(populator, jdbcTemplate.getDataSource());
	}

	public int insertUserDao(String name, String email, String passwd) {
		logger.debug("insertUserDao()");
		return jdbcTemplate.update(INSERT_USER, name, email, passwd);
	}

	public UserEntity retrieveUserDao(String email, String passwd) {
		logger.info("retrieveUserDao()");
		RowMapper<UserEntity> rowMapper = new RowMapper<UserEntity>() {
			public UserEntity mapRow(ResultSet rs, int rowNum) {
				try {
					return new UserEntity(rs.getLong("uid"), rs.getString("name"), rs.getString("email"), null);
				} catch (SQLException e) {
					throw new BeanInstantiationException(UserEntity.class, e.getMessage(), e);
				}
			}
		};
		return jdbcTemplate.queryForObject(RETRIEVE_USER, rowMapper, email, passwd);
	}

	public boolean validateUser(String email) {
		logger.info("---->UserDao.validateUser");
		String qry = "select uid, name, email from user where email = ?";
		RowMapper<UserEntity> rowMapper = new RowMapper<UserEntity>() {
			public UserEntity mapRow(ResultSet rs, int rowNum) {
				try {
					return new UserEntity(rs.getString("uid"), rs.getString("name"), rs.getString("email"));
				} catch (SQLException e) {
					throw new BeanInstantiationException(UserEntity.class, e.getMessage(), e);
				}
			}
		};
		logger.info("<----UserDao.validateUser");
		if (jdbcTemplate.queryForObject(qry, rowMapper, email) != null)
			return true;
		return false;
	}
}