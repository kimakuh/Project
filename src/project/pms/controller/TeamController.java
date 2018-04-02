// 팀 관련 기능을 모아 둔 클래스
package project.pms.controller;

import java.sql.Date;
import java.util.Scanner;

import project.pms.dao.TeamDao;
import project.pms.domain.Team;
import project.pms.util.Console;

public class TeamController {

    // 이 클래스를 사용하기 전에 App 클래스에서 준비한 Scanner 객체를
    // keyScan 변수에 저장하라!
    Scanner keyScan;

    // TeamDao 생성
    TeamDao teamDao;

    // 컨트롤러가 작업하는데 필요한 객체를 반드시 준비하도록 생성자를 추가한다.
    // => 생성자를 통해 필수 입력 값을 반드시 설정하도록 강제시킬 수 있다.
    // => 즉 생성자란, 객체를 사용하기 전에 유효한 값으로 설정하게 만드는 문법이다.
    // TeamDao를 생성자에서 주입 받도록 변경.
    public TeamController(Scanner scanner, TeamDao teamDao) {
        // TeamController의 메서드를 이용하려면 반드시 설정해야 하는 값이 있다.
        // Team[] 배열이나 teamIndex 처럼 내부에서 생성하는 값이 있고,
        // Scanner 처럼 외부에서 받아야 하는 값이 있다.
        // 외부에서 반드시 받아야 하는 값은 생성자를 통해 입력 받도록 하면 된다.
        // 이것이 생성자가 필요한 이유이다.
        // 즉 객체가 작업하는데 필수적으로 요구되는 값을 준비시키는 역할을 수행하는 게
        // 바로 "생성자"이다.
        this.keyScan = scanner;
        this.teamDao = teamDao;
    }

    // team/ 관련 명령어를 처리하는 코드를 메서드로 분리한다.
    public void service(String menu, String option) {
        if (menu.equals("team/add")) {
            this.onTeamAdd();
        } else if (menu.equals("team/list")) {
            this.onTeamList();
        } else if (menu.equals("team/view")) {
            this.onTeamView(option);
        } else if (menu.equals("team/update")) {
            this.onTeamUpdate(option);
        } else if (menu.equals("team/delete")) {
            this.onTeamDelete(option);
        } else {
            System.out.println("명령어가 올바르지 않습니다.");
        }
    }

    // team/add 명령어를 처리하는 코드를 메서드로 분리한다.

    void onTeamAdd() {
        System.out.println("[팀 정보 입력]");
        Team team = new Team();

        System.out.print("팀명? ");
        team.name = this.keyScan.nextLine();

        System.out.print("설명? ");
        team.description = this.keyScan.nextLine();

        System.out.print("최대인원 ");
        team.maxQty = this.keyScan.nextInt();
        keyScan.nextLine();

        System.out.print("시작일? ");
        // 시작일을 문자열로 입력 받아 Date 객체로 변환하여 저장.
        team.startDate = Date.valueOf(this.keyScan.nextLine());

        // 종료일을 문자열로 입력 받아 Date 객체로 변환하여 저장
        System.out.printf("종료일? ");
        team.endDate = Date.valueOf(this.keyScan.nextLine());

        teamDao.insert(team);

    }

    // team/list 명령어를 처리하는 코드를 메서드로 분리한다.

    void onTeamList() {
        System.out.println("[팀 목록]");
        Team[] list = teamDao.list();
        for (int i = 0; i < list.length; i++) {
            System.out.printf("%s, %d, %s ~ %s\n", list[i].name, list[i].maxQty, list[i].startDate, list[i].endDate);
        }
    }

    // team/view 명령어를 처리하는 코드를 메서드로 분리한다.

    void onTeamView(String name) {
        System.out.println("[팀 정보 조회]");
        if (name == null) {
            System.out.println("팀명을 입력하시기 바랍니다.");
            System.out.println();
            return; // 값을 리턴하면 안되기 때문에 return 명령어만 작성한다.
                    // 의미? 즉시 메서드 실행을 멈추고 이전 위치로 돌아간다.
        }
        
        Team team = teamDao.get(name);



        if (team == null) {
            System.out.println("해당 이름의 팀이 없습니다.");
        } else {
            System.out.printf("팀명: %s\n", team.name);
            System.out.printf("설명: %s\n", team.description);
            System.out.printf("최대인원: %d\n", team.maxQty);
            System.out.printf("기간: %s ~ %s\n", team.startDate, team.endDate);
        }
    }

    // team/update 명령어를 처리하는 코드를 메서드로 분리한다.
    void onTeamUpdate(String name) {
        System.out.println("[팀 정보 변경]");
        if (name == null) {
            System.out.println("팀명을 입력하시기 바랍니다.");
            return;
        }
        
        Team team = teamDao.get(name);

        if (team == null) {
            System.out.println("해당 이름의 팀이 없습니다.");
        } else {
            // 업데이트팀 정보를 받을 메모리 준비
            Team updateTeam = new Team();
            System.out.printf("팀명(%s)? ", team.name);
            updateTeam.name = this.keyScan.nextLine();
            System.out.printf("설명(%s)? ", team.description);
            updateTeam.description = this.keyScan.nextLine();
            System.out.printf("최대인권(%d)? ", team.maxQty);
            updateTeam.maxQty = this.keyScan.nextInt();
            this.keyScan.nextLine();
            System.out.printf("시작일(%s)? ", team.startDate);
            updateTeam.startDate = Date.valueOf(this.keyScan.nextLine());
            System.out.printf("종료일(%s)? ", team.endDate);
            updateTeam.endDate = Date.valueOf(this.keyScan.nextLine());
            teamDao.update(updateTeam);
            System.out.println("변경하였습니다.");
        }

    }

    // team/delete 명령어를 처리하는 코드를 메서드로 분리한다.
    void onTeamDelete(String name) {
        System.out.println("[팀 정보 삭제]");
        if (name == null) {
            System.out.println("팀명을 입력하시기 바랍니다.");
            return;// 값을 리턴하면 안되기 때문에 return 명령만 작성한다.
            // 의미? 즉시 메서드 실행을 멈추고 이전 위치로 돌아간다.
        }

        Team team = teamDao.get(name);

        if (team == null) {
            System.out.println("해당 이름의 팀이 없습니다");
        } else {
            if (Console.confirm("정말 삭제하시겠습니까?")) {
                teamDao.delete(team.name);
                System.out.println("삭제하였습니다.");
            }
        }
    }

}
