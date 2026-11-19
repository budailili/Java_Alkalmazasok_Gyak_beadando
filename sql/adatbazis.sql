-- AutóKönyv - adatbázis (forrás: szemelyek.sql, 3 tábla: szemely, jarmu, telefon)
-- Szerveren: HeidiSQL-ben a gvlrpx adatbázist kiválasztva kell lefuttatni.
-- Helyi gépen előtte:  CREATE DATABASE autokonyv DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci; USE autokonyv;
-- A users és az uzenet táblát a Hibernate hozza létre első induláskor (ddl-auto=update),
-- az admin felhasználót pedig az AdminBetolto osztály.

SET NAMES utf8;
-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `jarmu`
--

CREATE TABLE `jarmu` (
  `rndszm` varchar(6) NOT NULL,
  `marka` varchar(20) NOT NULL,
  `szin` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `jarmu`
--

INSERT INTO `jarmu` (`rndszm`, `marka`, `szin`) VALUES
('ABC123', 'Ford', 'piros'),
('FGH456', 'Skoda', 'kék'),
('HJK678', 'BMW', 'piros'),
('SDF345', 'Ford', 'fehér');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `szemely`
--

CREATE TABLE `szemely` (
  `id` int(11) NOT NULL,
  `nev` varchar(50) NOT NULL,
  `rendszam` varchar(6) NOT NULL,
  `magassag` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `szemely`
--

INSERT INTO `szemely` (`id`, `nev`, `rendszam`, `magassag`) VALUES
(1, 'Tóth Ferenc', 'ABC123', 175),
(2, 'Kiss József', 'FGH456', 168),
(3, 'Horváth Mária', 'SDF345', 165),
(4, 'Kerekes Katalin', 'HJK678', 160);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `telefon`
--

CREATE TABLE `telefon` (
  `id` int(11) NOT NULL,
  `szemelyid` int(11) NOT NULL,
  `szam` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `telefon`
--

INSERT INTO `telefon` (`id`, `szemelyid`, `szam`) VALUES
(1, 1, '345678'),
(2, 2, '456123'),
(3, 2, '234678'),
(4, 3, '345123'),
(5, 3, '123789'),
(6, 3, '345987');

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `jarmu`
--
ALTER TABLE `jarmu`
  ADD PRIMARY KEY (`rndszm`);

--
-- A tábla indexei `szemely`
--
ALTER TABLE `szemely`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `telefon`
--
ALTER TABLE `telefon`
  ADD PRIMARY KEY (`id`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `szemely`
--
ALTER TABLE `szemely`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT a táblához `telefon`
--
ALTER TABLE `telefon`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

