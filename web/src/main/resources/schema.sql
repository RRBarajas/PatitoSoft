CREATE TABLE address (
  address_id INT AUTO_INCREMENT PRIMARY KEY,
  street_address VARCHAR2(200) NOT NULL,
  state_name VARCHAR2(100) NOT NULL,
  country_name VARCHAR2(100) NOT NULL
);

CREATE TABLE position (
  position_id INT AUTO_INCREMENT PRIMARY KEY,
  position_name VARCHAR2(500) NOT NULL
);

CREATE TABLE employee (
  email VARCHAR2(255) PRIMARY KEY,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255),
  gender CHAR(1) NOT NULL,
  personal_email VARCHAR2(255),
  phone_number VARCHAR2(15),
  birth_date VARCHAR2(10),
  address_id INT NOT NULL,
  delete_flg BOOLEAN NOT NULL DEFAULT FALSE,
  created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP,

  CONSTRAINT fk_employee_address_id FOREIGN KEY (address_id) REFERENCES address (address_id)
);

CREATE TABLE employment_history (
  employee_email VARCHAR2(255) NOT NULL,
  position_id INT NOT NULL,
  salary NUMERIC NOT NULL,
  from_dt TIMESTAMP NOT NULL,
  to_dt TIMESTAMP,
  current BOOLEAN NOT NULL,

  CONSTRAINT fk_employment_pk PRIMARY KEY (employee_email, position_id, from_dt),
  CONSTRAINT fk_employment_email FOREIGN KEY (employee_email) REFERENCES employee (email),
  CONSTRAINT fk_employement_position FOREIGN KEY (position_id) REFERENCES position (position_id)
);
