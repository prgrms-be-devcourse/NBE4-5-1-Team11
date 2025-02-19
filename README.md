## **☕ Cafe Menu Management Service**
> **Spring Boot 기본의 카페 메뉴 및 주문 관리 서비스**  
> 고객이 온라인으로 커피 원두를 주문하면, 하루 동안의 주문을 취합하여 다음날 배송을 진행하는 시스템입니다.  

<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/H2%20Database-003366?style=for-the-badge&logo=h2&logoColor=white"/>
  <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"/>
</p>

---

## 📌 **목차**
- [🚀 프로젝트 소개](#-프로젝트-소개)
- [🛠 기술 스택](#-기술-스택)
- [📂 프로젝트 구조](#-프로젝트-구조)
- [🎯 주요 기능](#-주요-기능)
- [📦 ERD (데이터베이스 모델링)](#-erd-데이터베이스-모델링)
- [💻 실행 방법](#-실행-방법)
- [📮 API 명세](#-api-명세)
- [🎨 화면 예시](#-화면-예시)

---

## 🚀 **프로젝트 소개**
> **“Grids & Circles” 로컬 카페의 온라인 주문 시스템**  
- 고객이 **커피 원두 상품**을 온라인에서 주문할 수 있습니다.  
- 하루 동안의 주문을 **오후 2시 기준으로 물리** 다음날 일곱 배송합니다.  
- 고객 정보는 별도로 저장하지 않고, `email`을 통해 구분합니다.  

---

## 🛠 **기술 스택**
> **백업 (Backend)**
- **Spring Boot 3.0** - 웹 애플리케이션 프레임워크  
- **Spring Data JPA** - ORM 및 데이터베이스 관리  
- **H2 Database** - 개발용 인모모리 데이터베이스  
- **Lombok** - 간결한 코드 작성을 위한 라이브러리  

> **빌드 & 배포**
- **Gradle** - 프로젝트 빌드 및 의존성 관리  
- **GitHub Actions** - CI/CD 자동화  

> **협업 & 문서화**
- **GitHub Projects** - 칸반 보드로 일정 관리  
- **Swagger** - API 문서 자동화  
