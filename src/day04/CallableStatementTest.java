package day04;
/* 아래 day06_plsql.sql의 프로시져 사용하는 예제

create or replace procedure memo_add(
pname in varchar2 default '아무개',
pmsg in memo.msg%type)
is
begin
    insert into memo(idx,name,msg,wdate)
    values(memo_seq.nextval,pname,pmsg,sysdate);
    commit;
end;
/

 */

import java.util.*;
import common.DBUtil;
import java.sql.*;

public class CallableStatementTest {

	public static void main(String[] args) throws Exception {
		Scanner sc=new Scanner(System.in);
		System.out.println("작성자명: ");
		String name=sc.nextLine();
		System.out.println("메모내용: ");
		String msg=sc.nextLine();
		System.out.println(name+"/"+msg);
		
		Connection con=DBUtil.getCon();//[0]커넥션 드라이버 로드
		String sql="{call memo_add(?,?)}"; //[1]이것 먼저 선언{call 프로시져이름(인파라미터?,인파리미터?)}
		//프로시져를 호출하기 위해서는 CallableStatement 객체를 얻어온다.
		CallableStatement cs=con.prepareCall(sql);
		//in parameter값 setting
		cs.setString(1, name); //sc로받은 이름 전달
		cs.setString(2, msg); //sc로받은 내용 전달
		
		//실행
		cs.execute();
		System.out.println("메모글 등록 성공!!");
		//닫기
		cs.close();
		con.close();
	}

}
