'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import Modal from './modal';
import ProductEditModal from './ProductEditModal';
import './style.css';

interface Product {
  id: number;
  name: string;
  image: string;
  price: number;
}

interface OrderProduct {
  id: number;
  orderId: number;
  product: Product;
  price: number;
  quantity: number;
}

interface Order {
  id: number;
  email: string;
  address: string;
  code: string;
  totalPrice: number;
  products: OrderProduct[];
  status: 'PENDING' | 'DELIVERED';
  createdAt: string;
}

// 주문 시간 변환 함수
const formatDate = (dateString: string) => {
  if (!dateString) return '날짜 없음';

  const parsedDate = new Date(dateString.includes('T') ? dateString : dateString.replace(' ', 'T'));

  return parsedDate.toLocaleString('ko-KR', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit',
  });
};

export default function AdminOrderPage() {
  const router = useRouter();
  const [showProductModal, setShowProductModal] = useState(false);
  const [pendingOrders, setPendingOrders] = useState<Order[]>([]);
  const [completedOrders, setCompletedOrders] = useState<Order[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [showModal, setShowModal] = useState(false);
  const [selectedOrderId, setSelectedOrderId] = useState<number | null>(null);
  const [loading, setLoading] = useState(true);

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
      const response = await fetch("http://localhost:8080/admin/orders", {
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

      const orders: Order[] = await response.json();

      if (!Array.isArray(orders)) {
        throw new Error("올바른 주문 데이터 형식이 아닙니다.");
      }

      // 배송 상태 분류
      const pending = orders.filter(order => order.status === 'PENDING');
      const completed = orders.filter(order => order.status === 'DELIVERED');

      setPendingOrders(pending);
      setCompletedOrders(completed);
    } catch (error) {
      alert("🚨 접근 권한이 없습니다.");
      router.push("/");
    } finally {
      setLoading(false);
    }
  };

  // 모달 열기
  const openModal = (orderId: number) => {
    setSelectedOrderId(orderId);
    setShowModal(true);
  };

  // 모달 닫기
  const closeModal = () => {
    setShowModal(false);
    setSelectedOrderId(null);
  };

  // 주문 취소 처리
  const handleCancelOrder = async () => {
    if (selectedOrderId !== null) {
      try {
        const response = await fetch(`http://localhost:8080/admin/orders/${selectedOrderId}`, {
          method: 'DELETE',
          headers: {
            "Authorization": `Bearer ${localStorage.getItem("accessToken")}`
          }
        });

        if (!response.ok) {
          throw new Error('주문 취소에 실패했습니다.');
        }

        // 주문 취소 후 UI에서 즉시 반영
        setPendingOrders(prevOrders => prevOrders.filter(order => order.id !== selectedOrderId));
      } catch (error) {
        console.error('Error cancelling order:', error);
        alert('주문 취소 중 오류가 발생했습니다.');
      } finally {
        closeModal();
      }
    }
  };

  if (loading) return <p>주문 정보를 불러오는 중...</p>;

  return (
    <div className="container">
      {/* 상품 수정 버튼 */}
      <button className="editButton" onClick={() => setShowProductModal(true)}>상품 수정</button>
        
      {/* 배송 전 주문 목록 */}
      <div className="orderSection pendingOrders">
        <h2>배송 전 주문</h2>
        <div className="orderCardBox">
          <table className="orderTable">
            <thead>
              <tr>
                <th>번호</th>
                <th>이메일</th>
                <th>주소</th>
                <th>가격</th>
                <th>주문시간</th>
                <th>구매내역</th>
                <th>취소</th>
              </tr>
            </thead>
            <tbody>
              {pendingOrders.length === 0 ? (
                <tr>
                  <td colSpan={7} className="emptyText">배송 전 주문이 없습니다.</td>
                </tr>
              ) : (
                pendingOrders.map((order, idx) => (
                  <tr key={order.id}>
                    <td>{idx + 1}</td>
                    <td>{order.email}</td>
                    <td>{order.address} {order.code}</td>
                    <td>{order.totalPrice}</td>
                    <td>{formatDate(order.createdAt)}</td>
                    <td>{order.products.map(product => `${product.product.name} (${product.quantity}개)`).join(', ')}</td>
                    <td>
                      <button className="cancelButton" onClick={() => openModal(order.id)}>취소</button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>

      {/* 배송 완료 주문 목록 */}
      <div className="orderSection completedOrders">
        <h2>배송 완료 주문</h2>
        <div className="orderCardBox">
          <table className="orderTable">
            <thead>
              <tr>
                <th>번호</th>
                <th>이메일</th>
                <th>주소</th>
                <th>가격</th>
                <th>주문시간</th>
                <th>구매내역</th>
              </tr>
            </thead>
            <tbody>
              {completedOrders.length === 0 ? (
                <tr>
                  <td colSpan={6} className="emptyText">배송 완료된 주문이 없습니다.</td>
                </tr>
              ) : (
                completedOrders.map((order, idx) => (
                  <tr key={order.id}>
                    <td>{idx + 1}</td>
                    <td>{order.email}</td>
                    <td>{order.address} {order.code}</td>
                    <td>{order.totalPrice}</td>
                    <td>{formatDate(order.createdAt)}</td>
                    <td>{order.products.map(product => `${product.product.name} (${product.quantity}개)`).join(', ')}</td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>
      
      {/* 상품 수정 모달 */}
      <ProductEditModal isOpen={showProductModal} onClose={() => setShowProductModal(false)} />

      {/* 주문 취소 확인 모달 */}
      <Modal isOpen={showModal} onClose={closeModal} onConfirm={handleCancelOrder} />
    </div>
  );
}
