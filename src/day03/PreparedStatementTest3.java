package day03;
import java.sql.*;
import java.util.ArrayList;

import common.DBUtil;
import day02.MemoVO;
public class PreparedStatementTest3 {
	Connection con;
	PreparedStatement pstmt;
	/**[2]사원 이름으로 정보 가져오는 메서드 만들어보기.*/
	public ArrayList<MemoVO> findMemoByName(String keyword){
		try {
			con=DBUtil.getCon();
			String sql="select idx,name,msg,wdate from memo where name like '%"+keyword+"%' order by idx desc";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,8000);
			
			rs=stmt.executeQuery(sql);
			
			ArrayList<MemoVO> arr=makeList(rs);
			return arr;
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			close();
		}
	}//----
	//사번,사원명,부서명, 담당업무,입사일,근무지 가져와 출력하기
	public static void main(String[] args) throws SQLException {

		String sql="insert into emp(empno,ename,job,hiredate,sal,deptno)";
		sql+=" values(?,?,?,sysdate,?,?)";
		
		
		int n=pstmt.executeUpdate();
		System.out.println(n+"개의 레코드 삽입함.");
		
		con.close();
	}

}
