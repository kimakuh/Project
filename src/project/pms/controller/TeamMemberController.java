// 팀 멤버 관리 기능을 모아 둔 클래스
package project.pms.controller;

import java.util.Scanner;

import project.pms.dao.MemberDao;
import project.pms.dao.TeamDao;
import project.pms.domain.Member;
import project.pms.domain.Team;

//팀 멤버를 등록, 조회, 삭제할 수 있는 기능 추가. 
public class TeamMemberController {
    Scanner keyScan;
    TeamDao teamDao;
    MemberDao memberDao;

    // 컨트롤러가 작업하는데 필요한 객체를 반드시 준비하도록 생성자를 추가한다.
    // => 생성자를 통해 필수 입력 값을 반드시 설정하도록 강제시킬 수 있다.
    // => 즉 생성자란, 객체를 사용하기 전에 유효한 값으로 설정하게 만드는 문법이다.
    public TeamMemberController(Scanner scanner, TeamDao teamDao, MemberDao memberDao) {
        // BoardController의 메서드를 이용하려면 반드시 설정해야 하는 값이 있다.
        // Board[] 배열이나 boardIndex 처럼 내부에서 생성하는 값이 있고,
        // Scanner 처럼 외부에서 받아야 하는 값이 있다.
        // 외부에서 반드시 받아야 하는 값은 생성자를 통해 입력 받도록 하면 된다.
        // 이것이 생성자가 필요한 이유이다.
        // 즉 객체가 작업하는데 필수적으로 요구되는 값을 준비시키는 역할을 수행하는 게
        // 바로 "생성자"이다.
        this.keyScan = scanner;
        this.teamDao = teamDao;
        this.memberDao = memberDao;
    }

    public void service(String menu, String option) {
        if (menu.equals("team/member/add")) {
            this.onTeamMemberAdd(option);
        } else if (menu.equals("team/member/list")) {
            this.onTeamMemberList(option);
        } else if (menu.equals("team/member/delete")) {
            this.onTeamMemberDelete(option);
        } else {
            System.out.println("명령어가 올바르지 않습니다.");
        }
    }

    // team/member/add 명령어를 처리하는 코드를 메서드로 분리한다. (팀맴버등록)
    void onTeamMemberAdd(String teamName) {
        if (teamName == null) {
            System.out.println("팀명을 입력하시기 바랍니다.");
            return;
        }

        Team team = teamDao.get(teamName);
        if (team == null) {
            System.out.printf("%s 팀은 존재하지 않습니다.", teamName);
            return;
        }

        System.out.println("[팀 멤버 추가]");
        System.out.println("추가할 맴버의 아이디는? ");
        String memberId = keyScan.nextLine();

        Member member = memberDao.get(memberId);
        if (member == null) {
            System.out.printf("%s 회원은 없습니다.", memberId);
            return;
        }

        // 기존에 등록된 회원인지 검사

        boolean exist = false;
        for (int i = 0; i < team.members.length; i++) {
            if (team.members[i] == null)
                continue;
            if (team.members[i].id.equals(memberId)) {
                exist = true;
                break;
            }
        }

        if (exist) {
            System.out.println("이미 등록된 회원입니다.");
            return;
        }

        team.addMember(member);

    }

    // team/member/list 명령어를 처리하는 코드를 메서드로 분리한다. (팀맴버목록)
    void onTeamMemberList(String teamName) {
        if (teamName == null) {
            System.out.println("팀명을 입력하시기 바랍니다.");
            return;
        }

        Team team = teamDao.get(teamName);
        if (team == null) {
            System.out.printf("%s 팀은 존재하지 않습니다.", teamName);
            return;
        }

        System.out.println("[팀 멤버 목록]");
        System.out.print("회원들: ");
        for (int i = 0; i < team.members.length; i++) {
            if (team.members[i] == null)
                continue;
            System.out.printf("%s, ", team.members[i].id);
        }
        System.out.println();
    }

    // team/member/delete 명령어를 처리하는 코드를 메서드로 분리한다. (팀맴버삭제)
    void onTeamMemberDelete(String teamName) {
        if (teamName == null) {
            System.out.println("팀명을 입력하시기 바랍니다.");
            return;
        }

        Team team = teamDao.get(teamName);
        if (team == null) {
            System.out.printf("%s 팀은 존재하지 않습니다.", teamName);
            return;
        }

        System.out.print("삭제할 팀원은? ");
        String memberId = keyScan.nextLine();

        if (!team.isExist(memberId)) {
            System.out.println("이 팀의 회원이 아닙니다.");
            return;
        }

        team.deleteMember(memberId);

        // 팀 멤버 삭제
        System.out.println("[팀 멤버 삭제]");
        System.out.println("삭제하였습니다.");

    }

}
