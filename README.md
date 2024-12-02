# **Comment Service**

Comment Service는 댓글 등록, 수정, 삭제, 조회를 처리합니다.   
다른 마이크로서비스와의 통신을 위해 OpenFeign을 사용하며, CircuitBreaker와 OpenFeign Fallback을 설정하여 시스템의 안정성을 향상시켰습니다.

## **기술스택**

- **Java 21**
- **Spring boot**
- **Spring Cloud Netflix Eureka Client**
- **Spring Cloud Config Client**
- **Spring Cloud OpenFeign**
- **Apache Kafka**
- **Resilience4j CircuitBreaker**


## **API 엔드포인트**

### **댓글 등록**

- **URI**: `POST /api/posts/{postId}/comments`
- **요청 헤더**: 

    ```http
    Authorization: Bearer <JWT 토큰>
- **요청 본문**:

  ```json
  {
    "content": "content"
  }
 

### **댓글 수정**

- **URI**: `PUT /api/posts/{postId}/comments/{commentId}`
- **요청 헤더**: 

    ```http
    Authorization: Bearer <JWT 토큰>
- **요청 본문**:

  ```json
  {
    "content": "content"
  }

### **댓글 조회**
`게시글에 저장된 모든 댓글을 조회합니다.`
- **URI**: `GET /api/posts/{postId}/comments`
- **응답 본문**:

  ```json
  
    [
        {
            "id": 1,
            "userName": "user",
            "postId": 1,
            "content": "content"
        },
        {
            "id": 2,
            "userName": "user",
            "postId": 1,
            "content": "content"
        },
        ...        
    ]
    
  

### **댓글 삭제**

- **URI**: `DELETE /api/posts/{postId}/comments/{commentId}`
- **요청 헤더**: 

    ```http
    Authorization: Bearer <JWT 토큰>