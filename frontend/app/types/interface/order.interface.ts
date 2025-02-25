import { OrderProduct } from "./order.product.interface";
export default interface Order {
  id: number;
  email: string;
  address: string;
  code: string;
  totalPrice: number;
  createdAt: string;
  status: string;
  products: OrderProduct[];
}