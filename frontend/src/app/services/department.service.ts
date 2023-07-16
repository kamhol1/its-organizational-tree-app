import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {DepartmentModel} from "../models/department.model";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {
  private apiUrl = 'http://localhost:8080/api/departments';

  constructor(private http: HttpClient) { }

  getDepartments(): Observable<DepartmentModel[]> {
    return this.http.get<DepartmentModel[]>(this.apiUrl);
  }

}
