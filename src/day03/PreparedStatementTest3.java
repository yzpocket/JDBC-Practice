package day03;
import java.sql.*;

import java.util.Scanner;

import common.DBUtil;
public class PreparedStatementTest3 {

	public static void main(String[] args) throws Exception {
		Connection con=DBUtil.getCon();
		//검색할 사원의 이름을 입력받아서 해당 사원 정보를 출력하세요
		//사번, 사원명, 부서명, 담당업무, 입사일, 근무지 가져아와 출력하기.
        Scanner sc = new Scanner(System.in);
        System.out.println("검색할 이름을 입력하세요");



        String name = sc.nextLine();
        String  //컬럼인덱스 [1]  [2]    [3]       [4]      [5]     [6]
        sql =  "select empno, ename, e.deptno , job , hiredate ,d.loc";
        sql +=" from emp e join dept d on e.deptno = d.deptno";
        sql +=" where ename = upper(?)";
		
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,  name);
        ResultSet rs=pstmt.executeQuery();
        int cnt=0;
        while(rs.next()) {
        	cnt++;
        	int empno=rs.getInt(1); //1 == 컬럼 인덱스 empno이다.
        	String ename=rs.getString(2);
        	int dno=rs.getInt(3);
        	String job=rs.getString(4);
        	Date hdate=rs.getDate(5);
        	String loc=rs.getString(6);
        	System.out.println(empno+"\t"+ename+"\t"+dno+"\t"+job+"\t"+hdate+"\t"+loc);
        }//while---
        if(cnt==0)
        	System.out.println("해당 이름의 사원은 없습니다.");
        rs.close(); //닫기 순서 지키기.
        pstmt.close();
		con.close();
	}

}
