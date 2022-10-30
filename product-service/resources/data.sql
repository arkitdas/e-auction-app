INSERT INTO role_mst (id, name,description,is_active) VALUES
	(1, 'ADMIN', 'Admin Users', true),
	(2, 'CUSTOMER', 'Customer Users', true);

INSERT INTO user_mst (id, name, username, email, password, is_active ) VALUES
	(1, 'LMS Admin', 'admin', 'admin@lms.com', '$2a$10$hTQ7.4hT0BkTsaRCyhNpE.IxpVJLe5qHMrnVPES4mfO5rSZhrJmcy', true),
	(2, 'John Doe', 'test', 'johndoe@lms.com', '$2a$10$j.MgMbSv4PR2urHcZZjKguhUGFv7a.ESYvkIWyte6b6cRA0mgKLge', true),
	(3, 'Arkit Das', 'arkitdas', 'arkitdas@lms.com', '$2a$10$j.MgMbSv4PR2urHcZZjKguhUGFv7a.ESYvkIWyte6b6cRA0mgKLge', true);

INSERT INTO users_roles (user_id ,role_id) VALUES
	(1, 1),
	(2, 2),
	(3, 2);