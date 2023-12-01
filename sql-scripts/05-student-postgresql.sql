CREATE TABLE student (
id SERIAL Primary Key,
  first_name varchar(45),
  last_name varchar(45),
  email varchar(45)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

--LOCK TABLES student WRITE;
--BEGIN WORK;
--LOCK TABLE student IN SHARE MODE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO student VALUES (1,'Mary','Public','mary@luv2code.com'),(2,'John','Doe','john@luv2code.com'),(3,'Ajay','Rao','ajay@luv2code.com'),(4,'Bill','Neely','bill@luv2code.com'),(5,'Maxwell','Dixon','max@luv2code.com');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
--UNLOCK TABLES;
--COMMIT WORK;


