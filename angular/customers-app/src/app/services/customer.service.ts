import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, of, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { Customer } from '../models/customer-model';
import Swal from 'sweetalert2';
import { formatDate } from '@angular/common';

@Injectable({
  providedIn: 'root',
})
export class CustomerService {
  private endpoint: string = 'http://localhost:8080/api/customers';
  private headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });

  customers: Customer[] = [
    {
      id: 1,
      name: 'Diego',
      lastname: 'Scifo',
      email: 'dscifo@mail.com',
      createdAt: '2020-09-30',
    },
    {
      id: 1,
      name: 'Alma',
      lastname: 'Scifo Mauna',
      email: 'alma@mail.com',
      createdAt: '2020-09-30',
    },
    {
      id: 1,
      name: 'Sol',
      lastname: 'Mauna',
      email: 'sol@mail.com',
      createdAt: '2020-09-30',
    },
  ];

  constructor(private http: HttpClient, private router: Router) {}

  getCustomers(): Observable<Customer[]> {
    // return of(this.customers);
    // return this.http.get<Customer[]>(this.endpoint);
    return this.http
      .get(this.endpoint)
      .pipe(map((response) => {
        let customers = response as Customer[];
        return customers.map(customer => {
          // customer.name = customer.name.toUpperCase();
          // customer.createdAt = formatDate(customer.createdAt, 'EEEE-MM-yyyy', 'es');
          return customer;
        });
      }));
  }

  create(customer: Customer): Observable<Customer> {
    return this.http
      .post<Customer>(this.endpoint, customer, { headers: this.headers })
      .pipe(
        map((response: any) => response.customer as Customer),
        catchError((e) => {
          if (e.status == 400) {
            return throwError(e);
          }

          this.router.navigate(['/customers']);
          Swal.fire('Create Error', e.error.message, 'error');
          console.error(e.error.error);
          return throwError(e);
        })
      );
  }

  update(customer: Customer): Observable<Customer> {
    return this.http
      .put<Customer>(this.endpoint, customer, { headers: this.headers })
      .pipe(
        map((response: any) => response.customer as Customer),
        catchError((e) => {
          if (e.status == 400) {
            return throwError(e);
          }

          this.router.navigate(['/customers']);
          Swal.fire('Update Error', e.error.message, 'error');
          console.error(e.error.error);
          return throwError(e);
        })
      );
  }

  delete(id: number): Observable<Customer> {
    return this.http
      .delete<Customer>(`${this.endpoint}/${id}`, { headers: this.headers })
      .pipe(
        catchError((e) => {
          this.router.navigate(['/customers']);
          Swal.fire('Delete Error', e.error.message, 'error');
          console.error(e.error.error);
          return throwError(e);
        })
      );
  }

  getCustomer(id: number): Observable<Customer> {
    return this.http.get<Customer>(`${this.endpoint}/${id}`).pipe(
      map((response: any) => response.customer as Customer),
      catchError((e) => {
        this.router.navigate(['/customers']);
        Swal.fire('Error', e.error.message, 'error');
        console.error(e.error.error);
        return throwError(e);
      })
    );
  }
}
