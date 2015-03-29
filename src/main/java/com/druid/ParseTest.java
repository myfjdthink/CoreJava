package com.druid;

import java.util.List;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.alibaba.druid.util.JdbcUtils;

public class ParseTest {
	public ParseTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// String sql =
		// "select p, s.count as views, (select count(*) from Comments rc where rc.linkedId=p.id and rc.classcode='InfoPublishs') as commentNumber, (select count(*) from CollectIndexs rci where rci.toId=p.id and rci.classcode='InfoPublishs' and rci.type='favorite') as favorite FROM InfoPublishs p,UserScores s where p.id=s.linkedId and p.userInfo.id=s.userInfo.id and s.classCode='InfoPublishs' AND p.status=?1 ORDER BY p.createtime DESC";
		String sql = "select * from Comments rc, Student s LEFT JOIN Class c on s.id = c.id where rc.linkedId = 1 ";
		sql += " and exists ( select * from OK )";
		StringBuffer select = new StringBuffer();
		StringBuffer from = new StringBuffer();
		StringBuffer where = new StringBuffer();
		// parser得到AST
		OracleStatementParser parser = new OracleStatementParser(sql);
		List<SQLStatement> stmtList = parser.parseStatementList(); //
		// 将AST通过visitor输出
		SQLASTOutputVisitor visitor = SQLUtils.createFormatOutputVisitor(from,
				stmtList, JdbcUtils.ORACLE);
		SQLASTOutputVisitor whereVisitor = SQLUtils.createFormatOutputVisitor(
				where, stmtList, JdbcUtils.ORACLE);
		for (SQLStatement stmt : stmtList) {
			// stmt.accept(visitor);
			if (stmt instanceof SQLSelectStatement) {
				SQLSelectStatement sstmt = (SQLSelectStatement) stmt;
				SQLSelect sqlselect = sstmt.getSelect();
				SQLSelectQueryBlock query = (SQLSelectQueryBlock) sqlselect
						.getQuery();
				query.getFrom().accept(visitor);
				query.getWhere().accept(whereVisitor);
			} 
			System.out.println(from.toString());
			System.out.println(select);
			System.out.println(where);
		}
	}
}