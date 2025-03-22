package com.springboot.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springboot.dao.SysLogDao;
import com.springboot.domain.SysLog;

@Repository
public class SysLogDaoImp implements SysLogDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
public void saveSysLog(SysLog syslog) {
    // 修改点1：移除id字段和序列
    String sql = "INSERT INTO sys_log " +
                 "(username, operation, time, method, params, ip, create_time) " +
                 "VALUES (:username, :operation, :time, :method, :params, :ip, :createTime)";

    // 修改点2：直接使用jdbcTemplate的NamedParameterJdbcTemplate（无需重新创建）
    new NamedParameterJdbcTemplate(jdbcTemplate).update(
        sql,
        new BeanPropertySqlParameterSource(syslog)
    );
}


}
