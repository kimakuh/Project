// 이 클래스는 명령창에서 사용할 기능을 모아 둔 클래스이다.
package project.pms.util;

import java.util.Scanner;

public class Console {
    // 이 클래스를 사용하기 전에 반드시 Scanner 객체를 설정하라!
    
    public static Scanner keyScan;
    
    // 사용자에게 yes/no를 묻는 코드를 메서드로 분리한다. => confirm() 추가

    public static boolean confirm(String message) {
        System.out.printf("%s (y/n)", message);
        String input = keyScan.nextLine().toLowerCase();
        if (input.equals("y"))
            return true;
        else
            return false;
    }

    public static String[] prompt() {
        System.out.print("명령> ");
        // 입력 받은 문자열을 공백으로 잘라서 명령과 검색어로 구분한다.
        return keyScan.nextLine().toLowerCase().split(" ");
    }
}
