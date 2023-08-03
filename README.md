# my-post-api-app

- 참여 지원자 : 김휘민

</br>

## 1. 애플리케이션 실행 방법

```shell
$ docker-comsose up --build
```

</br>

### 1-1. endpoint 호출 방법

> 권한이 필요한 API는 HTTP Authorization 헤더에 Bearer {JWT 토큰} 형식으로 access token을 첨부해야함

1. 회원 가입 [POST]
    - `/api/auth/signup`

2. 로그인 [POST]
    - `/api/auth/signin`

3. 글작성(권한 필요) [POST]
    - `/api/post`

3. 글목록 [GET]
    - `/api/posts`

4. 특정 글 조회 [GET]
    - `/api/posts/{postId}`

5. 글 수정(권한 필요) [PATCH]
    - `/api/posts/{postId}`

6. 글 삭제(권한 필요) [DELETE]
    - `/api/posts/{postId}`

</br>

## 2. ERD
![image](https://github.com/hwibaski/wanted-pre-onboarding-backend/assets/85930725/df59fb0f-cff6-4756-a6e1-dd65b4097725)

</br>

## 3. API 동작 촬영 데모 영상 링크

- 회원가입


https://github.com/hwibaski/wanted-pre-onboarding-backend/assets/85930725/035f592b-da94-42d0-b9d8-b86ea311f5d0

- 로그인



https://github.com/hwibaski/wanted-pre-onboarding-backend/assets/85930725/f3b6b6a2-8143-4afa-bea8-f2b26b31180e


- 글 작성



https://github.com/hwibaski/wanted-pre-onboarding-backend/assets/85930725/cd03e728-873e-42a6-a5d0-2e6635a9ddf5


- 단일 글 조회

  
https://github.com/hwibaski/wanted-pre-onboarding-backend/assets/85930725/cd6e8007-1742-44cb-9da2-4b834679d44a


- 글 수정


https://github.com/hwibaski/wanted-pre-onboarding-backend/assets/85930725/7bf9e0d9-5f90-4f6e-b160-26853d4845dc


- 글 삭제


https://github.com/hwibaski/wanted-pre-onboarding-backend/assets/85930725/d57f132e-bc07-4db9-a29a-4781048cad3a


- 글 목록 조회


https://github.com/hwibaski/wanted-pre-onboarding-backend/assets/85930725/8bd9b71f-8444-4503-9c0e-6653df86147e


</br>

## 4. 구현 방법 및 이유에 대한 간략한 설명

- spring-data-jpa를 이용한 영속성 관리
    - 객체와 DB의 변환 과정을 간결하게 관리해주고 business 로직에 집중할 수 있도록 함
    - DB의 테이블을 Entity로 관리함으로서 객체지향 프로그래밍에 가깝게 프로그래밍할 수 있음
- 모든 service 코드에 대한 unit 테스트
    - 비지니스 코드에 대한 유닛 테스트를 작성함으로서 유지보수성 향상
- 모든 controller에 대한 e2e 테스트
    - e2e테스트를 작성해 비지니스 로직 뿐만 아니라 응답에 대한 명세도 확인

</br>

## 5. API 명세

[API 명세 및 테스트 호출](https://documenter.getpostman.com/view/16343905/2s9XxwwDyi#018e9073-9be0-42b8-8bd2-42ac14a1d758)

위 링크는 포스트맨을 이용한 API 명세 및 테스트 호출 가능한 링크입니다.

![Screen Shot 2023-08-03 at 5 08 35 PM](https://github.com/hwibaski/wanted-pre-onboarding-backend/assets/85930725/b80acf7c-536e-48a2-adde-168eb6700263)

> 상기 이미지에서 처럼 빨간박스를 클릭하시면 다양한 케이스들을 미리 만들어놓았습니다.

