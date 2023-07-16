import {Component, OnInit} from '@angular/core';
import {EmployeeModel} from "../../models/employee.model";
import {EmployeeService} from "../../services/employee.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Location} from '@angular/common';
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-employee-managers',
  templateUrl: './employee-managers.component.html',
  styleUrls: ['./employee-managers.component.css']
})
export class EmployeeManagersComponent implements OnInit {
  employeeId!: number;
  managers: EmployeeModel[] = [];

  constructor(private employeeService: EmployeeService,
              private route: ActivatedRoute,
              private location: Location,
              private router: Router,
              private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe({
      next: (params) => {
        const id = params.get('id');
        if (id) this.employeeId = parseInt(id);
      }
    });

    this.employeeService.getManagers(this.employeeId).subscribe({
      next: (managers: EmployeeModel[]) => {
        this.managers = managers;
      },
      error: (error) => {
        this.snackBar.open(error.error.message,
          'OK',
          {
            verticalPosition: 'top',
            panelClass: ['app-notification-error'],
            duration: 5000
          });

        this.goToEmployeeList();
      }
    });
  }

  goToEmployeeList(): void {
    this.router.navigate(['/']);
  }

  goBack(): void {
    this.location.back();
  }
}
