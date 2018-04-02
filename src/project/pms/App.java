package project.pms;

import java.util.Scanner;

import project.pms.controller.BoardController;
import project.pms.controller.MemberController;
import project.pms.controller.TeamController;
import project.pms.controller.TeamMemberController;
import project.pms.dao.MemberDao;
import project.pms.dao.TeamDao;
import project.pms.util.Console;

public class App {

    // 명령어를 입력 받는 코드를 메서드로 분리한다.
    static Scanner keyScan = new Scanner(System.in);
    public static String option = null; // 문자열 없음!

    // quit 명령어를 처리하는 코드를 메서드로 분리한다.
    static void onQuit() {
        System.out.println("안녕히 가세요!");
    }

    // help 명령어를 처리하는 코드를 메서드로 분리한다.
    static void onHelp() {
        System.out.println("팀 등록 명령 : team/add");
        System.out.println("팀 조회 명령 : team/list");
        System.out.println("팀 상세조회 명령 : team/view 팀명");
        System.out.println("팀 수정 명령 : team/update 팀명");
        System.out.println("팀 삭제 명령 : team/delete 팀명");
        System.out.println("회원 등록 명령 : member/add");
        System.out.println("회원 조회 명령 : member/list");
        System.out.println("회원 상세조회 명령 : member/view 아이디");
        System.out.println("회원 수정 명령 : member/update 아이디");
        System.out.println("회원 삭제 명령 : member/delete 아이디");
        System.out.println("게시물 등록 명령 : board/add");
        System.out.println("게시물 목록 명령 : board/list");
        System.out.println("게시물 상세조회 명령 : board/view 게시물 번호");
        System.out.println("게시물 수정 명령 : board/update 게시물 번호");
        System.out.println("게시물 삭제 명령 : board/delete 게시물 번호");
        System.out.println("팀멤버 등록 : team/member/add 팀명");
        System.out.println("팀멤버 조회 : team/member/list 팀명");
        System.out.println("팀멤버 조회 : team/member/delete 팀명");

        System.out.println("종효 : quit");
    }

    // 메인메서드 시작
    public static void main(String[] args) {
        // 클래스를 사용하기 전에 필수 값을 설정한다.

        // TeamDao와 MemberDao 객체 생성.
        TeamDao teamDao = new TeamDao();
        MemberDao memberDao = new MemberDao();

        Console.keyScan = keyScan;

        TeamController teamController = new TeamController(keyScan, teamDao);
        MemberController memberController = new MemberController(keyScan, memberDao);
        BoardController boardController = new BoardController(keyScan);
        TeamMemberController teamMemberController = new TeamMemberController(keyScan, teamDao, memberDao);

        while (true) {
            String[] arr = Console.prompt();
            String menu = arr[0];

            // 입력 값에서 검색어를 별도의 변수에 저장한다. -->option
            if (arr.length == 2) {
                option = arr[1];
            } else {
                option = null;
            }

            if (menu.equals("quit")) {
                onQuit();
                break;
            } else if (menu.equals("help")) {
                onHelp();
            } else if (menu.startsWith("team/member/")) { // startsWith
                teamMemberController.service(menu, option);
            } else if (menu.startsWith("team/")) { // startsWith
                teamController.service(menu, option);
            } else if (menu.startsWith("member/")) {
                memberController.service(menu, option);
            } else if (menu.startsWith("board/")) {
                boardController.service(menu, option);
            } else {
                System.out.println("명령어가 올바르지 않습니다.");
            }
            System.out.println();
        }

    }

}
