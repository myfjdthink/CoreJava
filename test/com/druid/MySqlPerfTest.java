/*
 * Copyright 1999-2011 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.druid;

import java.util.List;

import junit.framework.TestCase;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.alibaba.druid.util.JdbcUtils;

public class MySqlPerfTest extends TestCase {

	private String sql;

	protected void setUp() throws Exception {
		sql = "SELECT * FROM T";
		sql = "SELECT ID, NAME, AGE FROM USER WHERE ID = ?";

		// sql = Utils.readFromResource("benchmark/sql/ob_sql.txt");
	}

	public void test_pert() throws Exception {
		// for (int i = 0; i < 10; ++i) {
		// }
		perfMySql();
	}

	long perfMySql() {
		// long startYGC = TestUtils.getYoungGC();
		// long startYGCTime = TestUtils.getYoungGCTime();
		// long startFGC = TestUtils.getFullGC();

		long startMillis = System.currentTimeMillis();
		String sql = "select p, s.count as views, (select count(*) from Comments rc where rc.linkedId=p.id and rc.classcode='InfoPublishs') as commentNumber, (select count(*) from CollectIndexs rci where rci.toId=p.id and rci.classcode='InfoPublishs' and rci.type='favorite') as favorite FROM InfoPublishs p,UserScores s where p.id=s.linkedId and p.userInfo.id=s.userInfo.id and s.classCode='InfoPublishs' AND p.status=?1 ORDER BY p.createtime DESC";
		execMySql(sql);
		// for (int i = 0; i < 1000 * 1000; ++i) {
		// }
		long millis = System.currentTimeMillis() - startMillis;

		// long ygc = TestUtils.getYoungGC() - startYGC;
		// long ygct = TestUtils.getYoungGCTime() - startYGCTime;
		// long fgc = TestUtils.getFullGC() - startFGC;

		System.out.println("MySql\t" + millis);
		// System.out.println("MySql\t" + millis + ", ygc " + ygc + ", ygct " +
		// ygct + ", fgc " + fgc);
		return millis;
	}

	private String execMySql(String sql) {
		StringBuilder out = new StringBuilder();
		StringBuffer select = new StringBuffer();
		StringBuffer from = new StringBuffer();
		StringBuffer where = new StringBuffer();

		SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(
				sql, JdbcUtils.MYSQL);
		List<SQLStatement> stmtList = parser.parseStatementList(); //
		// 将AST通过visitor输出
		SQLASTOutputVisitor visitor = SQLUtils.createFormatOutputVisitor(from,
				stmtList, JdbcUtils.MYSQL);
		SQLASTOutputVisitor whereVisitor = SQLUtils.createFormatOutputVisitor(
				where, stmtList, JdbcUtils.MYSQL);
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
		
		
		// OracleExprParser parser = new OracleExprParser(sql);
		// OracleStatementParser parser = new OracleStatementParser(sql);
		// OracleSchemaStatVisitor visitor = new OracleSchemaStatVisitor();

		// MySqlOutputVisitor visitor = new MySqlOutputVisitor(out);
		// MySqlStatementParser parser = new MySqlStatementParser(sql);
		// List<SQLStatement> statementList = parser.parseStatementList();
		// for (SQLStatement statement : statementList) {
		// statement.accept(visitor);
		// visitor.println();
		// }
		return out.toString();
	}
}