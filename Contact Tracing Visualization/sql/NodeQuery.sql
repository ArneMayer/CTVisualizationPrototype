SELECT P.IDPERSON, P.IDCASE, P.FIRSTNAME, P.MIDDLENAME, P.LASTNAME, P.VISITCOUNT, LASTVISIT.IDLASTVISIT, COUNT(TRUESYMPTOM.SYMPTOM) AS SYMPTOMCOUNT FROM
	(
	SELECT P.IDPERSON, C.IDCASE, P.FIRSTNAME, P.MIDDLENAME, P.LASTNAME, (SELECT COUNT(V.IDVISIT) FROM SORMASN.VISIT AS V WHERE V.IDPERSON = P.IDPERSON) AS VISITCOUNT
	FROM SORMASN.PERSON AS P
	LEFT OUTER JOIN SORMASN."CASE" AS C
	ON P.IDPERSON = C.IDPERSON
	WHERE C.IDCASE != 0 OR EXISTS (SELECT CON.IDPERSON FROM SORMASN.CONTACT AS CON WHERE CON.IDPERSON = P.IDPERSON)
	) AS P
LEFT OUTER JOIN
	(
	SELECT P.IDPERSON, MAX(V.IDVISIT) AS IDLASTVISIT FROM SORMASN.PERSON AS P, SORMASN.VISIT AS V
	WHERE V.DATEOFVISIT = (SELECT MAX(VISIT.DATEOFVISIT) FROM SORMASN.VISIT WHERE VISIT.IDPERSON = P.IDPERSON)
	AND V.IDPERSON = P.IDPERSON
	GROUP BY P.IDPERSON
	) AS LASTVISIT
ON P.IDPERSON = LASTVISIT.IDPERSON
LEFT OUTER JOIN
	(
	SELECT S.SYMPTOM, S.IDVISIT FROM SORMASN.SYMPTOM AS S WHERE S.SYMPTOMRESULT = 'yes'  
	) AS TRUESYMPTOM
ON LASTVISIT.IDLASTVISIT = TRUESYMPTOM.IDVISIT
GROUP BY P.IDPERSON, P.IDCASE, P.FIRSTNAME, P.MIDDLENAME, P.LASTNAME, P.VISITCOUNT, LASTVISIT.IDLASTVISIT