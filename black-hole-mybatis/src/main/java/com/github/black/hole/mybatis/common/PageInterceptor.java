package com.github.black.hole.mybatis.common;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * @author hairen.long
 * @date 2020/11/10
 */
@Intercepts({
    @Signature(
            method = "prepare",
            type = StatementHandler.class,
            args = {Connection.class, Integer.class})
})
public class PageInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        StatementHandler delegate =
                (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
        BoundSql boundSql = delegate.getBoundSql();
        Object obj = boundSql.getParameterObject();
        if (obj instanceof QueryPage) {
            QueryPage page = (QueryPage) obj;
            MappedStatement mappedStatement =
                    (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
            Connection connection = (Connection) invocation.getArgs()[0];
            String sql = boundSql.getSql();
            this.setTotalRecord(page, mappedStatement, connection);
            String pageSql = this.getPageSql(page, sql);
            ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
        }
        return invocation.proceed();
    }

    /**
     * 给当前的参数对象page设置总记录数
     *
     * @param page Mapper映射语句对应的参数对象
     * @param mappedStatement Mapper映射语句
     * @param connection 当前的数据库连接
     */
    private void setTotalRecord(
            QueryPage page, MappedStatement mappedStatement, Connection connection) {
        BoundSql boundSql = mappedStatement.getBoundSql(page);
        String sql = boundSql.getSql();
        String countSql = this.getCountSql(sql);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        BoundSql countBoundSql =
                new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, page);

        ReflectUtil.setFieldValue(
                countBoundSql,
                "metaParameters",
                ReflectUtil.getFieldValue(boundSql, "metaParameters"));

        ParameterHandler parameterHandler =
                new DefaultParameterHandler(mappedStatement, page, countBoundSql);

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(countSql);
            parameterHandler.setParameters(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int totalRecord = rs.getInt(1);
                page.setTotal(totalRecord);
            }
        } catch (SQLException ignored) {
        } finally {
            try {
                if (Objects.nonNull(rs)) {
                    rs.close();
                }
            } catch (SQLException ignored) {
            } finally {
                if (Objects.nonNull(pstmt)) {
                    try {
                        pstmt.close();
                    } catch (SQLException ignored) {
                    }
                }
            }
        }
    }

    /**
     * 根据原Sql语句获取对应的查询总记录数的Sql语句
     *
     * @param sql inputSql
     * @return outPutSql
     */
    private String getCountSql(String sql) {
        return "select count(*) from (" + sql + ") tab_1";
    }

    /**
     * 根据page对象获取对应的分页查询Sql语句
     *
     * @param page 分页对象
     * @param sql 原sql语句
     * @return String
     */
    private String getPageSql(QueryPage page, String sql) {
        StringBuilder sqlBuilder = new StringBuilder(sql);
        return getMysqlPageSql(page, sqlBuilder);
    }

    /**
     * 获取Mysql数据库的分页查询语句
     *
     * @param page 分页对象
     * @param sqlBuilder 包含原sql语句的StringBuilder 对象
     * @return Mysql数据库分页语句
     */
    private String getMysqlPageSql(QueryPage page, StringBuilder sqlBuilder) {
        if (Objects.nonNull(page)
                && Objects.nonNull(page.getPageIndex())
                && Objects.nonNull(page.getPageSize())) {

            int offset = (page.getPageIndex() - 1) * page.getPageSize();
            sqlBuilder.append(" limit ").append(offset).append(",").append(page.getPageSize());
        }

        return sqlBuilder.toString();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {}
}
