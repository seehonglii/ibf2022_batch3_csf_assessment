import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { PizzaService } from '../pizza.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {
  email!: string;
  pendingOrders: any[] = [];
  private subscription: Subscription = new Subscription();

  constructor(
    private route: ActivatedRoute,
    private pizzaService: PizzaService
  ) {}

  ngOnInit() {
    this.email = this.route.snapshot.paramMap.get('email')!;
    this.loadPendingOrders();
  }

  loadPendingOrders() {
    this.pizzaService.getOrders(this.email).subscribe(
      (response) => {
        this.pendingOrders = response;
      },
      (error) => {
        console.error('Error retrieving pending orders:', error);
      }
    );
  }

  delivered(orderId: string) {
    this.pizzaService.delivered(orderId).subscribe(
      () => {
        // Order marked as delivered, update the UI as needed
      },
      (error: any) => {
        console.error('Error marking order as delivered:', error);
      }
    );
  }
  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
