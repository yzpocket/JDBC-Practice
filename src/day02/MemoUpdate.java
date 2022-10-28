package day02;
import java.sql.*;
import java.util.*;
public class MemoUpdate {
	private Connection con;
	private Statement stmt;
	
	private String url="jdbc:oracle:thin:@localhost:49161:XE";
	private String user="scott", pwd="tiger";
	
	public MemoUpdate() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}//----생성자 드라이버 로드해주고.
	
	public void close() { //닫는 메서드.
		try {
			if(stmt!=null)stmt.close();
			if(con!=null)con.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}//-----
	
	//메모업데이트하는 메서드.
	public int updateMemo(MemoVO memo) {
		try {
			con=DriverManager.getConnection(url,user,pwd);
			//sql문 => update문 작성하기.
			String sql="update memo set name='"+memo.getName()+"', msg='"+memo.getMsg()+"', wdate=sysdate where idx="+memo.getIdx();
//			sql문--UPDATE  테이블명 set 컬럼명 = 값 ... where 조건~~;
	
			//stmt얻어오기.
			stmt=con.createStatement(); //[4]
			//executeUpdate()로 실행하고 그 결과를 return한다.
			int n=stmt.executeUpdate(sql); //[6]
			return n;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}finally {
			close();
		}
	}//-----
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);

		System.out.println("수정할 글 번호 입력: ");
		int idx=sc.nextInt();

		System.out.println("수정할 작성자명: ");
		String name=sc.next();

		//nextLine 이 위에서 엔터값을 이미 받아서 입력을 생략해버렸다.
		//엔터값을 건너뛰어야 한다.
		// \r represents a line break on old Macs (OS 9 and before)
		// \n represents a line break on UNIX systems (OS X, Linux)
		// \r\n represents a line break on Windows.
		//맥은 nextLine()쓰려면 위에서 skip("\n");으로 브레이크 해주자.
		
		sc.skip("\n");
		System.out.println("수정할 메모내용: ");
		String msg=sc.nextLine();
		
		System.out.println(idx+"/"+name+"/"+msg);
		
		//이렇게 값을 받는 객체, 도메인 객체를 (VO(value object)객체, DTO객체 라고 한다.)
		//실무에서는 이렇게 값을 객체에 담아서 넘긴다. (day01처럼 하지 않음)
		
		//VO객체에 입력 받은 값 담아주기.
		// insert,update문은 주로 vo객체에 담는다.
		// delete==>글번호로 삭제, vo에 담지 않는다.
		// select==>단일행 반환 => VO객체에 담는다.
		//       ==>다중행 반환 => ArrayList<VO> 처럼 쓴다.
		MemoVO vo=new MemoVO(idx, name, msg, null);
		
		//수정처리할 메서드에 넣어주고.
		MemoUpdate app=new MemoUpdate();
		int n=app.updateMemo(vo); 
		
		//위 반환값에 따라 아래서 결과 확인.
		String res=(n>0)?"글 수정 성공":"수정 실패";
		System.out.println(res);
	}

}
