-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 23, 2014 at 03:15 PM
-- Server version: 5.6.16
-- PHP Version: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `gpsdata`
--

-- --------------------------------------------------------

--
-- Table structure for table `location`
--

CREATE TABLE IF NOT EXISTS `location` (
  `imei` bigint(15) NOT NULL,
  `time_sent` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `time_received` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `latitude` decimal(8,6) NOT NULL,
  `longitude` decimal(8,6) NOT NULL,
  `speed` decimal(5,2) NOT NULL,
  `direction` decimal(5,2) NOT NULL,
  `delay` mediumint(9) NOT NULL,
  PRIMARY KEY (`imei`,`time_sent`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `log`
--

CREATE TABLE IF NOT EXISTS `log` (
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `message` varchar(150) NOT NULL,
  `type` char(1) NOT NULL,
  PRIMARY KEY (`time`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `route`
--

CREATE TABLE IF NOT EXISTS `route` (
  `routeID` tinyint(4) unsigned NOT NULL,
  `engineID` varchar(5) NOT NULL,
  `imei` bigint(15) NOT NULL,
  PRIMARY KEY (`routeID`),
  KEY `imei` (`imei`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `route`
--

INSERT INTO `route` (`routeID`, `engineID`, `imei`) VALUES
(6, '17', 54321),
(12, '34', 12345);

-- --------------------------------------------------------

--
-- Table structure for table `route_stations`
--

CREATE TABLE IF NOT EXISTS `route_stations` (
  `routeID` tinyint(4) unsigned NOT NULL,
  `stationID` tinyint(4) unsigned NOT NULL,
  `Stationorder` tinyint(3) unsigned NOT NULL,
  KEY `routeID` (`routeID`),
  KEY `stationID` (`stationID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `route_stations`
--

INSERT INTO `route_stations` (`routeID`, `stationID`, `Stationorder`) VALUES
(12, 1, 3),
(12, 2, 4);

-- --------------------------------------------------------

--
-- Table structure for table `station`
--

CREATE TABLE IF NOT EXISTS `station` (
  `stationID` tinyint(4) unsigned NOT NULL,
  `up_latitude` decimal(8,6) NOT NULL,
  `up_longitude` decimal(8,6) NOT NULL,
  `down_latitude` decimal(8,6) NOT NULL,
  `down_longitude` decimal(8,6) NOT NULL,
  PRIMARY KEY (`stationID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `station`
--

INSERT INTO `station` (`stationID`, `up_latitude`, `up_longitude`, `down_latitude`, `down_longitude`) VALUES
(1, '1.000000', '4.000000', '1.500000', '5.000000'),
(2, '5.000000', '6.000000', '5.000000', '7.000000');

-- --------------------------------------------------------

--
-- Table structure for table `station_rp`
--

CREATE TABLE IF NOT EXISTS `station_rp` (
  `stationID` tinyint(4) unsigned NOT NULL,
  `rpID` tinyint(4) NOT NULL,
  `stationName` varchar(50) NOT NULL,
  PRIMARY KEY (`stationID`,`rpID`),
  UNIQUE KEY `rpID` (`rpID`),
  KEY `stationID` (`stationID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `station_rp`
--

INSERT INTO `station_rp` (`stationID`, `rpID`, `stationName`) VALUES
(1, 1, 'Ganemulla'),
(2, 2, 'Gampaha');

-- --------------------------------------------------------

--
-- Table structure for table `waypoint`
--

CREATE TABLE IF NOT EXISTS `waypoint` (
  `waypointindex` tinyint(4) NOT NULL AUTO_INCREMENT,
  `routeID` tinyint(4) unsigned NOT NULL,
  `latitude` decimal(8,6) NOT NULL,
  `longitude` decimal(8,6) NOT NULL,
  `time_reach` time NOT NULL,
  `previous_station` tinyint(5) unsigned NOT NULL,
  `next_station` tinyint(5) unsigned NOT NULL,
  PRIMARY KEY (`waypointindex`),
  KEY `routeID` (`routeID`),
  KEY `previous_station` (`previous_station`),
  KEY `next_station` (`next_station`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `waypoint`
--

INSERT INTO `waypoint` (`waypointindex`, `routeID`, `latitude`, `longitude`, `time_reach`, `previous_station`, `next_station`) VALUES
(1, 12, '2.000000', '5.200000', '08:01:15', 1, 2),
(2, 12, '3.000000', '5.300000', '07:02:35', 1, 2),
(3, 12, '4.000000', '5.400000', '08:03:45', 1, 2);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `location`
--
ALTER TABLE `location`
  ADD CONSTRAINT `location_ibfk_1` FOREIGN KEY (`imei`) REFERENCES `route` (`imei`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `route_stations`
--
ALTER TABLE `route_stations`
  ADD CONSTRAINT `route_stations_ibfk_1` FOREIGN KEY (`routeID`) REFERENCES `route` (`routeID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `route_stations_ibfk_2` FOREIGN KEY (`stationID`) REFERENCES `station` (`stationID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `station_rp`
--
ALTER TABLE `station_rp`
  ADD CONSTRAINT `station_rp_ibfk_1` FOREIGN KEY (`stationID`) REFERENCES `station` (`stationID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `waypoint`
--
ALTER TABLE `waypoint`
  ADD CONSTRAINT `waypoint_ibfk_1` FOREIGN KEY (`routeID`) REFERENCES `route` (`routeID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `waypoint_ibfk_2` FOREIGN KEY (`previous_station`) REFERENCES `station` (`stationID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `waypoint_ibfk_3` FOREIGN KEY (`next_station`) REFERENCES `station` (`stationID`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
