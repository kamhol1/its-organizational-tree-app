import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {EmployeeModel} from "../models/employee.model";

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private apiUrl = 'http://localhost:8080/api/employees';

  constructor(private http: HttpClient) { }

  getEmployees(
    page: number,
    size: number,
    sortField: string,
    sortOrder: string,
    firstNameFilter?: string,
    lastNameFilter?: string,
    positionFilter?: string,
    departmentFilter?: string
  ): Observable<any> {
    let params = new HttpParams()
      .set('firstName', firstNameFilter || '')
      .set('lastName', lastNameFilter || '')
      .set('position', positionFilter || '')
      .set('department', departmentFilter || '')
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sortBy', sortField)
      .set('sortOrder', sortOrder);

    return this.http.get<any>(this.apiUrl + '/active', { params });
  }

  save(employee: any): Observable<any> {
    return this.http.post(this.apiUrl, employee);
  }

  update(id: number, employee: any): Observable<any> {
    return this.http.put(this.apiUrl + '/' + id, employee);
  }

  getEmployeeById(id: number): Observable<EmployeeModel> {
    return this.http.get<EmployeeModel>(this.apiUrl + "/" + id);
  }

  deleteEmployee(id: number): Observable<any> {
    return this.http.patch(this.apiUrl + '/' + id + '/deactivate', {});
  }

  getManagers(id: number): Observable<EmployeeModel[]> {
    return this.http.get<EmployeeModel[]>(this.apiUrl + '/' + id + '/managers');
  }

}
