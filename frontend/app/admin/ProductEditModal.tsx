'use client';
import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';
import './style.css';

interface Product {
  id: number;
  name: string;
  price: number;
  image: string;
}

interface ProductEditModalProps {
  isOpen: boolean;
  onClose: () => void;
}

export default function ProductEditModal({ isOpen, onClose }: ProductEditModalProps) {
  const router = useRouter();
  const [products, setProducts] = useState<Product[]>([]);
  const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);
  const [newPrice, setNewPrice] = useState("");

  useEffect(() => {
    if (isOpen) {
      fetchProducts();
    }
  }, [isOpen]);

  const fetchProducts = async () => {
    try {
      const response = await fetch("http://localhost:8080/products");
      if (!response.ok) throw new Error("상품 정보를 불러올 수 없습니다.");
      const data = await response.json();
      setProducts(data);
    } catch (error) {
      console.error("상품 조회 실패:", error);
    }
  };

  const handleUpdatePrice = async (productId: number) => {
    if (!newPrice) return;
    const token = localStorage.getItem("accessToken");

    if (!token) {
      alert("로그인이 필요합니다.");
      router.push("/auth");
      return;
    }

    try {
      const response = await fetch(`http://localhost:8080/products/${productId}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ price: Number(newPrice) }),
      });

      if (!response.ok) throw new Error("가격 수정 실패");

      setProducts(prev =>
        prev.map(p => (p.id === productId ? { ...p, price: Number(newPrice) } : p))
      );

      setNewPrice("");
      setSelectedProduct(null);
    } catch (error) {
      console.error("가격 수정 오류:", error);
    }
  };

  if (!isOpen) return null;

  return (
    <div className="modalOverlay">
      <div className="modalContainer">
        <h2>상품 가격 수정</h2>
        <table className="orderTable">
          <thead>
            <tr>
              <th>상품명</th>
              <th>현재 가격</th>
              <th>새 가격</th>
              <th>수정</th>
            </tr>
          </thead>
          <tbody>
            {products.map(product => (
              <tr key={product.id}>
                <td>{product.name}</td>
                <td>{product.price}원</td>
                <td>
                  <input
                    type="number"
                    value={selectedProduct?.id === product.id ? newPrice : ""}
                    onChange={(e) => {
                      setSelectedProduct(product);
                      setNewPrice(e.target.value);
                    }}
                  />
                </td>
                <td>
                  <button onClick={() => handleUpdatePrice(product.id)}>수정</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <button onClick={onClose}>닫기</button>
      </div>
    </div>
  );
}
