package project.pms.controller;

import java.util.Scanner;

// MemberDao를 사용하여 회원 데이터를 관리한다.
import project.pms.dao.MemberDao;
import project.pms.domain.Member;
import project.pms.util.Console;

public class MemberController {

    // 이 클래스를 사용하려면 keyboard 스캐너가 있어야 한다.
    // 이 클래스를 사용하기 전에 스캐너를 설정하라!
    Scanner keyScan;

    MemberDao memberDao;

    // 컨트롤러가 작업하는데 필요한 객체를 반드시 준비하도록 생성자를 추가한다.
    // => 생성자를 통해 필수 입력 값을 반드시 설정하도록 강제시킬 수 있다.
    // => 즉 생성자란, 객체를 사용하기 전에 유효한 값으로 설정하게 만드는 문법이다.
    // MemberDao를 생성자에서 주입 받도록 변경.
    public MemberController(Scanner scanner, MemberDao memberDao) {
        // MemberController의 메서드를 이용하려면 반드시 설정해야 하는 값이 있다.
        // Member[] 배열이나 memberIndex 처럼 내부에서 생성하는 값이 있고,
        // Scanner 처럼 외부에서 받아야 하는 값이 있다.
        // 외부에서 반드시 받아야 하는 값은 생성자를 통해 입력 받도록 하면 된다.
        // 이것이 생성자가 필요한 이유이다.
        // 즉 객체가 작업하는데 필수적으로 요구되는 값을 준비시키는 역할을 수행하는 게
        // 바로 "생성자"이다.
        this.keyScan = scanner;
        this.memberDao = memberDao;
    }

    // member/ 관련 명령어를 처리하는 코드를 메서드로 분리한다.
    public void service(String menu, String option) {
        if (menu.equals("member/add")) {
            this.onMemberAdd();
        } else if (menu.equals("member/list")) {
            this.onMemberList();
        } else if (menu.equals("member/view")) {
            this.onMemberView(option);
        } else if (menu.equals("member/update")) {
            this.onMemberUpdate(option);
        } else if (menu.equals("member/delete")) {
            this.onMemberDelete(option);
        } else {
            System.out.println("명령어가 올바르지 않습니다.");
        }
    }

    // member/add 명령어를 처리하는 코드를 메서드로 분리한다.
    void onMemberAdd() {
        System.out.println("[회원 정보 입력]");
        Member member = new Member();

        System.out.print("아이디? ");
        member.id = this.keyScan.nextLine();

        System.out.print("이메일? ");
        member.email = this.keyScan.nextLine();

        System.out.print("암호? ");
        member.password = this.keyScan.nextLine();

        memberDao.insert(member);

    }

    // member/list 명령어를 처리하는 코드를 메서드로 분리한다.
    void onMemberList() {
        System.out.println("[회원 목록]");
        Member[] list = memberDao.list();

        for (int i = 0; i < list.length; i++) {
            System.out.printf("%s, %s, %s\n", list[i].id, list[i].email, list[i].password);
        }
    }

    // member/view 명령어를 처리하는 코드를 메서드로 분리한다.
    void onMemberView(String id) {
        System.out.println("[회원 정보 조회]");
        if (id == null) {
            System.out.println("아이디를 입력하시기 바랍니다.");
            return;// 값을 리턴하면 안되기 때문에 return 명령만 작성한다.
                   // 의미? 즉시 메서드 실행을 멈추고 이전 위치로 돌아간다.
        }

        Member member = memberDao.get(id);

        if (member == null) {
            System.out.println("해당 아이디의 회원이 없습니다.");
        } else {
            System.out.printf("아이디: %s\n", member.id);
            System.out.printf("이메일: %s\n", member.email);
            System.out.printf("암호: %s\n", member.password);
        }
    }

    // member/update 명령어를 처리하는 코드를 메서드로 분리한다.
    void onMemberUpdate(String id) {
        System.out.println("[회원 정보 변경]");
        if (id == null) {
            System.out.println("아이디를 입력하시기 바랍니다.");
            return;// 값을 리턴하면 안되기 때문에 return 명령만 작성한다.
                   // 의미? 즉시 메서드 실행을 멈추고 이전 위치로 돌아간다.
        }

        Member member = memberDao.get(id);

        if (member == null) {
            System.out.println("해당 아이디의 회원이 없습니다.");
        } else {
            Member updateMember = new Member();
            System.out.printf("아이디(%s)? ", member.id);
            updateMember.id = this.keyScan.nextLine();
            System.out.printf("이메일(%s)? ", member.email);
            updateMember.email = this.keyScan.nextLine();
            System.out.printf("암호?(%s)? ", member.password);
            updateMember.password = this.keyScan.nextLine();
            memberDao.update(updateMember);
            System.out.println("변경하였습니다.");
        }

    }

    // member/delete 명령어를 처리하는 코드를 메서드로 분리한다.
    void onMemberDelete(String id) {
        System.out.println("[회원 정보 삭제]");
        if (id == null) {
            System.out.println("아이디를 입력하시기 바랍니다.");
            return;// 값을 리턴하면 안되기 때문에 return 명령만 작성한다.
            // 의미? 즉시 메서드 실행을 멈추고 이전 위치로 돌아간다.
        }
        Member member = memberDao.get(id);

        if (member == null) {
            System.out.println("해당 아이디의 회원이 없습니다.");
        } else {
            if (Console.confirm("정말 삭제하시겠습니까?")) {
                memberDao.delete(id);
                System.out.println("삭제하였습니다.");
            }
        }

    }

}
