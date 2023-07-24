export interface EmployeeModel {
  id: number;
  firstName: string;
  lastName: string;
  position: string;
  active: boolean;
  supervisor: number;
  departmentId: number;
  department: string;
  division: string;
}
