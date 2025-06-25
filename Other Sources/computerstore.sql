-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 24, 2025 at 05:19 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `computerstore`
--

-- --------------------------------------------------------

--
-- Table structure for table `korisnik`
--

CREATE TABLE `korisnik` (
  `korisnik_id` int(11) NOT NULL,
  `ime_i_prezime` varchar(64) NOT NULL,
  `username` varchar(45) NOT NULL,
  `e_mail` varchar(64) NOT NULL,
  `datum_rodjenja` varchar(20) NOT NULL,
  `stanje_racuna` bigint(20) NOT NULL,
  `kolicina_potrosenog_novca` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `korisnik`
--

INSERT INTO `korisnik` (`korisnik_id`, `ime_i_prezime`, `username`, `e_mail`, `datum_rodjenja`, `stanje_racuna`, `kolicina_potrosenog_novca`) VALUES
(1, 'Kristina Spasojevic', 'kristina', 'kristina@example.com', '03.01.2001.', 7500, 10000),
(2, 'Marija Markovic', 'marija', 'marija@example.com', '10.06.1993.', 8500, 3000);

-- --------------------------------------------------------

--
-- Table structure for table `kupovina`
--

CREATE TABLE `kupovina` (
  `kupovina_id` int(11) NOT NULL,
  `korisnik_id` int(11) DEFAULT NULL,
  `proizvod_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kupovina`
--

INSERT INTO `kupovina` (`kupovina_id`, `korisnik_id`, `proizvod_id`) VALUES
(1, 1, 1),
(2, 2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `podesavanje_pretrage`
--

CREATE TABLE `podesavanje_pretrage` (
  `podesavanje_pretrage_id` int(11) NOT NULL,
  `min_cena` bigint(20) NOT NULL,
  `max_cena` bigint(20) NOT NULL,
  `vrsta_opreme` varchar(64) NOT NULL,
  `kljucna_rec` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `podesavanje_pretrage`
--

INSERT INTO `podesavanje_pretrage` (`podesavanje_pretrage_id`, `min_cena`, `max_cena`, `vrsta_opreme`, `kljucna_rec`) VALUES
(1, 100, 2000, 'Graficke kartice', 'RTX'),
(2, 500, 1500, 'Periferija', 'tastatura'),
(3, 50, 300, 'Oprema za snimanje', 'mikrofon'),
(4, 1000, 2500, 'Hardver', 'monitor');

-- --------------------------------------------------------

--
-- Table structure for table `pretraga`
--

CREATE TABLE `pretraga` (
  `pretraga_id` int(11) NOT NULL,
  `podesavanje_pretrage_id` int(11) DEFAULT NULL,
  `korisnik_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pretraga`
--

INSERT INTO `pretraga` (`pretraga_id`, `podesavanje_pretrage_id`, `korisnik_id`) VALUES
(1, 1, 1),
(2, 4, 2);

-- --------------------------------------------------------

--
-- Table structure for table `proizvod`
--

CREATE TABLE `proizvod` (
  `proizvod_id` int(11) NOT NULL,
  `naziv` varchar(88) NOT NULL,
  `cena` bigint(20) NOT NULL,
  `vrsta_opreme` varchar(64) NOT NULL,
  `stanje_na_lageru` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `proizvod`
--

INSERT INTO `proizvod` (`proizvod_id`, `naziv`, `cena`, `vrsta_opreme`, `stanje_na_lageru`) VALUES
(1, 'Grafička kartica RTX 4060', 1200, 'Grafičke kartice', 10),
(2, 'Monitor', 300, 'Hardver', 12);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `korisnik`
--
ALTER TABLE `korisnik`
  ADD PRIMARY KEY (`korisnik_id`);

--
-- Indexes for table `kupovina`
--
ALTER TABLE `kupovina`
  ADD PRIMARY KEY (`kupovina_id`),
  ADD KEY `korisnik_id` (`korisnik_id`),
  ADD KEY `proizvod_id` (`proizvod_id`);

--
-- Indexes for table `podesavanje_pretrage`
--
ALTER TABLE `podesavanje_pretrage`
  ADD PRIMARY KEY (`podesavanje_pretrage_id`);

--
-- Indexes for table `pretraga`
--
ALTER TABLE `pretraga`
  ADD PRIMARY KEY (`pretraga_id`),
  ADD KEY `korisnik_id` (`korisnik_id`),
  ADD KEY `podesavanje_pretrage_id` (`podesavanje_pretrage_id`);

--
-- Indexes for table `proizvod`
--
ALTER TABLE `proizvod`
  ADD PRIMARY KEY (`proizvod_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `korisnik`
--
ALTER TABLE `korisnik`
  MODIFY `korisnik_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `kupovina`
--
ALTER TABLE `kupovina`
  MODIFY `kupovina_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `podesavanje_pretrage`
--
ALTER TABLE `podesavanje_pretrage`
  MODIFY `podesavanje_pretrage_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `pretraga`
--
ALTER TABLE `pretraga`
  MODIFY `pretraga_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `proizvod`
--
ALTER TABLE `proizvod`
  MODIFY `proizvod_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `kupovina`
--
ALTER TABLE `kupovina`
  ADD CONSTRAINT `kupovina_ibfk_1` FOREIGN KEY (`korisnik_id`) REFERENCES `korisnik` (`korisnik_id`),
  ADD CONSTRAINT `kupovina_ibfk_2` FOREIGN KEY (`proizvod_id`) REFERENCES `proizvod` (`proizvod_id`);

--
-- Constraints for table `pretraga`
--
ALTER TABLE `pretraga`
  ADD CONSTRAINT `pretraga_ibfk_1` FOREIGN KEY (`korisnik_id`) REFERENCES `korisnik` (`korisnik_id`),
  ADD CONSTRAINT `pretraga_ibfk_2` FOREIGN KEY (`podesavanje_pretrage_id`) REFERENCES `podesavanje_pretrage` (`podesavanje_pretrage_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
