#p01_FirstName ----------------------------------------------------------------------------------------------------------------------------
SELECT `first_name`, `last_name` FROM employees
WHERE `first_name` LIKE 'Sa%'
ORDER BY employee_id;

#p02_LasttName----------------------------------------------------------------------------------------------------------------------------------
SELECT `first_name`, `last_name` FROM employees
WHERE `last_name` LIKE '%ei%'
ORDER BY employee_id;
#p03 -----------------------------------------------------------------------------------------------------------------------
Select `first_name` FROM employees
WHERE department_id IN (3, 10) AND
YEAR(`hire_date`) BETWEEN 1995 AND 2005
ORDER BY employee_id;

#p04_Except Engineer----------------------------------------------------------------------------------------------------------
Select `first_name`, `last_name` FROM employees
WHERE `job_title` NOT LIKE '%engineer%'
ORDER BY `employee_id`;
#p05_Find_towns_with_name_length-----------------------------------------------------------------------------------------------
Select `name` FROM towns
WHERE char_length(`name`) in (5,6)
ORDER BY `name`;
#06. Find Towns Starting With -=====================================================================================================
SELECT * FROM towns
WHERE `name` LIKE 'M%' OR `name` LIKE 'K%' OR `name` LIKE 'B%' OR `name` LIKE 'E%'
ORDER BY `name`;

#07. Find Towns Not Starting With ===================================================================================================
SELECT * FROM towns
WHERE `name` NOT LIKE 'R%' 
AND `name` NOT LIKE 'B%' 
AND `name` NOT LIKE 'D%'
ORDER BY `name`;

#08. Create View Employees Hired After ===============================================================================================
CREATE VIEW `v_employees_hired_after_2000` AS
	SELECT `first_name`, `last_name` FROM employees
    WHERE year(`hire_date`) > 2000;
SELECT * FROM v_employees_hired_after_2000;

#p09. Length of Last Name =============================================================================================================
SELECT `first_name`, `last_name` FROM `employees`
WHERE char_length(`last_name`) = 5;

#10. Countries Holding 'A' ================GEOGRAPHY================================================================
SELECT `country_name`, `iso_code` FROM countries
WHERE `country_name` LIKE '%a%a%a%'
ORDER BY `iso_code`;
    
#11. Mix of Peak and River Names =================================================================================================
SELECT peaks.`peak_name`, rivers.`river_name`,
concat( lower(substring(`peak_name`, 1, char_length(`peak_name`) -1)), lower(`river_name`)) as `mix` 
FROM `peaks`, `rivers`
WHERE Right(`peak_name`,1) = Left(`river_name`,1)
ORDER BY `mix`;
 
 #12. Games From 2011 and 2012 Year =========Diablo=================================
 SELECT `name`, 
 DATE_format(`start`, '%Y-%m-%d') AS 'start'
 FROM games
 WHERE year(`Start`) IN (2011, 2012)
 ORDER BY `start`, `name` LIMIT 50;

#13. User Email Providers ===========================================================================================
SELECT `user_name`, substring_index(`email`, '@', -1) AS `Email Provider` FROM users
ORDER BY `Email Provider` , `user_name`;

#14. Get Users with IP Address Like Pattern ===========================================================================
SELECT `user_name`, `ip_address` FROM users
WHERE `ip_address` LIKE '___.1%.%.___'
ORDER BY `user_name`;

#15. Show All Games with Duration =====================================================================================
SELECT `name` AS `game`,
CASE
WHEN HOUR(`start`) >= 0 and HOUR(`start`) < 12 THEN 'Morning'
WHEN HOUR(`start`) >= 12 and HOUR(`start`) < 18 THEN 'Afternoon'
WHEN HOUR(`start`) >= 18 and HOUR(`start`) < 24 THEN 'Evening'
END AS `Part of the Day`,
CASE
WHEN `duration` <= 3 THEN "Extra Short"
WHEN `duration` <= 6 THEN "Short"
WHEN `duration` <= 10 THEN "Long"
WHEN `duration` > 10 or `duration` is Null THEN "Extra Long"
END AS `Duration`
FROM games;

#16. Orders Table ================================================================================
SELECT  `product_name`, `order_date`,
DATE_ADD(`order_date`, INTERVAL 3 DAY) AS `pay_due`,
DATE_ADD(`order_date`, INTERVAL 1 MONTH) AS `deliver_due`
FROM `orders`;
