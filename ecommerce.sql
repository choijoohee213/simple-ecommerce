CREATE DATABASE  IF NOT EXISTS `ecommerce` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ecommerce`;
-- MySQL dump 10.13  Distrib 8.0.28, for macos11 (x86_64)
--
-- Host: localhost    Database: ecommerce
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `product_id` int NOT NULL,
  `quantity` int unsigned NOT NULL DEFAULT '1',
  `selected` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`product_id`),
  KEY `proudct_id_fk_idx` (`product_id`),
  CONSTRAINT `proudct_id_fk` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (17,1,0),(19,1,1);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `delivery_group` varchar(25) COLLATE utf8mb4_0900_as_cs NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_0900_as_cs NOT NULL,
  `price` int unsigned NOT NULL,
  `quantity` int unsigned NOT NULL DEFAULT '0',
  `image` varchar(500) COLLATE utf8mb4_0900_as_cs DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (16,'쓱배송','허쉬 초코멜로쿠키 45g',600,10,'https://sitem.ssgcdn.com/56/37/67/item/1000265673756_i1_1200.jpg'),(17,'쓱배송','크리스피롤 12 곡 180g',2800,8,'https://sitem.ssgcdn.com/85/05/42/item/1000024420585_i1_1200.jpg'),(18,'쓱배송','동물복지 유정란 15 개입 대란',4350,0,'https://sitem.ssgcdn.com/35/22/64/item/0000008642235_i1_1200.jpg'),(19,'쓱배송','말랑카우 핸드워시 250ml',2600,6,'https://sitem.ssgcdn.com/90/43/21/item/1000035214390_i1_1200.jpg'),(20,'쓱배송','삼립 미니꿀호떡 322g',1200,4,'https://sitem.ssgcdn.com/22/28/73/item/1000021732822_i1_1200.jpg'),(21,'새벽배송','피코크 어랑손만두 어랑만두 700g',6400,2,'https://sitem.ssgcdn.com/93/54/12/item/1000281125493_i1_1200.jpg'),(22,'새벽배송','빼빼로바 아몬드아이스크림 4 입',2800,1,'https://sitem.ssgcdn.com/00/50/52/item/1000260525000_i1_1200.jpg'),(23,'새벽배송','피코크 에이 클래스 우유 900ml',2500,3,'https://sitem.ssgcdn.com/35/82/38/item/1000057388235_i1_1200.jpg'),(24,'새벽배송','아삭달콤 방울토마토 500g',4500,0,'https://sitem.ssgcdn.com/46/56/15/item/2097001155646_i1_1200.jpg'),(25,'새벽배송','[롯데삼강] 돼지바 (70ml*6 입)',3000,1,'https://sitem.ssgcdn.com/31/17/49/item/0000008491731_i1_1200.jpg'),(26,'택배','키즈 피크닉 팩 M',65000,4,'https://sitem.ssgcdn.com/71/87/52/item/1000057528771_i1_1200.jpg'),(27,'택배','이달의 원두 500g',18500,4,'https://sitem.ssgcdn.com/52/99/78/item/1000082789952_i1_1200.jpg'),(28,'택배','아쿠아 머그',23000,7,'https://sitem.ssgcdn.com/30/94/96/item/1000279969430_i1_1200.jpg'),(29,'택배','삼성전자 43 인치 스마트모니터',480000,2,'https://sitem.ssgcdn.com/57/20/22/item/1000366222057_i1_1200.jpg'),(30,'택배','나이키 헤리티지 스우시 캡',25000,5,'https://sitem.ssgcdn.com/28/72/92/item/1000032927228_i1_1200.jpg');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-11 17:03:20
