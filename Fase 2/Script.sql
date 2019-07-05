DROP DATABASE IF EXISTS Movies;
CREATE DATABASE Movies;
USE Movies;


DELIMITER $$ 
DROP PROCEDURE IF EXISTS queri $$ 
CREATE PROCEDURE queri (p VARCHAR(2000))  
BEGIN 
SET @a = p;
prepare stmt FROM @a;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

 
 END $$ 
 DELIMITER ;
 
 
 
 DELIMITER $$ 
DROP PROCEDURE IF EXISTS register $$ 
CREATE PROCEDURE register (login VARCHAR(2000),pass VARCHAR(2000))  
BEGIN 
SET @a =CONCAT( "CREATE USER '",login,"' IDENTIFIED BY '",pass,"';"); 
SET @b =CONCAT("GRANT ALL PRIVILEGES ON * . * TO '",login,"';");
prepare stmt2 FROM @a;
prepare stmt3 FROM @b;
EXECUTE stmt2;
EXECUTE stmt3;
DEALLOCATE PREPARE stmt2;
DEALLOCATE PREPARE stmt3;
 END $$ 
 DELIMITER ;
 
 
 
 call register('as','as');
 
 
 
 
  DELIMITER $$ 
DROP PROCEDURE IF EXISTS search $$ 
CREATE PROCEDURE search (actor VARCHAR(2000),title VARCHAR(2000), genre VARCHAR(2000),director VARCHAR(2000),country VARCHAR(2000),orderwhat VARCHAR(2000),orderhow VARCHAR(2000))  
BEGIN
        SET @actorb = false;
		SET @titleb = false;
	    SET @genreb = false;
		SET @actorb = false;
        SET @directorb = false;
        SET @countryb = false;
        
        SET @querie = "SELECT DISTINCT m.title, p.name, g.description, m.country, m.imdb_score FROM Movie AS m NATURAL JOIN Director AS d JOIN Person AS p ON p.id_person = d.id_director NATURAL JOIN Genre_movie NATURAL JOIN Genre as g WHERE ";

       
        if actor <>"" THEN
           SET @querie = "SELECT DISTINCT m.title, p.name, g.description, m.country, m.imdb_score FROM Movie AS m NATURAL JOIN Director AS d JOIN Person AS p ON p.id_person = d.id_director NATURAL JOIN Genre_movie NATURAL JOIN Genre as g  JOIN Actor_movie AS am ON am.id_movie = m.id_movie JOIN Person AS P2 ON p2.id_person = am.id_actor WHERE ";
            SET @querie =CONCAT( @querie,"p2.name LIKE ('%" , actor ,"%' )");
           SET @actorb = true;
           
        END IF;
        
        if title <>"" THEN
            if @actorb = true THEN
                SET @querie = CONCAT(@querie,"AND ");
            END IF;
            SET @querie =CONCAT(@querie, "m.title LIKE ('%" , title ,"%' )");
            SET @titleb = true;
        END IF;
        if genre <>"" THEN
            if @actorb = true OR @titleb = true THEN
                SET @querie = CONCAT(@querie,"AND ");
                
            END IF;
            SET @querie =CONCAT(@querie, "g.description LIKE ('%" , genre ,"%' )");
            SET @genreb = true;
        END IF;
        if director <>"" THEN
            if @actorb = true OR @titleb = true OR @genreb = true THEN
                SET @querie = CONCAT(@querie,"AND ");
                
            END IF;
            SET @querie =CONCAT(@querie, "p.name LIKE ('%" , director ,"%' )");
            SET @directorb = true;
        END IF;
        if Country <>"" THEN
            if @actorb = true OR @titleb = true OR @genreb = true OR @directorb = true THEN
                SET @querie = CONCAT(@querie,"AND ");
                
            END IF;
            SET @querie =CONCAT(@querie, "m.country LIKE ('%" , country ,"%' )");
            SET @countryb = true;
        END IF;
        IF @actorb = false And @titleb = false And @genreb = false And @directorb = false And @countryb = false THEN
			SET @querie = "SELECT m.title, p.name, g.description, m.country, m.imdb_score FROM Movie AS m NATURAL JOIN Director AS d JOIN Person AS p ON p.id_person = d.id_director NATURAL JOIN Genre_movie NATURAL JOIN Genre as g";
			
		END IF;
       SET @querie = CONCAT(@querie," ORDER BY ", orderwhat," ", orderhow);
      
		prepare stmt3 FROM @querie;
		EXECUTE stmt3;
		DEALLOCATE PREPARE stmt3;
        
        END $$ 
 DELIMITER ;
 
 CALL search('','','','becaris','','m.title','ASC')
 

CALL search('',' ', '','beca','', 'm.title ', ' DESC')
CALL search('','miami','',' ','','m.title ',' DESC') 