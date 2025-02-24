export default interface Order {
    orderId?: number;
    userId: number;
    createdAt: string;
    totalPrice: string;
    address: string;
    code: string;
  }
