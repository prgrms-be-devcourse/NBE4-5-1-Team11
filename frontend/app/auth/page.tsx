"use client";
import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { useRouter } from "next/navigation";
import "./auth.css";
import { SignUp, SignIn } from "../types/interface";

const API_URL = "http://localhost:8080/users";


const Authentication = () => {
  const [view, setView] = useState<"sign-in" | "sign-up">("sign-in");
  const router = useRouter();

  // 로그인 폼 관리
  const {
    register: registerSignIn,
    handleSubmit: handleSignIn,
    setError: setSignInError,
    formState: { errors: signInErrors },
  } = useForm<SignIn>();

  // 회원가입 폼 관리
  const {
    register: registerSignUp,
    handleSubmit: handleSignUp,
    watch: watchSignUp,
    setError: setSignUpError,
    formState: { errors: signUpErrors },
  } = useForm<SignUp>();

  const password = watchSignUp("password");

  const onSignInSubmit = async (data: SignIn) => {
    try {
      const response = await fetch(`${API_URL}/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
      });

      const result = await response.json();
      localStorage.setItem("accessToken", result.accessToken);
      
      router.push("/");
      setTimeout(() => {
      window.location.reload(); // ✅ 100% 새로고침 보장
    }, 500); // ✅ 0.1초 뒤 새로고침 (router.push 적용 후 실행되도록)
      
    } catch (error) {
      alert("이메일 혹은 비밀번호가 일치하지 않습니다.");
    }
  };
  
  const onSignUpSubmit = async (data: SignUp) => {
    try {
      const response = await fetch(`${API_URL}/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
      });
    
      const result = await response.json();
       
      alert("회원 등록이 완료되었습니다.");
      setView("sign-in");

    } catch (error) {
      alert("이미 존재하는 이메일입니다.");
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-card">
        <h2 className="auth-title">{view === "sign-in" ? "로그인" : "회원가입"}</h2>

        {/* 로그인 폼 */}
        {view === "sign-in" && (
          <form onSubmit={handleSignIn(onSignInSubmit)} className="auth-form">
            <input
              type="email"
              placeholder="이메일 주소"
              {...registerSignIn("email", { required: "이메일을 입력해주세요." })}
              className="auth-input"
            />
            {signInErrors.email && <p className="auth-error">{signInErrors.email.message}</p>}

            <input
              type="password"
              placeholder="비밀번호"
              {...registerSignIn("password", { required: "비밀번호를 입력해주세요." })}
              className="auth-input"
            />
            {signInErrors.password && <p className="auth-error">{signInErrors.password.message}</p>}

            <button type="submit" className="auth-button">로그인</button>

            <p className="auth-switch">
              계정이 없나요?{" "}
              <span className="auth-link" onClick={() => setView("sign-up")}>
                회원가입
              </span>
            </p>
          </form>
        )}

        {/* 회원가입 폼 */}
        {view === "sign-up" && (
          <form onSubmit={handleSignUp(onSignUpSubmit)} className="auth-form">
            <input
              type="email"
              placeholder="이메일 주소"
              {...registerSignUp("email", { required: "이메일을 입력해주세요." })}
              className="auth-input"
            />
            {signUpErrors.email && <p className="auth-error">{signUpErrors.email.message}</p>}

            <input
              type="password"
              placeholder="비밀번호"
              {...registerSignUp("password", { required: "비밀번호를 입력해주세요.", minLength: 8 })}
              className="auth-input"
            />
            {signUpErrors.password && <p className="auth-error">{signUpErrors.password.message}</p>}

            <input
              type="password"
              placeholder="비밀번호 확인"
              {...registerSignUp("confirmPassword", {
                validate: (value) => value === password || "비밀번호가 일치하지 않습니다.",
              })}
              className="auth-input"
            />
            {signUpErrors.confirmPassword && <p className="auth-error">{signUpErrors.confirmPassword.message}</p>}

            <button type="submit" className="auth-button auth-signup-button">회원가입</button>

            <p className="auth-switch">
              계정이 있나요?{" "}
              <span className="auth-link" onClick={() => setView("sign-in")}>
                로그인
              </span>
            </p>
          </form>
        )}
      </div>
    </div>
  );
};

export default Authentication;
