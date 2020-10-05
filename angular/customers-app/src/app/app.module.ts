import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HeaderComponent } from './shared/header/header.component';
import { FooterComponent } from './shared/footer/footer.component';
import { NgForComponent } from './components/ng-for/ng-for.component';
import { CustomersComponent } from './components/customers/customers.component';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { FormComponent } from './components/customers/form.component';

const routes: Routes = [
  {path: '', redirectTo: '/customers', pathMatch: 'full'},
  {path: 'customers', component: CustomersComponent},
  {path: 'customers/form', component: FormComponent},
  {path: 'ng-for', component: NgForComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    NgForComponent,
    CustomersComponent,
    FormComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
