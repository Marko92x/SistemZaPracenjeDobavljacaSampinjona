/*
SQLyog Community v12.09 (64 bit)
MySQL - 5.6.17 : Database - projekat_prosoft
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`projekat_prosoft` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_croatian_ci */;

USE `projekat_prosoft`;

/*Table structure for table `administrativniradnik` */

DROP TABLE IF EXISTS `administrativniradnik`;

CREATE TABLE `administrativniradnik` (
  `administrativniradnikid` int(11) NOT NULL AUTO_INCREMENT,
  `ime` varchar(100) COLLATE utf8_croatian_ci NOT NULL,
  `prezime` varchar(100) COLLATE utf8_croatian_ci NOT NULL,
  `korisnickoime` varchar(100) COLLATE utf8_croatian_ci NOT NULL,
  `korisnickasifra` varchar(100) COLLATE utf8_croatian_ci NOT NULL,
  `token` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  PRIMARY KEY (`administrativniradnikid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_croatian_ci;

/*Data for the table `administrativniradnik` */

insert  into `administrativniradnik`(`administrativniradnikid`,`ime`,`prezime`,`korisnickoime`,`korisnickasifra`,`token`) values (1,'Marko','Baračkov','mare','mikimaus','TOKEN##1'),(2,'Lazar','Blanuša','laki','minimaus',NULL),(3,'Ognjen','Ašković','ogi','somina',NULL),(4,'Ivan','Aracki','raca','mikimaus',NULL),(5,'Ana','Adamović','anna','allegra',NULL),(6,'Marko','Peric','tuki','mare',NULL);

/*Table structure for table `dnevnaberba` */

DROP TABLE IF EXISTS `dnevnaberba`;

CREATE TABLE `dnevnaberba` (
  `jmbg` varchar(100) COLLATE utf8_croatian_ci NOT NULL,
  `dnevnaberbaid` int(11) NOT NULL AUTO_INCREMENT,
  `datum` date DEFAULT NULL,
  PRIMARY KEY (`jmbg`,`dnevnaberbaid`),
  KEY `dnevnaberbaid` (`dnevnaberbaid`),
  CONSTRAINT `dnevnaberba_ibfk_1` FOREIGN KEY (`jmbg`) REFERENCES `dobavljac` (`jmbg`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8 COLLATE=utf8_croatian_ci;

/*Data for the table `dnevnaberba` */

insert  into `dnevnaberba`(`jmbg`,`dnevnaberbaid`,`datum`) values ('0103992870087',77,'2016-03-26'),('0103992870087',115,'2016-05-04'),('0202996870056',105,'0002-11-30'),('0202996870056',106,'2016-12-12'),('0202996870056',108,'0001-01-01'),('0202996870056',109,'1111-01-01'),('0202996870056',112,'0001-01-01'),('0202996870056',113,'0001-01-01'),('0202996870056',114,'0001-01-01'),('0503973875029',84,'2016-04-08'),('0503973875029',85,'2016-04-08'),('0503973875029',86,'2016-04-08'),('0601969870001',87,'2016-04-08'),('0601969870001',89,'2016-04-08'),('0601969870001',97,'2016-04-08'),('0804156320008',103,'2016-04-08'),('0808992870009',67,'2016-02-20'),('0808992870009',70,'2016-02-23'),('0808992870009',78,'2016-03-26'),('0808992870009',110,'0001-01-01'),('1010101010101',88,'2016-04-08'),('2145896325632',91,'2016-04-08'),('2145896325632',92,'2016-04-08'),('2145896325632',93,'2016-04-08'),('2222222222222',94,'2016-04-08'),('2222222222222',95,'2016-04-08'),('2222222222222',96,'2016-04-08'),('25169851236',100,'2012-02-01'),('2804992710291',50,'2015-07-06'),('2804992710291',71,'2016-02-26'),('2804992710291',98,'2016-04-08'),('2804992710291',99,'2016-04-08'),('78962130201',102,'2015-08-08'),('7898325652301',44,'2015-07-06'),('7898325652301',45,'2015-07-06');

/*Table structure for table `dobavljac` */

DROP TABLE IF EXISTS `dobavljac`;

CREATE TABLE `dobavljac` (
  `jmbg` varchar(100) COLLATE utf8_croatian_ci NOT NULL,
  `brojgazdinstva` varchar(100) COLLATE utf8_croatian_ci NOT NULL,
  `brojlicnekarte` varchar(100) COLLATE utf8_croatian_ci NOT NULL,
  `ime` varchar(100) COLLATE utf8_croatian_ci NOT NULL,
  `prezime` varchar(100) COLLATE utf8_croatian_ci NOT NULL,
  `tekuciracun` varchar(100) COLLATE utf8_croatian_ci NOT NULL,
  `ulica` varchar(100) COLLATE utf8_croatian_ci NOT NULL,
  `broj` varchar(100) COLLATE utf8_croatian_ci NOT NULL,
  `mesto` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`jmbg`),
  KEY `mesto` (`mesto`),
  CONSTRAINT `dobavljac_ibfk_1` FOREIGN KEY (`mesto`) REFERENCES `mesto` (`ptt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_croatian_ci;

/*Data for the table `dobavljac` */

insert  into `dobavljac`(`jmbg`,`brojgazdinstva`,`brojlicnekarte`,`ime`,`prezime`,`tekuciracun`,`ulica`,`broj`,`mesto`) values ('0103992870087','234234','234233','Lazara','Blanuš','23423 - 23423 - 2342','slkdjflk','5',26300),('0202996870056','57645','5645','Miki','sdfsd','456 - 56546 - 5465','gfhfg','87',26330),('0503973875029','1684564981','00065654','Suzana','Barackov','546 - 65465 - 5465','Laze Ranica','21/a',11000),('0601969870001','','092324','Suzana','Suzana','','Laze','21/a',11000),('0804156320008','0515940','09540940','Vukasin','Jerovic','408 - 17001 - 94','Djure Strugara','29',25260),('0808992870009','2323423423','23523423','Marko','Baračkov','32232  - 322- 2322','Laze Ranića','21/a',26330),('0809992870009','654546546','0023215','Marko','Barackov','452 -6465461 -545','Laze Ranica','21/a',26330),('0909998870021','45645','454645','sdfsd','sdfsd','456 - 54549 - 54546','dfgdfg','45',23207),('1010101010101','54952957','098406405','Aleksandar','Cvijetinovic','708 - 15501 - 35','Disova','747',26300),('1111111111111','0595190','0641030','Aleksandar','Toskic','608 - 17001 - 94','Brace Nedic','674',11000),('1203993870022','45645','454','lksdjfls','fsdfs','456 - 546 - 654','dsafsd','21',25260),('1203993870023','45645','454','lksdjfls','fsdfs','456 - 546 - 654','dsafsd','21',25260),('128503263653214','190498045904','0949840','Marko','Blazenovic','208 - 17001 - 94','Laze Ranica','27',36313),('1808992870009','54654','46654','lksdjflk','sdfasdfa','24654 - 6546 - 654','56465','545',25260),('1809992870009','654546546','0023215','Marko','Barackov','452 -6465461 -545','Laze Ranica','21/a',26330),('2145896325632','09482490','09460406','Aleksandar','Blazenovic','888 - 20001 - 18','Aradska','7',11000),('2222222222222','0519510989','0940654','Lazar','Askovic','708 - 17001 - 94','Laze Ranica','264',26330),('2323232323232','29429894029489','0408','Vukasin','Sumadinovic','708 - 28501 - 29','Brace Nedic','652',11000),('25169851236','32165498','879804','Marko','Adamovic','708 - 36001 - 33','Laze Ranica','76',11000),('2804992710291','234234','2342342','Ognjen','Ašković','32423 - 324 - 3242','Mice Trofrtaljke','32',11000),('3030303030303','52095090','0984061','Ivan','Radjenovic','908 - 15501 - 35','Djure Strugara','857',36313),('324234','324234','2342342',' marko','barackov','23 - 23423423 - 234','jove','34',23207),('4040404040404','0594248904','04065406','Vukasin','Milosev','108 - 28501 - 29','Vojvode Vuka','84',26330),('4444444444444','05192980','0954066','Zivkica','Sumadinovic','908 - 17001 - 94','Egejska','214',25260),('45126520003','511982952','094098409','Marko','Blanusa','608 - 26501 - 15','Djure Strugara','22',11000),('4521658789856','090842480','0984064','Marko','Barackov ','908 - 28501 - 29','Djure Strugara','2',25260),('4523226458521','1654','091502','Marko','Nesic','108 - 36001 - 33','Egejska','22',26330),('4525987654321','0261515925','509459','Milica','Jokic','308 - 36001 - 33','2. Oktobar','321',23207),('4578985123215','91520021','0949540954','Katarina','Tomic','908 - 26501 - 15','Egejska','84',11000),('4585123654562','31945165','541915','Lazar','Askovic','908 - 11501 - 07','Djure Strugara','12',11000),('5050505050505','95240845','09490849','Milica','Askovic','208 - 28501 - 29','Egejska','92',25260),('54652132589','54665949','1951','Aleksandar','Blanusa','908 - 12501 - 14','Vojvode Vuka','11',36313),('5555555555555','50295904985','40951090','Milica','Blazenovic','108 - 15501 - 35','Disova','236',11000),('6060606060606','05284054','04643','Marko','Blazenovic','308 - 28501 - 29','Laze Ranica','72',11000),('6666666666666','059542984','09549049','Marko','Jovanovic','208 - 15501 - 35','Vojvode Vuka','237',26300),('7070707070707','019528949','5940654','Lazar','Milutinovic','408 - 28501 - 29','Brace Nedic','84',26300),('7865158849856','51565928','0549510','Vukasin','Askovic','508 - 36001 - 33','Djure Strugara','46',26330),('78653211230','05192510','0492409','Aleksandar','Sumadinovic','808 - 26501 - 15','Disova','94',26300),('7865321145214','519128912','409849084','Ivan','Nesic','408 - 26501 - 15','Disova','44',26300),('78962130201','15195487','04904049','Vukasin','Udovicic','308 - 26501 - 15','Egejska','66',25260),('7898325652301','216598','984054','Milica','Aracki','108 - 26501 - 15','Ickova','99',25260),('7898456321235','23154984','5094190','Aleksandar','Barisic','808 - 36001 - 33','Vojvode Vuka','88',36313),('8080808080808','095525949051','09846406','Aleksandar','Blanusa','508 - 28501 - 29','Djure Strugara','96',23207),('85201234563','15192826','094940954','Andrijana','Armenovic','708 - 26501 - 15','Vojvode Vuka','43',23207),('8532123965887','05195281709','04994094','Milica','Adamovic','108 - 17001 - 94','Brace Nedic','74',25260),('8585858585858','048246','098406','Aleksandar','Blanusa','808 - 28501 - 29','Disova','92',36313),('879854621456','0261565592','010594','Ognjen','Adamovic','208 - 36001 - 33','Ickova','33',25260),('8888888888888','0541952095','094065464','Ana','Aracki','508 - 17001 - 94','Egejska','456',26300);

/*Table structure for table `mesto` */

DROP TABLE IF EXISTS `mesto`;

CREATE TABLE `mesto` (
  `ptt` bigint(20) NOT NULL,
  `naziv` varchar(100) COLLATE utf8_croatian_ci DEFAULT NULL,
  PRIMARY KEY (`ptt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_croatian_ci;

/*Data for the table `mesto` */

insert  into `mesto`(`ptt`,`naziv`) values (11000,'Beograd'),(23207,'Aradac'),(25260,'Apatin'),(26300,'Vršac'),(26330,'Uljma'),(36313,'Ugao');

/*Table structure for table `stavkadnevneberbe` */

DROP TABLE IF EXISTS `stavkadnevneberbe`;

CREATE TABLE `stavkadnevneberbe` (
  `jmbg` varchar(100) COLLATE utf8_croatian_ci NOT NULL,
  `dnevnaberbaid` int(11) NOT NULL,
  `stavkaid` int(11) NOT NULL AUTO_INCREMENT,
  `tacne` double DEFAULT NULL,
  `prvaklasa` double DEFAULT NULL,
  `drugaklasa` double DEFAULT NULL,
  `trecaklasa` double DEFAULT NULL,
  `cenatacne` double DEFAULT NULL,
  `cenaprvaklasa` double DEFAULT NULL,
  `cenadrugaklasa` double DEFAULT NULL,
  `cenatrecaklasa` double DEFAULT NULL,
  PRIMARY KEY (`jmbg`,`dnevnaberbaid`,`stavkaid`),
  KEY `stavkaid` (`stavkaid`),
  KEY `stavkadnevneberbe_ibfk_2` (`dnevnaberbaid`),
  CONSTRAINT `stavkadnevneberbe_ibfk_1` FOREIGN KEY (`jmbg`) REFERENCES `dnevnaberba` (`jmbg`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `stavkadnevneberbe_ibfk_2` FOREIGN KEY (`dnevnaberbaid`) REFERENCES `dnevnaberba` (`dnevnaberbaid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8 COLLATE=utf8_croatian_ci;

/*Data for the table `stavkadnevneberbe` */

insert  into `stavkadnevneberbe`(`jmbg`,`dnevnaberbaid`,`stavkaid`,`tacne`,`prvaklasa`,`drugaklasa`,`trecaklasa`,`cenatacne`,`cenaprvaklasa`,`cenadrugaklasa`,`cenatrecaklasa`) values ('0103992870087',77,97,100,0,0,0,100,0,0,0),('0103992870087',115,96,100,0,0,0,100,0,0,0),('0202996870056',105,95,100,0,0,0,100,0,0,0),('0804156320008',103,92,80,0,0,0,100,0,0,0),('0804156320008',103,93,0,20,0,0,0,80,0,0),('0804156320008',103,94,20,0,0,0,80,0,0,0),('0808992870009',67,38,100,0,0,0,50,0,0,0),('0808992870009',67,64,100,8,89,89,50,8,80,80),('0808992870009',70,39,100,0,0,0,20,0,0,0),('0808992870009',78,56,29,0,0,0,28,0,0,0),('0808992870009',78,57,28,0,0,0,28,0,0,0),('0808992870009',78,59,19,0,0,0,19,0,0,0),('0808992870009',78,60,18,0,0,0,18,0,0,0),('0808992870009',78,61,23,0,0,0,23,0,0,0),('0808992870009',78,62,100,0,0,0,100,0,0,0),('0808992870009',78,63,100,0,0,0,20,0,0,0),('2145896325632',91,70,1,0,0,0,1,0,0,0),('2145896325632',91,71,0,1,0,0,0,1,0,0),('2145896325632',91,72,1,0,0,0,1,0,0,0),('2145896325632',91,73,0,1,0,0,0,1,0,0),('2145896325632',91,74,1,0,0,0,1,0,0,0),('2145896325632',91,75,0,1,0,0,0,1,0,0),('2222222222222',94,76,1,0,0,0,1,0,0,0),('2222222222222',95,77,1,0,0,0,1,0,0,0),('2222222222222',95,78,1,0,0,0,1,0,0,0),('2222222222222',95,79,2,0,0,0,2,0,0,0),('2222222222222',95,80,1,0,0,0,1,0,0,0),('2804992710291',50,24,23,3,0,0,100,50,0,0),('2804992710291',71,36,100,0,0,0,50,0,0,0),('2804992710291',98,84,1,0,0,0,1,0,0,0),('2804992710291',98,85,1,0,0,0,1,0,0,0),('2804992710291',99,86,1,0,0,0,1,0,0,0),('2804992710291',99,87,2,0,0,0,2,0,0,0),('2804992710291',99,88,3,0,0,0,3,0,0,0),('2804992710291',99,89,0,2,0,0,0,2,0,0),('78962130201',102,90,3,0,0,0,3,0,0,0),('78962130201',102,91,0,0,0,3,0,0,0,3),('7898325652301',44,18,3,0,100,0,100,0,30,0),('7898325652301',44,19,0,3,0,0,0,150,0,0),('7898325652301',45,20,3,0,0,0,50,0,0,0);

/*Table structure for table `zaduzenje` */

DROP TABLE IF EXISTS `zaduzenje`;

CREATE TABLE `zaduzenje` (
  `jmbg` varchar(100) COLLATE utf8_croatian_ci NOT NULL,
  `zaduzenjeid` int(11) NOT NULL AUTO_INCREMENT,
  `datumzaduzenja` date DEFAULT NULL,
  `datumrazduzenja` date DEFAULT NULL,
  `kompost` tinyint(1) DEFAULT NULL,
  `prevoz` tinyint(1) DEFAULT NULL,
  `brojvreca` int(11) DEFAULT NULL,
  `zaduzio` int(11) DEFAULT NULL,
  `razduzio` int(11) DEFAULT NULL,
  PRIMARY KEY (`jmbg`,`zaduzenjeid`),
  KEY `zaduzenjeid` (`zaduzenjeid`),
  KEY `zaduzio` (`zaduzio`),
  KEY `razduzio` (`razduzio`),
  CONSTRAINT `zaduzenje_ibfk_1` FOREIGN KEY (`zaduzio`) REFERENCES `administrativniradnik` (`administrativniradnikid`),
  CONSTRAINT `zaduzenje_ibfk_2` FOREIGN KEY (`razduzio`) REFERENCES `administrativniradnik` (`administrativniradnikid`),
  CONSTRAINT `zaduzenje_ibfk_3` FOREIGN KEY (`jmbg`) REFERENCES `dobavljac` (`jmbg`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8 COLLATE=utf8_croatian_ci;

/*Data for the table `zaduzenje` */

insert  into `zaduzenje`(`jmbg`,`zaduzenjeid`,`datumzaduzenja`,`datumrazduzenja`,`kompost`,`prevoz`,`brojvreca`,`zaduzio`,`razduzio`) values ('0103992870087',26,'2015-07-05','2016-03-24',0,1,0,6,1),('0103992870087',42,'2016-02-23','2016-02-23',0,1,0,1,1),('0103992870087',44,'2016-03-16','2016-03-16',1,0,40,1,1),('0103992870087',45,'2016-03-23','2016-03-23',0,1,0,1,1),('0103992870087',46,'2016-03-23','2016-04-03',0,1,0,1,1),('0103992870087',47,'2016-03-23',NULL,0,1,0,1,NULL),('0103992870087',48,'2016-03-23',NULL,0,1,0,1,NULL),('0103992870087',49,'2016-03-23','2016-03-30',0,1,0,1,1),('0103992870087',50,'2016-03-24','2016-03-24',0,1,0,1,1),('0103992870087',53,'2016-03-30',NULL,0,1,0,1,NULL),('0103992870087',54,'2016-04-11',NULL,1,0,0,1,NULL),('0103992870087',55,'2016-04-11',NULL,1,0,0,1,NULL),('0103992870087',56,'2016-04-11',NULL,1,0,0,1,NULL),('0103992870087',57,'2016-04-11',NULL,1,0,0,1,NULL),('0103992870087',58,'2016-04-11',NULL,1,0,0,1,NULL),('0103992870087',59,'2016-05-04',NULL,0,1,0,1,NULL),('0503973875029',51,'2016-03-26','2016-03-26',0,1,0,1,1),('0804156320008',15,'2015-07-04',NULL,0,1,0,6,NULL),('0808992870009',43,'2016-03-16','2016-03-16',0,1,0,1,1),('0808992870009',52,'2016-03-26','2016-03-26',0,1,0,1,1),('1010101010101',13,'2015-07-04','2015-07-04',0,1,0,6,6),('1010101010101',14,'2015-07-04',NULL,1,0,56,6,NULL),('1010101010101',21,'2015-07-04','2015-07-04',0,1,0,6,6),('1010101010101',24,'2015-07-05',NULL,1,0,6,6,NULL),('128503263653214',25,'2015-07-05',NULL,1,0,8,6,NULL),('2145896325632',34,'2015-07-10',NULL,0,1,0,1,NULL),('25169851236',3,'2015-05-23',NULL,1,0,96,3,NULL),('2804992710291',32,'2015-07-06','2015-07-06',0,1,0,1,1),('2804992710291',33,'2015-07-06','2015-07-06',1,0,34,1,1),('4040404040404',22,'2015-07-04',NULL,0,1,0,6,NULL),('4525987654321',4,'2015-05-08','2015-05-28',0,1,0,2,4),('4525987654321',5,'2015-07-08',NULL,1,0,100,4,NULL),('4525987654321',16,'2015-07-04',NULL,0,1,0,6,NULL),('6060606060606',23,'2015-07-05',NULL,0,1,0,6,NULL),('8532123965887',18,'2015-07-04',NULL,0,1,0,6,NULL),('8532123965887',19,'2015-07-04',NULL,1,0,56,6,NULL),('8888888888888',30,'2015-07-06',NULL,1,0,54,1,NULL),('8888888888888',31,'2015-07-06',NULL,0,1,0,1,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
