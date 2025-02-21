export default interface User {
  id?: number; // 유저 ID (백엔드에서 자동 생성되므로 옵셔널)
  email: string; // 유저 이메일 (필수)
}