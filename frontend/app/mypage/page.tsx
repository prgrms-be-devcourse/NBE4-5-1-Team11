"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { Order } from "../types/interface";
import "./style.css";

const API_URL = "http://localhost:8080";

const OrdersPage = () => {
  const router = useRouter();
  const [orders, setOrders] = useState<Order[]>([]);
  const [loading, setLoading] = useState(true);
  const [selectedOrder, setSelectedOrder] = useState<Order | null>(null);
  const [userId, setUserId] = useState<number | null>(null); // 유저 ID 저장

  useEffect(() => {
    fetchOrders();
  }, [router]);

  const fetchOrders = async () => {
    const token = localStorage.getItem("accessToken");

    if (!token) {
      alert("로그인이 필요합니다.");
      router.push("/auth");
      return;
    }

    try {
      const response = await fetch(`${API_URL}/orders/user`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.status === 401) {
        alert("세션이 만료되었습니다. 다시 로그인해주세요.");
        localStorage.removeItem("accessToken");
        router.push("/auth");
        return;
      }

      if (!response.ok) {
        throw new Error("주문 정보를 불러올 수 없습니다.");
      }

      const orderData: Order[] = await response.json();

      if (!Array.isArray(orderData)) {
        throw new Error("올바른 주문 데이터 형식이 아닙니다.");
      }

      setOrders(orderData);

      // 첫 번째 주문에서 유저 ID 가져오기 (모든 주문이 같은 사용자 ID를 가지므로)
      if (orderData.length > 0) {
        setUserId(orderData[0].id);
      }
    } catch (error) {
      console.error("🚨 주문 정보 가져오기 실패:", error);
      alert("서버 오류가 발생했습니다.");
    } finally {
      setLoading(false);
    }
  };

  /** ✅ 회원 탈퇴 함수 */
  const deleteUser = async () => {
    if (!userId) {
      alert("회원 정보를 찾을 수 없습니다.");
      return;
    }

    const token = localStorage.getItem("accessToken");

    if (!confirm("정말로 회원 탈퇴를 진행하시겠습니까? 이 작업은 되돌릴 수 없습니다.")) {
      return;
    }

    try {
      const response = await fetch(`${API_URL}/users/${userId}`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error("회원 탈퇴에 실패했습니다.");
      }

      alert("회원 탈퇴가 완료되었습니다.");
      localStorage.removeItem("accessToken"); // 토큰 삭제
      router.push("/auth"); // 로그인 페이지로 이동
    } catch (error) {
      console.error("🚨 회원 탈퇴 실패:", error);
      alert("회원 탈퇴 중 오류가 발생했습니다.");
    }
  };

  if (loading) return <p>로딩 중...</p>;

  return (
    <div className="orders-container">
      <h1>내 주문 목록</h1>
      {orders.length === 0 ? (
        <p>주문 내역이 없습니다.</p>
      ) : (
        <ul className="orders-list">
          {orders.map((order) => (
            <li key={order.id} className="order-item">
              <h3>주문 번호: {order.id}</h3>
              <p>주문 시간: {new Date(order.createdAt).toLocaleString("ko-KR")}</p>
              <p>주소: {order.address} {order.code}</p>
              <p>총 가격: {order.totalPrice}원</p>
              <p className={order.status === "DELIVERED" ? "delivered" : "pending"}>
                배송 상태: {order.status === "DELIVERED" ? "배송 완료" : "배송 전"}
              </p>

              {/* ✅ 배송 전(PENDING) 상태에서만 주문 취소 버튼 표시 */}
              
              {/* 주문 상세 보기 버튼 */}
              <button className="detail-btn" onClick={() => setSelectedOrder(order)}>
                주문 상세 보기
              </button>
            </li>
          ))}
        </ul>
      )}

      {/* ✅ 회원 탈퇴 버튼 */}
      <button className="delete-btn" onClick={deleteUser}>
        회원 탈퇴
      </button>

      {/* 주문 상세 모달 */}
      {selectedOrder && (
        <div className="modal-overlay" onClick={() => setSelectedOrder(null)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <h2>주문 상세 정보</h2>
            <button className="close-btn" onClick={() => setSelectedOrder(null)}>
              ✖
            </button>
            <ul>
              {selectedOrder.products.map((op, index) => (
                <li key={index} className="product-item">
                  <img src={op.product.image} alt={op.product.name} width={80} />
                  <div>
                    <p>상품명: {op.product.name}</p>
                    <p>수량: {op.quantity}개</p>
                    <p>가격: {op.product.price}원</p>
                  </div>
                </li>
              ))}
            </ul>
          </div>
        </div>
      )}
    </div>
  );
};

export default OrdersPage;
