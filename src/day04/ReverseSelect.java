package day04;
import java.sql.*;
import common.DBUtil;
public class ReverseSelect {
	/*ResultSet의 커서를 자유 자재로 이동시켜보자.
	 * - PreparedStatement ps=con.prepareStatement(sql)
	 *  ==> 이렇게 생성하면 rs의 커서는 next()만 가능함
	 * 
	 *  - rs의 커서를 자유 자재로 이동시키려면
	 *   PreparedStatement ps=con.prepareStatement(sql,
	 *   		ResultSet.TYPE_SCROLL_SENSITIVE,
	 *   	    ResultSet.CONCUR_READ_ONLY)
	 * */
	public static void main(String[] args) throws Exception{
		Connection con=DBUtil.getCon();
		String sql="select empno, ename, job from emp order by empno asc";
		//사번 오름차순으로 가져옴. 
		
		PreparedStatement pstmt=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		//위 옵션을 줘야 커서 위치를 변경할수있다. 
		ResultSet rs=pstmt.executeQuery();
		//기본적으로 rs의 커서는 첫번째 행의 직전에 위치한다. =>beforeFirst
		//우리는 마지막행 직후에 커서위치를 이동시켜보자. =>afterLast 아래처럼 커서위치를 설정.
		
		rs.afterLast();
		while(rs.previous()) {//next()반대
			int idx=rs.getInt("empno");
			String ename=rs.getString("ename");
			String job=rs.getString("job");
			System.out.println(idx+"\t"+ename+"\t"+job);
		}
		//rs. 커서를 특정행에 위치 : absolute(n)
		//n이 양수면 next()방향,
		//n이 음수면 =>previous() 방향

		rs.absolute(2);
		System.out.println(rs.getInt(1)+"/양수2/"+rs.getString(2)+"/양수2/"+rs.getString(3));
		
		rs.absolute(-1);
		System.out.println(rs.getInt(1)+"/음수-1/"+rs.getString(2)+"/음수-1/"+rs.getString(3));
		
		/*
		 * rs.beforeFirst() : 첫 번째 행의 직전에 위치
		 * rs.first() : 첫 번째 행에 위치
		 * rs.last() : 마지막 행
		 * rs.getRow() : 실제 커서가 위치한 곳의 행번호를 반환
		 * */
		
		rs.close();
		pstmt.close();
		con.close();
		
	}

}
