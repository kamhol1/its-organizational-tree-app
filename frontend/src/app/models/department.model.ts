import {EmployeeModel} from "./employee.model";

export interface DepartmentModel {
  id: number;
  name: string;
  shortName: string;
  division: string;
  manager: EmployeeModel;
  parentDepartment?: DepartmentModel;
}
