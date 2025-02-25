'use client';

import { useEffect, useState } from 'react';
import Modal from './modal';
import './style.css';

interface Product {
  id: number;
  name: string;
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
}

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
        setPendingOrders(orders);
        setCompletedOrders([]); // 배송 완료 주문은 비움, 상태 값 들어오는 거보고 수정 필요
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
                <th>구매내역</th>
                <th>취소</th>
              </tr>
            </thead>
            <tbody>
              {pendingOrders.length === 0 ? (
                <tr>
                  <td colSpan={6} className="emptyText">주문이 없습니다.</td>
                </tr>
              ) : (
                pendingOrders.map(order => (
                  <tr key={order.id}>
                    <td>{order.id}</td>
                    <td>{order.email}</td>
                    <td>{order.address} {order.code}</td>
                    <td>{order.totalPrice}</td>
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
                <th>구매내역</th>
              </tr>
            </thead>
            <tbody>
              {completedOrders.length === 0 ? (
                <tr>
                  <td colSpan={5} className="emptyText">배송 완료된 주문이 없습니다.</td>
                </tr>
              ) : (
                completedOrders.map(order => (
                  <tr key={order.id}>
                    <td>{order.id}</td>
                    <td>{order.email}</td>
                    <td>{order.address} {order.code}</td>
                    <td>{order.totalPrice}</td>
                    <td>{order.products.map(product => `${product.product.name} (${product.quantity}개)`).join(', ')}</td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>

      {/* 모달 컴포넌트 */}
      <Modal isOpen={showModal} onClose={closeModal} onConfirm={handleCancelOrder} />
    </div>
  );
}
