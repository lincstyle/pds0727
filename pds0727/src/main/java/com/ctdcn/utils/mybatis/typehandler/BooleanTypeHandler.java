package com.ctdcn.utils.mybatis.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Boolean类型转换器.
 * 数据库中Boolean类型true表示为 Number(1),false表示为Number(0)
 *
 * @author 张靖
 *         2015-06-14 14:31.
 */
public class BooleanTypeHandler implements TypeHandler<Boolean> {

    public void setParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
        Integer value = parameter == true ? 1 : 0;
        ps.setInt(i, value);
    }

    public Boolean getResult(ResultSet resultSet, String s) throws SQLException {
        Integer value = resultSet.getInt(s);
        Boolean rt = Boolean.FALSE;
        if (value == 1){
            rt = Boolean.TRUE;
        }
        return rt;
    }

    public Boolean getResult(ResultSet resultSet, int i) throws SQLException {
        Integer b = resultSet.getInt(i);
        return b == 1 ? true : false;
    }

    public Boolean getResult(CallableStatement callableStatement, int i) throws SQLException {
        Integer value = callableStatement.getInt(i);
        Boolean rt = Boolean.FALSE;
        if (value == 1){
            rt = Boolean.TRUE;
        }
        return rt;
    }
}
