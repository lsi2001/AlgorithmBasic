## 제출 과제
* java 1.8 이상에서 확인 필요
* 소스 내에 프퍼티 (src/main/resources/config.json 수정 후) maven 빌드(mvn clean package) 하여 jar 파일을 실행 합니다.
* 프로퍼티 수정할 항목은 아래 config.json 에서 호출할 호스트 부분 및 호스트 별 docRoot 값
* 소스내 a, b 폴더는 docRoot 로 사용되는 폴더
* jar 실행은 디펜던시 라이브러리가 모두 참조된 was-0.0.1-SNAPSHOT-jar-with-dependencies.jar를 실행 합니다. java -jar was-0.0.1-SNAPSHOT-jar-with-dependencies.jar


### 프로퍼티 설정(config.json)
```
{
	"service_port":8000, (서비스 포트)
	"max_threads":50, (프로세스내의 쓰레드 수)
	"default_host":"xxx.xxx", (여러개의 리모트 호스트 중 디폴트 설정 - 구현 프로그램의 경우 http://localhost 로 호출 아래 두개중 하나를 사용하도록 설정)
	"hosts":[    (복수개 설정)
		{
			"host":"xxx.xxx", (첫번째 호스트 설정, 변경할 필수)
			"docRoot":"(첫번째 호스트의 Document 루트, 변경 필수-소스폴더내의 a/a_XXX.html 이 위치한 경로)",
			"index_file":"a_index.html", (인덱스 html 파일 명 및 http 응답 코드 별 html 파일)
			"error_403":"a_403.html",
			"error_404":"a_404.html",
			"error_500":"a_500.html",
			"error_501":"a_501.html",
			"mappingServlets":{    (매핑 url 별 실행할 서블릿 자바 클래스)
				"/Hello":"Hello",
				"/service.Hello":"service.Hello",
				"/time":"CurrentTime"
			}
		},
		{
			"host":"xxx.xxx",
			"docRoot":"(첫번째 호스트의 Document 루트, 변경 필수-소스폴더내의 b/b_XXX.html 이 위치한 경로)",
			"index_file":"b_index.html",
			"error_403":"b_403.html",
			"error_404":"b_404.html",
			"error_500":"b_500.html",
			"error_501":"b_501.html",
			"mappingServlets":{
				"/Hello":"Hello",
				"/service.Hello":"service.Hello",
				"/time":"CurrentTime"
			}
		}
	]
}
```

### http 해더 해석
* 프로퍼티에 의해 구분되도록 구현

### 설정을 파일로 관리
* 위 예제 참조 및 config.json 으로 설정

### 403, 404, 500 오류 처리
* 상황별 파일명을 config.json 에서 읽어 사용

### 보안 규칙 
* 호출된 url에 ../ 가 있을 경우, .exe로 끝날 경우 403 에러 페이지로 응답 구현

### logback 사용
* logback.xml 에 설정하여 로깅

### 간단한 was 구현
* SimpleServlet 인터페이스로 하여 service 메소드를 각 구현체에서 생성

### 현재 시간 구현체
* CurrentTime.java, /time 호출에 매핑

### junit 테스트
* HelloTest.java : 개인 시간 부족으로 인해 하나의 클래스만 샘플로 생성하였습니다. 
