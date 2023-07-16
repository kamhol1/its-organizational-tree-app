import {Component, OnInit} from '@angular/core';
import {EmployeeModel} from "../../models/employee.model";
import {EmployeeService} from "../../services/employee.service";
import {EmployeeFilters} from "../../models/employee.filters";
import {debounceTime, distinctUntilChanged, Subject} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {
  employees: EmployeeModel[] = [];
  pageSize = 6;
  currentPage = 0;
  totalElements = 0;
  numberOfElements = 0;
  totalPages = 0;
  offset = 0;
  sortField = 'id';
  sortOrder = 'asc';
  firstNameFilter = '';
  lastNameFilter = '';
  positionFilter = '';
  departmentFilter = '';
  searchTerms = new Subject<string>();

  constructor(private employeeService: EmployeeService,
              private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.searchTerms.pipe(
      debounceTime(300),
      distinctUntilChanged()
    ).subscribe({
      next: (filters: string) => {
        const parsedFilters: EmployeeFilters = JSON.parse(filters);
        this.getEmployees(
          this.currentPage,
          this.pageSize,
          this.sortField,
          this.sortOrder,
          parsedFilters.firstNameFilter,
          parsedFilters.lastNameFilter,
          parsedFilters.positionFilter,
          parsedFilters.departmentFilter
        );
      },
      error: (error) => {
        this.snackBar.open(error.error.message,
          'OK',
          {
            verticalPosition: 'top',
            panelClass: ['app-notification-error'],
            duration: 5000
          });
      }
    });

    this.getEmployees(this.currentPage, this.pageSize, this.sortField, this.sortOrder);
  }

  getEmployees(page: number,
               size: number,
               sortField: string,
               sortOrder: string,
               firstNameFilter?: string,
               lastNameFilter?: string,
               positionFilter?: string,
               departmentFilter?: string): void {
    this.employeeService.getEmployees(page, size, sortField, sortOrder, firstNameFilter, lastNameFilter, positionFilter, departmentFilter).subscribe({
      next: (data) => {
        this.employees = data.content;
        this.totalElements = data.totalElements;
        this.numberOfElements = data.numberOfElements;
        this.totalPages = data.totalPages;
        this.offset = data.pageable.offset;
      },
      error: (error) => {
        this.snackBar.open(error.error.message,
          'OK',
          {
            verticalPosition: 'top',
            panelClass: ['app-notification-error'],
            duration: 5000
          });
      }
    });
  }

  filterEmployees(): void {
    const filters: EmployeeFilters = {
      firstNameFilter: this.firstNameFilter,
      lastNameFilter: this.lastNameFilter,
      positionFilter: this.positionFilter,
      departmentFilter: this.departmentFilter
    };

    this.searchTerms.next(JSON.stringify(filters));
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.getEmployees(this.currentPage, this.pageSize, this.sortField, this.sortOrder, this.firstNameFilter, this.lastNameFilter);
    }
  }

  previousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.getEmployees(this.currentPage, this.pageSize, this.sortField, this.sortOrder, this.firstNameFilter, this.lastNameFilter);
    }
  }

  sortData(sortField: string) {
    this.sortOrder == 'asc' && this.sortField == sortField ? this.sortOrder = 'desc' : this.sortOrder = 'asc';
    this.sortField = sortField;
    this.getEmployees(this.currentPage, this.pageSize, this.sortField, this.sortOrder, this.firstNameFilter, this.lastNameFilter);
  }

  deactivateEmployee(event: Event, id: number) {
    event.preventDefault();
    const confirmDelete = confirm('Czy na pewno chcesz usunąć tego pracownika?');

    if (confirmDelete) {
      this.employeeService.deleteEmployee(id).subscribe({
        next: (response) => {
          this.snackBar.open(response.message,
            'OK',
            {
              duration: 10000,
              verticalPosition: 'top',
              panelClass: ['app-notification-success']
            });

          location.reload();
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
}
