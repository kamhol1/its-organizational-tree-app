import {Component, OnInit} from '@angular/core';
import {EmployeeService} from "../../services/employee.service";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {NgForm} from "@angular/forms";
import {EmployeeModel} from "../../models/employee.model";
import {DepartmentModel} from "../../models/department.model";
import {DepartmentService} from "../../services/department.service";
import {Location} from '@angular/common';
import {MatSnackBar} from "@angular/material/snack-bar";
import {EmployeeWriteModel} from "../../models/employee-write.model";

@Component({
  selector: 'app-employee-edit',
  templateUrl: './employee-edit.component.html',
  styleUrls: ['./employee-edit.component.css']
})
export class EmployeeEditComponent implements OnInit {
  employeeId!: number;
  employee!: EmployeeWriteModel;
  departments: DepartmentModel[] = [];

  constructor(private employeeService: EmployeeService,
              private departmentService: DepartmentService,
              private route: ActivatedRoute,
              private router: Router,
              private location: Location,
              private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe({
      next: (params: ParamMap) => {
        const id = params.get('id');
        if (id) this.employeeId = parseInt(id);
      }
    });

    this.getEmployee(this.employeeId);

    this.departmentService.getDepartments().subscribe({
      next: (departments: DepartmentModel[]) => {
        this.departments = departments;
      }
    });
  }

  getEmployee(id: number) {
    this.employeeService.getEmployeeById(id)
      .subscribe({
        next: (employee: EmployeeModel) => {
          this.employee = {
            firstName: employee.firstName,
            lastName: employee.lastName,
            position: employee.position,
            department: employee.departmentId
          };
        },
        error: (error) => {
          this.snackBar.open(error.error.message, 'OK', {
            verticalPosition: 'top',
            panelClass: ['app-notification-error'],
            duration: 5000
          });
          this.goToEmployeeList();
        }
      });
  }

  onSubmit(form: NgForm) {
    if (form.valid) {
      this.employeeService.update(this.employeeId, this.employee)
        .subscribe({
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
