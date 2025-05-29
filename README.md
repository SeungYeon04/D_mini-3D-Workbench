## 🔗 앱 배포 URL 
url: 완성 후 오픈 예정  
  
## 🖥 프로젝트 소개  

이것은 JavaFx 라는 GUI를 사용한  
시각화 DB관리 워크벤치 미니 프로젝트입니다.  
  
JavaFx 공식: https://openjfx.io/  
  
사용할 라이브러리: 1. JavaFX (UI)  
2. MySQL Connector/J (DB 연결용)  
3. [ObjectGraphVisualization](https://github.com/Nurtak/ObjectGraphVisualization)  
  
JavaFx라는 GUI를 이용하여  
ObjectGraphVisualization 라이브러리를 사용하여  
DB 관리 프로그램을 시각화하여 관리할 수 있도록  
공부용 미니 워크벤치를 제작할 예정입니다.  
  
자바로 DB관리 프로그램을 만드는데  
JFrame의 기본 틀을 배웠지만..  
좀 더 완성도 높아보이는 개발과 그래픽을 원했기에..  
찾아보다가 택한 거랍니다.  
  
MySQL을 명령을 쳐서, 버튼을 눌러서  
직접 눈으로 보고 세팅할 수 있게 쉽게 쓸 수 있는  
초보자용 워크벤치 프로젝트입니다.  
(현재 진행형입니다. 2025)  
  
```
JavaFX는 JDK에서 분리되어 외부 라이브러리로 사용하므로, 
따라서 이클립스로 run할 때 --module-path와 --add-modules를 명시해야합니다. 

# Project 실행 전 주의사항
1. 가능하면 Java 21 이상 설치 
2. javafx-sdk-24.0.1 다운로드: https://gluonhq.com/products/javafx/
3. VM 옵션 (Eclips -> Run Configurations ->  VM arguments) :
 --module-path [자신의 JavaFX lib 경로] --add-modules javafx.controls,javafx.graphics

ex) javac --module-path "C:\javafx-sdk-24.0.1\lib" --add-modules javafx.controls,javafx.graphics -encoding UTF-8 -d bin src/pack/MainApp02.java
```
  
## ⚙️ 개발환경  
 
• CODE: Java  
• IDE: Eclips  
• VCS: Github desktop  
  
## 🕹 주요기능  

  
## 🖼 플레이 사진
