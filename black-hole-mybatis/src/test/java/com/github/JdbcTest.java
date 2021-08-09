package com.github;

import com.github.black.hole.mybatis.common.JdbcUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author hairen.long
 * @date 2021/8/7
 */
public class JdbcTest {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            //获取连接
            conn = JdbcUtil.getConnection();
            //创建SQL语句
            String sql = "select * from test_entity where id = ?";
            //创建PreparedStatement对象
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 1);
            //执行SQL语句
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String unm = rs.getString("name");
                Date pwd = rs.getDate("date");
                System.out.println(id + "--"+ unm + "--" + pwd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(rs, pstmt, conn);
        }
    }

}
