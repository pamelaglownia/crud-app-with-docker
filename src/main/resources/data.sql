CREATE TABLE company (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE department (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    company_id INTEGER,
    FOREIGN KEY (company_id) REFERENCES company(id)
);

CREATE TABLE project (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    manager_name VARCHAR(255) NOT NULL,
    manager_email VARCHAR(255) NOT NULL,
    manager_phone VARCHAR(50) NOT NULL
);

CREATE TABLE team (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    department_id INTEGER,
    project_id INTEGER,
    FOREIGN KEY (department_id) REFERENCES department(id),
    FOREIGN KEY (project_id) REFERENCES project(id)
);

INSERT INTO company (name) VALUES
('Create IT'),
('You can do IT');

INSERT INTO department (name, company_id) VALUES
('Development', 1),
('Marketing', 2);

INSERT INTO project (name, description, manager_name, manager_email, manager_phone) VALUES
('Stone Age Solutions', 'Modern solutions with a prehistoric twist.', 'Peter Griffin', 'peter@example.com', '123-456-789'),
('Underwater Tech', 'New underwater technologies.', 'SpongeBob SquarePants', 'spongebob@example.com', '987-654-321'),
('Infrastructure Initiative', 'Constructing new infrastructure.', 'Fred Flinstone', 'fred@example.com', '111-222-333');

INSERT INTO team (name, department_id, project_id) VALUES
('Griffin’s Heroes', 1, 1),
('SpongeBob Squad', 2, 2),
('Flintstone Force', 1, 3);