SELECT G.name,G.description,G.Developer,G.`Release date` FROM `Game` G
INNER JOIN `Tag Game Library` TGLL on G.Game_ID = TGLL.Game_ID
INNER JOIN `Tag Game Library` TGLR on TGLL.`tag name`=TGLR.`tag name`
INNER JOIN User_Game UG on TGLR.Game_ID = UG.Game_ID
where UG.`E-mail`=?;