-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 01-01-2026 a las 12:44:37
-- Versión del servidor: 10.1.9-MariaDB
-- Versión de PHP: 7.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `smartocupation`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `nombre` varchar(15) NOT NULL,
  `apellidos` varchar(50) NOT NULL,
  `DNI` varchar(15) NOT NULL,
  `FechaNacimiento` date NOT NULL,
  `datosFactura` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`nombre`, `apellidos`, `DNI`, `FechaNacimiento`, `datosFactura`) VALUES
('Francisco', 'Varela Moreno', '12121212R', '1970-06-16', 'FranVM'),
('Antonio', 'López García', '12345678A', '1985-11-05', 'AntonioLG'),
('Rocío', 'Núñez Casal', '32323232S', '1980-05-25', 'RocioNC'),
('Ana', 'Delagado Gómez', '45454545H', '1990-01-10', 'AnaDG');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reservas`
--

CREATE TABLE `reservas` (
  `DNI` varchar(30) NOT NULL,
  `NumIdentificador` varchar(25) NOT NULL,
  `FechaEntrada` date NOT NULL,
  `TiempoEstancia` int(10) NOT NULL,
  `NombreCliente` varchar(15) NOT NULL,
  `ApellidosCliente` varchar(50) NOT NULL,
  `NumExpediente` varchar(50) NOT NULL,
  `DatosFacturacion` varchar(15) NOT NULL,
  `DireccionVivienda` varchar(30) NOT NULL,
  `M2` int(11) NOT NULL,
  `NumHabitaciones` int(11) NOT NULL,
  `NumBanios` int(11) NOT NULL,
  `Precio` decimal(5,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `reservas`
--

INSERT INTO `reservas` (`DNI`, `NumIdentificador`, `FechaEntrada`, `TiempoEstancia`, `NombreCliente`, `ApellidosCliente`, `NumExpediente`, `DatosFacturacion`, `DireccionVivienda`, `M2`, `NumHabitaciones`, `NumBanios`, `Precio`) VALUES
('12121212R', '0001', '2025-11-03', 300, 'Fancisco', 'Varela Moreno', '0001', 'FranVM', 'Mar Mediterraneo 25', 100, 3, 2, '500.00'),
('12345678A', '0002', '2025-11-07', 420, 'Antonio', 'Lopez Garcia', '0002', 'AntonioLG', 'Jose Villegas 9', 150, 3, 3, '800.00'),
('32323232S', '0003', '2025-11-15', 300, 'Rocio', 'Nuñez Casal', '0003', 'RocioNC', 'Santa Lucia 32', 75, 2, 1, '450.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vivienda`
--

CREATE TABLE `vivienda` (
  `NumIdentificador` varchar(15) NOT NULL,
  `direccion` varchar(20) NOT NULL,
  `m2` int(5) NOT NULL,
  `NumHabitaciones` int(10) NOT NULL,
  `NumBanios` int(10) NOT NULL,
  `precio` decimal(5,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `vivienda`
--

INSERT INTO `vivienda` (`NumIdentificador`, `direccion`, `m2`, `NumHabitaciones`, `NumBanios`, `precio`) VALUES
('0001', 'Mar Mediterraneo 25', 100, 3, 2, '500.00'),
('0002', 'Jose Villegas 9', 150, 3, 3, '800.00'),
('0003', 'Santa Lucía 32', 75, 2, 1, '450.00');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`DNI`);

--
-- Indices de la tabla `reservas`
--
ALTER TABLE `reservas`
  ADD PRIMARY KEY (`NumExpediente`);

--
-- Indices de la tabla `vivienda`
--
ALTER TABLE `vivienda`
  ADD PRIMARY KEY (`NumIdentificador`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
