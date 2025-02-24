import Product from "./product.interface";
import Order from "./order.interface";

export default interface OrderProduct {
  orderProductId?: number;
  quantity: number;
  orderId?: number;
  productId: number;
}