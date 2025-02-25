"use client";
import { useAuth } from "@/app/context/LoginContext"; // ✅ 로그인 상태 가져오기
import "./style.css";

const Header = () => {
  const { isAuthenticated, logout } = useAuth(); // ✅ 로그인 상태 및 `logout` 함수 가져오기

  return (
    <header className="header">
      <h1>Coffee Management System</h1>
      <nav>
        <ul>
          <li><a href="/">Home</a></li>
          {!isAuthenticated ? (
            <li><a href="/auth">Auth</a></li>
          ) : (
            <li><a href="#" onClick={logout}>Logout</a></li>
          )}
          <li><a href="/mypage">MyPage</a></li>
          <li><a href="/admin">Admin</a></li>
        </ul>
      </nav>
    </header>
  );
};

export default Header;
