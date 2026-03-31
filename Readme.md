# AD-visor 🎯

> AI 기반 인스타그램 광고 이미지 자동 생성 플랫폼
---

## 목차

1. [프로젝트 개요](#1-프로젝트-개요)
2. [기술 스택](#2-기술-스택)
3. [시스템 아키텍처](#3-시스템-아키텍처)

---

## 1. 프로젝트 개요

**AD-visor**는 사용자가 광고 관련 요청사항을 입력하면, AI를 기반으로 인스타그램 광고에 최적화된 이미지를 자동으로 생성해주는 플랫폼입니다.

---

## 2. 기술 스택

| 분류        | 기술                        
|-----------|------------------------------|
| Language  | Kotlin 2.3.x                 | 
| Framework | Spring Boot 4.x              | 
| DB        | Postgres 17                  | 
| Auth      | Spring Security + OAuth2 + JWT |

### Infrastructure

| 분류 | 기술 |
|---|---|
| Container | Docker / Docker Compose |
| Cloud | AWS (EC2, RDS, S3) — 추후 배포 예정 |
| Image Storage | AWS S3 (생성된 광고 이미지 저장) |
| AI API | OpenAI DALL-E 3 |

## 3. 아키텍처

### 패키지 구조

```
{domain}
├── app
│   └── build.gradle.kts      # 어플리케이션 실행부, 의존성 조립
├── common
│   └── build.gradle.kts      # 전역 유틸리티, 공통 예외 처리 등
├── src
│   ├── domain
│   │   └── build.gradle.kts  # 핵심 비즈니스 로직, 엔티티
│   ├── application
│   │   └── build.gradle.kts  # 유스케이스, 서비스 인터페이스
│   ├── port
│   │   └── build.gradle.kts  # 인/아웃바운드 인터페이스(추상화)
│   └── adapter
│       ├── inbound
│       │   └── build.gradle.kts  # Controller, API, Consumer
│       └── outbound
│           └── build.gradle.kts  # DB Repository 구현체, 외부 API 클라이언트
├── build.gradle.kts          # 루트 빌드 설정
└── settings.gradle.kts       # 모듈 계층 정의
```
