import {Component, OnInit} from '@angular/core';
import {NgForm} from "@angular/forms";
import {EmployeeService} from "../../services/employee.service";
import {ActivatedRoute, Router} from "@angular/router";
import {DepartmentService} from "../../services/department.service";
import {DepartmentModel} from "../../models/department.model";
import {EmployeeModel} from "../../models/employee.model";
import {Location} from '@angular/common';
import {MatSnackBar} from "@angular/material/snack-bar";
import {EmployeeWriteModel} from "../../models/employee-write.model";

@Component({
  selector: 'app-employee-add',
  templateUrl: './employee-add.component.html',
  styleUrls: ['./employee-add.component.css']
})
export class EmployeeAddComponent implements OnInit {
  employee!: EmployeeWriteModel;
  departments: DepartmentModel[] = [];

  constructor(private employeeService: EmployeeService,
              private departmentService: DepartmentService,
              private route: ActivatedRoute,
              private router: Router,
              private location: Location,
              private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.employee = {
      firstName: '',
      lastName: '',
      position: '',
      department: undefined!
    }

    this.departmentService.getDepartments().subscribe({
      next: (departments: DepartmentModel[]) => {
        this.departments = departments;
      }
    });
  }

  onSubmit(form: NgForm) {
    if (form.valid) {
      console.log(this.employee);
      this.employeeService.save(this.employee).subscribe({
        next: (response) => {
          this.snackBar.open(response.message,
            'OK',
            {
              duration: 10000,
              verticalPosition: 'top',
              panelClass: ['app-notification-success']
            });
          this.goToEmployeeList();
        },
        error: (error) => {
          this.snackBar.open(error.error.message,
            'OK',
            {
              verticalPosition: 'top',
              panelClass: ['app-notification-error']
            });
        }
      });
    }
  }

  goToEmployeeList(): void {
    this.router.navigate(['/']);
  }

  goBack(): void {
    this.location.back();
  }
}
