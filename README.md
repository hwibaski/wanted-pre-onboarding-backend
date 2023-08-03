# my-post-api-app

- 참여 지원자 : 김휘민

## 1. 애플리케이션 실행 방법

```shell
$ docker-comsose up --build
```

### 1-1. endpoint 호출 방법

> 권한이 필요한 API는 HTTP Authorization 헤더에 Bearer {JWT 토큰} 형식으로 access token을 첨부해야함

1. 회원 가입 [POST]
    - /api/auth/signup

2. 로그인 [POST]
    - /api/auth/signin

3. 글작성(권한 필요) [POST]
    - /api/post

3. 글목록 [GET]
    - /api/posts

4. 특정 글 조회 [GET]
    - /api/posts/{postId}

5. 글 수정(권한 필요) [PATCH]
    - /api/posts/{postId}

6. 글 삭제(권한 필요) [DELETE]
    - /api/posts/{postId}

## 2. ERD
![image](https://github.com/hwibaski/wanted-pre-onboarding-backend/assets/85930725/df59fb0f-cff6-4756-a6e1-dd65b4097725)

## 3. API 동작 촬영 데모 영상 링크

## 4. 구현 방법 및 이유에 대한 간략한 설명

- spring-data-jpa를 이용한 영속성 관리
    - 객체와 DB의 변환 과정을 간결하게 관리해주고 business 로직에 집중할 수 있도록 함
    - DB의 테이블을 Entity로 관리함으로서 객체지향 프로그래밍에 가깝게 프로그래밍할 수 있음
- 모든 service 코드에 대한 unit 테스트
    - 비지니스 코드에 대한 유닛 테스트를 작성함으로서 유지보수성 향상
- 모든 controller에 대한 e2e 테스트
    - e2e테스트를 작성해 비지니스 로직 뿐만 아니라 응답에 대한 명세도 확인

## 5. API 명세


