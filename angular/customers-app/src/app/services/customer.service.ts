import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Customer } from '../models/customer-model';


@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private endpoint: string = "http://localhost:8080/api/customers";
  private headers: HttpHeaders = new HttpHeaders({'Content-Type': 'application/json' });

  customers: Customer[] = [
    {id: 1, name: 'Diego', lastname: 'Scifo', email: 'dscifo@mail.com', createdAt: '2020-09-30'},
    {id: 1, name: 'Alma', lastname: 'Scifo Mauna', email: 'alma@mail.com', createdAt: '2020-09-30'},
    {id: 1, name: 'Sol', lastname: 'Mauna', email: 'sol@mail.com', createdAt: '2020-09-30'}
  ];

  constructor(private http: HttpClient) { }

  getCustomers(): Observable<Customer[]> {
    // return of(this.customers);
    // return this.http.get<Customer[]>(this.endpoint);
    return this.http.get(this.endpoint).pipe(map( response => response as Customer[] ));
  }

  create(customer: Customer): Observable<Customer> {
    return this.http.post<Customer>(this.endpoint, customer, {headers: this.headers});
  }
}
