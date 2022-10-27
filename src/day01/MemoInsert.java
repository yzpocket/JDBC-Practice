package day01;
import java.sql.*;
import javax.swing.*;

public class MemoInsert {

	public static void main(String[] args) 
	throws ClassNotFoundException, SQLException
	{
		String name=JOptionPane.showInputDialog("작성자 입력하세요");
		String msg=JOptionPane.showInputDialog("메시지를 입력하세요");
		if(name==null) return;
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("Driver Loading Success!!");
		
		String url="jdbc:oracle:thin:@localhost:49161:XE";
		//프로토콜:DBMS유형:driver타입:host:port:전역데이터베이스 이름
		String user="scott", pwd="tiger";
		
		
		Connection con=DriverManager.getConnection(url,user,pwd);
		String sql="insert into memo(idx,name,msg,wdate) values(memo_seq.nextval,'"+name+"','"+msg+"',sysdate)";
		System.out.println(sql);

//		String sql="insert into memo(idx,name,msg,wdate) values(memo_seq.nextval,'홍길동','첫번째 작성한 글입니다',sysdate)";
		Statement stmt=con.createStatement();
//		boolean b=stmt.execute(sql); //select문일때만 true를 반환함
		//DML문장 (insert/delecte/update)일때는 int executeUpdate()로 실행시킨다.
		int b=stmt.executeUpdate(sql);
		//sql문에 의해서 영향받은 레코드 개수를 반환한다.
		System.out.println("b:"+b +"개의 레코드를 넣었습니다.");
		stmt.close();
		con.close();
	}

}