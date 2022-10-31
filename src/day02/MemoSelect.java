package day02;

import java.sql.*;
import java.util.*;




//모든 메모글을 가져와 출력하자.(글번호 내림차순)

public class MemoSelect {
	Connection con;
	Statement stmt;
	ResultSet rs;
	
	private String url="jdbc:oracle:thin:@localhost:49161:XE";
	private String user="scott", pwd="tiger";
	
	/**드라이버 로드 메서드*/
	public MemoSelect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//-----
	
	/**닫는 메서드
	 * 닫는 순서중요
	 * */
	public void close() { 
		try {
			if(rs!=null) rs.close(); //[1]resultset
			if(stmt!=null)stmt.close();//[2]stmt
			if(con!=null)con.close();//[3]con
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}//-----
	
	/**앱 메인*/
	public static void main(String[] args) {
		MemoSelect app=new MemoSelect(); //이때 드라이버가 로드된다.
		//모든 메모 출력 부분
		ArrayList<MemoVO> memoList=app.selectMemoAll();
		app.printMemo(memoList);
		
		//작성자로 검색 부분
		System.out.println("검색할 작성자명 입력: ");
		Scanner sc=new Scanner(System.in);
		String name=sc.next();
		
		ArrayList<MemoVO> findList=app.findMemoByName(name);
		app.printMemo(findList);
		
		//메모 내용으로 검색 부분
		System.out.println("검색할 메모 내용 키워드 입력: ");
		String keyword=sc.next();
		findList=app.findMemoByMsg(keyword);
		app.printMemo(findList);
		
		//글번호(PK)로 검색 부분
		System.out.println("검색할 글 번호 입력: ");
		int idx=sc.nextInt();
		MemoVO vo=app.selectMemo(idx);
		String str=(vo==null)?"검색한 글은 없어요":vo.getIdx()+"\t"+vo.getName()+"\t"+vo.getMsg()+"\t"+vo.getWdate();
		System.out.println(str);
	}//-----
	
	/**[4]글번호(PK)로 특정글을 가져오는 메서드 만들어보기.*/
	public MemoVO selectMemo(int idx) {
		try {
			con=DriverManager.getConnection(url,user,pwd);
			String sql="select * from memo where idx="+idx;
			stmt = con.createStatement();
			rs=stmt.executeQuery(sql);
			ArrayList<MemoVO> arr=makeList(rs);
			
			//Primary Key는 1개니까.
			if(arr!=null && arr.size()==1) {
				MemoVO vo=arr.get(0); //1개 인덱스인 0번째객체를 반환(==글번호)
				return vo;
			}
			return null; //null일 경우엔 null반환.
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			close();
		}
	}//----
	
	/**[3]글내용으로 특정 글을 가져오는 메서드 만들어보기.*/
	public ArrayList<MemoVO> findMemoByMsg(String keyword){
		try {
			con=DriverManager.getConnection(url,user,pwd);
			String sql="select idx,name,msg,wdate from memo where msg like '%"+keyword+"%' order by idx desc";
			System.out.println(sql);
			
			stmt=con.createStatement();
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
	
	
	/**[2]이름으로 특정 글 가져오는 메서드 만들어보기.*/
	public ArrayList<MemoVO> findMemoByName(String keyword){
		try {
			con=DriverManager.getConnection(url,user,pwd);
			String sql="select idx,name,msg,wdate from memo where name like '%"+keyword+"%' order by idx desc";
			System.out.println(sql);
			
			stmt=con.createStatement();
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
	
	/** makeList 만들어주는 메서드*/
	public ArrayList<MemoVO> makeList(ResultSet rs) throws SQLException{
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
		
	}
	
	/**[1]모든 글 가져오는 메서드 만들어보기.*/
	private ArrayList<MemoVO> selectMemoAll() {
		try {
			con=DriverManager.getConnection(url,user,pwd);
			String sql="select idx, name, RPAD(msg,30,' ') msg, wdate from memo order by idx desc";
			stmt=con.createStatement();
//			boolean b=stmt.execute(sql);
//			ResultSet rs=stmt.getResultSet(); //true이면 이것을 받야아하는 절차가 필요.
//			System.out.println("b: "+b);
			
			//SELECT문은  ResultSet을 반환하는 executeQuery()를 사용.
			rs=stmt.executeQuery(sql);
			ArrayList<MemoVO> arr=makeList(rs);
						
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
