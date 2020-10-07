SELECT G.name,G.description,G.Developer,G.`Release date` FROM `Game` G
INNER JOIN `Game Developer` GD ON GD.name=G.Developer
where GD.name=?
ORDER BY G.`Release date` DESC ;