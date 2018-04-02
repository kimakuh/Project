package project.pms.dao;

import project.pms.domain.Team;

// Data Acess Object(DAO) 데이터 접근 객체
// TeamDao를 사용하여 팀 데이터를 관리한다.
// TeamController로부터 데이터 관리 기능을 분리하여 TeamDao 생성.
public class TeamDao {

    // 여러 팀 정보를 저장할 레퍼런스 배열을 준비한다.
    Team[] teams = new Team[1000];
    int teamIndex = 0;

    // 팀 정보가 담겨있는 객체의 주소를 배열에 보관하는 코드를 메서드로 분리한다.
    public void insert(Team team) {
        // 팀 정보가 담겨있는 객체의 주소를 배열에 보관
        this.teams[this.teamIndex++] = team;
    }

    public Team[] list() {
        Team[] arr = new Team[this.teamIndex];
        for (int i = 0; i < this.teamIndex; i++)
            arr[i] = this.teams[i];
        return arr;
    }

    public Team get(String name) {
        int i = this.getTeamIndex(name);
        if (i == -1)
            return null;
        return teams[i];
    }

    public void update(Team team) {
        int i = this.getTeamIndex(team.name);
        if (i != -1)
            teams[i] = team;
    }

    public void delete(String name) {
        int i = this.getTeamIndex(name);
        if (i != -1)
            teams[i] = null;
    }

    // 팀명으로 배열에서 팀 정보를 찾는 코드를 함수로 분리한다. => getTeamIndex() 추가
    // 다음 메서드는 내부에서만 사용할 것이기 때문에 공개하지 않는다.
    private int getTeamIndex(String name) {
        for (int i = 0; i < teamIndex; i++) {
            if (this.teams[i] == null)
                continue;
            if (name.equals(this.teams[i].name.toLowerCase())) {
                return i;
            }
        }
        return -1;
    }
}
