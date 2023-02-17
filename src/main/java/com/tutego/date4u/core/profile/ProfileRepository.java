package com.tutego.date4u.core.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.Tuple;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ProfileRepository
        extends JpaRepository<Profile, Long>,
        QuerydslPredicateExecutor<Profile> {

  @Query(nativeQuery = true, value = """
          SELECT YEAR(lastseen) AS y, MONTH(lastseen) AS m, COUNT(*) AS COUNT
          FROM profile
          WHERE lastseen > ?1 AND lastseen < ?2
          GROUP BY YEAR(lastseen), MONTH(lastseen)""")
  List<Tuple> findMonthlyProfileCount(LocalDate startDate, LocalDate endDate);

  @Query( """
	SELECT p
	FROM   Profile p
	WHERE  (p.attractedToGender=:myGender OR p.attractedToGender IS NULL)
   	AND (p.gender = :attractedToGender OR :attractedToGender IS NULL)
   	AND (p.manelength BETWEEN :minManelength AND :maxManelength)
   	AND (p.birthdate  BETWEEN :minBirthdate  AND :maxBirthdate)""" )
  List<Profile> search( byte myGender, Byte attractedToGender,
                        LocalDate minBirthdate, LocalDate maxBirthdate,
                        short minManelength, short maxManelength );

  List<Profile> findProfilesByManelengthGreaterThanEqual(short manelength);
}
