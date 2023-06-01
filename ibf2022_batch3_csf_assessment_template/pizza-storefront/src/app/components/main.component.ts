import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PizzaService } from '../pizza.service';

const SIZES: string[] = [
  "Personal - 6 inches",
  "Regular - 9 inches",
  "Large - 12 inches",
  "Extra Large - 15 inches"
]

const PIZZA_TOPPINGS: string[] = [
    'chicken', 'seafood', 'beef', 'vegetables',
    'cheese', 'arugula', 'pineapple'
]

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit{

  pizzaSize = SIZES[0]

  form!: FormGroup
  orderPlaced!: boolean
  message!: string
  orderId!: string

  constructor(private fb: FormBuilder, private router: Router, private pizzaSvc: PizzaService) { }

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)]
  }

  ngOnInit(): void {
    this.form = this.createForm()
  }

  createForm(){
    return this.fb.group({
      name: this.fb.control<string>('', [Validators.required]),
      email: this.fb.control<string>('', [Validators.required, Validators.email]),
      size: this.fb.control<string>('', [Validators.required]),
      base: this.fb.control<string>('', [Validators.required]),
      sauce: this.fb.control<string>('', [Validators.required]),
      toppings: this.fb.array([], [Validators.required]),
      comments: this.fb.control<string>('')
    })
  }

  placeOrder(){
    const formData: FormData = new FormData
    const date: string = new Date().toLocaleString();

    formData.set('name', this.form.get('name')?.value)
    formData.set('email', this.form.get('email')?.value)
    formData.set('size', this.form.get('size')?.value)
    formData.set('base', this.form.get('base')?.value)
    formData.set('sauce', this.form.get('sauce')?.value)
    formData.set('toppings', this.form.get('toppings')?.value)
    formData.set('comments', this.form.get('comments')?.value)
    formData.set('Date', date)

    this.pizzaSvc.placeOrder(formData).subscribe(
      (p: any) => {
        this.orderPlaced = true;
        this.orderId = p._id;
        this.router.navigate(['/orders', this.orderId]);
      },
      (error: any) => {
        this.orderPlaced = false;
        this.message = error.error;
      }
    );
  }

}
