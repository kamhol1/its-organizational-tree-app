import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmployeeAddComponent } from "./components/employee-add/employee-add.component";
import { EmployeeListComponent } from "./components/employee-list/employee-list.component";
import { EmployeeEditComponent } from "./components/employee-edit/employee-edit.component";
import {CommonModule} from "@angular/common";
import {EmployeeManagersComponent} from "./components/employee-managers/employee-managers.component";

const routes: Routes = [
  { path: '', component: EmployeeListComponent },
  { path: 'add', component: EmployeeAddComponent },
  { path: 'edit/:id', component: EmployeeEditComponent },
  { path: 'managers/:id', component: EmployeeManagersComponent },
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ]
})
export class AppRoutingModule { }
