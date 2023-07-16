import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {EmployeeListComponent} from './components/employee-list/employee-list.component';
import {HttpClientModule} from "@angular/common/http";
import {EmployeeAddComponent} from './components/employee-add/employee-add.component';
import {AppRoutingModule} from './app-routing.module';
import {RouterLink, RouterOutlet} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {EmployeeEditComponent} from './components/employee-edit/employee-edit.component';
import {EmployeeManagersComponent} from './components/employee-managers/employee-managers.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatSnackBarModule} from "@angular/material/snack-bar";

@NgModule({
  declarations: [
    AppComponent,
    EmployeeListComponent,
    EmployeeAddComponent,
    EmployeeEditComponent,
    EmployeeManagersComponent
  ],
    imports: [
        BrowserModule,
        HttpClientModule,
        AppRoutingModule,
        RouterLink,
        RouterOutlet,
        FormsModule,
        BrowserAnimationsModule,
        MatSnackBarModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
