import { Component, OnInit } from '@angular/core';
import { Customer } from 'src/app/models/customer-model';
import { CustomerService } from 'src/app/services/customer.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html'
})
export class CustomersComponent implements OnInit {

  customers: Customer[] = [];

  constructor(private customerService: CustomerService) { }

  ngOnInit(): void {
    this.customerService.getCustomers().subscribe(data => this.customers = data);
  }

  delete(customer: Customer) {
    Swal.fire({
      title: 'Are you sure?',
      text: `${customer.name} ${customer.lastname} will be deleted. You won't be able to revert this!`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
      if (result.isConfirmed) {
        this.customerService.delete(customer.id).subscribe(() => {

          this.customers = this.customers.filter(cli => cli !== customer);

          Swal.fire(
            'Deleted!',
            'Your file has been deleted.',
            'success'
          )
        });
      }
    })
  }

}
