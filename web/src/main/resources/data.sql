-- Address
INSERT INTO address (street_address, state_name, country_name) VALUES ('My Street 10', 'Jalisco', 'Mexico');
INSERT INTO address (street_address, state_name, country_name) VALUES ('My other address 1023', 'Jalisco', 'Mexico');
INSERT INTO address (street_address, state_name, country_name) VALUES ('Gran avenida 992', 'Sonora', 'Mexico');
INSERT INTO address (street_address, state_name, country_name) VALUES ('Awesome Dr', 'California', 'USA');
INSERT INTO address (street_address, state_name, country_name) VALUES ('Super Rd', 'New York', 'USA');
COMMIT;

-- Position
INSERT INTO position (position_name) VALUES ('Java Developer');
INSERT INTO position (position_name) VALUES ('Success Coach');
INSERT INTO position (position_name) VALUES ('Apprentice');
INSERT INTO position (position_name) VALUES ('Nights watch');
COMMIT;

-- Employee
INSERT INTO employee (email, first_name, last_name, gender, personal_email, phone_number, birth_date, address_id)
  VALUES ('foo@email.com', 'Foo', 'Bar', 'M', 'personal@email.com', null, PARSEDATETIME('1990/01/05', 'yyyy/MM/dd'), 1);
INSERT INTO employee (email, first_name, last_name, gender, personal_email, phone_number, birth_date, address_id)
  VALUES ('jane.doe@email.com', 'Jane', 'Doe', 'F', null, null, null, 3);
COMMIT;

-- Employment history
INSERT INTO employment_history (employee_email, position_id, from_dt, to_dt, salary, current)
  VALUES ('foo@email.com', 1, PARSEDATETIME('2020/01/05', 'yyyy/MM/dd'), null, 124, true);
INSERT INTO employment_history (employee_email, position_id, from_dt, to_dt, salary, current)
  VALUES ('foo@email.com', 3, PARSEDATETIME('2018/01/05', 'yyyy/MM/dd'), PARSEDATETIME('2020/01/04', 'yyyy/MM/dd'), 123.45, false);
COMMIT;
