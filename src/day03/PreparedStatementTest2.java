package day03;
import java.sql.*;
import java.util.*;
public class PreparedStatementTest2 {

	public static void main(String[] args) throws Exception{
		//
		Scanner sc=new Scanner(System.in);
		
		System.out.println("수정할 사번(EMPNO) 입력 : ");
		int no=sc.nextInt();
		
		System.out.println("수정할 업무(JOB) 입력: ");
		String job=sc.next();
		
		System.out.println("수정할 부서번호(DEPTNO) 입력: ");
		int dno=sc.nextInt();
		
		System.out.println("수정할 급여(SAL) 입력: ");
		int sal=sc.nextInt();
		
		//EMP테이블을 수정하는 문장을 PreparedStatement를 이용해 실행시키세요.
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url="jdbc:oracle:thin:@localhost:49161:XE";
		String user="scott", pwd="tiger";
		
		Connection con=DriverManager.getConnection(url,user,pwd);
		String sql="update emp set job=?, deptno=?, sal=? where empno=?";
						//인파라미터들은 set으로 받아
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, job);
		pstmt.setInt(2, dno);
		pstmt.setInt(3, sal);
		pstmt.setInt(4, no);

		int n=pstmt.executeUpdate();
		System.out.println(n+"개의 데이터 수정함.");
		con.close();
	}

}
