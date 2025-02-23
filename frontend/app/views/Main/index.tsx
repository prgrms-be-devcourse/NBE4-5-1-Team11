"use client";
import { useEffect, useState } from "react";
import "./style.css";  // 스타일 파일 import
import { Product } from "@/app/types/interface";

interface CartItem {
  product: Product;
  quantity: number;
}

const Home = () => {
  // 상태 관리
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [cart, setCart] = useState<CartItem[]>([]);
  const [email, setEmail] = useState<string>("");
  const [address, setAddress] = useState<string>("");
  const [code, setCode] = useState<string>("");

  // 📌 상품 목록 가져오기 (최초 1회 실행)
  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await fetch("http://localhost:8080/products");
        const data = await response.json();
        setProducts(data);
      } catch (error) {
        console.error("상품 목록을 가져오는 데 실패했습니다:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchProducts();
  }, []);

  // 📌 장바구니에 상품 추가
  const addToCart = (product: Product) => {
    setCart((prevCart) => {
      const itemIndex = prevCart.findIndex((item) => item.product.id === product.id);
      if (itemIndex !== -1) {
        // 이미 장바구니에 있는 경우 수량 증가
        const updatedCart = [...prevCart];
        updatedCart[itemIndex].quantity += 1;
        return updatedCart;
      } else {
        // 새로운 상품 추가
        return [...prevCart, { product, quantity: 1 }];
      }
    });
  };

  // 📌 장바구니에서 상품 삭제
  const removeFromCart = (productId: number) => {
    setCart((prevCart) => prevCart.filter((item) => item.product.id !== productId));
  };

  // 📌 장바구니 수량 증가
  const increaseQuantity = (productId: number) => {
    setCart((prevCart) =>
      prevCart.map((item) =>
        item.product.id === productId ? { ...item, quantity: item.quantity + 1 } : item
      )
    );
  };

  // 📌 장바구니 수량 감소 (1 이하일 경우 삭제)
  const decreaseQuantity = (productId: number) => {
    setCart((prevCart) =>
      prevCart
        .map((item) =>
          item.product.id === productId && item.quantity > 1
            ? { ...item, quantity: item.quantity - 1 }
            : item
        )
        .filter((item) => item.quantity > 0)
    );
  };

  // 📌 총 결제 금액 계산
  const totalPrice = cart.reduce((sum, item) => sum + item.product.price * item.quantity, 0);

  // 📌 주문 처리 함수
  const handleOrder = async (e: React.FormEvent) => {
    e.preventDefault();
    
    try {
      // 1️⃣ 기존 유저 확인
      let userResponse = await fetch(`http://localhost:8080/users?email=${email}`);
      let userResult = await userResponse.json();
  
      if (!userResponse.ok || !userResult.id) {
        // 2️⃣ 유저가 없으면 회원가입 진행
        const newUserResponse = await fetch("http://localhost:8080/users", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ email }),
        });
  
        if (!newUserResponse.ok) throw new Error("회원 등록 실패");
  
        userResult = await newUserResponse.json();
      }
  
      // 3️⃣ 주문 데이터 생성
      const orderData = {
        userId: userResult.id,
        address,
        code,
        createdAt: new Date().toISOString(),
        totalPrice,
        products: cart.map((item) => ({
          id: item.product.id,
          name: item.product.name,
          quantity: item.quantity,
        })),
      };
  
      // 4️⃣ 주문 요청
      const orderResponse = await fetch("http://localhost:8080/order", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(orderData),
      });
  
      if (!orderResponse.ok) throw new Error("주문 요청 실패");
  
      alert("주문이 완료되었습니다!");
      setCart([]); // 장바구니 초기화
    } catch (error) {
      console.error("처리 중 오류 발생:", error);
      alert("처리 중 오류가 발생했습니다.");
    }
  };

  // 📌 로딩 상태 처리
  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  return (
    <div className="container">
      {/* 상품 목록 섹션 */}
      <div className="productSection">
        <h2>상품 목록</h2>
        <div className="productList">
          {products.map((product) => (
            <div key={product.id} className="productCard">
              <img src="/default-coffee.png" alt={product.name} className="productImage" />
              <div className="productInfo">
                <div className="productDetails">
                  <h3 className="productTitle">{product.name}</h3>
                  <p className="productPrice">{product.price} 원</p>
                </div>
                <button onClick={() => addToCart(product)} className="addButton">담기</button>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* 장바구니 섹션 */}
      <div className="cartSection">
        <h2>장바구니</h2>
        <div className="cartContainer">
          {cart.length === 0 ? (
            <p className="emptyCart">장바구니가 비어있습니다</p>
          ) : (
            cart.map((item) => (
              <div key={item.product.id} className="cartItem">
                <h4 className="cartItemTitle">{item.product.name}</h4>
                <p className="cartItemPrice">{item.product.price} 원 │ {item.quantity}개 │ {item.product.price * item.quantity}원</p>
                <div className="cartItemControls">
                  <button className="quantityBtn" onClick={() => decreaseQuantity(item.product.id)}>-</button>
                  <span className="quantityText">{item.quantity}</span>
                  <button className="quantityBtn" onClick={() => increaseQuantity(item.product.id)}>+</button>
                  <button className="removeBtn" onClick={() => removeFromCart(item.product.id)}>삭제</button>
                </div>
              </div>
            ))
          )}
          {cart.length > 0 && (
            <div className="cartTotal">
              <span>합계</span>
              <span>{totalPrice} 원</span>
            </div>
          )}
        </div>
      </div>

      {/* 결제 섹션 */}
      <div className="checkoutSection">
        <h2>결제 정보</h2>
        <form className="paymentForm" onSubmit={handleOrder}>
          <div className="formGroup">
            <label htmlFor="email">이메일</label>
            <input type="email" id="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
          </div>
          <div className="formGroup">
            <label htmlFor="address">주소</label>
            <input type="text" id="address" value={address} onChange={(e) => setAddress(e.target.value)} required />
          </div>
          <div className="formGroup">
            <label htmlFor="code">상세 주소</label>
            <input type="text" id="code" value={code} onChange={(e) => setCode(e.target.value)} required />
          </div>
          <button type="submit" className="paymentButton">결제하기</button>
        </form>
      </div>
    </div>
  );
};

export default Home;
