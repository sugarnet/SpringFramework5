import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Customer } from 'src/app/models/customer-model';
import { CustomerService } from 'src/app/services/customer.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
})
export class FormComponent implements OnInit {
  title: string = 'Create Customer';
  customer: Customer = new Customer();

  constructor(
    private customerService: CustomerService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadCustomer();
  }

  create(): void {
    console.log(this.customer);
    this.customerService.create(this.customer).subscribe((c) => {
      Swal.fire('Customer Created', `${c.name} ${c.lastname}`, 'success');
      this.router.navigate(['/customers']);
    });
  }

  edit(): void {
    console.log(this.customer);
    this.customerService.update(this.customer).subscribe((c) => {
      Swal.fire('Customer Edited', `${c.name} ${c.lastname}`, 'success');
      this.router.navigate(['/customers']);
    });
  }

  loadCustomer() {
    this.activatedRoute.params.subscribe(params => {
      let id = params['id'];
      if (id) {
        this.customerService.getCustomer(id).subscribe(customer => this.customer = customer);
      }
    });
  }
}
