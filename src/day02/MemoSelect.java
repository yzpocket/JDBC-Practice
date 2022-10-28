package day02;

import java.sql.*;
import java.util.*;

////글번호(PK) -- 로 특정글을 가져오는 메서드 만들어보기
//public MemoVO selectMemo(int idx) {
//	
//}//----
//
////글내용으로 특정 글을 가져오는 메서드 만들어보기.
//public ArrayList<MemoVO> findMemoByMsg(String keyword){
//	
//}//----
////이름으로 특정 글 가져오는 메서드 만들어보기.
//public ArrayList<MemoVO> findMemoByName(String keyword){
//	
//}//----


//모든 메모글을 가져와 출력하자.(글번호 내림차순)

public class MemoSelect {
	Connection con;
	Statement stmt;
	
	private String url="jdbc:oracle:thin:@localhost:49161:XE";
	private String user="scott", pwd="tiger";
	
	//생성자. //드라이버 로
	public MemoSelect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//-----
	
	public void close() { //닫는 메서드.
		try {
			if(stmt!=null)stmt.close();
			if(con!=null)con.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}//-----
	
	public static void main(String[] args) {
		MemoSelect app=new MemoSelect(); //이때 드라이버가 로드된다.
		ArrayList<MemoVO> memoList=app.selectMemoAll();
		
		app.printMemo(memoList);
	}//-----
	
	private ArrayList<MemoVO> selectMemoAll() {
		try {
			con=DriverManager.getConnection(url,user,pwd);
			String sql="select idx, name, RPAD(msg,30,' ') msg, wdate from memo order by idx desc";
			stmt=con.createStatement();
//			boolean b=stmt.execute(sql);
//			ResultSet rs=stmt.getResultSet(); //true이면 이것을 받야아하는 절차가 필요.
//			System.out.println("b: "+b);
			
			//SELECT문은  ResultSet을 반환하는 executeQuery()를 사용.
			ResultSet rs=stmt.executeQuery(sql);
			ArrayList<MemoVO> arr=new ArrayList<>();
			
			//boolean next() : 커서는 결과테이블의 beforeFirst에 위치하고 있다가
			//				next()가 호출이 되면 커서를 다음칸으로 이동 시키고
			//				이동한곳에 레코드가 있으면 true를 반환하면서 arr에 쌓는다.
			//				이동하다가 레코드가 모두 끝나면 false를 반환하고 반복문을 종료.
			while(rs.next()) { //컬럼 데이터들을 꺼내오는것.
				int idx = rs.getInt("idx");  // "컬럼명"
				String name = rs.getString("name");
				String msg = rs.getString("msg");
				java.sql.Date wdate=rs.getDate("wdate");
				//받고 VO객체에 담아줘야함.
				MemoVO vo=new MemoVO(idx,name,msg,wdate); //VO객체 ==>레코드 1개(row) 
				arr.add(vo); //준비한 ArrayList에 쌓는다.
			}//while-----
			
			return arr;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			close();
		}
	}//-----
	private void printMemo(ArrayList<MemoVO> memoList) {
		//null이아닐때만 출력해야하니까.
		if(memoList!=null) {
			System.out.println("-------------------------------------");
			System.out.println("\t글번호\t작성자명\t메모내용\t\t작성일");
			System.out.println("-------------------------------------");
			for(MemoVO memo:memoList) {
				System.out.println("\t"+memo.getIdx()+"\t"+memo.getName()+"\t"+memo.getMsg()+"\t\t"+memo.getWdate());
			}
			System.out.println("-------------------------------------");
		}
	}//-----
}//////////////////////
