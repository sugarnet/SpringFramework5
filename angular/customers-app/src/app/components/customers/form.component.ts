import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Customer } from 'src/app/models/customer-model';
import { CustomerService } from 'src/app/services/customer.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html'
})
export class FormComponent implements OnInit {

  title: string = 'Create Customer';
  customer: Customer = new Customer();

  constructor(private customerService: CustomerService, private router: Router) { }

  ngOnInit(): void {
  }

  create(): void {
    console.log(this.customer);
    this.customerService.create(this.customer).subscribe(c => this.router.navigate(['/customers']));
  }
}
