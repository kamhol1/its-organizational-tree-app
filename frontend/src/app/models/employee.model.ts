import {DepartmentModel} from "./department.model";

export interface EmployeeModel {
  id: number;
  firstName: string;
  lastName: string;
  position: string;
  active: boolean;
  department: DepartmentModel;
  supervisor?: EmployeeModel;
}
