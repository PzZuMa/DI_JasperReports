-- --------------------------------------------------------
-- Host:                         localhost
-- Versión del servidor:         9.0.1 - MySQL Community Server - GPL
-- SO del servidor:              Linux
-- HeidiSQL Versión:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para ejercicioConjunto
CREATE DATABASE IF NOT EXISTS `hibernateConjuntoDB` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `hibernateConjuntoDB`;

-- Volcando estructura para tabla ejercicioConjunto.Copia
CREATE TABLE IF NOT EXISTS `Copia` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_pelicula` int NOT NULL,
  `id_usuario` int NOT NULL,
  `estado` enum('bueno','dañado') DEFAULT NULL,
  `soporte` enum('DVD','Blu-ray') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `id_pelicula` (`id_pelicula`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `id_pelicula` FOREIGN KEY (`id_pelicula`) REFERENCES `Pelicula` (`id`),
  CONSTRAINT `id_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `Usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla ejercicioConjunto.Copia: ~6 rows (aproximadamente)
INSERT INTO `Copia` (`id`, `id_pelicula`, `id_usuario`, `estado`, `soporte`) VALUES
	(1, 1, 1, 'bueno', 'DVD'),
	(2, 1, 1, 'bueno', 'Blu-ray'),
	(3, 2, 2, 'dañado', 'DVD'),
	(4, 3, 1, 'bueno', 'Blu-ray'),
	(5, 4, 2, 'bueno', 'DVD'),
	(6, 4, 1, 'bueno', 'Blu-ray');

-- Volcando estructura para tabla ejercicioConjunto.Pelicula
CREATE TABLE IF NOT EXISTS `Pelicula` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(50) DEFAULT NULL,
  `genero` varchar(50) DEFAULT NULL,
  `año` year DEFAULT NULL,
  `descripcion` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `director` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla ejercicioConjunto.Pelicula: ~4 rows (aproximadamente)
INSERT INTO `Pelicula` (`id`, `titulo`, `genero`, `año`, `descripcion`, `director`) VALUES
	(1, 'Origen', 'Sci-Fi', '2010', 'Un ladrón que roba secretos corporativos a través del uso de la tecnología de compartir sueños recibe una oportunidad para borrar su historial criminal.', 'Christopher Nolan'),
	(2, 'Matrix', 'Acción', '1999', 'Un hacker de ordenadores aprende de rebeldes misteriosos sobre la verdadera naturaleza de su realidad y su papel en la guerra contra sus controladores.', 'Lana Wachowski'),
	(3, 'Interstellar', 'Sci-Fi', '2014', 'Un equipo de exploradores viaja a través de un agujero de gusano en el espacio en un intento de asegurar la supervivencia de la humanidad.', 'Christopher Nolan'),
	(4, 'Star Wars: Episodio IV - Una nueva esperanza', 'Sci-Fi', '1977', 'Un joven granjero se une a una rebelión contra un imperio galáctico tiránico.', 'George Lucas');

-- Volcando estructura para tabla ejercicioConjunto.Usuario
CREATE TABLE IF NOT EXISTS `Usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `contraseña` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla ejercicioConjunto.Usuario: ~2 rows (aproximadamente)
INSERT INTO `Usuario` (`id`, `nombre`, `contraseña`) VALUES
	(1, 'juanperez', 'password123'),
	(2, 'mariagonzalez', 'mypassword');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
