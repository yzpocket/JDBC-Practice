package day05;
import java.sql.*;

import javax.swing.JOptionPane;

import common.DBUtil;
public class CallableStatementTest4 {

	public static void main(String[] args)
	throws SQLException{
		String dno=JOptionPane.showInputDialog("검색할 부서 번호를 입력: ");
		if(dno==null) return;
		//부서번호를 인파라미터로 전달하면 해당 부서에 있는
		//사원 정보(사원명ename, 업무job, 입사일hiredate)와
		//부서정보(부서명dname,근무지loc)를 가져오는 프로시저를 작성하고 
		// 위는 SQL Developer에서.
		//그것을 자바에서 호출해서 결과 데이터를 출력하세요.
		
		Connection con=DBUtil.getCon();
		String sql="{call emp_forjava(?,?)}"; //1은 in, 2는 out
		CallableStatement cs=con.prepareCall(sql);
		cs.setString(1, dno);
		cs.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
		cs.execute();
		
		ResultSet rs=(ResultSet)cs.getObject(2);
		System.out.println(dno+"번 부서 사원 목록");
		while(rs.next()) {
			String ename=rs.getString(1);
			String job=rs.getString(2);
			Date hiredate=rs.getDate(3);
			String dname=rs.getString(4);
			String loc=rs.getString(5);
			System.out.println(ename+"\t"+job+"\t"+hiredate+"\t"+dname+"\t"+loc);
		}
		rs.close();
		cs.close();
		con.close();		
	}

}
