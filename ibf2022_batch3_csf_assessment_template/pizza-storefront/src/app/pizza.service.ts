import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable()
export class PizzaService {

  constructor(private http: HttpClient){ }

  // TODO: Task 3
  // You may add any parameters and return any type from placeOrder() method
  // Do not change the method name
  placeOrder(formData: FormData): Observable<any> {
    return this.http.post<string>('/api/order', formData)
  }

  // TODO: Task 5
  // You may add any parameters and return any type from getOrders() method
  // Do not change the method name
  getOrders(email: string): Observable<any> {
    return this.http.get<string>('/api/orders' + '/' + email)
  }

  // TODO: Task 7
  // You may add any parameters and return any type from delivered() method
  // Do not change the method name
  delivered(orderId: string): Observable<any> {
    return this.http.get<string>('/api/order/' + orderId)
  }

}
