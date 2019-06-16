#01. Records’ Count =========================================================================================================
SELECT count(*) FROM wizzard_deposits;

#02. Longest Magic Wand ====================================================================================================
SELECT MAX(`magic_wand_size`)FROM `wizzard_deposits`;

#03. Longest Magic Wand per Deposit Groups =================================================================================
SELECT `deposit_group` , MAX(`magic_wand_size`) AS `longes_magic_wand` FROM `wizzard_deposits`
GROUP BY `deposit_group`
ORDER BY MAX(`magic_wand_size`)  , `deposit_group` ASC;

#04. Smallest Deposit Group per Magic Wand Size ============================================================================
SELECT `deposit_group`  FROM `wizzard_deposits`
GROUP BY `deposit_group`
ORDER BY AVG(`magic_wand_size`) LIMIT 1;

#05. Deposits Sum ===========================================================================================================
SELECT `deposit_group`, SUM(deposit_amount) AS total_sum FROM wizzard_deposits
GROUP BY deposit_group
ORDER BY total_sum;

#06. Deposits Sum for Ollivander Family ======================================================================================
SELECT `deposit_group`, SUM(deposit_amount) AS total_sum FROM wizzard_deposits
WHERE magic_wand_creator LIKE 'Ollivander family'
GROUP BY deposit_group
ORDER BY total_sum;

#07. Deposits Filter =========================================================================================================
SELECT `deposit_group`, SUM(deposit_amount) AS total_sum FROM wizzard_deposits
where magic_wand_creator LIKE 'Ollivander family'
GROUP BY `deposit_group`
HAVING total_sum < 150000
ORDER BY total_sum DESC;

#08. Deposit Charge =================================================================================
SELECT `deposit_group`, `magic_wand_creator`, MIN(`deposit_charge`) AS min_deposit_charge FROM wizzard_deposits
GROUP BY `deposit_group`, `magic_wand_creator`
ORDER BY `magic_wand_creator`, `deposit_group`;

#09. Age Groups ====================================================================================================
SELECT
CASE 
	when `age` >= 0 and `age` <= 10 then '[0-10]'
	when `age` >= 11 and `age` <= 20 then '[11-20]'
    when `age` >= 21 and `age` <= 30 then '[21-30]'
    when `age` >= 31 and `age` <= 40 then '[31-40]'
    when `age` >= 41 and `age` <= 50 then '[41-50]'
    when `age` >= 51 and `age` <= 60 then '[51-60]'
    else '[61+]'
end as `age_group`,
count(`age`) as `wizard_count`
from `wizzard_deposits`
group by `age_group`
order by `age_group`;

#10. First Letter ==============================================================================\
SELECT substring(`first_name`, 1, 1) AS first_letter FROM wizzard_deposits
WHERE deposit_group LIKE "Troll chest"
GROUP BY first_letter
ORDER by first_letter;

#11. Average Interest =============================================================================
SELECT `deposit_group`,`is_deposit_expired`, AVG(`deposit_interest`) AS `average_interest`
FROM `wizzard_deposits`
WHERE `deposit_start_date` > '1985-01-01'
GROUP BY `deposit_group`, `is_deposit_expired`
ORDER BY `deposit_group`desc, `is_deposit_expired` ASC;

#12. Rich Wizard, Poor Wizard ===============================================================================
select sum(`diff`.`next_wizard`) as `sum_difference`
from (
	select `deposit_amount` - 
		(select `deposit_amount` 
        from `wizzard_deposits`
        where `id` = `wd`.`id` + 1) as `next_wizard`
	from `wizzard_deposits` as `wd`) as `diff`;
#13. Employees Minimum Salaries =============================================================================
SELECT `department_id`, MIN(`salary`)  FROM employees
WHERE `department_id` IN (2, 5, 7)
AND `hire_date` > '2000-01-01'
GROUP BY department_id
ORDER BY department_id;

#14. Employees Average Salaries ===================================================================
CREATE TABLE `eas`
SELECT `department_id`, salary FROM employees
WHERE salary > 30000 AND `manager_id` NOT IN (42);

UPDATE `eas`
SET salary = salary + 5000
WHERE department_id IN (1);

SELECT `department_id`, AVG(salary)as avg_salary FROM eas
GROUP BY department_id
ORDER BY department_id;

#15. Employees Maximum Salaries ==========================================================================
SELECT department_id, MAX(salary) as `max_salary` from employees
GROUP BY department_id
HAVING max_salary NOT BETWEEN 30000 and 70000
ORDER BY department_id;

#16. Employees Count Salaries ====================================================================================
SELECT COUNT(*) FROM employees
WHERE manager_id is NULL;

#17. 3rd Highest Salary=============================================================================
SELECT e1.department_id,
	(SELECT e2.salary FROM employees as e2
		WHERE e2.department_id = e1.department_id
        GROUP BY e2.salary
		ORDER BY e2.salary DESC LIMIT 1 OFFSET 2
    )as `third_highest_salary`
FROM employees as e1
GROUP BY e1.department_id
HAVING `third_highest_salary` IS NOT NULL
ORDER BY e1.department_id;

#18. Salary Challenge ===============================================================================
select `e1`.`first_name`, `e1`.`last_name`, `e1`.`department_id`
from `employees` as `e1`
where `e1`.`salary` > (
    select avg(`e2`.`salary`)
	from `employees` as `e2`
	where `e2`.`department_id` = `e1`.`department_id`
	group by `e2`.`department_id` 
) 

order by `e1`.`department_id`, `e1`.`employee_id`  limit 10;

#19.	Departments Total Salaries ===============================================================================
select `department_id`, sum(`salary`) as `total_salary`
from `employees`
group by `department_id`
order by `department_id`;


