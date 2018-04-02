package project.pms.dao;

import project.pms.domain.Board;

// Data Acess Object(DAO) 데이터 접근 객체
// BoardDao를 사용하여 팀 데이터를 관리한다.
// BoardController로부터 데이터 관리 기능을 분리하여 BoardDao 생성.
public class BoardDao {
    // 여러 게시물 정보를 저장할 레퍼런스 배열을 준비한다.
    Board[] boards = new Board[1000];
    int boardIndex = 0;

    // 게시물 정보가 담겨있는 객체의 주소를 배열에 보관하는 코드를 메서드로 분리한다.
    public void insert(Board board) {
        board.no = boardIndex;
        // 게시물 정보가 담겨있는 객체의 주소를 배열에 보관한다.
        this.boards[this.boardIndex++] = board;
    }

    public Board[] list() {
        Board[] arr = new Board[boardIndex];
        for (int i = 0; i < boardIndex; i++)
            arr[i] = boards[i];
        return arr;
    }

    public Board get(int i) {
        if (i < 0 || i >= boardIndex) // ||(또는)
            return null;
        return boards[i];
    }

    public void update(Board board) {
        boards[board.no] = board;
    }

    public void delete(int i) {
        boards[i] = null;
    }

}
