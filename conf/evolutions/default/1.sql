-- Driver schema

-- !Ups
CREATE TABLE driver (
    driverId bigInt(20) NOT NULL AUTO_INCREMENT,
    driverName varchar(255) NOT NULL,
    longitude DOUBLE NOT NULL,
    latitude DOUBLE NOT NULL,
    status varchar(50) NOT NULL,
    primary key (driverId)
);

insert into driver (driverName, longitude, latitude, status) values('Suresh', 23.03, 77.97, 'AVAILABLE');
insert into driver (driverName, longitude, latitude, status) values('Ramesh', 63.03, -87.97, 'AVAILABLE');
insert into driver (driverName, longitude, latitude, status) values('Manish', 53.03, 97.97, 'AVAILABLE');
insert into driver (driverName, longitude, latitude, status) values('Punit', -23.03, 7.97, 'AVAILABLE');
insert into driver (driverName, longitude, latitude, status) values('Summit', 69.03, 27.97, 'AVAILABLE');
insert into driver (driverName, longitude, latitude, status) values('Namit', 21.03, 45.97, 'AVAILABLE');

CREATE TABLE orders (
    orderId bigInt(20) NOT NULL AUTO_INCREMENT,
    customerName varchar(255) NOT NULL,
    longitude DOUBLE NOT NULL,
    latitude DOUBLE NOT NULL,
    primary key (orderId)
);

-- !Downs
DROP TABLE driver;
DROP TABLE orders;
