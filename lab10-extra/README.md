## Lab 10 - extra: Pozivanje procedure, link ka dodatnim materijalima

```
CREATE DEFINER=`root`@`localhost` PROCEDURE `FIND_CAR_BY_YEAR`(in p_year int)
BEGIN
SELECT c.ID, c.MODEL, c.YEAR
    FROM car c
    WHERE c.YEAR = p_year;
END
```

Link ka dodatnim JS materijalima: https://goo.gl/dh2K4a

