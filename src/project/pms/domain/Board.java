package project.pms.domain;

import java.sql.Date;

//게시글 데이터를 저장할 새 데이터 타입을 정의한다.
public class Board {
    public String title;
    public String content;
    public Date createdDate;
    public int no; // 게시물 번호를 담을 변수
}
