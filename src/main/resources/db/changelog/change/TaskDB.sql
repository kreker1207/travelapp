insert into users (name, surname, mail, phone, login, password, role, status)
values ('Ivan', 'Pancrad', 'panc@gmail.com', '+380665356989', 'punk', '1234', 'USER', 'ACTIVE');
insert into users (name, surname, mail, phone, login, password, role, status)
values ('Slava', 'Yarmishko', 'slava@gmail.com', '+38069966644', 'slava', '2222', 'USER', 'ACTIVE');
insert into users (name, surname, mail, phone, login, password, role, status)
values ('Ilya', 'Yachmenev', 'ilya@gmail.com', '+788888888', 'ilya', '332123', 'USER', 'ACTIVE');
insert into users (name, surname, mail, phone, login, password, role, status)
values ('Ckipincos', 'Ramero', 'chop@gmail.com', '+722222222', 'rembo', '4444', 'USER', 'ACTIVE');
insert into users (name, surname, mail, phone, login, password, role, status)
values ('Slava', 'Ramires', 'ram@gmail.com', '+3809655545', 'ramires', 'password', 'USER',
        'ACTIVE');
insert into users (name, surname, mail, phone, login, password, role, status)
values ('Anton', 'Chaika', 'anton@gmail.com', '+3805566623', 'anton', 'dsagfd', 'USER', 'ACTIVE');
insert into users (name, surname, mail, phone, login, password, role, status)
values ('Alevtine', 'Churchello', 'chorch@gmail.com', '+3805558523', 'church', 'wqerwq', 'USER',
        'ACTIVE');

select DISTINCT name
from users;

select *
FROM users
WHERE name LIKE 'A%'
  and phone like '+380%';

SELECT CONCAT(users.name, ' ', users.surname) AS Full_name, ticket_status as Booked_tickets
FROM users
         JOIN ticket on users.id = ticket.user_id
WHERE ticket_status LIKE 'BOOKED';

SELECT CONCAT(users.name, ' ', users.surname) AS Full_name,
       count(ticket_status)                   as Booked_tickets
FROM users
         CROSS JOIN (select count(ticket_status) from ticket where ticket_status LIKE 'BOOKED') s
         left join ticket t on users.id = t.user_id and t.ticket_status = ticket_status
group by CONCAT(users.name, ' ', users.surname), ticket_status
order by CONCAT(users.name, ' ', users.surname), ticket_status;



SELECT login
FROM users
WHERE exists(Select ticket
             FROM ticket
             WHERE users.id = ticket.user_id and ticket_status LIKE 'BOUGHT');


SELECT race_number
FROM race
         JOIN ticket t on race.id = t.race_id
         JOIN users u on t.user_id = u.id
WHERE name LIKE 'Ivan';


SELECT surname, count(surname)
FROM users
GROUP BY surname
HAVING (count(surname)) > 1;


UPDATE users
SET status = 'BANNED'
WHERE phone LIKE '+7%';
SELECT *
from users;
SELECT race_number, departure_date_time
FROM race
WHERE departure_date_time between '11/11/2022' and '11/13/2022';