package TestIOT; // 실제 패키지명에 맞게 조정하세요.

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class iot {

    /**
     * 키(신장) 데이터 목록을 받아서 평균 키를 계산합니다.
     *
     * @param heights 키 데이터 목록
     * @return 계산된 평균 키 값. 데이터가 없으면 0.0을 반환합니다.
     */
    public static double calculateAverageHeight(List<Double> heights) {
        if (heights == null || heights.isEmpty()) {
            return 0.0;
        }

        double sumOfHeights = 0.0;
        for (double height : heights) {
            sumOfHeights += height;
        }

        return sumOfHeights / heights.size();
    }

    public static void main(String[] args) {
        System.out.println("--- B반 학생 키 데이터 분석 ---");

        // 이승헌 학생 데이터 (B반에 속하는지 명시되지 않았으므로 별도 처리)
        String leeSeungHeon = "이승헌";
        double leeSeungHeonHeight = 180.0;
        System.out.println(leeSeungHeon + " 키: " + leeSeungHeonHeight + "cm");
        System.out.println("------------------------------------");

        // B반 학생들의 키 데이터를 Map으로 저장 (이름 -> 키)
        Map<String, Double> bClassStudentHeights = new HashMap<>();
        bClassStudentHeights.put("김준하", 172.0);
        bClassStudentHeights.put("조찬우", 170.0);
        bClassStudentHeights.put("최영석", 169.0);
        bClassStudentHeights.put("김지수", 169.0);
        bClassStudentHeights.put("김태을", 170.0);
        bClassStudentHeights.put("고태승", 181.0);
        bClassStudentHeights.put("윤희준", 181.0);
        bClassStudentHeights.put("박지성", 173.0);
        bClassStudentHeights.put("웬휴담", 165.0);
        bClassStudentHeights.put("전승민", 180.0);
        bClassStudentHeights.put("유현우", 172.0);
        bClassStudentHeights.put("여사우", 170.0);
        bClassStudentHeights.put("박찬솔", 165.0);
        bClassStudentHeights.put("손원형", 168.0);
        bClassStudentHeights.put("유정섭", 163.0);
        bClassStudentHeights.put("이형진", 182.0);

        // B반 학생들의 키만 따로 List에 모음
        List<Double> bClassHeightsList = new ArrayList<>(bClassStudentHeights.values());

        System.out.println("B반 학생 개별 키:");
        for (Map.Entry<String, Double> entry : bClassStudentHeights.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue() + "cm");
        }
        System.out.println("------------------------------------");

        // B반 전체 평균 키 계산
        double bClassAverageHeight = calculateAverageHeight(bClassHeightsList);

        System.out.println(String.format("B반 학생 수: %d명", bClassHeightsList.size()));
        System.out.println(String.format("B반 전체 평균 키 ('헤지값'): %.2f cm", bClassAverageHeight));
        System.out.println("------------------------------------");
    }
}