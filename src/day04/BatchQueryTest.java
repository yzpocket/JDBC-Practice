package day04;
import java.sql.*;
import common.DBUtil;
/*여러개의 sql문을 한꺼번에 전송하는 일괄처리 방식 연습
 * - 여러개의 sql문을 작성해서 Statement의 addBatch(sql)...하고
 * - 한꺼번에 executeBatch()메서드로 일괄 처리하는것.
 * 모두 성공하던지, 아니면 모두 실패하던지 ==> Transaction 처리라함.
 */
public class BatchQueryTest {

	public static void main(String[] args) throws Exception
	{
		Connection con=DBUtil.getCon();
		con.setAutoCommit(false); // 자동커밋 취소 = 수동으로 트랜잭션 관리하겠다는 것 설정.
		
		Statement st=con.createStatement();
		st.addBatch("insert into memo values(memo_seq.nextval, '김길동', 'Batch테스트1', sysdate)");
		st.addBatch("insert into memo values(memo_seq.nextval, '종길동', 'Batch테스트2', sysdate)");
		st.addBatch("insert into memo values(memo_seq.nextval, '최길동', 'Batch테스트3', sysdate)");
		st.addBatch("insert into memo values(memo_seq.nextval, '감길동', 'Batch테스트4', sysdate)");
		//일부러 sql문 하나를 에러를 발생시켜 보자.  위에 nexval로 오타를 내봤음.
		st.addBatch("insert into memo values(memo_seq.nextval, '유길동', 'Batch테스트5', sysdate)");
		boolean isCommit=false;
		try {
			int[] updateCOunt=st.executeBatch(); //일괄처리하여 실행
			isCommit=true; //오류없으면 커밋하게.
		} catch (SQLException e) {//오타때문에 여기에서 false로 흐르게 됨.
			isCommit=false; //오류발생하면 커밋못하게.
			e.printStackTrace();
		}
		if(isCommit) {//오류없으면 커밋하게.
			con.commit();
		}else {//오류발생하면 커밋못하게.
			con.rollback();
		}
		con.setAutoCommit(true);
		
		boolean b=st.execute("select * from memo order by idx desc");
		if(b) {
			ResultSet rs=st.getResultSet();
			while(rs.next()) {
				System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getDate(4));
			}
			rs.close();
		}//if---
		
		st.close();
		con.close();
	}

}
