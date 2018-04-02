package project.pms.dao;

import project.pms.domain.Member;

// Data Acess Object(DAO) 데이터 접근 객체
// MemberDao를 사용하여 팀 데이터를 관리한다.
// MemberController로부터 데이터 관리 기능을 분리하여 MemberDao 생성.
public class MemberDao {
    // 여러 회원 정보를 저장할 레퍼런스 배열을 준비한다.
    Member[] members = new Member[1000];
    int memberIndex = 0;
    
    
    // 회원 정보가 담겨있는 객체의 주소를 배열에 보관하는 코드를 메서드로 분리한다.
    public void insert(Member member) {
        // 회원 정보가 담겨있는 객체의 주소를 배열에 보관한다.
        this.members[this.memberIndex++] = member;
    }
    
    
    

    public Member[] list() {
        Member[] arr = new Member[this.memberIndex];
        for (int i = 0; i < this.memberIndex; i++)
            arr[i] = this.members[i];
        return arr;
    }

    public Member get(String id) {
        int i = this.getMemberIndex(id);
        if (i == -1)
            return null;
        return this.members[i];
    }
    
    
    public void update(Member member) {
        int i = this.getMemberIndex(member.id);
        if (i != -1) {
            this.members[i] = member;
        }
    }
    
    
    public void delete(String id) {
        int i = this.getMemberIndex(id);
        if (i != -1)
            this.members[i] = null;
    }
    
    

    // 회원아이디로 배열에서 회원 정보를 찾는 코드를 함수로 분리한다. => getMemberIndex() 추가
    // 다음 메서드는 내부에서만 사용할 것이기 때문에 공개하지 않는다.
    private int getMemberIndex(String id) {
        for (int i = 0; i < this.memberIndex; i++) {
            if (this.members[i] == null)
                continue;
            if (id.equals(this.members[i].id.toLowerCase())) {
                return i;
            }

        }
        return -1;
    }
}
