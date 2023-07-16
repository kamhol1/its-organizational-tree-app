INSERT INTO employees(id, first_name, last_name, position, active) VALUES
(1, 'Jan', 'Kowalski', 'PREZES', true),
(2, 'Jan', 'Nowak', 'DYREKTOR', true),
(3, 'Adam', 'Nowakowski', 'KOORDYNATOR', true),
(4, 'Adam', 'Kowal', 'KIEROWNIK', true),
(5, 'Jacek', 'Kowalski', 'PRACOWNIK', true);

INSERT INTO departments(id, name, short_name, division, manager_id, parent_department_id) VALUES
(1, 'Prezes', 'P', 'P', 1, null),
(2, 'Dyrektor Techniczny', 'DT', 'P', 2, 1),
(3, 'Koordynator ds. Wsparcia Użytkowników Systemów Informatycznych', 'TWP', 'P', 3, 2),
(4, 'Zespół Wsparcia Lokalnego 1', 'TZW1', 'P', 4, 3);

UPDATE employees SET department_id = 1 WHERE id = 1;
UPDATE employees SET department_id = 2 WHERE id = 2;
UPDATE employees SET department_id = 3 WHERE id = 3;
UPDATE employees SET department_id = 4 WHERE id = 4;
UPDATE employees SET department_id = 4 WHERE id = 5;

ALTER SEQUENCE employees_id_seq RESTART WITH 6;
ALTER SEQUENCE departments_id_seq RESTART WITH 5;