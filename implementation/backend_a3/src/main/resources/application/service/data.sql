
INSERT INTO `user` (`id`,`username`, `password`, `fullName`) VALUES ('1','doctor1', 'doctor1', 'Doctor One');
INSERT INTO `user` (`id`,`username`, `password`, `fullName`) VALUES ('2','doctor2', 'doctor2', 'Doctor Two');

INSERT INTO `doctor` (`id`) VALUES (1);
INSERT INTO `doctor` (`id`) VALUES (2);

INSERT INTO `workinghour`(`id`,`startHour`,`endHour`,`dayOfWeek`,`doctorId`) VALUES
('1','0', '23', '1', '1'),
('2','0', '23', '2', '1'),
('3','0', '23', '3', '1'),
('4','0', '23', '4', '1'),
('5','0', '23', '5', '1'),
('6','0', '23', '6', '1'),
('7','0', '23', '7', '1'),
('8','0', '23', '1', '2'),
('9','0', '23', '2', '2'),
('10','0', '23', '3', '2'),
('11','0', '23', '4', '2'),
('12','0', '23', '5', '2'),
('13','0', '23', '6', '2'),
('14','0', '23', '7', '2');


INSERT INTO `patient` (`id`,`fullName`,`idCardSeries`,`idCardNumber`,`personalNumericalCode`,`address`) VALUES
('1','Nandrea Gabriela','AS','770951','1950624346462','Str. Bucuresti Nr. 117'),
('2','Vincze Robert Francisc','BV','958390','1940909430511','Str. Bucuresti Nr. 10');