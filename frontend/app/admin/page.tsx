'use client';

import { useEffect, useState } from 'react';
import Modal from './modal';
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

// 주문 시간 변환
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
  const [pendingOrders, setPendingOrders] = useState<Order[]>([]);
  const [completedOrders, setCompletedOrders] = useState<Order[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [showModal, setShowModal] = useState(false);
  const [selectedOrderId, setSelectedOrderId] = useState<number | null>(null);

  useEffect(() => {
    async function fetchOrders() {
      try {
        const response = await fetch('http://localhost:8080/admin/orders');
        if (!response.ok) {
          throw new Error(`Failed to fetch orders: ${response.status} ${response.statusText}`);
        }

        const orders: Order[] = await response.json();

        // 배송 상태 분류
        const pending = orders.filter(order => order.status === 'PENDING');
        const completed = orders.filter(order => order.status === 'DELIVERED');

        setPendingOrders(pending);
        setCompletedOrders(completed);
      } catch (error: any) {
        console.error('Error fetching orders:', error);
        setError(error.message);
      }
    }

    fetchOrders();
  }, []);

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

  return (
    <div className="container">
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

      {/* Modal */}
      <Modal isOpen={showModal} onClose={closeModal} onConfirm={handleCancelOrder} />
    </div>
  );
}
