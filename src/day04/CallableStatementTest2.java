package day04;
/*
 create or replace procedure memo_edit
(midx in memo.idx%type, 
mname in memo.name%type, 
mmsg in memo.msg%type)
is
begin
    update memo set 
    name=mname, 
    msg = mmsg 
    where idx = midx;
end;
/
 */
import java.util.*;
import common.DBUtil;
import day02.MemoVO;

import java.sql.*;
public class CallableStatementTest2 {

	public static void main(String[] args) throws Exception {
		/* 메모글을 수정하는 memo_edit 프로시저를 호출하는 jdbc를 구현하세요.
		 * */
		Scanner sc=new Scanner(System.in);
		System.out.println("글번호: ");
		int idx=sc.nextInt();
		sc.skip("\n"); //nextLine 엔터입력 브레이크해줘야됨.
		System.out.println("수정할 이름: ");
		String name=sc.nextLine();
		System.out.println("수정할 내용: ");
		String msg=sc.nextLine();
		System.out.println(idx+"/"+name+"/"+msg);
		
		MemoVO vo=new MemoVO(idx,name,msg,null); //VO에 넘겨서 보자.
		
		Connection con=DBUtil.getCon();//[0]커넥션 드라이버 로드
		String sql="{call memo_edit(?,?,?)}"; //[1]이것 먼저 선언{call 프로시져이름(인파라미터?,인파리미터?)}
		CallableStatement cs=con.prepareCall(sql);
		//vo에 담는것
		cs.setInt(1, vo.getIdx()); //sc로받은 인덱스 vo로 전달
		cs.setString(2, vo.getName()); //sc로받은 이름 vo로 전달
		cs.setString(3, vo.getMsg()); //sc로받은 내용 vo로 전달
		//vo 담지않고 하던것.
//		cs.setInt(1, idx); //sc로받은 인덱스 전달
//		cs.setString(2, name); //sc로받은 이름 전달
//		cs.setString(3, msg); //sc로받은 내용 전달
		
		//실행 vo에 담은것 몇개 레코드 수정했는지 확인하는것 
		int n=cs.executeUpdate();
		//실행vo 담지않고 하던것 
//		cs.execute();
		System.out.println("메모글 수정 성공!!"+n+"개 레코드가 수정되었습니다.");
		
		//닫기
		cs.close();
		con.close();
	}

}
