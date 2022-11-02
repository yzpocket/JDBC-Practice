package day05;
import java.sql.*;
import common.DBUtil;
/* 
 * create or replace procedure memo_all(mycr out sys_refcursor)
is
begin
    --커서 오픈
    open mycr for
    select idx, name, msg, wdate from memo
    order by idx desc;
end;
/
 */
public class CallableStatementTest3 {

	public static void main(String[] args) throws SQLException{
		Connection con=DBUtil.getCon();
		String sql="{call memo_all(?)}";
		//					프로시져명 (?==아웃파라미터)
		CallableStatement cs=con.prepareCall(sql);
		//		아웃파라미터는 registerOutParameter로 등록하고 
		cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
//		ResultSet rs=cs.executeQuery(); //이렇게하면 에러가 발생된다. [X]
		cs.execute(); //그래서 그냥 execute로 해당 프로시저를 실행하고.
		//		아웃파라미터들을 getter로 받아야한다. 그리고 ResulSet으로 형변환
		ResultSet rs=(ResultSet)cs.getObject(1); // 저위의 ?가 이거다. 그리고 강제형변환을하고 Resultset에 넣는다.
		
		while(rs.next()) {
			int idx=rs.getInt("idx");
			String name=rs.getString("name");
			String msg=rs.getString("msg");
			Date wdate=rs.getDate("wdate");
			System.out.println(idx+"\t"+name+"\t"+msg+"\t"+wdate);
		}
		rs.close();
		cs.close();
		con.close();
		
	}

}
