CREATE TABLE comment
(
  id SERIAL PRIMARY KEY,
  employee_id INT REFERENCES employee(id) NOT NULL,
  author INT REFERENCES "user"(id) NOT NULL,
  message text NOT NULL
);

CREATE TABLE log
(
  id SERIAL PRIMARY KEY,
  employee_id INT REFERENCES employee(id) NOT NULL,
  author VARCHAR(64) NOT NULL,
  message text NOT NULL,
  event_date DATE NOT NULL
);

insert into comment (employee_id, author, message) VALUES (1, 2, 'Заказать униформу новичка');
insert into comment (employee_id, author, message) VALUES (1, 2, 'Нужно провести дополнительную Welcome - встречу');
insert into comment (employee_id, author, message) VALUES (1, 1, 'Необходимо организовать командировку в Питер');
insert into comment (employee_id, author, message) VALUES (1, 1, 'Не пришел на результаты промежуточной встречи, сказал, что болит живот');
insert into comment (employee_id, author, message) VALUES (1, 1, 'Оставляем на второй испытательный срок');

insert into comment (employee_id, author, message) VALUES (2, 2, 'Заказать униформу новичка');
insert into comment (employee_id, author, message) VALUES (2, 2, 'Нужно провести дополнительную Welcome - встречу');
insert into comment (employee_id, author, message) VALUES (2, 1, 'Необходимо организовать командировку в Питер');
insert into comment (employee_id, author, message) VALUES (2, 1, 'Не пришел на результаты промежуточной встречи, сказал, что болит живот');
insert into comment (employee_id, author, message) VALUES (2, 1, 'Оставляем на второй испытательный срок');

insert into comment (employee_id, author, message) VALUES (3, 2, 'Заказать униформу новичка');
insert into comment (employee_id, author, message) VALUES (3, 2, 'Нужно провести дополнительную Welcome - встречу');
insert into comment (employee_id, author, message) VALUES (3, 1, 'Необходимо организовать командировку в Питер');
insert into comment (employee_id, author, message) VALUES (3, 1, 'Не пришел на результаты промежуточной встречи, сказал, что болит живот');
insert into comment (employee_id, author, message) VALUES (3, 1, 'Оставляем на второй испытательный срок');

insert into comment (employee_id, author, message) VALUES (4, 2, 'Заказать униформу новичка');
insert into comment (employee_id, author, message) VALUES (4, 2, 'Нужно провести дополнительную Welcome - встречу');
insert into comment (employee_id, author, message) VALUES (4, 1, 'Необходимо организовать командировку в Питер');
insert into comment (employee_id, author, message) VALUES (4, 1, 'Не пришел на результаты промежуточной встречи, сказал, что болит живот');
insert into comment (employee_id, author, message) VALUES (4, 1, 'Оставляем на второй испытательный срок');

insert into comment (employee_id, author, message) VALUES (5, 2, 'Заказать униформу новичка');
insert into comment (employee_id, author, message) VALUES (5, 2, 'Нужно провести дополнительную Welcome - встречу');
insert into comment (employee_id, author, message) VALUES (5, 1, 'Необходимо организовать командировку в Питер');
insert into comment (employee_id, author, message) VALUES (5, 1, 'Не пришел на результаты промежуточной встречи, сказал, что болит живот');
insert into comment (employee_id, author, message) VALUES (5, 1, 'Оставляем на второй испытательный срок');
