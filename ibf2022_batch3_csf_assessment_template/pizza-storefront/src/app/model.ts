export interface Order
{
  orderId: string
  date: Date
  total: string
  name: string
  email: string
  sauce: string
  base: string
  size: string
  comments: string
  toppings: string[]
}
