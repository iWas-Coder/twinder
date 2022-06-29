-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: twinder
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tweet`
--

DROP TABLE IF EXISTS `tweet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tweet` (
  `tweetID` int NOT NULL AUTO_INCREMENT,
  `userID` int NOT NULL,
  `publishDate` datetime NOT NULL,
  `parentID` int DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `lang` varchar(24) DEFAULT NULL,
  `retweets` int NOT NULL,
  `likes` int NOT NULL,
  PRIMARY KEY (`tweetID`,`userID`),
  KEY `userID` (`userID`),
  KEY `parentID` (`parentID`),
  CONSTRAINT `tweet_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`),
  CONSTRAINT `tweet_ibfk_2` FOREIGN KEY (`parentID`) REFERENCES `tweet` (`tweetID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tweet`
--

LOCK TABLES `tweet` WRITE;
/*!40000 ALTER TABLE `tweet` DISABLE KEYS */;
INSERT INTO `tweet` VALUES (1,1,'2022-04-19 12:21:53',NULL,'Se vende opel corsa','esp',0,0),(2,1,'2022-04-18 20:57:45',NULL,'Hola por la carretera','esp',0,0),(3,2,'2022-03-10 10:34:12',NULL,'Como funciona esta pagina?','esp',0,0),(4,2,'2022-03-10 10:41:36',3,'New tweet','eng',0,0),(5,3,'2022-04-02 23:54:45',NULL,'hello','eng',1,0),(6,3,'2022-04-02 23:56:13',5,'hi','eng',1,0),(7,5,'2022-05-29 13:42:30',NULL,'Who is chicamaja123???','eng',1,0),(8,7,'2022-06-01 10:00:00',NULL,'Saludos twinderos, espero que disfruteis de esta pagina y siguiendo podamos crear una hermosa comunidad <3','esp',0,0),(9,7,'2022-06-01 10:00:02',NULL,'Greetings twindeers, I hope you are enying the page so far and following the rules we could create a great community <3','eng',0,0);
/*!40000 ALTER TABLE `tweet` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-27 22:24:18
