#01.	Employee Address =========================================================================================
SELECT e.employee_id, e.job_title, a.address_id, a.address_text 
FROM employees AS e
JOIN addresses AS a
ON e.address_id = a.address_id
ORDER BY a.address_id LIMIT 5;

#02.	Addresses with Towns ======================================================================================
SELECT e.first_name, e.last_name, t.name, a.address_text
 FROM employees AS e
 JOIN addresses AS a 
ON e.address_id = a.address_id
JOIN towns AS t
ON  a.town_id = t.town_id
ORDER BY e.first_name, e.last_name LIMIT 5;

#03.	Sales Employee ==============================================================================================
SELECT e.employee_id, e.first_name, e.last_name, d.name
FROM employees AS e
JOIN departments AS d
ON e.department_id = d.department_id
WHERE d.name LIKE "Sales"
ORDER BY e.employee_id DESC ;

#04.	Employee Departments ==============================================================================================
 SELECT e.employee_id, e.first_name, e.salary, d.name
 FROM employees AS e
 JOIN departments AS d
 ON e.department_id = d.department_id
 WHERE e.salary > 15000
 ORDER BY d.department_id DESC LIMIT 5;
 
#05.	Employees Without Project ==============================================================================================
SELECT e.employee_id, e.first_name
FROM employees AS e
LEFT JOIN employees_projects AS ep
ON e.employee_id = ep.employee_id
WHERE ep.employee_id IS NULL
ORDER BY e.employee_id DESC LIMIT 3; 

#06.	Employees Hired After ==============================================================================================
SELECT e.first_name, e.last_name, e.hire_date, d.name AS `dept_name`
 FROM employees AS e
 JOIN departments AS d
 ON e.department_id = d.department_id
 WHERE DATE(e.hire_date) > '1999-1-1' 
	AND  d.name IN ("Sales", "Finance")
ORDER BY e.hire_date;

#07.	Employees with Project ==============================================================================================
SELECT e.employee_id, e.first_name, p.name AS `project_name`
 FROM employees AS e
  JOIN employees_projects AS ep
 ON e.employee_id = ep.employee_id
 LEFT JOIN projects AS p
 ON ep.project_id = p.project_id
 WHERE p.end_date IS NULL AND DATE(p.start_date) > "2002-8-13"
 ORDER BY e.first_name, p.name LIMIT 5;

#08.	Employee 24 ==============================================================================================
SELECT  e.employee_id, e.first_name,
	CASE
		WHEN YEAR(p.start_date) >= 2005 
			THEN p.name = NULL
		ELSE p.name	
	END AS `project_name`
 FROM employees AS e
 JOIN employees_projects AS ep
	ON e.employee_id = ep.employee_id
 JOIN projects AS p
	ON ep.project_id = p.project_id
WHERE e.employee_id = 24
ORDER BY project_name;
 
#09.	Employee Manager ==============================================================================================
SELECT e.employee_id, e.first_name, e.manager_id, e2.first_name AS `manager_name`
FROM employees as e
JOIN employees AS e2
ON e.manager_id = e2.employee_id
WHERE e.manager_id IN (7, 3)
ORDER BY e.first_name;

#10.	Employee Summary ==============================================================================================
SELECT e.employee_id,
concat(e.first_name, " ", e.last_name) AS `employee_name`,
concat(e2.first_name, " ", e2.last_name) AS `manager_name`,
d.name AS `department_name`
FROM employees AS e
JOIN employees AS e2
ON e.manager_id = e2.employee_id
JOIN departments AS d
ON e.department_id = d.department_id
ORDER BY e.employee_id LIMIT 5;

#11.	Min Average Salary ==============================================================================================
SELECT AVG(salary) AS `min_average_salary` FROM employees
GROUP BY department_id
ORDER BY `min_average_salary` ASC LIMIT 1;

#12.	Highest Peaks in Bulgaria ==============================================================================================
SELECT mc.country_code, m.mountain_range, p.peak_name, p.elevation
	FROM mountains_countries  AS mc
 JOIN mountains AS m 
	ON mc.mountain_id = m.id
 JOIN peaks AS p
	ON m.id = p.mountain_id
 WHERE mc.country_code LIKE "BG" 
	AND p.elevation > 2835
 ORDER BY elevation DESC;

#13.	Count Mountain Ranges ==============================================================================================
SELECT `country_code_table`.country_code, COUNT(m.mountain_range) as `mountain_range`
FROM (SELECT c2.country_name, c2.country_code
			FROM countries AS c2 
		WHERE c2.country_name IN ("United States", "Russia", "Bulgaria")
        ) as `country_code_table`
JOIN mountains_countries as mc
	ON `country_code_table`.country_code = mc.country_code
 JOIN mountains AS m
	ON mc.mountain_id = m.id
GROUP BY `country_code_table`.country_code	
ORDER BY `mountain_range` DESC;

#14.	Countries with Rivers ==============================================================================================
SELECT c.country_name, r.river_name  FROM countries AS c
LEFT JOIN countries_rivers AS cr
	ON c.country_code = cr.country_code
LEFT JOIN rivers AS r
	ON cr.river_id = r.id
WHERE c.continent_code LIKE "AF"
ORDER BY c.country_name ASC LIMIT 5 ;

#15.	*Continents and Currencies ==============================================================================================
SELECT cu2.continent_code, cu2.currency_code , cu2.currency_usage
	FROM (SELECT continent_code, currency_code ,count(currency_code) AS 'currency_usage'
	        FROM countries
			GROUP BY continent_code, currency_code
			HAVING count(currency_code) > 1
           ) AS cu2
	JOIN
		(SELECT cu.continent_code, max(currency_usage) AS 'max_currency_usage'
			FROM
		(SELECT continent_code, currency_code ,count(currency_code) AS 'currency_usage'
			FROM countries
			GROUP BY continent_code, currency_code
			HAVING count(currency_code) > 1) AS cu
			GROUP BY cu.continent_code) AS max_cu
ON cu2.continent_code = max_cu.continent_code
AND cu2.currency_usage = max_cu.max_currency_usage
ORDER BY cu2.continent_code, cu2.currency_code;

#16.	Countries without any Mountains ==============================================================================================
SELECT COUNT(*) FROM countries AS c
LEFT JOIN mountains_countries AS mc
	ON c.country_code = mc.country_code
WHERE mc.mountain_id IS NULL;

#17.	Highest Peak and Longest River by Country ==============================================================================================
SELECT c.country_name, 
		MAX(p.elevation) AS `highest_peak_elevation`,
		MAX(r.length) AS `longest_river_length`
FROM countries AS c
JOIN mountains_countries AS mc
	ON c.country_code = mc.country_code
JOIN peaks as p
	ON  mc.mountain_id = p.mountain_id
JOIN countries_rivers as cr
	ON c.country_code = cr.country_code
JOIN rivers AS r
	ON cr.river_id = r.id
GROUP BY country_name
ORDER BY `highest_peak_elevation` DESC,
		`longest_river_length` DESC,
		c.country_name ASC LIMIT 5;
    










