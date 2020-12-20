-- drop table R
create table R(
FK1 int not null,
FK2 int not null,
C1 varchar(100) not null, C2 varchar(50) not null, C3 int,
C4 int,
C5 varchar(20),
CONSTRAINT pk_R PRIMARY KEY(FK1, FK2))

insert into R(FK1, FK2, C1, C2, C3, C4, C5) values
(1, 1, 'Pisica pe acoperisul fierbinte', 'Tennessee Williams', 100, 20, 'AB'),
(1, 2, 'Conul Leonida fata cu reactiunea', 'Ion Luca Caragiale', 50, 50, 'CQ'),
(1, 3, 'Concert din muzica de Bach', 'Hortensia Papadat-Bengescu', 50, 10, 'QC'),
(2, 1, 'Fata babei si fata mosneagului', 'Ion Creanga', 100, 100, 'QM'),
(2, 2, 'Frumosii nebuni ai marilor orase', 'Fanus Neagu', 10, 10, 'BA'),
(2, 3, 'Frumoasa calatorie a ursilor panda povestita de un saxofonist care avea o iubita la Frankfurt', 'Matei Visniec', 100, 20, 'MQ'),
(3, 1, 'Mansarda la Paris cu vedere spre moarte', 'Matei Visniec', 200, 10, 'PQ'),
(3, 2, 'Richard al III-lea se interzice sau Scene din viata lui Meyerhold', 'Matei Visniec', 100, 50, 'PQ'),
(3, 3, 'Masinaria Cehov. Nina sau despre fragilitatea pescarusilor impaiati', 'Matei Visniec', 100, 100, 'AZ'),
(4, 1, 'Omul de zapada care voia sa intalneasca soarele', 'Matei Visniec', 100, 100, 'CP'),
(4, 2, 'Extraterestrul care isi dorea ca amintire o pijama', 'Matei Visniec', 50, 10, 'CQ'),
(4, 3, 'O femeie draguta cu o floare si ferestre spre nord', 'Edvard Radzinski', 10, 100, 'CP'), (4, 4, 'Trenul din zori nu mai opreste aici', 'Tennessee Williams', 200, 200, 'MA')